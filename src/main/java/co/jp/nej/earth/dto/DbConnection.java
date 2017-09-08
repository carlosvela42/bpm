package co.jp.nej.earth.dto;

import co.jp.nej.earth.model.enums.DatabaseType;

public class DbConnection {
    private String schemaName;
    private String dbUser;
    private String dbPassword;
    private String owner;
    private String dbServer;
    private Integer port;
    private DatabaseType dbType;

    public DbConnection() {
        super();
    }

    public DbConnection(String schemaName, String dbUser, String dbPassword, String owner, Integer port,
                        DatabaseType dbType) {
        super();
        this.schemaName = schemaName;
        this.dbUser = dbUser;
        this.dbPassword = dbPassword;
        this.owner = owner;
        this.port = port;
        this.dbType = dbType;
    }

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

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public DatabaseType getDbType() {
        return dbType;
    }

    public void setDbType(DatabaseType dbType) {
        this.dbType = dbType;
    }

    public void setDbType(String dbType) {
        if (dbType.equals(DatabaseType.ORACLE.name())) {
            setDbType(DatabaseType.ORACLE);
        } else if (dbType.equals(DatabaseType.SQL_SERVER.name())) {
            setDbType(DatabaseType.SQL_SERVER);
        }
    }

    public String getDbServer() {
        return dbServer;
    }

    public void setDbServer(String dbServer) {
        this.dbServer = dbServer;
    }
}
