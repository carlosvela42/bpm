package co.jp.nej.earth;

import co.jp.nej.earth.config.AppConfig;
import co.jp.nej.earth.config.JdbcConfig;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.DBunitModel;
import co.jp.nej.earth.model.MgrWorkspaceConnect;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.service.WorkspaceService;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class BaseTest {
    @Autowired
    private JdbcConfig config;
    @Autowired
    private WorkspaceService wkService;
    @Autowired
    private MessageSource messageSource;

    public void resetSystemDataSource() {
        // Create System Query Factory.
        MgrWorkspaceConnect sysConnection = getSystemConnection();
        if (ConnectionManager.exists(Constant.EARTH_WORKSPACE_ID)) {
            ConnectionManager.remove(Constant.EARTH_WORKSPACE_ID);
        }

        ConnectionManager.addDataSource(Constant.EARTH_WORKSPACE_ID,
                config.dataSource(sysConnection.getStringWorkspaceId(), sysConnection));
    }

    public MgrWorkspaceConnect getSystemConnection() {

        Properties prop = new Properties();
        InputStream input = null;
        MgrWorkspaceConnect earthConn = new MgrWorkspaceConnect();
        try {

            input = getClass().getResourceAsStream("resource/config.properties");

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            earthConn.setSchemaName(prop.getProperty("datasource.schema-name"));
            earthConn.setDbPassword(prop.getProperty("datasource.password"));
            earthConn.setDbServer(prop.getProperty("datasource.server"));
            earthConn.setDbUser(prop.getProperty("datasource.user"));
            earthConn.setPort(Integer.parseInt(prop.getProperty("datasource.port")));
            String strDbType = prop.getProperty("datasource.db-type");
            if ("ORACLE".equals(strDbType)) {
                earthConn.setDbType(DatabaseType.ORACLE);
            } else if ("SQLSERVER".equals(strDbType)) {
                earthConn.setDbType(DatabaseType.SQL_SERVER);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return earthConn;
    }

    protected void excuteTest(String workspaceId, File fileInput, TestCaller caller) throws Exception {
        if (workspaceId == null || workspaceId.isEmpty()) {
            workspaceId = Constant.EARTH_WORKSPACE_ID;
        }
        IDatabaseConnection connection = createConnection(workspaceId);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(fileInput);
        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
        caller.execute();
        connection = createConnection(workspaceId);
        try {
            DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

    protected void executeTestForManySchema(List<DBunitModel> models, TestCaller caller) throws Exception {
        for (DBunitModel model : models) {
            setUpDatabase(model.getWorkspaceId(), model.getFileInput());
        }
        caller.execute();
        for (DBunitModel model : models) {
            deleteAllData(model.getWorkspaceId(), model.getFileInput());
        }
    }

    private IDatabaseConnection createConnection(String workspaceId) throws Exception {
        DataSource dataSource = null;
        MgrWorkspaceConnect mgrWorkspaceConnect = wkService.getMgrConnectionByWorkspaceId(workspaceId);
        if (mgrWorkspaceConnect == null) {
            throw new EarthException(
                    messageSource.getMessage("connection.notfound", new String[]{"workspaceId"}, Locale.ENGLISH));
        } else {
            dataSource = config.dataSource(mgrWorkspaceConnect.getStringWorkspaceId(), mgrWorkspaceConnect);
        }
        Connection jdbcConnection = dataSource.getConnection();
        IDatabaseConnection connection = new DatabaseConnection(jdbcConnection, jdbcConnection.getSchema());
        return connection;
    }

    protected void setUpDatabase(String workspaceId, File fileInput) throws Exception {
        IDatabaseConnection connection = createConnection(workspaceId);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(fileInput);
        try {
            DatabaseOperation.CLEAN_INSERT.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

    protected void deleteAllData(String workspaceId, File fileInput) throws Exception {
        IDatabaseConnection connection = createConnection(workspaceId);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(fileInput);
        try {
            DatabaseOperation.DELETE_ALL.execute(connection, dataSet);
        } finally {
            connection.close();
        }
    }

    @Test
    public void testResetSystemDataSource() {
        // resetSystemDataSource();
        Assert.assertTrue(ConnectionManager.exists(Constant.EARTH_WORKSPACE_ID));

    }

    public interface TestCaller {
        void execute() throws Exception;
    }
}
