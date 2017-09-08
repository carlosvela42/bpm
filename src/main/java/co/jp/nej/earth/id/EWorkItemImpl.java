package co.jp.nej.earth.id;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant.EarthId;
import co.jp.nej.earth.model.sql.QMgrWorkItemId;
import co.jp.nej.earth.util.DateUtil;

/**
 * Class EWorkItemImpl
 *
 * @author daopq
 * @version 1.0
 */
@Service
public class EWorkItemImpl extends BaseAutoIncrement implements EWorkItemId {

    /**
     * Get auto id of work item
     *
     * @return
     * @throws EarthException
     */
    @Override
    public String getAutoId(String sessionId) throws EarthException {

        QMgrWorkItemId qMgrWorkItemId = QMgrWorkItemId.newInstance();
        String tableName = qMgrWorkItemId.getTableName();
        String issueDateColumn = qMgrWorkItemId.issueDate.getMetadata().getName();
        String countColumn = qMgrWorkItemId.count.getMetadata().getName();
        String sessionIdColumn = qMgrWorkItemId.sessionId.getMetadata().getName();
        String incrementDateTimeColumn = qMgrWorkItemId.incrementDateTime.getMetadata().getName();
        String lastUpdateTimeColumn = qMgrWorkItemId.lastUpdateTime.getMetadata().getName();

        Map<String, String> inputColumns = new LinkedHashMap<>();
        inputColumns.put(issueDateColumn, DateUtil.getCurrentShortDateString());
        inputColumns.put(incrementDateTimeColumn, DateUtil.getCurrentDateString());
        inputColumns.put(sessionIdColumn, sessionId);
        inputColumns.put(lastUpdateTimeColumn, DateUtil.getCurrentDateString());

        String whereCondition = issueDateColumn + "='" + DateUtil.getCurrentShortDateString() + "'";

        return this.generateId(EarthId.WORKITEM, tableName, inputColumns, countColumn, whereCondition);
    }
}
