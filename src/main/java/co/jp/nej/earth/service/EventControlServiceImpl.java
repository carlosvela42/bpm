package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.EventDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.id.EEventId;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.AgentBatch;
import co.jp.nej.earth.model.entity.CtlEvent;
import co.jp.nej.earth.model.sql.QCtlEvent;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import com.querydsl.core.types.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author p-tvo-sonta
 */
@Service
public class EventControlServiceImpl extends BaseService implements EventControlService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EEventId eventId;

    /**
     * log
     */
    private static final Logger LOG = LoggerFactory.getLogger(EventControlServiceImpl.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertEvent(String workspaceId, CtlEvent event) throws EarthException {
        return (boolean) executeTransaction(workspaceId, () -> {
            event.setLastUpdateTime(DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            event.setEventId(eventId.getAutoId(" "));
            eventDao.add(workspaceId, event);
            return true;
        });
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean insertEvents(String workspaceId, List<CtlEvent> events) throws EarthException {
        return (boolean) executeTransaction(workspaceId, () -> {
            for(CtlEvent event:events){
                event.setEventId(eventId.getAutoId(" "));
                eventDao.add(workspaceId, event);
            }
            return true;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getListEventIdByStatus(String status, String workSpaceId) throws EarthException {
        return ConversionUtil.castList(this.executeTransaction(workSpaceId, () -> {
            try {
                return eventDao.getListCtlEventIdByStatus(status, workSpaceId);
            } catch (Exception e) {
                throw new EarthException(e);
            }
        }), String.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean updateBulkEventStatus(List<String> eventIds, String workSpaceId) throws EarthException {
        return (boolean) this.executeTransaction(workSpaceId, () -> {
            try {
                return eventDao.updateBulkEventStatus(eventIds, workSpaceId);
            } catch (Exception e) {
                throw new EarthException(e);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CtlEvent getEventByEventId(String eventId, String workSpaceId) throws EarthException {
        return (CtlEvent) this.executeTransaction(workSpaceId, () -> {
            try {
                Map<Path<?>, Object> mapCondition = new HashMap<>();
                mapCondition.put(QCtlEvent.newInstance().eventId, eventId);
                return eventDao.findOne(workSpaceId, mapCondition);
            } catch (Exception e) {
                throw new EarthException(e);
            }
        });

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean deleteEvent(CtlEvent event, String workSpaceId) throws EarthException {
        return (boolean) this.executeTransaction(workSpaceId, () -> {
            return eventDao.deleteEvent(workSpaceId, event);
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unlockEventControl(String workspaceId, String eventId) throws EarthException {
        return (boolean) this.executeTransaction(workspaceId, () -> {
            QCtlEvent qCtlEvent = QCtlEvent.newInstance();
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qCtlEvent.eventId, eventId);
            condition.put(qCtlEvent.status, AgentBatch.STATUS_OPEN);

            // Processing delete.
            if (eventDao.delete(workspaceId, condition) > 0) {
                return true;
            }

            // Processing update.
            Map<Path<?>, Object> updateMap = new HashMap<>();
            updateMap.put(qCtlEvent.status, AgentBatch.STATUS_EDIT);
            condition.put(qCtlEvent.status, AgentBatch.STATUS_EDITING);
            if (eventDao.update(workspaceId, condition, updateMap) > 0) {
                return true;
            }
            return false;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean unlockEventControls(String workspaceId, List<String> eventIds) throws EarthException {
        return (boolean) this.executeTransaction(workspaceId, () -> {
            // Delete events have status is open.
            eventDao.deleteBulkEvents(workspaceId, eventIds, AgentBatch.STATUS_OPEN);

            // Update events have status is Editing to Edit.
            eventDao.updateBulkEventStatus(workspaceId, eventIds, AgentBatch.STATUS_EDITING, AgentBatch.STATUS_EDIT);

            return true;
        });
    }

    /**
     * Update status and TransactionToken for event
     *
     * @param status
     * @param workSpaceId
     * @return
     */
    @Override
    public CtlEvent getOneEventByStatusForUpdate(String status, String workSpaceId, String userId)
        throws EarthException {
        return (CtlEvent) this.executeTransaction(workSpaceId, () -> {
            try {
                return eventDao.getEventIsEditing(status, workSpaceId, userId);
            } catch (Exception e) {
                throw new EarthException(e);
            }
        });
    }

    @Override
    public String getEventIdByWorkItemId(String workspaceId, String workItemId) throws EarthException {
        CtlEvent ctlEvent= (CtlEvent) this.executeTransaction(workspaceId, () -> {
            try {
                return eventDao.getEventByWorkItemId(workspaceId, workItemId);
            } catch (Exception e) {
                throw new EarthException(e);
            }
        });
        if (ctlEvent!=null){
            return ctlEvent.getEventId();
        }
        return "";
    }
}
