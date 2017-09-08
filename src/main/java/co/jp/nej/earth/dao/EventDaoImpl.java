package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.constant.Constant.AgentBatch;
import co.jp.nej.earth.model.entity.CtlEvent;
import co.jp.nej.earth.model.sql.QCtlEvent;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Repository
public class EventDaoImpl extends BaseDaoImpl<CtlEvent> implements EventDao {

    public EventDaoImpl() throws Exception {
        super();
    }

    private static final QCtlEvent qCtlEvent = QCtlEvent.newInstance();
    @Autowired
    private WorkspaceDao workspaceDao;

    public boolean deleteListByUserIds(List<String> userIds) throws EarthException {
        try {
            List<MgrWorkspace> mgrWorkspaces = workspaceDao.getAll();
            for (MgrWorkspace mgrWorkspace : mgrWorkspaces) {
                List<Map<Path<?>, Object>> conditions = new ArrayList<>();
                for (String userId : userIds) {
                    Map<Path<?>, Object> condition = new HashMap<>();
                    condition.put(qCtlEvent.userId, userId);
                    conditions.add(condition);
                }
                this.deleteList(mgrWorkspace.getStringWorkspaceId(), conditions);
            }
            return true;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<String> getListCtlEventIdByStatus(String status, String workSpaceId) throws EarthException {
        QBean<CtlEvent> selectList = Projections.bean(CtlEvent.class, qCtlEvent.all());
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workSpaceId);
        return ConversionUtil.castList(executeWithException(() -> {
            List<CtlEvent> ctlEvents = earthQueryFactory.select(selectList)
                .from(qCtlEvent).where(qCtlEvent.status.eq(status)).fetch();
            return ctlEvents.stream().map(event -> event.getEventId()).collect(Collectors.toList());
        }), String.class);
    }

    @Override
    public boolean updateBulkEventStatus(List<String> eventIds, String workSpaceId) throws EarthException {
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workSpaceId);
        return (boolean) executeWithException(() -> {
            earthQueryFactory.update(qCtlEvent).set(qCtlEvent.status, AgentBatch.STATUS_EDITING)
                .where(qCtlEvent.eventId.in(eventIds)).execute();
            return true;
        });
    }

    @Override
    public long countEventByUserIds(List<String> userIds, String workspaceId) throws EarthException {
        BooleanBuilder condition = new BooleanBuilder();
        Predicate pre1 = qCtlEvent.userId.in(userIds);
        condition.and(pre1);

        return this.search(workspaceId, condition).size();
    }

    /**
     * Update status and TransactionToken for event
     *
     * @param transactionToken
     * @param workSpaceId
     * @return
     */
    @Override
    public CtlEvent getEventIsEditing(String transactionToken, String workSpaceId, String userId)
        throws EarthException {
        QBean<CtlEvent> selectList = Projections.bean(CtlEvent.class, qCtlEvent.all());
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workSpaceId);
        return ConversionUtil.castObject(executeWithException(() -> {
            return earthQueryFactory.select(selectList)
                .from(qCtlEvent)
                .where(qCtlEvent.userId.eq(userId))
                .where(qCtlEvent.status.eq(AgentBatch.STATUS_EDITING))
                .where(qCtlEvent.transactionToken.eq(transactionToken))
                .fetchFirst();
        }), CtlEvent.class);
    }

    /**
     * Update status and transactionToken for one
     *
     * @param transactionToken
     * @param workSpaceId
     * @return
     * @throws EarthException
     */
    @Override
    public boolean updateStatusAndTokenEvent(String transactionToken, String workSpaceId) throws
        EarthException {
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workSpaceId);
        return (boolean) executeWithException(() -> {
            earthQueryFactory.update(qCtlEvent)
                .set(qCtlEvent.status, AgentBatch.STATUS_EDITING)
                .set(qCtlEvent.transactionToken, transactionToken)
                .where(
                    qCtlEvent.eventId.in(
                        earthQueryFactory.select(qCtlEvent.eventId)
                            .from(qCtlEvent)
                            .where(qCtlEvent.status.eq(AgentBatch.STATUS_EDIT))
                            .limit(1)
                    )
                )
                .execute();
            return true;
        });
    }

    @Override
    public CtlEvent getEventByWorkItemId(String workspaceId, String workItemId) throws EarthException {
        return ConversionUtil.castObject(executeWithException(() -> {
            QBean<CtlEvent> selectList = Projections.bean(CtlEvent.class, qCtlEvent.all());
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            return earthQueryFactory.select(selectList).from(qCtlEvent).where(qCtlEvent.workitemId.eq(workItemId))
                .fetchFirst();
        }), CtlEvent.class);
    }

    @Override
    public boolean deleteEvent(String workSpaceId, CtlEvent event) throws EarthException {
        try {
            QCtlEvent qCtlEvent = QCtlEvent.newInstance();
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qCtlEvent.eventId, event.getEventId());
            condition.put(qCtlEvent.status, AgentBatch.STATUS_EDITING);
            return delete(workSpaceId, condition) > 0;
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    @Override
    public long deleteBulkEvents(String workspaceId, List<String> eventIds, String status) throws EarthException {
        QCtlEvent qCtlEvent = QCtlEvent.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        return earthQueryFactory
            .delete(qCtlEvent)
            .where(qCtlEvent.eventId.in(eventIds)
                .and(qCtlEvent.status.eq(status)))
            .execute();
    }

    @Override
    public long updateBulkEventStatus(String workspaceId, List<String> eventIds, String oldStatus, String newStatus)
        throws EarthException {
        QCtlEvent qCtlEvent = QCtlEvent.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        return earthQueryFactory
            .update(qCtlEvent)
            .where(qCtlEvent.eventId.in(eventIds)
                .and(qCtlEvent.status.eq(oldStatus)))
            .set(qCtlEvent.status, newStatus)
            .set(qCtlEvent.lastUpdateTime, DateUtil.getCurrentDateString())
            .execute();
    }

    @Override
    public boolean checkExist(String workspaceId, String workItemId) throws EarthException {
        QCtlEvent qCtlEvent = QCtlEvent.newInstance();
        QBean<CtlEvent> selectList = Projections.bean(CtlEvent.class, qCtlEvent.all());
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        List<CtlEvent> ctlEvents = earthQueryFactory
            .select(selectList)
            .from(qCtlEvent)
            .where(qCtlEvent.workitemId.eq(workItemId))
            .fetch();
        return ctlEvents.size() > 0;
    }
}