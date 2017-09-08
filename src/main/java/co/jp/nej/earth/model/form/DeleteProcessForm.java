package co.jp.nej.earth.model.form;

import java.util.List;

/**
 * @author p-tvo-sonta
 */
public class DeleteProcessForm {
    private List<Integer> processIds;
    private boolean confirmDelete;
    private String workspaceId;

    /**
     * @return the processIds
     */
    public List<Integer> getProcessIds() {
        return processIds;
    }

    /**
     * @param processIds the processIds to set
     */
    public void setProcessIds(List<Integer> processIds) {
        this.processIds = processIds;
    }

    /**
     * @return the confirmDelete
     */
    public boolean isConfirmDelete() {
        return confirmDelete;
    }

    /**
     * @param confirmDelete the confirmDelete to set
     */
    public void setConfirmDelete(boolean confirmDelete) {
        this.confirmDelete = confirmDelete;
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
        this.workspaceId = workspaceId;
    }

}
