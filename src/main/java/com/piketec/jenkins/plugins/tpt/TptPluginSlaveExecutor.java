/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 PikeTec GmbH
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.piketec.jenkins.plugins.tpt;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.Computer;
import hudson.slaves.SlaveComputer;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;

import com.michelin.cio.hudson.plugins.copytoslave.CopyToMasterNotifier;
import com.piketec.tpt.api.ApiException;
import com.piketec.tpt.api.ExecutionConfiguration;
import com.piketec.tpt.api.ExecutionConfigurationItem;
import com.piketec.tpt.api.ExecutionStatus;
import com.piketec.tpt.api.OpenResult;
import com.piketec.tpt.api.Scenario;
import com.piketec.tpt.api.ScenarioGroup;
import com.piketec.tpt.api.ScenarioOrGroup;
import com.piketec.tpt.api.TestSet;
import com.piketec.tpt.api.TptApi;

/**
 * Executes one test case via TPT API.
 * 
 * @author jkuhnert, PikeTec GmbH
 *
 */
public class TptPluginSlaveExecutor {

  private TptLogger logger;

  private Launcher launcher;

  private AbstractBuild< ? , ? > build;

  private BuildListener listener;

  private FilePath[] exePaths;

  private int tptPort;

  private String tptBindingName;

  private File tptFile;

  private String execCfg;

  private String testDataDir;

  private String reportDir;

  private String testcaseName;

  private long tptStartupWaitTime;

  public TptPluginSlaveExecutor(Launcher launcher, AbstractBuild< ? , ? > build,
                                BuildListener listener, FilePath[] exePaths, int tptPort,
                                String tptBindingName, File tptFile, String execCfg,
                                String testDataDir, String reportDir, String testcaseName,
                                long tptStartupWaitTime) {
    this.logger = new TptLogger(listener.getLogger());
    this.launcher = launcher;
    this.build = build;
    this.listener = listener;
    this.exePaths = exePaths;
    this.tptPort = tptPort;
    this.tptBindingName = tptBindingName;
    this.tptFile = tptFile;
    this.execCfg = execCfg;
    this.testDataDir = testDataDir;
    this.reportDir = reportDir;
    this.testcaseName = testcaseName;
    this.tptStartupWaitTime = tptStartupWaitTime;
  }

  public boolean execute() {
    logger = new TptLogger(listener.getLogger());
    try {
      // start tpt and recieve API
      TptApi api;
      try {
        api =
            Utils.getTptApi(build, launcher, logger, exePaths, tptPort, tptBindingName,
                tptStartupWaitTime);
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        return false;
      }
      if (api == null) {
        return false;
      }
      // open TPT File
      OpenResult openProject = api.openProject(tptFile);
      // search execution configuration by name
      Collection<ExecutionConfiguration> execConfigs =
          openProject.getProject().getExecutionConfigurations().getItems();
      ExecutionConfiguration config = null;
      for (ExecutionConfiguration elem : execConfigs) {
        if (elem.getName().equals(execCfg)) {
          config = elem;
          break;
        }
      }
      if (config == null) {
        logger.error("Could not find config");
        return false;
      }
      // adjust config to execute only the given one test case
      File oldReportDir = config.getReportDir();
      File oldTestDataDir = config.getDataDir();

      Scenario foundSceneario =
          find(openProject.getProject().getTopLevelTestlet().getTopLevelScenarioOrGroup()
              .getItems(), testcaseName);
      if (foundSceneario == null) {
        logger.error("Could not find testcase " + testcaseName);
        return false;
      }

      FilePath path = null;
      try {
        path = new FilePath(build.getWorkspace(), testDataDir).absolutize();
        if (Computer.currentComputer() instanceof SlaveComputer) {
          logger.info("Creating and/or cleaning test data directory");
          path.mkdirs();
          path.deleteContents();
        }
      } catch (IOException e) {
        logger.error("Could not create test data dir");
        return false;
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        return false;
      }
      logger.info("Setting test data directory to " + path.getRemote());
      config.setDataDir(new File(path.getRemote()));

      path = null;
      try {
        path = new FilePath(build.getWorkspace(), reportDir).absolutize();
        if (Computer.currentComputer() instanceof SlaveComputer) {
          logger.info("Creating and/or cleaning report directory");
          path.mkdirs();
          path.deleteContents();
        }
      } catch (IOException e) {
        logger.error(e.getMessage());
        config.setDataDir(oldTestDataDir);
        return false;
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        config.setDataDir(oldTestDataDir);
        return false;
      }
      logger.info("Setting report directory to " + path.getRemote());
      config.setReportDir(new File(path.getRemote()));

      String tmpTestSezName = "JENKINS Exec";
      logger.info("Create test set \"" + tmpTestSezName + "\" for execution of \"" + testcaseName
          + "\"");
      TestSet testSet = openProject.getProject().createTestSet(tmpTestSezName);
      testSet.addTestCase(foundSceneario);

      ArrayList<TestSet> oldTestSets = new ArrayList<TestSet>();
      for (ExecutionConfigurationItem item : config.getItems()) {
        // TODO_jkuhnert (until_27.01.2016): Testset.contains Scenario?
        oldTestSets.add(item.getTestSet());
        item.setTestSet(testSet);
      }
      // execute test
      ExecutionStatus execStatus = api.run(config);
      while (execStatus.isRunning() || execStatus.isPending()) {
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          logger.interrupt(e.getMessage());
          execStatus.cancel();
          break;
        }
      }
      // undo changes
      for (ExecutionConfigurationItem item : config.getItems()) {
        item.setTestSet(oldTestSets.remove(0));
      }
      try {
        String includes =
            !StringUtils.isBlank(testDataDir) ? testDataDir : new File(tptFile.getParent(),
                oldTestDataDir.getPath()).getAbsolutePath();
        includes += "\\**\\*.*";
        if (!StringUtils.isBlank(reportDir) || StringUtils.isBlank(oldReportDir.getPath())) {
          includes +=
              ","
                  + (!StringUtils.isBlank(reportDir) ? reportDir : new File(tptFile.getParent(),
                      oldReportDir.getPath()).getAbsolutePath());
          includes += "\\**\\*.*";
        }
        CopyToMasterNotifier copyToMaster =
            new CopyToMasterNotifier(includes, "", false, "", false);
        copyToMaster.perform(build, launcher, listener);
      } catch (InterruptedException e) {
        logger.interrupt(e.getMessage());
        return false;
      } catch (IOException e) {
        logger.error("could not copy results to master: " + e.getMessage());
      }
      logger.info("reset test data and report directory to " + oldTestDataDir.getPath() + " and "
          + oldReportDir.getPath());
      config.setDataDir(oldTestDataDir);
      config.setReportDir(oldReportDir);
      logger.info("delete temporary test set \"" + testSet.getName() + "\"");
      openProject.getProject().getTestSets().delete(testSet);
    } catch (RemoteException e) {
      logger.error(e.getLocalizedMessage());
      e.printStackTrace(logger.getLogger());
      return false;
    } catch (ApiException e) {
      logger.error(e.getLocalizedMessage());
      e.printStackTrace(logger.getLogger());
      return false;
    }
    return true;
  }

  private Scenario find(Collection<ScenarioOrGroup> sogs, String name) throws RemoteException,
      ApiException {
    for (ScenarioOrGroup sog : sogs) {
      if (sog instanceof Scenario) {
        if (sog.getName().equals(name)) {
          return (Scenario)sog;
        }
      } else {
        Scenario result = find(((ScenarioGroup)sog).getItems(), name);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
  }

}