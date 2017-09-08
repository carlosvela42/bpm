package co.jp.nej.earth.config;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.MgrWorkspaceConnect;
import co.jp.nej.earth.model.StrageDb;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.util.CryptUtil;
import co.jp.nej.earth.util.EStringUtil;
import com.querydsl.sql.OracleTemplates;
import com.querydsl.sql.SQLServer2012Templates;
import com.querydsl.sql.SQLTemplates;
import com.querydsl.sql.types.DateTimeType;
import com.querydsl.sql.types.LocalDateType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author p-tvo-khanhnv
 */
@Configuration
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class JdbcConfig {
    private static final String ORACLE_URL_FORMAT = "jdbc:oracle:thin:@{server}:{port}:earth";
    private static final String SQL_SERVER_URL_FORMAT = "jdbc:jtds:sqlserver://{server}:{port};"
            + "databaseName={schema};integratedSecurity=false;";

    private static final Logger LOG = LoggerFactory.getLogger(JdbcConfig.class);

    @Value("${spring.datasource.schema-name}")
    private String schemaName;
    @Value("${spring.datasource.server}")
    private String server;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.maximum-pool-size}")
    private String maxPoolSize;
    @Value("${spring.datasource.db-type}")
    private String dbType;
    @Value("${spring.datasource.port}")
    private String port;

    public DataSource dataSource(String workspaceId, MgrWorkspaceConnect mgrConnect) {
        LOG.debug("Create new hikari data source with workspaceId=" + workspaceId);
        HikariConfig config = new HikariConfig();
        try {
            if (ConnectionManager.exists(workspaceId)) {
                return ConnectionManager.getDataSource(workspaceId);
            } else {
                if (DatabaseType.ORACLE.name().equals(dbType)) {
                    config.setDriverClassName(DatabaseType.ORACLE.toString());
                } else if (DatabaseType.SQL_SERVER.name().equals(dbType)) {
                    config.setDriverClassName(DatabaseType.SQL_SERVER.toString());
                    config.setConnectionTestQuery("SELECT 1");
                }

                config.setJdbcUrl(createDbUrl(mgrConnect));
                config.setUsername(mgrConnect.getDbUser());

                config.setPassword(CryptUtil.decryptData(mgrConnect.getDbPassword()));

            }

            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("useServerPrepStmts", "true");
            config.setMaximumPoolSize(Integer.parseInt(maxPoolSize));

        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return new HikariDataSource(config);
    }

    public DataSource systemDataSource() {
        LOG.debug("Create System Data Source");
        MgrWorkspaceConnect mgrConnect = new MgrWorkspaceConnect();
        try {
            mgrConnect.setDbUser(username);
            mgrConnect.setWorkspaceId(Integer.parseInt(Constant.EARTH_WORKSPACE_ID));
            mgrConnect.setSchemaName(schemaName);
            mgrConnect.setDbPassword(CryptUtil.encryptData(password));
            mgrConnect.setDbServer(server);
            mgrConnect.setDbType(dbType);
            mgrConnect.setPort(Integer.parseInt(port));
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return dataSource(Constant.EARTH_WORKSPACE_ID, mgrConnect);
    }

    public DataSource strDbDataSource(StrageDb strageDb) {
        LOG.debug("Create new Data Source");
        MgrWorkspaceConnect mgrConnect = new MgrWorkspaceConnect();
        try {
            mgrConnect.setDbUser(strageDb.getDbUser());
            mgrConnect.setSchemaName(strageDb.getSchemaName());
            mgrConnect.setDbPassword(CryptUtil.encryptData(strageDb.getDbPassword()));
            mgrConnect.setDbServer(strageDb.getDbServer());
            mgrConnect.setDbType(dbType);
            mgrConnect.setPort(Integer.parseInt(port));
        } catch(Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return dataSource("DocumentSavePlace", mgrConnect);
    }

    @Bean
    public PlatformTransactionManager getPlatformTransactionManager() {
        return new DataSourceTransactionManager(systemDataSource());
    }

    private String createDbUrl(MgrWorkspaceConnect mgrConnect) {
        LOG.debug("Create Database Url with workspaceId=" + mgrConnect.getWorkspaceId());
        DatabaseType dbType = databaseType();
        String dbUrl = EStringUtil.EMPTY;
        if (dbType.isOracle()) {
            dbUrl = ORACLE_URL_FORMAT;
        } else {
            dbUrl = SQL_SERVER_URL_FORMAT;
        }

        dbUrl = dbUrl.replace("{server}", mgrConnect.getDbServer())
                .replace("{port}", String.valueOf(mgrConnect.getPort()))
                .replace("{schema}", mgrConnect.getSchemaName());
        LOG.debug("dbUrl=" + dbUrl);

        return dbUrl;
    }

    @Bean
    public DatabaseType databaseType() {
        if (dbType.equals(DatabaseType.ORACLE.name())) {
            return DatabaseType.ORACLE;
        }
        return DatabaseType.SQL_SERVER;
    }

    public com.querydsl.sql.Configuration querydslConfiguration() {
        DatabaseType dbType = databaseType();
        SQLTemplates templates = null;
        if (dbType.isOracle()) {
            templates = OracleTemplates.builder().build();
        } else {
            templates = SQLServer2012Templates.builder().build();
        }

        com.querydsl.sql.Configuration configuration = new com.querydsl.sql.Configuration(templates);
        configuration.setExceptionTranslator(new SpringExceptionTranslator());
        configuration.register(new DateTimeType());
        configuration.register(new LocalDateType());
        return configuration;
    }

    @PostConstruct
    public void createSystemDataSource() throws EarthException {
        // Create System Factory.
        ConnectionManager.addDataSource(Constant.EARTH_WORKSPACE_ID, systemDataSource());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
     * @return the server
     */
    public String getServer() {
        return server;
    }

    /**
     * @param server the server to set
     */
    public void setServer(String server) {
        this.server = server;
    }

    /**
     * @return the maxPoolSize
     */
    public String getMaxPoolSize() {
        return maxPoolSize;
    }

    /**
     * @param maxPoolSize the maxPoolSize to set
     */
    public void setMaxPoolSize(String maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
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
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }
}
