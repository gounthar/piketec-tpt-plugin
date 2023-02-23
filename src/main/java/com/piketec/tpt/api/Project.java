/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2014-2022 PikeTec GmbH
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
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import com.piketec.tpt.api.Requirement.RequirementType;
import com.piketec.tpt.api.importinterface.ImportInterfaceSettings;
import com.piketec.tpt.api.requirements.RequirementsExportSettings;
import com.piketec.tpt.api.requirements.RequirementsImportSettings;
import com.piketec.tpt.api.requirements.TestCasesExportSettings;
import com.piketec.tpt.api.requirements.TestCasesImportSettings;
import com.piketec.tpt.api.requirements.codebeamer.CodeBeamerRequirementsImportSettings;
import com.piketec.tpt.api.requirements.codebeamer.CodeBeamerTestCasesExportSettings;
import com.piketec.tpt.api.requirements.codebeamer.CodeBeamerTestCasesImportSettings;
import com.piketec.tpt.api.requirements.csv.CsvFileRequirementsExportSettings;
import com.piketec.tpt.api.requirements.csv.CsvFileRequirementsImportSettings;
import com.piketec.tpt.api.requirements.csv.CsvFileTestCasesExportSettings;
import com.piketec.tpt.api.requirements.csv.CsvFileTestCasesImportSettings;
import com.piketec.tpt.api.requirements.excel.ExcelFileRequirementsExportSettings;
import com.piketec.tpt.api.requirements.excel.ExcelFileRequirementsImportSettings;
import com.piketec.tpt.api.requirements.excel.ExcelFileTestCasesExportSettings;
import com.piketec.tpt.api.requirements.excel.ExcelFileTestCasesImportSettings;
import com.piketec.tpt.api.requirements.polarion.PolarionRequirementsImportSettings;
import com.piketec.tpt.api.requirements.polarion.PolarionTestCasesExportSettings;
import com.piketec.tpt.api.requirements.polarion.PolarionTestCasesImportSettings;
import com.piketec.tpt.api.requirements.reqif.ReqIfRequirementsImportSettings;
import com.piketec.tpt.api.requirements.reqif.ReqIfTestCasesExportSettings;
import com.piketec.tpt.api.steplist.formalrequirements.FormalRequirementDefine;

/**
 * This object represents a TPT project. It has been either newly created with
 * {@link TptApi#newProject(File)} or opened via {@link TptApi#openProject(File)}.
 * 
 *
 * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
 */
public interface Project extends AssessmentOwner, ExecutionConfigurationOwner, TestSetOwner {

  /**
   * Modes how to match existing declarations and imported declarations.
   * 
   * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
   *
   */
  public static enum SynchronizationMethod {
    /**
     * Match declarations by their TPT name.
     */
    NAME,
    /**
     * Match declarations by their external name of a given rename mapping.
     */
    EXTERNAL_NAME;
  }

  /**
   * Modes that determine which types of requirement links are included in calculations.
   * 
   * @author Copyright (c) 2014-2022 Piketec GmbH - MIT License (MIT) - All rights reserved
   *
   */
  public static enum RequirementsLinking {
    /**
     * Only links to test cases are considered for calculations.
     */
    TEST_CASES_ONLY,
    /**
     * Only links to assesslets are considered for calculations.
     */
    ASSESSLETS_ONLY,
    /**
     * All kinds of links, i.e. test cases, variants and assesslets are considered for calculations.
     */
    ALL_KINDS;
  }

  /**
   * Closes the project represented by this object in the TPT GUI.
   * <p>
   * It discards all changes since the last time the project has been saved. Closed projects cannot
   * be saved anymore.
   * </p>
   * 
   * @return <code>false</code> if the Project could not be closed.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           The project is not open/unknown
   */
  boolean closeProject() throws ApiException, RemoteException;

  /**
   * Saves the project in its assigned file. The file has been specified in one of the following
   * functions:
   * <p>
   * {@link Project#saveAsProject(File)}
   * </p>
   * <p>
   * {@link TptApi#newProject(File)}
   * </p>
   * <p>
   * {@link TptApi#openProject(File)}
   * </p>
   * 
   * @return A list of messages that have occurred while saving the project.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the project is not opened or if the file cannot be written.
   */
  List<String> saveProject() throws ApiException, RemoteException;

  /**
   * 
   * Saves the project in the given file. The file can be changed by this method. This function
   * basically represents the "Save as..." menu item.
   * <p>
   * The file extension defines the save format for the file (*.tpt, *.tptz, *.tptprj)
   * </p>
   * 
   * @param file
   *          the file to save the project to
   * 
   * @see Project#saveProject()
   * @see TptApi#newProject(File)
   * @see TptApi#openProject(File)
   *
   * @return A list of messages that have occurred during the save operation.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the project is not open or it the project cannot be written to the given file,
   */
  List<String> saveAsProject(File file) throws ApiException, RemoteException;

  /**
   * 
   * Saves the project in the file at the given path. The file can be changed by this method. This
   * function basically represents the "Save as..." menu item.
   * <p>
   * The file extension defines the save format for the file (*.tpt, *.tptz, *.tptprj)
   * </p>
   * 
   * @param file
   *          the path to the file to save the project to
   * 
   * @see Project#saveProject()
   * @see TptApi#newProject(File)
   * @see TptApi#openProject(File)
   *
   * @return A list of messages that have occurred during the save operation.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the project is not open or it the project cannot be written to the given file,
   */
  List<String> saveAsProjectByPath(String file) throws ApiException, RemoteException;

  /**
   * 
   * Returns the {@link File} that is associated with this TPT project. If no file has been
   * specified so far, it returns <code>null</code>.
   * 
   * @return A TPT file or <code>null</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  File getFile() throws RemoteException;

  /**
   * 
   * Returns the path to the file that is associated with this TPT project. If no file has been
   * specified so far, it returns <code>null</code>.
   * 
   * @return A path to a TPT file or <code>null</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getFilePath() throws RemoteException;

  /**
   * @return Returns the list of all {@link ExecutionConfiguration ExecutionConfigurations} and
   *         {@link ExecutionConfigurationGroup ExecutionConfigurationGroups} defined at the top
   *         level of this project.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteList<ExecutionConfigurationOrGroup> getTopLevelExecutionConfigurations()
      throws RemoteException;

  /**
   * @return Returns the set of all {@link ExecutionConfiguration ExecutionConfigurations} defined
   *         in this project i.e. a flat list of the execution configuration tree containing
   *         {@link ExecutionConfiguration ExecutionConfigurations} only. The returned
   *         RemoteCollection is a virtual collection. Changes to the returned RemoteCollection will
   *         change the TPT model but changes in the TPT model will not be reflected here.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #getTopLevelExecutionConfigurations()
   */
  RemoteCollection<ExecutionConfiguration> getExecutionConfigurations() throws RemoteException;

  /**
   * Delivers the first execution configuration or group with the given name or <code>null</code> if
   * no such execution configuration or group exists.
   * 
   * @param name
   *          The name of the <code>ExecutionConfigurationOrGroup</code>.
   * @return The {@link ExecutionConfigurationOrGroup} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  ExecutionConfigurationOrGroup getExecutionConfigurationOrGroupByName(String name)
      throws RemoteException;

  /**
   * Delivers all execution configurations and/or groups, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * @return Collection of all {@link ExecutionConfigurationOrGroup ExecutionConfigurationOrGroups},
   *         matching the given name pattern.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<ExecutionConfigurationOrGroup> getExecutionConfigurationOrGroupByNamePattern(Pattern namepattern)
      throws RemoteException;

  /**
   * Delivers all execution configurations and/or groups, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * @return Collection of all {@link ExecutionConfigurationOrGroup ExecutionConfigurationOrGroups},
   *         matching the given name pattern.
   * 
   * @throws PatternSyntaxException
   *           If the expression's syntax is invalid
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<ExecutionConfigurationOrGroup> getExecutionConfigurationOrGroupByNamePattern(String namepattern)
      throws PatternSyntaxException, RemoteException;

  /**
   * @return Returns the set of all {@link TestSet TestSets} defined in this project. Changes to the
   *         returned RemoteCollection will change the TPT model but changes in the TPT model will
   *         not be reflected here.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #getTopLevelTestSets()
   */
  RemoteCollection<TestSet> getTestSets() throws RemoteException;

  /**
   * Delivers the first test set or test set group with the given name or <code>null</code> if no
   * such test set or test set group exists.
   * 
   * @param name
   *          The name of the <code>TestSet</code>.
   * @return The {@link TestSet TestSets}/{@link TestSetGroup TestSetGroups} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  TestSetOrGroup getTestSetOrGroupByName(String name) throws RemoteException;

  /**
   * Delivers all test sets and test set groups, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * 
   * @return Collection of all {@link TestSet TestSets} and {@link TestSetGroup TestSetGroups},
   *         matching the given name pattern.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<TestSetOrGroup> getTestSetOrGroupByNamePattern(Pattern namepattern)
      throws RemoteException;

  /**
   * Delivers all test sets and test set groups, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * 
   * @return Collection of all {@link TestSet TestSets} and {@link TestSetGroup TestSetGroups},
   *         matching the given name pattern.
   * 
   * @throws PatternSyntaxException
   *           If the expression's syntax is invalid
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<TestSetOrGroup> getTestSetOrGroupByNamePattern(String namepattern)
      throws RemoteException, PatternSyntaxException;

  /**
   * @return Returns the list of all {@link TestSet TestSets} and {@link TestSetGroup TestSetGroups}
   *         defined at the top level of this project.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteList<TestSetOrGroup> getTopLevelTestSets() throws RemoteException;

  /**
   * @return Returns the list of all {@link PlatformConfiguration PlatformConfigurations} defined
   *         for this project.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteList<PlatformConfiguration> getPlatformConfigurations() throws RemoteException;

  /**
   * Delivers the first platform configuration with the given name or <code>null</code> if no such
   * platform configuration exists.
   * 
   * @param name
   *          The name of the <code>PlatformConfiguration</code>.
   * @return The {@link PlatformConfiguration} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  PlatformConfiguration getPlatformConfigurationByName(String name) throws RemoteException;

  /**
   * Delivers all Platform configurations, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * @return Collection of all {@link PlatformConfiguration PlatformConfigurations}, matching the
   *         given name pattern.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<PlatformConfiguration> getPlatformConfigurationByNamePattern(Pattern namepattern)
      throws RemoteException;

  /**
   * Delivers all Platform configurations, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * @return Collection of all {@link PlatformConfiguration PlatformConfigurations}, matching the
   *         given name pattern.
   * 
   * @throws PatternSyntaxException
   *           If the expression's syntax is invalid
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<PlatformConfiguration> getPlatformConfigurationByNamePattern(String namepattern)
      throws PatternSyntaxException, RemoteException;

  /**
   * Returns the list all top level <code>Assessments</code> and <code>AssessmentGroups</code> of
   * this project. These are basically those elements that are placed on the highest hierarchy level
   * in the Assessment view.
   * 
   * @return A list of {@link AssessmentOrGroup} representing the top level assessments and
   *         assessment groups.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteList<AssessmentOrGroup> getTopLevelAssessments() throws RemoteException;

  /**
   * Sets the {@link RequirementsLinking} for this project.
   * 
   * @param requirementsLinking
   *          the requirements linking to be used
   * @throws RemoteException
   *           remote communication problem
   */
  void setRequirementsLinking(RequirementsLinking requirementsLinking) throws RemoteException;

  /**
   * Returns the {@link RequirementsLinking} used for this project.
   * 
   * @return the requirements linking used for this project
   * @throws RemoteException
   *           remote communication problem
   */
  RequirementsLinking getRequirementsLinking() throws RemoteException;

  /**
   * Adds a new requirement to this project.
   * 
   * @param id
   *          The unique ID of the requirement
   * @param document
   *          The requirements document or <code>null</code>.
   * @param text
   *          The describing requirement text.
   * @param type
   *          The type of the requirement.
   * @return The newly created requirement.
   * 
   * @throws ApiException
   *           If a requirement with the same id already exists.
   * @throws RemoteException
   *           remote communication problem
   */
  Requirement createRequirement(String id, String document, String text, RequirementType type)
      throws ApiException, RemoteException;

  /**
   * Get the list of all requirements of this project.
   * 
   * @return The list of requirements.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteIndexedList<String, Requirement> getRequirements() throws RemoteException;

  /**
   * Add a new requirement set to this project.
   * 
   * @param name
   *          The name of the requirement set
   * @param condition
   *          the condtion of the requirement set. The value <code>null</code> will be corrected to
   *          an empty string.
   * @return The newly created requirement set.
   * @throws ApiException
   *           If <code>name</code> is <code>null</code>.
   * @throws RemoteException
   *           remote communication problem
   */
  RequirementSet createRequirementSet(String name, String condition)
      throws ApiException, RemoteException;

  /**
   * Get the list of all requirement sets of this project.
   * 
   * @return The list of requirement sets.
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteList<RequirementSet> getRequirementSets() throws RemoteException;

  /**
   * Returns all {@link FormalRequirementDefine defines} of all {@link Requirement} of the current
   * TPT project.
   *
   * @return all {@link FormalRequirementDefine defines} of all {@link Requirement} of the current
   *         TPT project.
   *
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteIndexedList<String, FormalRequirementDefine> getFormalRequirementDefines()
      throws RemoteException;

  /**
   * Adds a {@link FormalRequirementDefine define} to the current TPT project.
   * 
   * @param name
   *          The name of the define
   * @param value
   *          The value of the define
   * @return {@link FormalRequirementDefine define} which was created and added to the current TPT
   *         project
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If the name already exists or is illegal
   */
  FormalRequirementDefine addFormalRequirementDefine(String name, String value)
      throws RemoteException, ApiException;

  /**
   * Removes the requirement attribute with the given name. The attribute will be removed in all
   * requirements of the project.
   * 
   * @param attributeName
   *          The name of the attribute to remove.
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the name of the attribute is empty or {@code null}
   */
  void removeRequirementAttribute(String attributeName) throws RemoteException;

  /**
   * Sets the name spaces which will be used for the Defines of this TPT project. If several name
   * spaces are used the names have to be comma separated.
   * 
   * @param nameSpaces
   *          the name space, comma separated
   * 
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if nameSpace is {@code null}
   */
  void setUsedNameSpaces(String nameSpaces) throws RemoteException, ApiException;

  /**
   * Gets the name spaces which will be used for the Defines of this TPT project.
   * 
   * @return the name spaces as String
   * 
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getUsedNameSpaces() throws RemoteException;

  /**
   * Delivers the scenario or scenario group with the given id or <code>null</code> if no such
   * scenario or scenario group exists.
   * 
   * @param id
   *          The ID of the <code>ScenarioOrGroup</code>.
   * @return The {@link ScenarioOrGroup} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  ScenarioOrGroup getScenarioOrGroupByID(String id) throws RemoteException;

  /**
   * Delivers the scenario or scenario group with the given uuid or <code>null</code> if no such
   * scenario or scenario group exists.
   * 
   * @param uuid
   *          The <code>UUID</code> of the <code>ScenarioOrGroup</code>.
   * @return The {@link ScenarioOrGroup} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  ScenarioOrGroup getScenarioOrGroupByUUID(UUID uuid) throws RemoteException;

  /**
   * Delivers the first scenario or scenario group with the given name or <code>null</code> if no
   * such scenario or scenario group exists.
   * 
   * @param name
   *          The name of the <code>ScenarioOrGroup</code>.
   * @return The {@link ScenarioOrGroup} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  ScenarioOrGroup getScenarioOrGroupByName(String name) throws RemoteException;

  /**
   * Delivers all scenarios and/or scenario groups, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * @return Collection of all {@link ScenarioOrGroup ScenarioOrGroups}, matching the given name
   *         pattern.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<ScenarioOrGroup> getScenarioOrGroupByNamePattern(Pattern namepattern)
      throws RemoteException;

  /**
   * Delivers all scenarios and/or scenario groups, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * @return Collection of all {@link ScenarioOrGroup ScenarioOrGroups}, matching the given name
   *         pattern.
   * 
   * @throws PatternSyntaxException
   *           If the expression's syntax is invalid
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<ScenarioOrGroup> getScenarioOrGroupByNamePattern(String namepattern)
      throws PatternSyntaxException, RemoteException;

  /**
   * Create a new {@link TestSet} with the given name.
   * 
   * @param name
   *          The name of the new test set. <code>Null</code> will be reduced to an empty string.
   * @return The freshly created and empty test set.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  TestSet createTestSet(String name) throws RemoteException;

  /**
   * Creates a new {@link TestSet} with the given name below the given {@link TestSetGroup}. If
   * <code>groupOrNull == null</code> the new {@link TestSet} will automatically become a top level
   * item.
   * 
   * @param name
   *          The name of the new Test Set. <code>Null</code> will be reduced to an empty string.
   * @param groupOrNull
   *          The parent group for the new Test Set or null.
   * @return The object representing the new TestSet.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  TestSet createTestSet(String name, TestSetGroup groupOrNull) throws RemoteException;

  /**
   * Creates a new {@link TestSetGroup} with the given name below the given {@link TestSetGroup}. If
   * <code>groupOrNull == null</code> the new {@link TestSetGroup} will automatically become a top
   * level item.
   * 
   * @param name
   *          The name of the new Test Set Group. <code>Null</code> will be reduced to an empty
   *          string.
   * @param groupOrNull
   *          The parent group for the new Test Set Group or null.
   * @return The object representing the new TestSet Group.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  TestSetGroup createTestSetGroup(String name, TestSetGroup groupOrNull) throws RemoteException;

  /**
   * Create a new type. The structure of the type is given by the <code>typeString</code> argument.
   * You can see this string in TPT in the type editor in the "Type String" field at the bottom or
   * in an tptaif export.
   * <p>
   * Patterns:
   * </p>
   * <ul>
   * <li>Array : "primitive_type[]"</li>
   * <li>Curve : "curve[primitive_type1,primitive_type2]"</li>
   * <li>Map : "map[primitive_type1,primitive_type2,primitive_type3]"</li>
   * <li>Matrix : "primitive_type[][]"</li>
   * <li>Struct : "struct[e1:primitive_type1, e2:primitive_type2, e3:primitive_type3, ...]"</li>
   * <li>Enums : "int8 [ a = 1, b = 2 ]"</li>
   * </ul>
   * 
   * @param nameOrNull
   *          The name of the declared type or <code>null</code> to create an anonymous type.
   * @param typeString
   *          The type definition in the same format as seen in tptaif or in the type editor.
   * @return the new created type object.
   * 
   * @throws ApiException
   *           If the given name is not a legal identifier name, a type with the given name already
   *           exists or the given type string could not be parsed.
   * @throws RemoteException
   *           remote communication problem
   */
  Type createType(String nameOrNull, String typeString) throws ApiException, RemoteException;

  /**
   * Get a collection of all known types. Anonymous and predefined types are not included. If you
   * call this method multiple times the returned lists will have different hashes and equals will
   * return false when comparing these objects.
   * 
   * @return The collection of all explicitly declared types.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteCollection<Type> getTypes() throws RemoteException;

  /**
   * A declared type can be renamed using this method. This replaces the old type (declared under
   * name <code>oldName</code> before) with a new type (declared under name <code>newName</code>)
   * with the same structure (just renamed). This method has no effect if there is no type declared
   * with name <code>oldName</code>.
   * 
   * @param oldName
   *          name of the declared type to be renamed
   * @param newName
   *          new name for the renamed type. No other type must ne declared using this name.
   * @throws ApiException
   *           if another type named <code>newName</code> exists.
   * @throws RemoteException
   *           remote communication problem
   */
  void renameType(String oldName, String newName) throws ApiException, RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>boolean</code>.
   * 
   * @return The predefined Type <code>boolean</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getTypeBoolean() throws RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>uint8</code>.
   * 
   * @return The predefined Type <code>uint8</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getTypeUInt8() throws RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>int8</code>.
   * 
   * @return The predefined Type <code>int8</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getTypeInt8() throws RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>uint16</code>.
   * 
   * @return The predefined Type <code>uint16</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getTypeUInt16() throws RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>int16</code>.
   * 
   * @return The predefined Type <code>int16</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getTypeInt16() throws RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>uint32</code>.
   * 
   * @return The predefined Type <code>uint32</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getTypeUInt32() throws RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>int32</code>.
   * 
   * @return The predefined Type <code>int32</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getTypeInt32() throws RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>int64</code>.
   * 
   * @return The predefined Type <code>int64</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getTypeInt64() throws RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>float</code>.
   * 
   * @return The predefined Type <code>float</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getTypeFloat() throws RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>double</code>.
   * 
   * @return The predefined Type <code>double</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getTypeDouble() throws RemoteException;

  /**
   * The predefined Types are not returned by {@link #getTypes()}. This method returns the
   * predefined type <code>string</code>.
   * 
   * @return The predefined Type <code>string</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Type getTypeString() throws RemoteException;

  /**
   * Returns an indexed list of all {@link Declaration Declarations} defined in this project. The
   * name of the {@link Declaration} serves as index.
   * 
   * @return The list of all declarations (assessment variables, channels, constants, parameters,
   *         measurements).
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteIndexedList<String, Declaration> getDeclarations() throws RemoteException;

  /**
   * Creates a new channel with the given name.
   * 
   * @param name
   *          The name of the channel
   * @return The new created channel
   * 
   * @throws ApiException
   *           If the given name is not a legal identifier name or a declaration with the given name
   *           already exists.
   * @throws RemoteException
   *           remote communication problem
   */
  Channel createChannel(String name) throws ApiException, RemoteException;

  /**
   * Creates a new parameter with the given name. The default exchange mode is "exchange".
   * 
   * @param name
   *          The name of the parameter
   * @return The new created parameter
   * 
   * @throws ApiException
   *           If the given name is not a legal identifier name or a declaration with the given name
   *           already exists.
   * @throws RemoteException
   *           remote communication problem
   */
  Parameter createParameter(String name) throws ApiException, RemoteException;

  /**
   * Creates a new constant with the given name.
   * 
   * @param name
   *          The name of the constant
   * @return The new created constant
   * @throws ApiException
   *           If the given name is not a legal identifier name or a declaration with the given name
   *           already exists.
   * @throws RemoteException
   *           remote communication problem
   */
  Constant createConstant(String name) throws ApiException, RemoteException;

  /**
   * Creates a new measurement with the given name.
   * 
   * @param name
   *          The name of the measurement
   * @return The new created measurement
   * @throws ApiException
   *           If the given name is not a legal identifier name or a declaration with the given name
   *           already exists.
   * @throws RemoteException
   *           remote communication problem
   */
  Measurement createMeasurement(String name) throws ApiException, RemoteException;

  /**
   * Creates a new assessment variable with the given name.
   * 
   * @param name
   *          The name of the assessment variable
   * @return The new created assessment variable
   * @throws ApiException
   *           If the given name is not a legal identifier name or a declaration with the given name
   *           already exists.
   * @throws RemoteException
   *           remote communication problem
   */
  AssessmentVariable createAssessmentVariable(String name) throws ApiException, RemoteException;

  /**
   * Creates a new {@link ExecutionConfiguration} with the given name at top level.
   * 
   * @param name
   *          The name of the new Execution Configuration. <code>null</code> will be reduced to an
   *          empty string.
   * @return The object representing the new Execution Configuration
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  ExecutionConfiguration createExecutionConfiguration(String name) throws RemoteException;

  /**
   * Creates a new {@link ExecutionConfiguration} with the given name below the given
   * {@link ExecutionConfigurationGroup}. If <code>groupOrNull == null</code> the new
   * {@link ExecutionConfiguration} will automatically become a top level item.
   * 
   * @param name
   *          The name of the new Execution Configuration. <code>Null</code> will be reduced to an
   *          empty string.
   * @param groupOrNull
   *          The parent group for the new Execution Configuration or null.
   * @return The object representing the new Execution Configuration
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  ExecutionConfiguration createExecutionConfiguration(String name,
                                                      ExecutionConfigurationGroup groupOrNull)
      throws RemoteException;

  /**
   * Creates a new {@link ExecutionConfigurationGroup} with the given name below the given
   * {@link ExecutionConfigurationGroup}. If <code>groupOrNull == null</code> the new
   * {@link ExecutionConfigurationGroup} will automatically become a top level item.
   * 
   * @param name
   *          The name of the new Execution Configuration Group. <code>Null</code> will be reduced
   *          to an empty string.
   * @param groupOrNull
   *          The parent group for the new Execution Configuration Group or null.
   * @return The object representing the new Execution Configuration Group.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  ExecutionConfigurationGroup createExecutionConfigurationGroup(String name,
                                                                ExecutionConfigurationGroup groupOrNull)
      throws RemoteException;

  /**
   * Create a new {@link PlatformConfiguration} with the given name and the given type.
   * 
   * @param name
   *          The name for the new PlatformConfiguration. <code>Null</code> will be reduced to an
   *          empty string.
   * @param type
   *          A <code>String</code> representing the platform type.
   * 
   * @return Returns an object representing the new <code>PlatformConfiguration</code>.
   * 
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if <code>type==null</code> or <code>type</code> is unknown.
   */

  PlatformConfiguration createPlatformConfiguration(String name, String type)
      throws ApiException, RemoteException;

  /**
   * Delivers the assessment or assessment group with the given id or <code>null</code> if no such
   * assessment or assessment group exists.
   * 
   * @param id
   *          The ID of the <code>AssessmentOrGroup</code>.
   * @return The {@link AssessmentOrGroup} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  AssessmentOrGroup getAssessmentOrGroupByID(String id) throws RemoteException;

  /**
   * Delivers the assessment or assessment group with the given uuid or <code>null</code> if no such
   * assessment or assessment group exists.
   * 
   * @param uuid
   *          The <code>UUID</code> of the <code>AssessmentOrGroup</code>.
   * @return The {@link AssessmentOrGroup} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  AssessmentOrGroup getAssessmentOrGroupByUUID(UUID uuid) throws RemoteException;

  /**
   * Delivers the first assessment or assessment group with the given name or <code>null</code> if
   * no such assessment or assessment group exists.
   * 
   * @param name
   *          The name of the <code>AssessmentOrGroup</code>.
   * @return The {@link AssessmentOrGroup} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  AssessmentOrGroup getAssessmentOrGroupByName(String name) throws RemoteException;

  /**
   * Delivers all assessments and/or assessment groups, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * @return Collection of all {@link AssessmentOrGroup AssessmentOrGroups}, matching the given name
   *         pattern.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<AssessmentOrGroup> getAssessmentOrGroupByNamePattern(Pattern namepattern)
      throws RemoteException;

  /**
   * Delivers all assessments and/or assessment groups, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * @return Collection of all {@link AssessmentOrGroup AssessmentOrGroups}, matching the given name
   *         pattern.
   * 
   * @throws PatternSyntaxException
   *           If the expression's syntax is invalid
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<AssessmentOrGroup> getAssessmentOrGroupByNamePattern(String namepattern)
      throws PatternSyntaxException, RemoteException;

  /**
   * 
   * Create a {@link AssessmentGroup} with the given name and within the given parent
   * <code>AssessmentGroup</code>.
   * <p>
   * If <code>groupOrNull==null</code>, the newly created <code>AssessmentGroup</code> automatically
   * becomes a top level item.
   * </p>
   * 
   * @param name
   *          The name for the new <code>AssessmentGroup</code>. <code>Null</code> will be reduced
   *          to an empty string.
   * @param groupOrNull
   *          Either the parent <code>AssessmentGroup</code> or <code>null</code>
   * @return An object representing the newly created <code>AssessmentGroup</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  AssessmentGroup createAssessmentGroup(String name, AssessmentGroup groupOrNull)
      throws RemoteException;

  /**
   * 
   * Creates a new <code>Assessment</code> with the given name and the given type below the given
   * <code>AssessmentGroup</code>. If <code>groupOrNull==null</code>, the newly created
   * <code>Assessment</code> automatically becomes a top level assessment
   * 
   * @param name
   *          The name of the new <code>Assessment</code>. <code>Null</code> will be reduced to an
   *          empty string.
   * @param type
   *          A <code>String</code> representing the assessment type. Constants for available types
   *          can be found in {@link Assessment}.
   * @param groupOrNull
   *          The parent group for the new <code>Assessment</code> or <code>null</code>
   * @return The object representing the newly created <code>Assessment</code>
   * 
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           <code>type==null</code>
   */
  Assessment createAssessment(String name, String type, AssessmentGroup groupOrNull)
      throws ApiException, RemoteException;

  /**
   * This function returns the top level testlet of a TPT project for which all other testlets are
   * child nodes.
   * <p>
   * In the project tree, this testlet is represented by the project node. Its variants are test
   * cases.
   * </p>
   * 
   * @return The top level {@link Testlet} for this project.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Testlet getTopLevelTestlet() throws RemoteException;

  /**
   * Returns the list of declared {@link TestCaseAttribute TestCaseAttributes} for this project.
   * 
   * @return A list of TestCaseAttributes.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteCollection<TestCaseAttribute> getTestCaseAttributes() throws RemoteException;

  /**
   * Create a new {@link TestCaseAttribute}
   * 
   * @see TestCaseAttribute#getType()
   * 
   * @param name
   *          The name for the new attribute.
   * @param type
   *          The type of the attribute as String
   * @return A newly created {@link TestCaseAttribute}
   * 
   * @throws ApiException
   *           If there already exists a {@link TestCaseAttribute} with the given name or
   *           <code>name==null</code> or <code>type==null</code> or if the given <code>type</code>
   *           is unknown.
   * @throws RemoteException
   *           remote communication problem
   * @deprecated will be removed in TPT-21. Use
   *             {@link #createTestCaseAttribute(String, com.piketec.tpt.api.TestCaseAttribute.TestCaseAttributeType)
   *             createTestCaseAttribute(String, TestCaseAttributeType)} instead.
   */
  @Deprecated
  TestCaseAttribute createTestCaseAttribute(String name, String type)
      throws ApiException, RemoteException;

  /**
   * Create a new {@link TestCaseAttribute}
   * 
   * @see TestCaseAttribute#getAttributeType()
   * 
   * @param name
   *          The name for the new attribute.
   * @param type
   *          The type of the attribute as TestCaseAttributeType.
   * @return A newly created {@link TestCaseAttribute}
   * 
   * @throws ApiException
   *           If there already exists a {@link TestCaseAttribute} with the given name or
   *           <code>name==null</code> or <code>type==null</code>.
   * @throws RemoteException
   *           remote communication problem
   */
  TestCaseAttribute createTestCaseAttribute(String name,
                                            TestCaseAttribute.TestCaseAttributeType type)
      throws ApiException, RemoteException;

  /**
   * Returns an entry point for an API extension of the given type. The Return value needs to be
   * cast accordingly. Returns <code>null</code>, if no extension was registered for
   * <code>key</code>.
   * 
   * @param key
   *          Name of the extension
   * @return <code>null</code>, if no extension could have been found with the given
   *         <code>key</code>. Otherwise, an object representing the entry point.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           If an extension has been found but is not available for the current TPT instance
   *           (e.g. missing license option).
   */
  Remote getExtensionOrNull(String key) throws RemoteException, ApiException;

  /**
   * @return Returns a list of the {@link Mapping Mappings} that are defined for this project.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteList<Mapping> getMappings() throws RemoteException;

  /**
   * Delivers the first mapping with the given name or <code>null</code> if no such mapping exists.
   * 
   * @param name
   *          The name of the <code>Mapping</code>.
   * @return The {@link Mapping} or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Mapping getMappingByName(String name) throws RemoteException;

  /**
   * Delivers all mappings, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * 
   * @return Collection of all {@link Mapping Mappings}, matching the given name pattern.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<Mapping> getMappingByNamePattern(Pattern namepattern) throws RemoteException;

  /**
   * Delivers all mappings, matching the given name pattern.
   * 
   * @param namepattern
   *          A regular expression for the name pattern.
   * 
   * @return Collection of all {@link Mapping Mappings}, matching the given name pattern.
   * 
   * @throws PatternSyntaxException
   *           If the expression's syntax is invalid
   * @throws RemoteException
   *           remote communication problem
   */
  Collection<Mapping> getMappingByNamePattern(String namepattern)
      throws PatternSyntaxException, RemoteException;

  /**
   * Create a new {@link Mapping} with the <code>given</code> name. If the <code>name</code> already
   * exists, a suffix (_00&lt;n&gt;) is added to the name of the new mapping.
   * 
   * @param name
   *          The intended <code>name</code> for the new mapping. <code>Null</code> will be reduced
   *          to an empty string.
   * @return An object representing the new {@link Mapping}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Mapping createMapping(String name) throws RemoteException;

  /**
   * Imports existing test data as step lists to TPT. This corresponds to "Tools | Generate Test
   * Cases from Test Data". This function could either try to update an already existing set of
   * imported test data or create a new set. *
   * 
   * @param scenarioGroup
   *          A variant group where the imported test data should be inserted.
   * @param dir
   *          The directory where the test data resides.
   * @param filePatternOrNull
   *          A regular expression to constrain the set of files that are imported to TPT.<br>
   *          For example, use <code>".*\.mdf"</code> to consider only MDF files (i.e., all files
   *          whose file name ends with '.mdf').<br>
   *          If the expression is <code>null</code> or empty, all available files with supported
   *          file format will be imported.
   * @param includeSubdirs
   *          Enable/disable the traversal of sub-directories of <code>dir</code> for further data
   *          to import.
   * @param channelPatternOrNull
   *          Provide a regular expression to define a mapping between channels in TPT and signal
   *          names in the file. The placeholder ${CHANNEL} can be used to refer to a arbitrary
   *          channel name in TPT: the expression <code>"prefix_${CHANNEL}_postfix"</code> would
   *          match a TPT channel <code>"mysignal"</code> to the signal
   *          <code>"prefix_mysignal_postfix"</code> in the file.
   * @param linkSignals
   *          Enable or disable the linking of test data files. If set to <code>false</code>, test
   *          date will be imported as Embedded Signal Step, instead.
   * @param renameMappingNameOrNull
   *          The name of a existing mapping with a rename flavor, that should be used to map the
   *          channels in TPT to the signal in the file.
   * @param createAssesslets
   *          Enable/Disable the automatic creation of Signal Comparison Assesslets for the input
   *          channels in the test data.
   * @param timeTolOrNull
   *          Specify the time tolerance for the Signal Comparison Assesslets. <code>null</code>
   *          means none.
   * @param valueTolOrNull
   *          Specify the value tolerance for the Signal Comparison Assesslets. <code>null</code>
   *          means none.
   * @param createLocalReferenceChannels
   *          Create local signals for the reference channels of the Signal comparison from the
   *          channels of the file to have the reference data available for embedded signals, too.
   * @param addTerminationCondition
   *          Add a wait stept that terminates the variant when the test data has been fully
   *          replayed.
   * @param assignParameters
   *          Enable the assignment of parameter values to test cases, if those are present in the
   *          test data file and a respective mapping flavor is present.
   * @param updateExistingGeneratedScenarios
   *          If this argument is set to <code>true</code>, TPT tries to find an older import to
   *          update with the new data. If it finds an older import, TPT updates already existing
   *          test cases, adds missing test cases, and removes such test cases, that do not have
   *          corresponding test data anymore.
   *          <p>
   *          A older updateable import exists, if the testlet for the provided
   *          <code>scenarioGroup</code> contains exactly one child group that matches the name
   *          scheme <code>"Import DD.MM.YY HH:MM:SS - RootDirName"</code> and there exists a test
   *          case group of the same name.
   *          </p>
   * @return Returns a <code>String</code> containing the error and warning messages occurred during
   *         the import.
   * @throws ApiException
   *           <ul>
   *           <li>If <code>dir</code> cannot be read.</li>
   *           <li>If no mapping with the name <code>renameMappingNameOrNull</code> can be found or
   *           the Mapping does not have a Rename Flavor.
   *           <li>If <code>createAssesslets == true</code>, but are no TPT-Input-Channels for which
   *           a Signal Comparison Assesslet could be created.</li>
   *           <li>If an error occurs during the import.</li>
   *           </ul>
   * @throws RemoteException
   *           remote communication problem
   */
  String generateTestCasesFromTestData(ScenarioGroup scenarioGroup, File dir,
                                       String filePatternOrNull, boolean includeSubdirs,
                                       String channelPatternOrNull, boolean linkSignals,
                                       String renameMappingNameOrNull, boolean createAssesslets,
                                       String timeTolOrNull, String valueTolOrNull,
                                       boolean createLocalReferenceChannels,
                                       boolean addTerminationCondition, boolean assignParameters,
                                       boolean updateExistingGeneratedScenarios)
      throws ApiException, RemoteException;

  /**
   * Imports existing test data as step lists to TPT. This corresponds to "Tools | Generate Test
   * Cases from Test Data". This function could either try to update an already existing set of
   * imported test data or create a new set. *
   * 
   * @param scenarioGroup
   *          A variant group where the imported test data should be inserted.
   * @param dir
   *          The path to the directory where the test data resides.
   * @param filePatternOrNull
   *          A regular expression to constrain the set of files that are imported to TPT.<br>
   *          For example, use <code>".*\.mdf"</code> to consider only MDF files (i.e., all files
   *          whose file name ends with '.mdf').<br>
   *          If the expression is <code>null</code> or empty, all available files with supported
   *          file format will be imported.
   * @param includeSubdirs
   *          Enable/disable the traversal of sub-directories of <code>dir</code> for further data
   *          to import.
   * @param channelPatternOrNull
   *          Provide a regular expression to define a mapping between channels in TPT and signal
   *          names in the file. The placeholder ${CHANNEL} can be used to refer to a arbitrary
   *          channel name in TPT: the expression <code>"prefix_${CHANNEL}_postfix"</code> would
   *          match a TPT channel <code>"mysignal"</code> to the signal
   *          <code>"prefix_mysignal_postfix"</code> in the file.
   * @param linkSignals
   *          Enable or disable the linking of test data files. If set to <code>false</code>, test
   *          date will be imported as Embedded Signal Step, instead.
   * @param renameMappingNameOrNull
   *          The name of a existing mapping with a rename flavor, that should be used to map the
   *          channels in TPT to the signal in the file.
   * @param createAssesslets
   *          Enable/Disable the automatic creation of Signal Comparison Assesslets for the input
   *          channels in the test data.
   * @param timeTolOrNull
   *          Specify the time tolerance for the Signal Comparison Assesslets. <code>null</code>
   *          means none.
   * @param valueTolOrNull
   *          Specify the value tolerance for the Signal Comparison Assesslets. <code>null</code>
   *          means none.
   * @param createLocalReferenceChannels
   *          Create local signals for the reference channels of the Signal comparison from the
   *          channels of the file to have the reference data available for embedded signals, too.
   * @param addTerminationCondition
   *          Add a wait stept that terminates the variant when the test data has been fully
   *          replayed.
   * @param assignParameters
   *          Enable the assignment of parameter values to test cases, if those are present in the
   *          test data file and a respective mapping flavor is present.
   * @param updateExistingGeneratedScenarios
   *          If this argument is set to <code>true</code>, TPT tries to find an older import to
   *          update with the new data. If it finds an older import, TPT updates already existing
   *          test cases, adds missing test cases, and removes such test cases, that do not have
   *          corresponding test data anymore.
   *          <p>
   *          A older updateable import exists, if the testlet for the provided
   *          <code>scenarioGroup</code> contains exactly one child group that matches the name
   *          scheme <code>"Import DD.MM.YY HH:MM:SS - RootDirName"</code> and there exists a test
   *          case group of the same name.
   *          </p>
   * @return Returns a <code>String</code> containing the error and warning messages occurred during
   *         the import.
   * @throws ApiException
   *           <ul>
   *           <li>If <code>dir</code> cannot be read.</li>
   *           <li>If no mapping with the name <code>renameMappingNameOrNull</code> can be found or
   *           the Mapping does not have a Rename Flavor.
   *           <li>If <code>createAssesslets == true</code>, but are no TPT-Input-Channels for which
   *           a Signal Comparison Assesslet could be created.</li>
   *           <li>If an error occurs during the import.</li>
   *           </ul>
   * @throws RemoteException
   *           remote communication problem
   */
  String generateTestCasesFromTestDataByPath(ScenarioGroup scenarioGroup, String dir,
                                             String filePatternOrNull, boolean includeSubdirs,
                                             String channelPatternOrNull, boolean linkSignals,
                                             String renameMappingNameOrNull,
                                             boolean createAssesslets, String timeTolOrNull,
                                             String valueTolOrNull,
                                             boolean createLocalReferenceChannels,
                                             boolean addTerminationCondition,
                                             boolean assignParameters,
                                             boolean updateExistingGeneratedScenarios)
      throws ApiException, RemoteException;

  /**
   * Imports the interface from the given file. Supported are tpt, tptprj, tptz, tptaif, xml and
   * xlsx files.
   * 
   * @param f
   *          The interface file containing the declarations to import.
   * @param mappingOrNull
   *          The mapping where mapping information shall be imported or <code>null</code> if a new
   *          mapping should be created if needed.
   * @param syncMethod
   *          Specifies how the existing and imported declarations are matched.
   * @return A list of warnings that occurred during import.
   * @throws ApiException
   *           If an error occurs during import or the file format is not supported.
   * @throws RemoteException
   *           remote communication problem
   */
  List<String> importIO(File f, Mapping mappingOrNull, SynchronizationMethod syncMethod)
      throws ApiException, RemoteException;

  /**
   * Imports the interface from the given file. Supported are tpt, tptprj, tptz, tptaif, xml and
   * xlsx files.
   * 
   * @param f
   *          The path to the interface file containing the declarations to import.
   * @param mappingOrNull
   *          The mapping where mapping information shall be imported or <code>null</code> if a new
   *          mapping should be created if needed.
   * @param syncMethod
   *          Specifies how the existing and imported declarations are matched.
   * @return A list of warnings that occurred during import.
   * @throws ApiException
   *           If an error occurs during import or the file format is not supported.
   * @throws RemoteException
   *           remote communication problem
   */
  List<String> importIOByPath(String f, Mapping mappingOrNull, SynchronizationMethod syncMethod)
      throws ApiException, RemoteException;

  /**
   * Imports the interface by selecting the suitable importer by the given
   * {@link ImportInterfaceSettings} sub class of <code>settings</code>, using the configuration
   * contained in this instance.
   * 
   * @param settings
   *          A sub class of {@link ImportInterfaceSettings} for a specific import type containing
   *          the needed information to execute the import without user interactions.
   * @return The log containing warning und error messages, that occured during import and did not
   *         result in an {@link ApiException}
   * @throws ApiException
   *           If an error occurs during import or the file format is not supported.
   * @throws RemoteException
   *           remote communication problem
   */
  Log importIO(ImportInterfaceSettings settings) throws ApiException, RemoteException;

  /**
   * @return is this ProjectFile created with the current TPT-version?
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  boolean createdWithCurrentVersion() throws RemoteException;

  /**
   * @return Fileformat version number of the tpt-project-file.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  int getCreatedWithFileFormatVersion() throws RemoteException;

  /**
   * @return TPT version name of the tpt-project-file.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getCreatedWithTptVersionName() throws RemoteException;

  /**
   * @param f
   *          {@link File} to be imported.
   * @return {@link List} with warnings occurred during import.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  List<String> importEquivalenceClasses(File f) throws RemoteException;

  /**
   * @param f
   *          the path to the file to be imported.
   * @return {@link List} with warnings occurred during import.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the path is null
   */
  List<String> importEquivalenceClassesByPath(String f) throws RemoteException, ApiException;

  /**
   * @param f
   *          target-{@link File} for export.
   * @return {@link List} with warnings occurred during import.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  List<String> exportEquivalenceClasses(File f) throws RemoteException;

  /**
   * @param f
   *          path to the target File for export.
   * @return {@link List} with warnings occurred during import.
   * @throws ApiException
   *           if the path is null
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  List<String> exportEquivalenceClassesByPath(String f) throws ApiException, RemoteException;

  /**
   * This method provides the functionality to generate {@link Scenario test cases} from equivalence
   * classes.
   * 
   * @param scenarioOrGroup
   *          where generated {@link Scenario test cases} should be added.
   * @param settings
   *          for generation of {@link Scenario test cases} from equivalence classes.
   * @param equivalenceClassMap
   *          selected equivalence classes.
   * @return A {@link List List&lt;String&gt;} with all warnings as a log.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  List<String> generateTestCasesFromEquivalenceClasses(ScenarioOrGroup scenarioOrGroup,
                                                       GenerateTestCasesFromEquivalenceClassessSettings settings,
                                                       Map<Declaration, Collection<String>> equivalenceClassMap)
      throws RemoteException;

  /**
   * Get the default report section.
   * 
   * @return The default report section
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Assessment getDefaultReportSection() throws RemoteException;

  /**
   * Set the default report section. If the given argument is <code>null</code> the top level report
   * section is set as the default.
   * 
   * @param reportSection
   *          The report section to be set or <code>null</code> to set top level as default.
   * @throws ApiException
   *           if the report section is not part of the project or if it is not a report section
   *           assesslet.
   * @throws RemoteException
   *           remote communication problem
   */
  void setDefaultReportSection(Assessment reportSection) throws ApiException, RemoteException;

  /**
   * @return the {@link Project Projects} {@link Unit Units}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteList<Unit> getUnits() throws RemoteException;

  /**
   * Creates a new {@link Unit} for this project.
   * 
   * @param name
   *          to be set.
   * @param symbol
   *          to be set.
   * @return the newly created unit
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the given symbol or name is <code>null</code>, has illegal characters or a unit
   *           with the same name or symbol already exists
   */
  Unit createUnit(String name, String symbol) throws ApiException, RemoteException;

  /**
   * @return A list of all status types defined for this project.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteList<String> getStatusTypes() throws RemoteException;

  /**
   * Creates a new status type for this project.
   * 
   * @param status
   *          The status type to be created.
   * @throws ApiException
   *           If status is empty, <code>null</code>, contains line breaks, or a status with the
   *           given name already exists.
   * @throws RemoteException
   *           remote communication problem
   */
  void createStatusType(String status) throws ApiException, RemoteException;

  /**
   * Get all tags already used for tagging statuses.
   * 
   * @return The list of tags.
   * @throws RemoteException
   *           remote communication problem
   */
  Set<String> getStatusTags() throws RemoteException;

  /**
   * Adds the same tag to all current revisions of test cases and assesslets.
   * 
   * @param tag
   *          The tag to be added.
   * @throws ApiException
   *           If tag is empty, <code>null</code>, contains line breaks, or a tag with the given
   *           name already exists.
   * @throws RemoteException
   *           remote communication problem
   */
  void tagCurrentRevisions(String tag) throws ApiException, RemoteException;

  /**
   * Exports the last test results to an extern destination.
   * 
   * @param key
   *          the key of the exporter to use
   * @throws ApiException
   *           if any problems occur during the export
   * @throws RemoteException
   *           remote communication problem
   */
  void exportTestResults(TestResultsExporterKey key) throws ApiException, RemoteException;

  /**
   * Exports the test cases to a CSV, Excel, or ReqIF file to import the changes to a test
   * management tool. It is also possible to export test cases directly to codeBeamer or Polarion.
   * 
   * @param exportSettings
   *          The settings for the individual export.<br>
   *          For the test cases export to a CSV file use
   *          {@link CsvFileTestCasesExportSettings}.<br>
   *          For the test cases export to an Excel file use
   *          {@link ExcelFileTestCasesExportSettings}.<br>
   *          For the test cases export to Polarion use {@link PolarionTestCasesExportSettings}.<br>
   *          For the test cases export to codeBeamer use
   *          {@link CodeBeamerTestCasesExportSettings}.<br>
   *          For the test cases export to refIF use {@link ReqIfTestCasesExportSettings}.
   * @throws ApiException
   *           if any problems occur during the export
   * @throws RemoteException
   *           remote communication problem
   */
  void exportTestCases(TestCasesExportSettings exportSettings) throws ApiException, RemoteException;

  /**
   * Imports the test cases from a CSV or an Excel file. It is also possible to import test cases
   * directly from codeBeamer or Polarion.
   * 
   * @param importSettings
   *          The settings for the individual import.<br>
   *          For the test cases import from a CSV file use
   *          {@link CsvFileTestCasesImportSettings}.<br>
   *          For the test cases import from an Excel file use
   *          {@link ExcelFileTestCasesImportSettings}.<br>
   *          For the test cases import from Polarion use
   *          {@link PolarionTestCasesImportSettings}.<br>
   *          For the test cases import from codeBeamer use
   *          {@link CodeBeamerTestCasesImportSettings}.
   * @throws ApiException
   *           if any problems occur during the import
   * @throws RemoteException
   *           remote communication problem
   */
  void importTestCases(TestCasesImportSettings importSettings) throws ApiException, RemoteException;

  /**
   * Exports the requirements to a CSV or an Excel file to import the changes to a test management
   * tool.
   * 
   * @param exportSettings
   *          The settings for the individual export.<br>
   *          For the requirements export to a CSV file use
   *          {@link CsvFileRequirementsExportSettings}.<br>
   *          For the requirements export to an Excel file use
   *          {@link ExcelFileRequirementsExportSettings}.
   * @throws ApiException
   *           if any problems occur during the export
   * @throws RemoteException
   *           remote communication problem
   */
  void exportRequirements(RequirementsExportSettings exportSettings)
      throws ApiException, RemoteException;

  /**
   * Imports the requirements from a CSV, Excel, or ReqIF file. It is also possible to import
   * requirements directly from codeBeamer or Polarion.
   * 
   * @param importSettings
   *          The settings for the individual import.<br>
   *          For the requirements import from a CSV file use
   *          {@link CsvFileRequirementsImportSettings}.<br>
   *          For the requirements import from an Excel file use
   *          {@link ExcelFileRequirementsImportSettings}.<br>
   *          For the requirements import from Polarion use
   *          {@link PolarionRequirementsImportSettings}.<br>
   *          For the requirements import from codeBeamer use
   *          {@link CodeBeamerRequirementsImportSettings}.<br>
   *          For the requirements import from refIF use {@link ReqIfRequirementsImportSettings}.
   * @throws ApiException
   *           if any problems occur during the import
   * @throws RemoteException
   *           remote communication problem
   */
  void importRequirements(RequirementsImportSettings importSettings)
      throws ApiException, RemoteException;

  /**
   * Delivers the last used requirements import settings for the given document name or
   * <code>null</code> if no settings exist.
   * 
   * @param documentName
   *          The name of the document. An empty string represents the default document.
   * @return The settings for the individual import.<br>
   *         For the requirements import from a CSV file {@link CsvFileRequirementsImportSettings}
   *         is returned.<br>
   *         For the requirements import from an Excel file
   *         {@link ExcelFileRequirementsImportSettings} is returned.<br>
   *         For the requirements import from Polarion {@link PolarionRequirementsImportSettings} is
   *         returned.<br>
   *         For the requirements import from codeBeamer
   *         {@link CodeBeamerRequirementsImportSettings} is returned.<br>
   *         For the requirements import from refIF {@link ReqIfRequirementsImportSettings} is
   *         returned.
   * @throws ApiException
   *           if any problems occur during the import
   * @throws RemoteException
   *           remote communication problem
   */
  RequirementsImportSettings getLastImportRequirementsSettings(String documentName)
      throws ApiException, RemoteException;

  /**
   * Delivers the names of all requirements documents contained in the project in alphabetical
   * order.
   * 
   * @return A list of all names of requirements documents in this project
   * @throws RemoteException
   *           remote communication problem
   */
  List<String> getRequirementsDocuments() throws RemoteException;

}
