package co.jp.nej.earth.web.form;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Form handle request param of edit work item screen
 *
 * @author DaoPQ
 * @version 1.0
 */
public class EditWorkItemForm {

    @NotEmpty(message = "E0001,workspaceId")
    private String workspaceId;

    @NotEmpty(message = "E0001,workitemId")
    private String workItemId;

    private String processId;
    private String folderItemNo;
    private String documentNo;
    private String layerNo;
    private String ownerId;
    private String type;

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    /**
     * @return the workItemId
     */
    public String getWorkItemId() {
        return workItemId;
    }

    /**
     * @param workItemId the workItemId to set
     */
    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }

    /**
     * @return the processId
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }

    /**
     * @return the folderItemNo
     */
    public String getFolderItemNo() {
        return folderItemNo;
    }

    /**
     * @param folderItemNo the folderItemNo to set
     */
    public void setFolderItemNo(String folderItemNo) {
        this.folderItemNo = folderItemNo;
    }

    /**
     * @return the documentNo
     */
    public String getDocumentNo() {
        return documentNo;
    }

    /**
     * @param documentNo the documentNo to set
     */
    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the layerNo
     */
    public String getLayerNo() {
        return layerNo;
    }

    /**
     * @return the ownerId
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * @param layerNo the layerNo to set
     */
    public void setLayerNo(String layerNo) {
        this.layerNo = layerNo;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
