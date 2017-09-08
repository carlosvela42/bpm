package co.jp.nej.earth.model.enums;

public enum DatabaseType {
    ORACLE("oracle.jdbc.driver.OracleDriver", "oracle_1.0.sql"),
    //SQL_SERVER("com.microsoft.sqlserver.jdbc.SQLServerDriver", "SQLServer_1.0.sql");
    SQL_SERVER("net.sourceforge.jtds.jdbc.Driver", "SQLServer_1.0.sql");

    private String driver;
    private String sourceFile;

    DatabaseType(String driver, String sourceFile) {
        this.driver = driver;
        this.sourceFile = sourceFile;
    }

    public boolean isOracle() {
        return DatabaseType.ORACLE.toString().equals(this.toString());
    }

    public boolean isSqlServer() {
        return DatabaseType.SQL_SERVER.toString().equals(this.toString());
    }

    public String getSourceFile() {
        return sourceFile;
    }

    @Override
    public String toString() {
        return driver;
    }
}
