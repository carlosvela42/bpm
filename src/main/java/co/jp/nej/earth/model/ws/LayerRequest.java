package co.jp.nej.earth.model.ws;

import org.hibernate.validator.constraints.NotEmpty;

public class LayerRequest extends Request {
    @NotEmpty(message = "E0001,workspaceId")
    private String workspaceId;
    @NotEmpty(message = "E0001,workitemId")
    private String workItemId;
    @NotEmpty(message = "E0001,folderItemNo")
    private String folderItemNo;
    @NotEmpty(message = "E0001,documentNo")
    private String documentNo;
    @NotEmpty(message = "E0001,layerNo")
    private String layerNo;

    @NotEmpty(message = "E0001,ownerId")
    private String ownerId;

    /**
     * @return the workspaceId
     */
    public String getWorkspaceId() {
        return workspaceId;
    }

    /**
     * @param workspaceId the workspaceId to set
     */
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
     * @return the layerNo
     */
    public String getLayerNo() {
        return layerNo;
    }

    /**
     * @param layerNo the layerNo to set
     */
    public void setLayerNo(String layerNo) {
        this.layerNo = layerNo;
    }

    /**
     * @return the ownerId
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * @param ownerId the ownerId to set
     */
    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
}
