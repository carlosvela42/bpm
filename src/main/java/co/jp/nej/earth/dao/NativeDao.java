package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;

import java.sql.Connection;

/**
 * @author cuongtm
 */
public interface NativeDao {
    boolean iSchemaExits(String schemaName) throws EarthException;

    boolean createSchema(String schemaName, String password, Connection connection)
        throws EarthException;

    void createTables(Connection sysConn, String schemaName, String sqlFileName)
        throws EarthException;

    void addRoleMember(String user, String schemaName, String password, Connection connection) throws EarthException;
    boolean dropSchema(String schemaName, Connection connection) throws EarthException;

    boolean checkExistsTable(Connection connection, String schemaName, String tableName) throws EarthException;

    boolean isExistsUser(String user, Connection sysConnection) throws EarthException;
}
