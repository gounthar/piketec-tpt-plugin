package com.piketec.tpt.api.requirements.polarion;

import java.util.List;

import com.piketec.tpt.api.Pair;
import com.piketec.tpt.api.Requirement;
import com.piketec.tpt.api.Requirement.RequirementType;
import com.piketec.tpt.api.requirements.RequirementsImportSettings;

/**
 * The settings for the requirements import from Polarion.
 * 
 * @author Copyright (c) 2014-2022 PikeTec GmbH - MIT License (MIT) - All rights reserved
 */
public class PolarionRequirementsImportSettings extends RequirementsImportSettings {

  static final long serialVersionUID = 1L;

  /**
   * The choice for the import. Either import via Document by using the path to the document
   * followed by its ID (e.g., Testing/Test Specification), or import via Lucene by using a query in
   * Lucene syntax.
   */
  public enum PolarionImportMethod {
    /**
     * Import via Document by using the path to the document followed by its ID (e.g., Testing/Test
     * Specification)
     */
    DOCUMENT,
    /**
     * Import via Lucene by using a query in Lucene syntax.
     */
    LUCENE;
  }

  private String projectId = null;

  private PolarionImportMethod importMethod = PolarionImportMethod.DOCUMENT;

  private String documentPath = null;

  private String luceneQuery = null;

  private List<PolarionWorkItemFieldToTCAttributeAssignment> propertyAssignments = null;

  private List<Pair<String, Requirement.RequirementType>> typeAssignments = null;

  /**
   * The constructor for the settings of the requirements import from Polarion.
   * 
   * @param projectId
   *          The ID of the project in Polarion.
   */
  public PolarionRequirementsImportSettings(String projectId) {
    super();
  }

  /**
   * @return The ID of the project in Polarion.
   */
  public String getProjectId() {
    return projectId;
  }

  /**
   * @param projectId
   *          The ID of the project in Polarion.
   */
  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }

  /**
   * @return The choice for the import. Either import via {@link PolarionImportMethod#DOCUMENT
   *         Document} by using the path to the document followed by its ID (e.g., Testing/Test
   *         Specification), or import via {@link PolarionImportMethod#LUCENE Lucene} by using a
   *         query in Lucene syntax.
   */
  public PolarionImportMethod getImportMethod() {
    return this.importMethod;
  }

  /**
   * @param importMethod
   *          The choice for the import. Either import via {@link PolarionImportMethod#DOCUMENT
   *          Document} by using the path to the document followed by its ID (e.g., Testing/Test
   *          Specification), or import via {@link PolarionImportMethod#LUCENE Lucene} by using a
   *          query in Lucene syntax.
   */
  public void setImportMethod(PolarionImportMethod importMethod) {
    this.importMethod = importMethod;
  }

  /**
   * @return The path to the document followed by its ID (e.g., Testing/Test Specification). Only
   *         relevant if import method is {@link PolarionImportMethod#DOCUMENT Document}.
   * @see #setImportMethod(PolarionImportMethod)
   */
  public String getDocumentPath() {
    return this.documentPath;
  }

  /**
   * @param documentPath
   *          The path to the document followed by its ID (e.g., Testing/Test Specification). Only
   *          relevant if import method is {@link PolarionImportMethod#DOCUMENT Document}.
   * @see #setImportMethod(PolarionImportMethod)
   */
  public void setDocumentPath(String documentPath) {
    this.documentPath = documentPath;
  }

  /**
   * @return If <code>null</code>, all work items of the project are imported. Otherwise a query in
   *         Lucene syntax to import a subset of the project work items (e.g., type:testcase). Only
   *         relevant if import method is {@link PolarionImportMethod#LUCENE Lucene}.
   * @see #setImportMethod(PolarionImportMethod)
   */
  public String getLuceneQuery() {
    return this.luceneQuery;
  }

  /**
   * @param luceneQuery
   *          If set to <code>null</code>, all work items of the project are imported. Otherwise set
   *          to a query in Lucene syntax to import a subset of the project work items (e.g.,
   *          type:testcase). Only relevant if import method is {@link PolarionImportMethod#LUCENE
   *          Lucene}.
   * @see #setImportMethod(PolarionImportMethod)
   */
  public void setLuceneQuery(String luceneQuery) {
    this.luceneQuery = luceneQuery;
  }

  /**
   * @return The list of assignments of Polarion Work Item fields to a TPT test case attributes.
   */
  public List<PolarionWorkItemFieldToTCAttributeAssignment> getPropertyAssignments() {
    return this.propertyAssignments;
  }

  /**
   * @param propertyAssignments
   *          The list of assignments of Polarion Work Item fields to a TPT test case attributes.
   */
  public void setPropertyAssignments(List<PolarionWorkItemFieldToTCAttributeAssignment> propertyAssignments) {
    this.propertyAssignments = propertyAssignments;
  }

  /**
   * @return The assignments of Polarion types (items in the Polarion Work Item field 'Types') to
   *         the requirement type in TPT. Items without an assigned TPT type are imported as
   *         {@link RequirementType#REQUIREMENT Requirement}.
   */
  public List<Pair<String, Requirement.RequirementType>> getTypeAssignments() {
    return typeAssignments;
  }

  /**
   * @param typeAssignments
   *          The assignments of Polarion types (items in the Polarion Work Item field 'Types') to
   *          the requirement type in TPT. Items without an assigned TPT type are imported as
   *          {@link RequirementType#REQUIREMENT Requirement}.
   */
  public void setTypeAssignments(List<Pair<String, Requirement.RequirementType>> typeAssignments) {
    this.typeAssignments = typeAssignments;
  }

}
