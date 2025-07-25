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

import java.net.URI;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.piketec.tpt.api.steplist.CompareStep;
import com.piketec.tpt.api.steplist.formalrequirements.ConditionTreeNode;
import com.piketec.tpt.api.steplist.formalrequirements.GeneralShallNode;
import com.piketec.tpt.api.util.DeprecatedAndRemovedException;

/**
 * TPT API representation of a requirement. This interface allows more manipulation than the user
 * can do from the UI. The UI only allows globally removing an attribute and editing the comment.
 * <br>
 * <br>
 * <b>Note:</b> Changing the requirement via API - in contrast to changing it by importing
 * requirements - will not lead to a "modified" state of the requirement. Only UI actions that lead
 * to a "modified" state will do so via API as well, e.g. setting status to "deleted". Otherwise use
 * {@link #markAsModified()} to set the status to modified explicitly.
 * 
 * @author Copyright (c) 2014-2025 Synopsys Inc. - MIT License (MIT) - All rights reserved
 */
public interface Requirement extends IdentifiableRemote {

  /**
   * There are three types of requirements. Two of them are only informative.
   */
  public enum RequirementType {

    /**
     * A real requirement.
     */
    REQUIREMENT("Requirement"),
    /**
     * A requirement used as a heading
     */
    HEADING("Heading"),
    /**
     * A requirement used as informative text.
     */
    INFORMATION("Information");

    private final String displayName;

    RequirementType(String name) {
      this.displayName = name;
    }

    /**
     * @return A better readable name than the enum constant name
     */
    public String getDisplayName() {
      return displayName;
    }
  }

  /**
   * There are three types of status for requirements.
   * 
   * @deprecated use {@link #markAsDeleted(boolean)} and {@link #isMarkedAsDeleted()} instead
   */
  @Deprecated
  public enum RequirementStatus {

    /**
     * A new requirement.
     */
    NEW("New"),
    /**
     * A normal requirement.
     */
    NORMAL("Normal"),
    /**
     * A deleted requirement.
     */
    DELETED("Deleted");

    private final String displayName;

    RequirementStatus(String name) {
      this.displayName = name;
    }

    /**
     * @return A better readable name than the enum constant name
     */
    public String getDiplayableName() {
      return displayName;
    }

  }

  /**
   * Gets the type of the requirement.
   * 
   * @return The type of the requirement.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RequirementType getType() throws RemoteException;

  /**
   * Sets the type of the requirement.
   * 
   * @param type
   *          The new type of the Requirement
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  void setType(RequirementType type) throws RemoteException;

  /**
   * Every requirement has a unique ID.
   * 
   * @return The unique ID.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getID() throws RemoteException;

  /**
   * A requirement normally belongs to a document. The default document is an empty string.
   * 
   * @return The name of the document the requirement belongs to.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getDocument() throws RemoteException;

  /**
   * Gets the document version of the requirement.
   * 
   * @return The requirement's document version.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getDocumentVersion() throws RemoteException;

  /**
   * A requirement normally belongs to a module. The default module is an empty string.
   * 
   * @return The name of the module the requirement belongs to.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @deprecated Use {@link #getDocument()} instead. Removed in TPT-19. Throws
   *             {@link DeprecatedAndRemovedException}.
   */
  @Deprecated
  String getModule() throws RemoteException;

  /**
   * Gets the describing text a requirement normally has.
   * 
   * @return The describing text of the requirement.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getText() throws RemoteException;

  /**
   * Sets the describing text a requirement normally has.
   * 
   * @param text
   *          The new describing text. <code>Null</code> will be reduced to an empty string.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  void setText(String text) throws RemoteException;

  /**
   * Gets the comment of the requirement. This comment is also editable from the UI.
   * 
   * @return The comment of the requirement.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getComment() throws RemoteException;

  /**
   * Sets the comment of the requirement. This comment is also editable from the UI.
   * 
   * @param comment
   *          The new comment of the requirement. <code>Null</code> will be reduced to an empty
   *          string.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  void setComment(String comment) throws RemoteException;

  /**
   * Gets the tag of the requirement. This tag is also editable from the UI.
   * 
   * @return The tag of the requirement.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getTag() throws RemoteException;

  /**
   * Sets the tag of the requirement. This tag is also editable from the UI.
   * 
   * @param tagName
   *          The name of the new tag of the requirement.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the name of the tag empty or <code>null</code> or a tag with the given name does
   *           not exist
   */
  void setTag(String tagName) throws RemoteException, ApiException;

  /**
   * Gets the URI of the requirement. If a requirement has a URI a clickable link symbol will be
   * displayed in the UI.
   * 
   * @return The URI of the requirement or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  URI getURI() throws RemoteException;

  /**
   * Gets the indent level of the requirement. This is the indentation of the requirement in the
   * requirement table. There may be another user attribute indent level or object level. This
   * method returns the internal attribute, that is used to layout the requirements in the UI.
   * 
   * @return The indent level of the requirement.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  int getIndentLevel() throws RemoteException;

  /**
   * Sets the URI of the requirement. If a requirement has a URI a clickable link symbol will be
   * displayed in the UI.
   * 
   * @param uri
   *          The new URI or <code>null</code> to remove the link.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  void setURI(URI uri) throws RemoteException;

  /**
   * Gets the URI of the requirement. If a requirement has a URI a clickable link symbol will be
   * displayed in the UI.
   * 
   * @return The URI of the requirement or <code>null</code>.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  String getURIString() throws RemoteException;

  /**
   * Sets the URI of the requirement. If a requirement has a URI a clickable link symbol will be
   * displayed in the UI.
   * 
   * @param uri
   *          The new URI or <code>null</code> to remove the link.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  void setURIString(String uri) throws RemoteException;

  /**
   * Returns the attributes and the associated values of the requirement. A requirement can have
   * additional attributes. Each attribute has a value associated for the requirement.
   * 
   * @return A map from attribute name to value.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Map<String, String> getAttributes() throws RemoteException;

  /**
   * Sets the attribute value of the requirement. A requirement can have additional attributes. Each
   * attribute has a value associated for the requirement. If the attribute does not exist yet it
   * will be created.
   * 
   * @param attributeName
   *          The name of the attribute.
   * @param value
   *          The new value of the attribute.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  void setAttribute(String attributeName, String value) throws RemoteException;

  /**
   * Delivers the list of attachments the requirement directly has. This list is shown in the UI as
   * attachments to the attribute &quot;Text&quot;. For attachments of other attributes see
   * {@link #getAttributeAttachments(String)}.
   * 
   * @return The List of attachments of the requirement.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteList<Attachment> getRequirementAttachments() throws RemoteException;

  /**
   * Adds a new attachment to the list of attachments every requirement can have directly. This list
   * is shown in the UI as attachments to the attribute &quot;Text&quot;. For attachments of other
   * attributes see {@link #createAttributeAttachment(String, String, byte[])}.
   * 
   * @param fileName
   *          The file name of the attachment. The file name is used to create temporary files when
   *          viewing the attachment and will be shown in the UI as tooltip in TPT.
   * @param content
   *          The content of the attachment.
   * @return The newly created attachment
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Attachment createRequirementAttachment(String fileName, byte[] content) throws RemoteException;

  /**
   * Delivers the list of attachments every requirement attribute can have. The default attributes
   * "ID", "Text", "URI", and "Comment" cannot have attachments but attachments of the requirement
   * (see {@link #getRequirementAttachments()}) will be shown in the UI as attachments of "Text".
   * 
   * @param attributeName
   *          The name of the attribute.
   * @return The List of attachments of the given attribute. Returns <code>null</code> if the
   *         attribute is not defined for the requirement.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteList<Attachment> getAttributeAttachments(String attributeName) throws RemoteException;

  /**
   * Removes the attachment of the requirement attribute with the given name.
   * 
   * @param attributeName
   *          The name of the attribute.
   * @param attachment
   *          The attachment to remove
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the attachment and the requirement from different projects or if the name of the
   *           attribute is empty or {@code null}
   */
  void removeAttributeAttachment(String attributeName, Attachment attachment)
      throws RemoteException;

  /**
   * Removes all attachments of the requirement attribute with the given name.
   * 
   * @param attributeName
   *          The name of the attribute.
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the name of the attribute is empty or {@code null}
   */
  void removeAllAttributeAttachments(String attributeName) throws RemoteException;

  /**
   * Adds a new attachment to the list of attachments every requirement attribute can have. If no
   * attribute with the given attribute name exists for the requirement the attribute will be
   * created and an empty value associated. The default attributes "ID", "Text", "URI", and
   * "Comment" cannot have attachments but attachments created with
   * {@link #createRequirementAttachment(String, byte[])} will be shown as attachments of "Text".
   * 
   * @param attributeName
   *          The name of the attribute.
   * @param fileName
   *          The file name of the attachment. The file name is used to create temporary files when
   *          viewing the attachment and will be shown as tooltip in TPT.
   * @param content
   *          The content of the attachment.
   * @return The newly created attachment
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Attachment createAttributeAttachment(String attributeName, String fileName, byte[] content)
      throws RemoteException;

  /**
   * Gets all assesslets linked to this requirement.
   * 
   * @return The list of linked assessments.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteCollection<Assessment> getLinkedAssessments() throws RemoteException;

  /**
   * Creates a link between this requirement and the given {@link Assessment}. If the argument is a
   * {@link AssessmentGroup} all assessments of the group will be added recursively. You can remove
   * a link by removing the <code>Assessment</code> from {@link #getLinkedAssessments()}.
   * 
   * @param aog
   *          The {@link Assessment} or {@link AssessmentGroup} to create links to this requirement.
   * @throws ApiException
   *           If the {@link RequirementType type} of the requirement is not linkable or if aog is
   *           not of the same project as the requirement.
   * @throws RemoteException
   *           remote communication problem
   */
  void linkAssessment(AssessmentOrGroup aog) throws ApiException, RemoteException;

  /**
   * Gets all test cases and variants linked to this requirement.
   * 
   * @return The list of linked test cases and variants.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteCollection<Scenario> getLinkedScenarios() throws RemoteException;

  /**
   * Creates a link between this requirement and the given {@link Scenario}. If the argument is a
   * {@link ScenarioGroup} all scenarios of the group will be added recursively. You can remove a
   * link by removing the <code>Scenario</code> from {@link #getLinkedScenarios()}.
   * 
   * @param sog
   *          The {@link Scenario} or {@link ScenarioOrGroup} to create links to this requirement.
   * @throws ApiException
   *           If the {@link RequirementType type} of the requirement is not linkable or if sog is
   *           not of the same project as the requirement.
   * @throws RemoteException
   *           remote communication problem
   */
  void linkScenario(ScenarioOrGroup sog) throws ApiException, RemoteException;

  /**
   * Gets all {@link CompareStep CompareSteps} linked to this requirement.
   * 
   * @return The list of linked compare steps.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteCollection<CompareStep> getLinkedCompareSteps() throws RemoteException;

  /**
   * Creates a link between this requirement and the given {@link CompareStep}. You can remove a
   * link by removing the <code>CompareStep</code> from {@link #getLinkedCompareSteps()}.
   * 
   * @param compareStep
   *          The {@link CompareStep} to create a link to this requirement.
   * @throws ApiException
   *           If the {@link RequirementType type} of the requirement is not linkable or if
   *           compareStep is not of the same project as the requirement.
   * @throws RemoteException
   *           remote communication problem
   */
  void linkCompareStep(CompareStep compareStep) throws ApiException, RemoteException;

  /**
   * Gets all {@link CompareStep CompareSteps} linked to this requirement.
   * 
   * @return The list of linked compare steps.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  RemoteCollection<GeneralShallNode> getLinkedShallNodes() throws RemoteException;

  /**
   * Creates a link between this requirement and the given {@link GeneralShallNode}. You can remove
   * a link by removing the <code>GeneralShallNode</code> from {@link #getLinkedShallNodes()}.
   * 
   * @param shallNode
   *          The {@link GeneralShallNode} to create a link to this requirement.
   * @throws ApiException
   *           If the {@link RequirementType type} of the requirement is not linkable or if
   *           shallNode is not of the same project as the requirement or if the shallNode does not
   *           belong to a {@link Assessment} of type {@link Assessment#CONDITION_TREE_TYPE}
   * @throws RemoteException
   *           remote communication problem
   */
  void linkShallNode(GeneralShallNode shallNode) throws ApiException, RemoteException;

  /**
   * Set the status of the requirement.
   * 
   * @param author
   *          The author of the status. <code>Null</code> will be reduced to an empty string.
   * @param comment
   *          The comment of the status. <code>Null</code> will be reduced to an empty string.
   * @param status
   *          The status type of the status.
   * @throws ApiException
   *           If <code>author</code> contains line break or the given <code>status</code> is
   *           invalid.
   * @throws RemoteException
   *           remote communication problem
   */
  void setStatus(String author, String comment, String status) throws ApiException, RemoteException;

  /**
   * Returns a list of all status history entries of the requirement, the newest comes first.
   * 
   * @return A list of all {@link Status Statuses}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<Status> getStatusHistory() throws RemoteException;

  /**
   * Sets the requirement status.
   * 
   * @param status
   *          to be set.
   * @throws ApiException
   *           If the the given status is not allowed or <code>null</code>.
   * @throws RemoteException
   *           remote communication problem
   * @deprecated use {@link #markAsDeleted(boolean)} instead.
   */
  @Deprecated
  void setStatus(RequirementStatus status) throws ApiException, RemoteException;

  /**
   * Returns the current requirement status.
   * 
   * @return the current requirement status.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @deprecated use {@link #isMarkedAsDeleted()} instead.
   */
  @Deprecated
  RequirementStatus getStatus() throws RemoteException;

  /**
   * Marks this requirement as modified.
   * 
   * @throws RemoteException
   *           remote communication problem
   * 
   * @see #markAsReviewed()
   * @deprecated Will be removed in Z-2027.03. Use {@link #setStatus(String, String, String)}
   *             instead.
   */
  @Deprecated
  void markAsModified() throws RemoteException;

  /**
   * Marks this requirement as reviewed.
   * 
   * 
   * @throws RemoteException
   *           remote communication problem
   * @see #markAsModified()
   * @deprecated Will be removed in Z-2027.03. Use {@link #setStatus(String, String, String)}
   *             instead.
   */
  @Deprecated
  void markAsReviewed() throws RemoteException;

  /**
   * Marks this requirement as deleted.
   * 
   * @param markAsDeleted
   *          if <code>true</code> the requirement will be marked as deleted and unmarked otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  void markAsDeleted(boolean markAsDeleted) throws RemoteException;

  /**
   * Returns <code>true</code> if the requirement is marked as deleted, otherwise
   * <code>false</code>.
   * 
   * @return <code>true</code> if requirement is marked as deleted, otherwise <code>false</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isMarkedAsDeleted() throws RemoteException;

  /**
   * Returns <code>true</code> if the requirement is marked as modified, otherwise
   * <code>false</code>.
   * 
   * @return <code>true</code> if requirement is marked as modified, otherwise <code>false</code>
   * 
   * @throws RemoteException
   *           remote communication problem
   * @deprecated Will be removed in Z-2027.03. Use {@link #getStatusHistory()} instead.
   */
  @Deprecated
  boolean isModified() throws RemoteException;

  /**
   * Returns <code>true</code> if the requirement is a sub-requirement, otherwise
   * <code>false</code>.
   * 
   * @return <code>true</code> if requirement is a sub-requirement, otherwise <code>false</code>
   * @throws RemoteException
   *           remote communication problem
   */
  boolean isSubRequirement() throws RemoteException;

  /**
   * Returns a parent {@link Requirement} if this is a sub-requirement, otherwise {@code null}
   * 
   * @return the parent requirement or {@code null} if this is not a sub-requirement
   * @throws RemoteException
   *           remote communication problem
   */
  Requirement getParentRequirement() throws RemoteException;

  /**
   * Adds a new sub-requirement to this requirement
   * 
   * @param id
   *          The unique ID of the requirement
   * 
   * @return The newly created requirement.
   * 
   * @throws ApiException
   *           If a requirement with the same id already exists.
   * @throws RemoteException
   *           remote communication problem
   */
  Requirement createSubRequirement(String id) throws ApiException, RemoteException;

  /**
   * Returns all sub-requirements of this {@link Requirement}
   * 
   * @return all sub-requirements of this {@link Requirement}
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  List<Requirement> getSubRequirements() throws RemoteException;

  /**
   * Returns all (explicit and implicit) to this {@link Requirement} linked {@link Scenario test
   * cases}.
   * 
   * @return all (explicit and implicit) to this {@link Requirement} linked {@link Scenario test
   *         cases}.
   * 
   * @throws RemoteException
   *           remote communication problem
   */
  Set<Scenario> getLinkedTestCases() throws RemoteException;

  /**
   *
   * Creates a formal requirement step of a given type at the given position. The indices of the
   * formal requirement steps start at 0.
   *
   * <p>
   * Formal requirements are a TPT feature that facilitates requirement-driven development. A clear,
   * requirements pattern helps to be concise, unambiguous, and testable. The formal requirements
   * are created and managed in <code>FormalRequirementNodeList</code> via and belong to a
   * requirement.
   * </p>
   *
   * @param index
   *          indicates the position where the new step shall be inserted in the step list
   *
   * @param type
   *          the type of the newly created step as String, possible types are : While, While Bounds
   *          Check, While Signal Compare, Between, When, Time Restriction, Shall, Shall Signal
   *          Compare, Shall Bounds Check, Set Signal Rule, Otherwise or Documentation
   * @return the newly created Step.
   *
   * @throws RemoteException
   *           remote communication problem
   * @throws ApiException
   *           if the given <code>type</code> does not exist.
   */
  public ConditionTreeNode createFormalRequirementNode(int index, String type)
      throws ApiException, RemoteException;

  /**
   * Returns all formal requirement steps of the requirement.
   *
   * @return the list of {@link ConditionTreeNode} in their given order.
   *
   * @throws RemoteException
   *           remote communication problem
   */
  public RemoteList<ConditionTreeNode> getFormalRequirementsNodes() throws RemoteException;

  /**
   * Returns <code>true</code> if this {@link Requirement} was loaded from the parent project.
   * 
   * @return <code>true</code> if this requirement was loaded from parent project,
   *         <code>false</code> otherwise.
   * 
   * @throws RemoteException
   *           remote communication problem
   * @see ParentProjectSettings
   */
  boolean isLoadedFromParent() throws RemoteException;

}
