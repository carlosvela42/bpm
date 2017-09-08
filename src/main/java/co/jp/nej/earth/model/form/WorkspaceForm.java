package co.jp.nej.earth.model.form;

import co.jp.nej.earth.contraints.NotEmptyAndValidSize;
import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.constant.Constant;

public class WorkspaceForm extends BaseModel<MgrWorkspace> {

    private Integer workspaceId;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,workspace.workspaceName",
        messageLengthMax = "E0026,workspace.workspaceName," + Constant.Regexp.MAX_LENGTH)
    private String workspaceName;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_20,
        messageNotEmpty = "E0001,workspace.schemaName",
        messageLengthMax = "E0026,workspace.schemaName," + Constant.Regexp.MAX_20)
    private String schemaName;

    // @Pattern(regexp = Constant.Regexp.ALPHABETS_VALIDATION, message = "E0011,dbUser")
    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_20,
        messageNotEmpty = "E0001,workspace.dbUser",
        messageLengthMax = "E0026,workspace.dbUser," + Constant.Regexp.MAX_20)
    private String dbUser;

    private boolean changePassword;

    @NotEmptyAndValidSize(max = Constant.Regexp.MAX_LENGTH_PASS,
        messageLengthMax = "E0026,workspace.dbPassword,515")
    private String dbPassword;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_20,
        messageNotEmpty = "E0001,workspace.owner",
        messageLengthMax = "E0026,workspace.owner," + Constant.Regexp.MAX_20)
    // @Pattern(regexp = Constant.Regexp.ALPHABETS_VALIDATION, message = "E0011,owner")
    private String owner;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,workspace.dbServer",
        messageLengthMax = "E0026,workspace.dbServer,255")
    private String dbServer;

    private String dbType;

    public String getSchemaName() {
        return schemaName;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getDbServer() {
        return dbServer;
    }

    public void setDbServer(String dbServer) {
        this.dbServer = dbServer;
    }

    public Integer getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(Integer workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }
}
