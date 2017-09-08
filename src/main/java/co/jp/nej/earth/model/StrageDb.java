package co.jp.nej.earth.model;

import co.jp.nej.earth.contraints.NotEmptyAndValidSize;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.sql.QStrageDb;

/**
 * @author p-tvo-sonta
 */
public class StrageDb extends BaseModel<StrageDb> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer processId;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,process.SchemaName",
        messageLengthMax = "E0026,process.SchemaName,255")
    private String schemaName;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,process.DBuser",
        messageLengthMax = "E0026,process.DBuser,255")
    private String dbUser;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH_PASS,
        messageNotEmpty = "E0001,process.DBpasssword",
        messageLengthMax = "E0026,process.DBpasssword," + Constant.Regexp.MAX_LENGTH_PASS)
    private String dbPassword;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,process.Owner",
        messageLengthMax = "E0026,process.Owner," + Constant.Regexp.MAX_LENGTH)
    private String owner;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,process.DBserver",
        messageLengthMax = "E0026,process.DBserver,255")
    private String dbServer;
    private String dbType;
    private String dbPort;

    public StrageDb() {
        super();
        this.setqObj(QStrageDb.newInstance());
    }

    /**
     * @return the processId
     */
    public Integer getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    /**
     * @return the schemaName
     */
    public String getSchemaName() {
        return schemaName;
    }

    /**
     * @param schemaName the schemaName to set
     */
    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    /**
     * @return the dbUser
     */
    public String getDbUser() {
        return dbUser;
    }

    /**
     * @param dbUser the dbUser to set
     */
    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    /**
     * @return the dbPassword
     */
    public String getDbPassword() {
        return dbPassword;
    }

    /**
     * @param dbPassword the dbPassword to set
     */
    public void setDbPassword(String dbPassword) {
        this.dbPassword = dbPassword;
    }

    /**
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @param owner the owner to set
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * @return the dbServer
     */
    public String getDbServer() {
        return dbServer;
    }

    /**
     * @param dbServer the dbServer to set
     */
    public void setDbServer(String dbServer) {
        this.dbServer = dbServer;
    }

    /**
     * @return the dbType
     */
    public String getDbType() {
        return dbType;
    }

    /**
     * @param dbType the dbType to set
     */
    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    /**
     * @return the dbPort
     */
    public String getDbPort() {
        return dbPort;
    }

    /**
     * @param dbPort the dbPort to set
     */
    public void setDbPort(String dbPort) {
        this.dbPort = dbPort;
    }

}
