/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2025 Synopsys Inc.
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
package com.piketec.tpt.api;

import java.io.File;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import com.piketec.tpt.api.constants.assessments.GlobalEquivalenceClasses;
import com.piketec.tpt.api.constants.assessments.GlobalScript;
import com.piketec.tpt.api.constants.assessments.GlobalVariable;
import com.piketec.tpt.api.util.DeprecatedAndRemovedException;

/**
 * This object represents all settings for a particular execution configuration.
 * <p>
 * An <code>ExecutionConfiguration</code> can be only created via
 * {@link Project#createExecutionConfiguration(String)}. The configuration consists of a number of
 * attributes as well as a list of {@link ExecutionConfigurationItem}. The attributes represent the
 * controls from the execution configuration GUI.
 * </p>
 * For a detailed description of the attributes please refer to the User Guide.
 * 
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface ExecutionConfiguration extends ExecutionConfigurationOrGroup,
    AccessList<ExecutionConfigurationItem>, PlatformOrExecutionItemEnabler {

  /**
   * Enumeration representing the possible output formats of the report.
   * 
   * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
   */
  public enum ReportFormat {
    /**
     * HTML
     */
    Html,
    /**
     * HTML with embedded resources
     */
    HtmlAllInOne,
    /**
     * PDF
     */
    Pdf,
    /**
     * PDF with overview and test reports in one big document
     */
    AllInOnePdf
  }

  /**
   * Enumeration representing the possible reference modes.
   * 
   * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
   */
  public enum ReferenceMode {
    /**
     * The given reference directory path points to a execution directory containing platform
     * directories which contain the test case folders.
     */
    EXECUTION_DIR,
    /**
     * The given reference directory path points to a platform directory containing the test case
     * folders.
     */
    PLATFORM_DIR
  }

  /**
   * Enumeration representing the different directory structure configurations.
   * 
   * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
   */
  public enum DataDirStructure {
    /**
     * Hierarchical layout with indexes (based on test case tree)
     */
    HIERARCHICAL_WITH_INDEX,
    /**
     * Hierarchical layout with unique IDs (based on test case tree)
     */
    HIERARCHICAL_WITH_ID,
    /**
     * Flat layout with indexes (no hierarchy)
     */
    FLAT_WITH_INDEX,
    /**
     * Flat layout with unique IDs (no hierarchy)
     */
    FLAT_WITH_ID,
    /**
     * Flat layout with unique IDs (no hierarchy and no test case names)
     */
    FLAT_ONLY_ID,
    /**
     * Flat layout with indexes and unique IDs (no hierarchy and no test case names)
     */
    FLAT_WITH_INDEX_AND_ID
  }

  /**
   * Creates a new {@link ExecutionConfigurationItem} and adds it to the tail of the
   * ExecutionConfiguration list. This list is represented by the table in the GUI.
   * 
   * 
   * @return A fresh ExecutionConfigurationItem
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public ExecutionConfigurationItem createExecutionConfigurationItem() throws RemoteException;

  /**
   * @return The data directory.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated No support for $-variables and relative paths - use {@link #getDataDirPath()}
   *             instead. Removed in TPT-19. Throws {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public File getDataDir() throws RemoteException;

  /**
   * @return Returns the test data directory as {@link String}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getDataDirPath() throws RemoteException;

  /**
   * @return Returns the advanced report settings.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public AdvancedReportSettings getAdvancedReportSettings() throws RemoteException;

  /**
   * @return The report directory.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated No support for $-variables and relative paths - use {@link #getReportDirPath()}
   *             instead. Removed in TPT-19. Throws {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public File getReportDir() throws RemoteException;

  /**
   * @return Returns the report directory.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getReportDirPath() throws RemoteException;

  /**
   * @return Returns the reference directory.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public String getReferenceDirPath() throws RemoteException;

  /**
   * Set the data directory.
   * 
   * @param f
   *          The Data directory as File.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated No support for $-variables and relative paths - use {@link #setDataDirPath(String)}
   *             instead. Removed in TPT-19. Throws {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public void setDataDir(File f) throws RemoteException;

  /**
   * Set the data directory.
   * 
   * @param path
   *          A directory as String.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setDataDirPath(String path) throws RemoteException;

  /**
   * Set the report directory.
   * <p>
   * Optionally, specify the report directory as {@link File}. Use <code>null</code> to set the data
   * directory.
   * </p>
   * 
   * @param f
   *          The report directory as <code>File</code> or <code>null</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated No support for $-variables and relative paths - use
   *             {@link #setReportDirPath(String)} instead. Removed in TPT-19. Throws
   *             {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public void setReportDir(File f) throws RemoteException;

  /**
   * Set the reference directory.
   * 
   * @param path
   *          A directory as String.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setReferenceDirPath(String path) throws RemoteException;

  /**
   * Set the report directory.
   * <p>
   * Optionally, specify the report directory as {@link String}. Use <code>null</code> to set the
   * data directory.
   * </p>
   * 
   * @param path
   *          The report directory as <code>String</code> or <code>null</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setReportDirPath(String path) throws RemoteException;

  /**
   * @return Returns <code>true</code> if test should be executed. Represents the "Execute" check
   *         box.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isRunExec() throws RemoteException;

  /**
   * @return Returns <code>true</code> if the assessments should be executed. Represents the
   *         "Assess" check box.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isRunAssess() throws RemoteException;

  /**
   * 
   * @return Returns <code>true</code> if a report should be generated. Represents the "Report"
   *         check box.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isRunReport() throws RemoteException;

  /**
   * @return Returns <code>true</code> if the dashboard should be enabled during test execution.
   *         Represents the "Dashboard" check box.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public boolean isRunDashboard() throws RemoteException;

  /**
   * @param enabled
   *          Set whether tests should be executed.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setRunExec(boolean enabled) throws RemoteException;

  /**
   * @param enabled
   *          Set whether assessments should be executed.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setRunAssess(boolean enabled) throws RemoteException;

  /**
   * 
   * @param enabled
   *          Set whether a report should be generated.
   *          <p>
   *          Note that a report can be only generated if assessments have been executed.
   *          </p>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setRunReport(boolean enabled) throws RemoteException;

  /**
   * @param enabled
   *          Enable the dashboard during execution.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public void setRunDashboard(boolean enabled) throws RemoteException;

  /**
   * Returns the additional attributes for the execution configuration as specified by the user.
   * This map corresponds to the "Attributes" tab of the execution configuration GUI.
   * 
   * @return User attributes as map
   * 
   * @throws RemoteException
   *           remote communication problem
   * @deprecated Removed in TPT-19. Throws {@link DeprecatedAndRemovedException}. Please use
   *             {@link #getAttributesList()} instead
   */
  @Deprecated
  public Map<String, String> getAttributes() throws RemoteException;

  /**
   * Set a user-defined attribute given by <code>key</code> to the value given by the
   * <code>value</code> parameter.
   * 
   * If <code>value==null</code>, the attribute <code>key</code> will be deleted.
   * 
   * @param key
   *          The name of the attribute to be set.
   * @param value
   *          The new value or <code>null</code> to delete the attribute.
   * 
   * @throws ApiException
   *           if <code>key==null</code>
   * @throws RemoteException
   *           remote communication problem
   * @deprecated Please use {@link #setAttributesList(List)} instead. Removed in TPT-19. Throws
   *             {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public void setAttributes(String key, String value) throws ApiException, RemoteException;

  /**
   * Returns the additional attributes for the execution configuration as specified by the user.
   * This list corresponds to the "Attributes" tab of the execution configuration GUI.
   * 
   * @return User attributes as list.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public List<Pair<String, String>> getAttributesList() throws RemoteException;

  /**
   * Set the list of user-defined attributes given by <code>pair.first</code> to the value given by
   * the <code>pair.second</code> parameter.
   * 
   * @param attributes
   *          user-defined attribute list
   * 
   * @throws ApiException
   *           if
   *           <code>attributes==null || pair.second == null || pair.first==null || pair.first.contains("<span>&#92;</span>n") </code>
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAttributesList(List<Pair<String, String>> attributes)
      throws ApiException, RemoteException;

  /**
   * 
   * @return The currently selected report format
   * 
   * @throws ApiException
   *           If the currently selected report format is unknown.
   * @throws RemoteException
   *           remote communication problem
   */
  public ReportFormat getReportFormat() throws ApiException, RemoteException;

  /**
   * Set the report format.
   * 
   * @param rf
   *          the new report format.
   * 
   * @throws ApiException
   *           If the given report format is not known to the API.
   * @throws RemoteException
   *           remote communication problem
   */
  public void setReportFormat(ReportFormat rf) throws ApiException, RemoteException;

  /**
   * 
   * @return The currently selected reference mode.
   * 
   * @throws ApiException
   *           If the currently selected report format is unknown.
   * @throws RemoteException
   *           remote communication problem
   */
  public ReferenceMode getReferenceDirMode() throws ApiException, RemoteException;

  /**
   * Set the currently selected reference mode.
   * 
   * @param mode
   *          the new reference mode.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the given report format is not known to the API.
   */
  public void setReferenceDirMode(ReferenceMode mode) throws ApiException, RemoteException;

  /**
   * @return the currently selected directory structure for saving the test data.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the directory structure is unknown
   */
  public DataDirStructure getDataDirStructure() throws ApiException, RemoteException;

  /**
   * Set the directory structure for the data directory.
   * 
   * @param dds
   *          The new directory structure.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the given directory structure is unknown to the API.
   */
  public void setDataDirStructure(DataDirStructure dds) throws ApiException, RemoteException;

  /**
   * Get the Python script that will be executed at the start of the execution.
   *
   * @return The Python script.
   * @throws RemoteException
   *           remote communication problem
   */
  public String getBeforeExecutionStartupScript() throws RemoteException;

  /**
   * Set the Python script that will be executed at the start of the execution.
   *
   * @param script
   *          The Python script. <code>Null</code> will be replaced by an empty String
   * @throws RemoteException
   *           remote communication problem
   */
  public void setBeforeExecutionStartupScript(String script) throws RemoteException;

  /**
   * Get the Python script that will be executed at the end of the execution.
   *
   * @return The Python script.
   * @throws RemoteException
   *           remote communication problem
   */
  public String getAfterExecutionShutdownScript() throws RemoteException;

  /**
   * Set the Python script that will be executed at the start of the execution.
   *
   * @param script
   *          The Python script. <code>Null</code> will be replaced by an empty String
   * @throws RemoteException
   *           remote communication problem
   */
  public void setAfterExecutionShutdownScript(String script) throws RemoteException;

  /**
   * 
   * @return true, if the global assessment is enabled and false if not.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Use assesslets
   *             {@link GlobalVariable}, {@link GlobalScript} or {@link GlobalEquivalenceClasses}.
   */
  @Deprecated
  public boolean isGlobalAssessmentEnabled() throws RemoteException;

  /**
   * 
   * @param enabled
   *          or disable the execution of the global assessment.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Use assesslets
   *             {@link GlobalVariable}, {@link GlobalScript} or {@link GlobalEquivalenceClasses}.
   */
  @Deprecated
  public void setGlobalAssessmentEnabled(boolean enabled) throws RemoteException;

  /**
   * 
   * @return the script from the global assessment.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Use assesslets
   *             {@link GlobalVariable}, {@link GlobalScript} or {@link GlobalEquivalenceClasses}.
   */
  @Deprecated
  public String getGlobalAssessmentScript() throws RemoteException;

  /**
   * 
   * @param script
   *          that shall be executed during the global assessment.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Use assesslets
   *             {@link GlobalVariable}, {@link GlobalScript} or {@link GlobalEquivalenceClasses}.
   */
  @Deprecated
  public void setGlobalAssessmentScript(String script) throws RemoteException;

  /**
   * @return the list of global assessment rows
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Use assesslets
   *             {@link GlobalVariable}, {@link GlobalScript} or {@link GlobalEquivalenceClasses}.
   */
  @Deprecated
  public RemoteList<Object> getGlobalAssessmentRows() throws RemoteException;

  /**
   * Creates a new GlobalAssessmentRow and adds it to the tail of the GlobalAssessmentRows list.
   * This list is represented by the table in the GUI.
   * 
   * 
   * @return A fresh GlobalAssessmentRow
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Removed in TPT-20. Throws {@link DeprecatedAndRemovedException}. Use assesslets
   *             {@link GlobalVariable}, {@link GlobalScript} or {@link GlobalEquivalenceClasses}.
   */
  @Deprecated
  public Object createGlobalAssessmentRow() throws RemoteException;

  /**
   * returns the list of all execution config items of this {@link ExecutionConfiguration}
   * 
   * @return list of all execution config items
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<ExecutionConfigurationItem> getExecutionConfigurationItems()
      throws RemoteException;

  /**
   * Set the number of cores used during execution. Contrary to the TPT UI you can set more cores
   * than actually available on the current machine. At test execution at most actually available
   * cores will be used.
   * 
   * @param cores
   *          the maximum number of cores to use.
   * @throws ApiException
   *           if cores is less than 1
   * @throws RemoteException
   *           remote communication problem
   */
  public void setCores(int cores) throws ApiException, RemoteException;

  /**
   * Get the maximal number of cores used during test execution. That number may be greater than
   * actually available cores on the current machine.
   * 
   * @return the maximal number of cores used during test execution.
   * @throws RemoteException
   *           remote communication problem
   */
  public int getCores() throws RemoteException;

  /**
   * @return Returns <code>null</code> if "Pack report" is not enabled. Otherwise, the target file
   *         for the ZIP is returned.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated No support for $-variables and relative paths - use
   *             {@link AdvancedReportSettings#getCompressionPath()} instead. Removed in TPT-19.
   *             Throws {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public File getReportPackFile() throws RemoteException;

  /**
   * Set the ZIP file where the packed report should be stored. Using <code>zipFile==null</code>
   * will disable the "Pack report" option.
   *
   * @param zipFile
   *          target file or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated No support for $-variables and relative paths - use
   *             {@link AdvancedReportSettings#setCompressionPath(String)} instead. Removed in
   *             TPT-19. Throws {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public void setReportPackFile(File zipFile) throws RemoteException;

  /**
   * @return Returns <code>null</code> if "Pack report" is not enabled. Otherwise, the target file
   *         for the ZIP is returned.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Use {@link AdvancedReportSettings#getCompressionPath()} instead. Removed in TPT-19.
   *             Throws {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public String getReportPackPath() throws RemoteException;

  /**
   * Set the ZIP file where the packed report should be stored. Using <code>zipFile==null</code>
   * will disable the "Pack report" option.
   *
   * @param zipFile
   *          target file or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Use {@link AdvancedReportSettings#setCompressionPath(String)} instead. Removed in
   *             TPT-19. Throws {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public void setReportPackPath(String zipFile) throws RemoteException;

  /**
   * @return Returns <code>true</code> if the report directory should be deleted after it has been
   *         zipped by "Pack report".
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Use {@link AdvancedReportSettings#isDeleteReportDirAfterCompression()} instead.
   *             Removed in TPT-19. Throws {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public boolean isDeleteReportDirAfterPack() throws RemoteException;

  /**
   * @param enable
   *          Enable or disable the automatic removal of the report directory if "Pack report" is
   *          enabled and after the report has been zipped.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Use {@link AdvancedReportSettings#setDeleteReportDirAfterCompression(boolean)}
   *             instead. Removed in TPT-19. Throws {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  public void setDeleteReportDirAfterPack(boolean enable) throws RemoteException;

  /**
   * Sets the number of times each test case should be repeated.
   * 
   * @param repetitions
   *          the number of repetitions which should be done per test case
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           not allowed number of repetitions
   */
  public void setTestRepetitions(int repetitions) throws RemoteException, ApiException;

  /**
   * Returns the number of times each test case is repeated.
   * 
   * @return number of repetitions
   * @throws RemoteException
   *           remote communication problem
   */
  public int getTestRepetitions() throws RemoteException;

}
