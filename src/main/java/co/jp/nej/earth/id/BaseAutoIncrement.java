package co.jp.nej.earth.id;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.EarthId;
import co.jp.nej.earth.model.enums.DatabaseType;
import co.jp.nej.earth.service.BaseService;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EStringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BaseAutoIncrement extends BaseService {
    private static final Logger LOG = LoggerFactory.getLogger(BaseAutoIncrement.class);

    private static final int ID_LENGTH = 20;
    private static final String FORMAT_COUNT_PAD = "%012d";

    @Autowired
    protected DatabaseType databaseType;

    protected String generateId(EarthId earthId, String tableName, Map<String, String> inputColumns,
            String outPutColumn, String whereCondition) throws EarthException {
        return (String) executeTransaction(() -> {
            try {
                Connection connection = ConnectionManager.getEarthQueryFactory().getConnection();
                int count = insert(connection, tableName, inputColumns, outPutColumn, whereCondition);

                if (EarthId.WORKITEM.equals(earthId) || EarthId.EVENT.equals(earthId)) {
                    StringBuilder newId = new StringBuilder(ID_LENGTH);
                    newId.append(DateUtil.getCurrentShortDateString()).append(String.format(FORMAT_COUNT_PAD, count));
                    return newId.toString();
                } else {
                    return String.valueOf(count);
                }

            } catch (Exception e) {
                throw new EarthException(e);
            }
        });

    }

    protected int insert(Connection connection, String tableName, Map<String, String> inputColumns, String outputcolumn,
            String whereCondtion) throws EarthException {
        int value = 0;
        try {
            String sql = insertSql(tableName, inputColumns, outputcolumn, whereCondtion);
            PreparedStatement preparedStatement = databaseType.isOracle() ? connection.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS) : connection.prepareStatement(sql);

            Iterator<Entry<String, String>> it = inputColumns.entrySet().iterator();
            int index = 0;
            while (it.hasNext()) {
                Entry<String, String> element = it.next();
                preparedStatement.setString(++index, element.getValue());
            }

            ResultSet rs = null;
            if (databaseType.isOracle()) {
                preparedStatement.executeUpdate();
                rs = preparedStatement.getGeneratedKeys();
                value = Integer
                            .parseInt(getColumnByRowId(connection, tableName, outputcolumn, getFirstFromResultSet(rs)));
            } else if (databaseType.isSqlServer()) {
                rs = preparedStatement.executeQuery();
                if (rs != null && rs.next()) {
                    value = rs.getInt(1);
                }
            }

            return value;

        } catch (SQLException e) {
            throw new EarthException(e);
        }
    }

    private String getFirstFromResultSet(ResultSet rs) throws SQLException {
        if (rs!= null && rs.next()) {
            return rs.getString(1);
        }
        return Constant.ID_DEFAULT;
    }

    protected String insertSql(String tableName, Map<String, String> inputColumns, String outputColumn,
            String whereCondtion) {
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ").append(tableName).append("(");

        Iterator<Entry<String, String>> it = inputColumns.entrySet().iterator();
        int index = 0;
        while (it.hasNext()) {
            Entry<String, String> element = (Entry<String, String>)it.next();
            if (index == 0) {
                sql.append(element.getKey()).append(",").append(outputColumn);
            } else {
                sql.append(",").append(element.getKey());
            }
            index++;
        }

        if (databaseType.isSqlServer()) {
            sql.append(") ");
            sql.append(" OUTPUT INSERTED.").append(outputColumn);
            sql.append(" VALUES (?, ");

        } else if (databaseType.isOracle()) {
            sql.append(") VALUES (?, ");
        }

        sql.append("(SELECT COALESCE(MAX(").append(outputColumn).append("),0) + 1 FROM ");
        sql.append(tableName).append(" WHERE ");
        sql.append(whereCondtion).append(")");
        sql.append(", ?, ?, ?)");
        return sql.toString();
    }

    protected String getColumnByRowId(Connection connection, String tableName, String selectColumn, String rowIdValue)
            throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append(selectColumn);
        sql.append(" FROM ").append(tableName);
        sql.append(" WHERE ROWID = '" + rowIdValue + "'");

        PreparedStatement prepared = connection.prepareStatement(sql.toString());

        ResultSet rsInsertedItem = prepared.executeQuery();
        String value = EStringUtil.EMPTY;
        if (rsInsertedItem != null && rsInsertedItem.next()) {
            value = rsInsertedItem.getString(1);
        }
        return value;
    }
}
