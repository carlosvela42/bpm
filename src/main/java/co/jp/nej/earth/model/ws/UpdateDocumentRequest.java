package co.jp.nej.earth.model.ws;

import co.jp.nej.earth.model.Document;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

public class UpdateDocumentRequest extends Request {
    @NotEmpty(message = "E001,workspaceId")
    private String workspaceId;
    @NotEmpty(message = "E001,workItemId")
    private String workItemId;
    @NotEmpty(message = "E001,folderItemNo")
    private String folderItemNo;
    @Valid
    private Document document;

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
     * @return the document
     */
    public Document getDocument() {
        return document;
    }

    /**
     * @param document the document to set
     */
    public void setDocument(Document document) {
        this.document = document;
    }

}
