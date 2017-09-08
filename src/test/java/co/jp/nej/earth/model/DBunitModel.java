package co.jp.nej.earth.model;

import co.jp.nej.earth.model.constant.Constant;

import java.io.File;

public class DBunitModel {
    private String workspaceId;
    private File fileInput;

    public DBunitModel(String workspaceId, File fileInput) {
        this.workspaceId = workspaceId == null || workspaceId.isEmpty() ? Constant.EARTH_WORKSPACE_ID : workspaceId;
        this.fileInput = fileInput;
    }

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
        this.workspaceId = workspaceId == null || workspaceId.isEmpty() ? Constant.EARTH_WORKSPACE_ID : workspaceId;
    }

    /**
     * @return the fileInput
     */
    public File getFileInput() {
        return fileInput;
    }

    /**
     * @param fileInput the fileInput to set
     */
    public void setFileInput(File fileInput) {
        this.fileInput = fileInput;
    }

}
