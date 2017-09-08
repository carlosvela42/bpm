package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrWorkItemId;
import co.jp.nej.earth.model.sql.QMgrWorkItemId;
import co.jp.nej.earth.util.DateUtil;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WorkItemIdDaoImpl extends BaseDaoImpl<MgrWorkItemId> implements WorkItemIdDao {

    private static final QMgrWorkItemId qMgrWorkItemId = QMgrWorkItemId.newInstance();

    private static final Logger LOG = LoggerFactory.getLogger(EventIdDaoImpl.class);

    public WorkItemIdDaoImpl() throws Exception {
        super();
    }

    /**
     * getByIssueToday
     *
     * @return
     * @throws EarthException
     */
    public MgrWorkItemId getByIssueToday() throws EarthException {
        QBean<MgrWorkItemId> selectList = Projections.bean(MgrWorkItemId.class, qMgrWorkItemId.all());
        return ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
            .select(selectList)
            .from(qMgrWorkItemId)
            .where(qMgrWorkItemId.issueDate.contains(DateUtil.getCurrentShortDateString())).fetchOne();
    }

    /**
     * getByIssueDate
     *
     * @param issueDate
     * @return
     * @throws EarthException
     */
    public MgrWorkItemId getByIssueDate(String issueDate) throws EarthException {
        Map<Path<?>, Object> condition = new HashMap<>();
        condition.put(qMgrWorkItemId.issueDate, issueDate);
        return this.findOne(condition);
    }

    /**
     * Update workItem
     *
     * @param workItemId
     * @return
     * @throws EarthException
     */
    @Override
    public Long update(MgrWorkItemId workItemId) throws EarthException {
        Map<Path<?>, Object> keys = new HashMap<>();
        keys.put(qMgrWorkItemId.issueDate, workItemId.getIssueDate());
        Map<Path<?>, Object> values = new HashMap<>();
        values.put(qMgrWorkItemId.count, workItemId.getCount());
        values.put(qMgrWorkItemId.lastUpdateTime, workItemId.getLastUpdateTime());

        return update(Constant.EARTH_WORKSPACE_ID, keys, values);
    }
}
