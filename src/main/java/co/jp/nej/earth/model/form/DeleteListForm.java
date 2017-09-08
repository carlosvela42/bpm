package co.jp.nej.earth.model.form;

import java.util.List;

/**
 * @author p-tvo-sonta
 */
public class DeleteListForm {
    private List<String> listIds;
    private String workspaceId;

    public List<String> getListIds() {
        return listIds;
    }

    public void setListIds(List<String> listIds) {
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
