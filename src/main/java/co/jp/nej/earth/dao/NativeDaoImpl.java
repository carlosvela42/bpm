package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.util.EStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static co.jp.nej.earth.dao.WorkspaceDaoImpl.ADD_ROLE_MEMBER;
import static co.jp.nej.earth.dao.WorkspaceDaoImpl.CREATE_SCHEMA_ERROR_CODE;
import static co.jp.nej.earth.dao.WorkspaceDaoImpl.CREATE_TABLES_ERROR_CODE;

/**
 * Created by cuongtm on 2017/08/08.
 */
@Repository
public class NativeDaoImpl implements NativeDao {
    private static final String ENDSTRING = ";";
    private static final String STRING = "';";
    private static final String PASSWORD_POLICY_OFF = "', CHECK_POLICY=OFF;";

    @Autowired
    private DatabaseType databaseType;

    public boolean iSchemaExits(String schemaName) throws EarthException {
        try {
            Connection connection = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID).getConnection();
            Statement stmt = connection.createStatement();

            String query;
            String sanitizedSchemaName = schemaName.toUpperCase();
            if(databaseType.isOracle()) {
                query = "SELECT * FROM all_users WHERE USERNAME = '" + sanitizedSchemaName + "'";
            } else {
                query = "SELECT name FROM master.dbo.sysdatabases WHERE name = N'" + sanitizedSchemaName + "'";
            }
            ResultSet resultSet = stmt.executeQuery(query);
            return resultSet.next();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    public boolean isExistsUser(String user, Connection sysConnection) throws EarthException {
        ResultSet resultSet = null;
        try {
            if (databaseType.isSqlServer()) {
                String checkExistsUserSql = Constant.WorkSpace
                    .CHECK_EXISTS_USER_LOGIN_SQL_SERVER.replace("{userDb}", user);
                PreparedStatement prepared = sysConnection.prepareStatement(checkExistsUserSql);
                resultSet = prepared.executeQuery();
            }

            return  (resultSet != null && resultSet.next() && (!EStringUtil.isEmpty(resultSet.getString(1))));
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    public boolean dropSchema(String schemaName, Connection connection) throws EarthException {
        try {
            Statement stmt = connection.createStatement();
            String dropSchema = databaseType.isSqlServer()
                ? Constant.WorkSpace.DROP_DATABASE_SQLSERVER.replace("{databaseName}", schemaName)
                : Constant.WorkSpace.DROP_USER_ORACLE.replace("{userName}", schemaName);

            stmt.execute(dropSchema);
        } catch (Exception e) {
            throw new EarthException(e);
        }
        return true;
    }

    @Override
    public void addRoleMember(String user, String schemaName, String password, Connection connection) throws
        EarthException {
        try {
            if (databaseType.isSqlServer()) {
                Statement stmt = connection.createStatement();
                String createLogin = Constant.WorkSpace.CREATE_LOGIN + user
                    + Constant.WorkSpace.WITH_PASSWORD + password
                    + PASSWORD_POLICY_OFF;
                String createUser = Constant.WorkSpace.CREATE_USER + user
                    + Constant.WorkSpace.WITH_DEFAULT_SCHEMA + schemaName
                    + ENDSTRING;
                String execAddRole = Constant.WorkSpace.EXEC_SYS_ADMIN_ROLE.replace("{schemaName}", schemaName)
                    .replace("{dbUserName}", user);

                if (!isExistsUser(user, connection)) {
                    stmt.execute(createLogin);
                    stmt.execute(createUser);
                }

                stmt.execute(execAddRole);
                stmt.executeBatch();
            }

        } catch (Exception ex) {
            throw new EarthException(ADD_ROLE_MEMBER, ex);
        }
    }
    @Override
    public boolean checkExistsTable(Connection connection, String schemaName, String tableName) throws EarthException {
        try {
            String checkExistsTableSql = databaseType.isOracle() ? Constant.WorkSpace.CHECK_EXISTS_TABLE_ORACLE
                : Constant.WorkSpace.CHECK_EXISTS_TABLE_SQL_SERVER;
            checkExistsTableSql = checkExistsTableSql
                .replace("{tableNameUppercase}", tableName.toUpperCase())
                .replace("{ownerUppercase}", schemaName.toUpperCase());

            PreparedStatement prepared = connection.prepareStatement(checkExistsTableSql);
            ResultSet resultSet = prepared.executeQuery();

            return  (resultSet != null && resultSet.next() && (!EStringUtil.isEmpty(resultSet.getString(1))));
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    public boolean createSchema(String schemaName, String password, Connection connection)
        throws EarthException {
        try {
            Statement stmt = connection.createStatement();
            if (databaseType.isOracle()) {
                stmt.execute(Constant.WorkSpace.CREATE_USER + schemaName + Constant.WorkSpace.IDENTIFIED_BY + password);
                stmt.execute(Constant.WorkSpace.GRANT_SESSION + schemaName);
                stmt.execute(Constant.WorkSpace.GRANT_DBA + schemaName);
            } else {
                String createDatabase = Constant.WorkSpace.CREATE_DATABASE + schemaName + ENDSTRING;

                stmt.execute(createDatabase);
            }
        } catch (Exception e) {
            throw new EarthException(CREATE_SCHEMA_ERROR_CODE, e);
        }
        return true;
    }

    public void createTables(Connection sysConn, String schemaName, String sqlFileName)
        throws EarthException {
        BufferedReader reader;
        String line = EStringUtil.EMPTY;
        String[] parts;
        try {
            StringBuilder buffer = new StringBuilder();
            Statement stmt = sysConn.createStatement();
            if (databaseType.isOracle()) {
                String scriptFilePath = getClass().getClassLoader().getResource(sqlFileName).getPath();
                reader = new BufferedReader(new FileReader(scriptFilePath));
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                parts = buffer.toString()
                    .replaceAll(Constant.WorkSpace.CHARACTER_REPLACE, schemaName.toUpperCase())
                    .split(";");
            } else {
                String scriptFilePath = getClass().getClassLoader().getResource(sqlFileName).getPath();
                buffer.append(Constant.WorkSpace.USE + schemaName + ENDSTRING);
                reader = new BufferedReader(new FileReader(scriptFilePath));
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                }
                buffer.append(";");

                parts = buffer.toString().split(";");
            }
            for (String part : parts) {
                stmt.addBatch(part);
            }
            stmt.executeBatch();
            reader.close();
        } catch (Exception ex) {
            throw new EarthException(CREATE_TABLES_ERROR_CODE, ex);
        }
    }
}