package co.jp.nej.earth.model.ws;

import co.jp.nej.earth.model.Document;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

public class SaveImageRequest extends Request {
    @NotEmpty(message = "E0001,workspaceId")
    private String workspaceId;
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
