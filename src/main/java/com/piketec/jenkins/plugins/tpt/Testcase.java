/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2018 Synopsys Inc.
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

import java.util.Date;
import java.util.List;

import com.piketec.jenkins.plugins.tpt.TptLog.LogEntry;
import com.piketec.jenkins.plugins.tpt.TptLog.LogLevel;

/**
 * The result of a parsed TPT test case execution result
 * 
 * @author jkuhnert, Synopsys Inc.
 */
public class Testcase {

  private String executionConfigName;

  private String platformName;

  private String name;

  private int id;

  private TptResult result = TptResult.INCONCLUSIVE;

  private Date execDate;

  private String execDuration;

  private TptLog log;

  Testcase() {
    log = new TptLog();
    execDuration = "0";
    result = null;
    executionConfigName = "";
    platformName = "";
    name = "";
    id = 0;
  }

  /**
   * @return The name of the execution config the test was executed with
   */
  public String getExecutionConfigName() {
    return executionConfigName;
  }

  /**
   * @param executionConfigName
   *          set the name of the execution config the test was executed with
   */
  public void setExecutionConfigName(String executionConfigName) {
    this.executionConfigName = executionConfigName;
  }

  /**
   * @return The name of the platform the test was executed with. When a platform was used multiple
   *         times in an execution config the index is appended.
   */
  public String getPlatformName() {
    return platformName;
  }

  /**
   * @param platformName
   *          Set the name of the platform the test was executed with. When a platform was used
   *          multiple times in an execution config the index is appended.
   */
  public void setPlatformName(String platformName) {
    this.platformName = platformName;
  }

  /**
   * @return name get the name of the testcase
   * 
   */
  String getName() {
    return name;
  }

  /**
   * @param name
   *          set the name of the testcase while parsing
   * 
   */
  void setName(String name) {
    this.name = name;
  }

  /**
   * @return id get the id of the testcase
   * 
   */
  int getID() {
    return id;
  }

  /**
   * @param id
   *          set the id of the testcase while parsing
   * 
   */
  void setID(int id) {
    this.id = id;
  }

  /**
   * @param date
   *          set the date of the testcase while parsing
   * 
   */
  void setExecDate(Date execDate) {
    this.execDate = execDate;
  }

  /**
   * @return date get the date of execution from the testcase
   */
  Date getExecDate() {
    return execDate;
  }

  /**
   * @param time
   *          set the execution duration from a test case
   */
  void setExecDuration(String time) {
    this.execDuration = time;
  }

  /**
   * @return get the execution duration from a test case
   */
  String getExecDuration() {
    return execDuration;
  }

  /**
   * @param result
   *          set the result from a test case
   */
  void setResult(TptResult result) {
    this.result = result;
  }

  /**
   * @return get the result from a test case
   */
  TptResult getResult() {
    return result;
  }

  void addLogEntry(String entry, LogLevel level) {
    log.log(level, entry);
  }

  List<LogEntry> getLogEntries(LogLevel level) {
    return log.getLog(level);
  }

  /**
   * @return the testcase name concatinated with the id
   */
  String getQualifiedName() {
    return name + "_" + id;
  }
}
