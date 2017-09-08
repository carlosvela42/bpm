package co.jp.nej.earth.model.ws;

import co.jp.nej.earth.model.FolderItem;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

public class UpdateFolderItemRequest extends Request {
    @NotEmpty(message = "E0001,workspaceId")
    private String workspaceId;
    @NotEmpty(message = "E0001,workitemId")
    private String workItemId;
    @Valid
    private FolderItem folderItem;

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
     * @return the folderItem
     */
    public FolderItem getFolderItem() {
        return folderItem;
    }

    /**
     * @param folderItem the folderItem to set
     */
    public void setFolderItem(FolderItem folderItem) {
        this.folderItem = folderItem;
    }

}
