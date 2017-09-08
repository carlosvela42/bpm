package co.jp.nej.earth.manager.connection;

import co.jp.nej.earth.config.JdbcConfig;
import co.jp.nej.earth.config.SpringConnectionProvider;
import co.jp.nej.earth.exception.ConnectionFailedException;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.MgrWorkspaceConnect;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.model.constant.Constant.ScreenItem;
import co.jp.nej.earth.service.WorkspaceService;
import co.jp.nej.earth.util.EMessageResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.inject.Provider;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@Component
public class ConnectionManager {

    private static Map<String, DataSource> dataSources = new HashMap<String, DataSource>();
    private static Map<String, PlatformTransactionManager> mgrTransactions
        = new HashMap<>();

    private static JdbcConfig config;
    private static WorkspaceService wkService;
    private static EMessageResource messges;

    @Autowired
    private JdbcConfig jdbcConfig;

    @Autowired
    private WorkspaceService workspaceService;

    @Autowired
    private EMessageResource messageSource;

    @PostConstruct
    public void initConnectionManager() {
        config = jdbcConfig;
        wkService = workspaceService;
        messges = messageSource;
    }

    public static void addDataSource(String id, DataSource dataSource) {
        dataSources.put(id, dataSource);
        mgrTransactions.put(id, new DataSourceTransactionManager(dataSource));
    }

    public static DataSource getDataSource(String id) {
        return dataSources.get(id);
    }

    public static void remove(String id) {
        if (exists(id)) {
            dataSources.remove(id);
            mgrTransactions.remove(id);
        }
    }

    public static EarthQueryFactory getEarthQueryFactory() throws EarthException {
        return getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
    }

    public static EarthQueryFactory getEarthQueryFactory(String id) throws EarthException {

        if (StringUtils.isEmpty(id)) {
            throw new EarthException(messges.get(ErrorCode.E0001, new String[]{ScreenItem.WORKSPACE_ID}));
        }

        if (!exists(id)) {
            // Get DataSource from Database.
            getDataSourceFromDb(id);
        }

        EarthQueryFactory earthQueryFactory = createEarthQueryFactory(dataSources.get(id));
        return earthQueryFactory;
    }

    public static boolean exists(String id) {
        return (dataSources.containsKey(id));
    }

    public static EarthQueryFactory createEarthQueryFactory(DataSource dataSource) {
        Provider<Connection> sysProvider = new SpringConnectionProvider(dataSource);
        return new EarthQueryFactory(config.querydslConfiguration(), sysProvider);
    }

    public static PlatformTransactionManager getTransactionManager(String id) throws EarthException {
        if (!exists(id)) {
            // Get DataSource from Database.
            try {
                getDataSourceFromDb(id);
            } catch (Exception exception) {
                throw new ConnectionFailedException(exception, id);
            }
        }

        return mgrTransactions.get(id);
    }

    private static void getDataSourceFromDb(String id) throws EarthException {
        MgrWorkspaceConnect connection = wkService.getMgrConnectionByWorkspaceId(id);
        if (connection == null) {
            throw new EarthException(
                messges.get("connection.notfound", new String[]{id}));
        } else {
            DataSource dataSource = config.dataSource(connection.getStringWorkspaceId(), connection);
            addDataSource(id, dataSource);
        }
    }

    public static void commitConnection(Connection conn) throws EarthException {
        try {
            if (conn != null) {
                conn.commit();
            }
        } catch (SQLException e) {
            throw new EarthException(e);
        }
    }

    public static void rollbackConnection(Connection conn) throws EarthException {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (SQLException e) {
            throw new EarthException(e);
        }
    }
}
