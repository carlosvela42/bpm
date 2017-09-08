package co.jp.nej.earth.web.form;

import co.jp.nej.earth.model.ws.Request;
import org.hibernate.validator.constraints.NotEmpty;

public class ImageViewerForm extends Request {
    private String workspaceId;
    @NotEmpty(message = "E0001,workitemId")
    private String workItemId;
    private String processId;
    @NotEmpty(message = "E0001,folderItemNo")
    private String folderItemNo;
    @NotEmpty(message = "E0001,documentNo")
    private String documentNo;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }
    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }
    public String getWorkItemId() {
        return workItemId;
    }
    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }
    public String getFolderItemNo() {
        return folderItemNo;
    }
    public void setFolderItemNo(String folderItemNo) {
        this.folderItemNo = folderItemNo;
    }
    public String getDocumentNo() {
        return documentNo;
    }
    public void setDocumentNo(String documentNo) {
        this.documentNo = documentNo;
    }
}