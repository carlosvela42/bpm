package co.jp.nej.earth.model.form;

import java.util.List;

/**
 * @author p-tvo-sonta
 */
public class DeleteWorkspaceListForm {
    private List<Integer> listIds;
    private String workspaceId;

    public List<Integer> getListIds() {
        return listIds;
    }

    public void setListIds(List<Integer> listIds) {
        this.listIds = listIds;
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
