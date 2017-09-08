package co.jp.nej.earth.model;

import co.jp.nej.earth.dto.DbConnection;

public class MgrWorkspaceConnect extends DbConnection {
    private Integer workspaceId;
    private String lastUpdateTime;
    private boolean changePassword;

    private MgrWorkspace mgrWorkspace;

    /**
     * @return the workspaceId
     */
    public Integer getWorkspaceId() {
        return workspaceId;
    }

    /**
     * @return the workspaceId
     */
    public String getStringWorkspaceId() {
        return String.valueOf(workspaceId);
    }

    /**
     * @param workspaceId the workspaceId to set
     */
    public void setWorkspaceId(Integer workspaceId) {
        this.workspaceId = workspaceId;
    }

    /**
     * @return the lastUpdateTime
     */
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    /**
     * @param lastUpdateTime the lastUpdateTime to set
     */
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    public MgrWorkspace getMgrWorkspace() {
        return mgrWorkspace;
    }

    public void setMgrWorkspace(MgrWorkspace mgrWorkspace) {
        this.mgrWorkspace = mgrWorkspace;
    }
}
