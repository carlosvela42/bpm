package co.jp.nej.earth.dto;

public class WorkspaceDto {
    private String workspaceId;
    private String workspaceName;
    private DbConnection connnection;

    public WorkspaceDto() {
        super();
    }

    public WorkspaceDto(String workspaceId, String workspaceName, DbConnection connnection) {
        super();
        this.workspaceId = workspaceId;
        this.workspaceName = workspaceName;
        this.connnection = connnection;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public DbConnection getConnnection() {
        return connnection;
    }

    public void setConnnection(DbConnection connnection) {
        this.connnection = connnection;
    }
}
