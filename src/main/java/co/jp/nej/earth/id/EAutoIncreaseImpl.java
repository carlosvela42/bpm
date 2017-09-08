package co.jp.nej.earth.id;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant.EarthId;
import co.jp.nej.earth.model.sql.QMgrIncrement;
import co.jp.nej.earth.util.DateUtil;

@Service
public class EAutoIncreaseImpl extends BaseAutoIncrement implements EAutoIncrease {
    @Override
    public String getAutoId(EarthId earthId, String sessionId) throws EarthException {
        QMgrIncrement qMgrIncrement = QMgrIncrement.newInstance();
        String tableName = qMgrIncrement.getTableName();
        String incrementTypeColumn = qMgrIncrement.incrementType.getMetadata().getName();
        String incrementDataColumn = qMgrIncrement.incrementData.getMetadata().getName();
        String incrementDateTimeColumn = qMgrIncrement.incrementDateTime.getMetadata().getName();
        String sessionIdColumn = qMgrIncrement.sessionId.getMetadata().getName();
        String lastUpdateTimeColumn = qMgrIncrement.lastUpdateTime.getMetadata().getName();

        Map<String, String> inputColumns = new LinkedHashMap<>();
        inputColumns.put(incrementTypeColumn, earthId.getValue());
        inputColumns.put(incrementDateTimeColumn, DateUtil.getCurrentDateString());
        inputColumns.put(sessionIdColumn, sessionId);
        inputColumns.put(lastUpdateTimeColumn, DateUtil.getCurrentDateString());

        String whereCondtion = incrementTypeColumn + "='" + earthId.getValue() + "'";

        return this.generateId(earthId, tableName, inputColumns, incrementDataColumn, whereCondtion);
    }
}
