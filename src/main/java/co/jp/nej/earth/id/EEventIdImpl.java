package co.jp.nej.earth.id;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant.EarthId;
import co.jp.nej.earth.model.sql.QMgrEventId;
import co.jp.nej.earth.util.DateUtil;

@Service
public class EEventIdImpl extends BaseAutoIncrement implements EEventId {

    @Override
    public String getAutoId(String sessionId) throws EarthException {
        QMgrEventId qMgrEventId = QMgrEventId.newInstance();
        String tableName = qMgrEventId.getTableName();
        String issueDateColumn = qMgrEventId.issueDate.getMetadata().getName();
        String countColumn = qMgrEventId.count.getMetadata().getName();
        String sessionIdColumn = qMgrEventId.sessionId.getMetadata().getName();
        String incrementDateTimeColumn = qMgrEventId.incrementDateTime.getMetadata().getName();
        String lastUpdateTimeColumn = qMgrEventId.lastUpdateTime.getMetadata().getName();

        Map<String, String> inputColumns = new LinkedHashMap<>();
        inputColumns.put(issueDateColumn, DateUtil.getCurrentShortDateString());
        inputColumns.put(incrementDateTimeColumn, DateUtil.getCurrentDateString());
        inputColumns.put(sessionIdColumn, sessionId);
        inputColumns.put(lastUpdateTimeColumn, DateUtil.getCurrentDateString());

        String whereCondition = issueDateColumn + "='" + DateUtil.getCurrentShortDateString() + "'";

        return this.generateId(EarthId.EVENT, tableName, inputColumns, countColumn, whereCondition);
    }
}
