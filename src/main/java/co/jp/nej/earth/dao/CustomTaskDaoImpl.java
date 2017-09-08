package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrCustomTask;
import co.jp.nej.earth.model.enums.CustomTaskType;
import co.jp.nej.earth.model.sql.QCtlEvent;
import co.jp.nej.earth.model.sql.QMgrCustomTask;
import co.jp.nej.earth.model.sql.QMgrTask;
import co.jp.nej.earth.util.ConversionUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/** Class to manager custom task
 *
 * @author DaoPQ
 * @version 1.0 */
@Repository
public class CustomTaskDaoImpl extends BaseDaoImpl<MgrCustomTask> implements CustomTaskDao {

    private static final String CODE_ID = "1";

    @Autowired
    private MstCodeDao mstCodeDao;

    private static final Logger LOG = LoggerFactory.getLogger(CustomTaskDaoImpl.class);

    public CustomTaskDaoImpl() throws Exception {
        super();
    }

    @Override
    public List<MgrCustomTask> getAllCustomTasks() throws EarthException {
        try {
            return this.findAll(Constant.EARTH_WORKSPACE_ID);
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }

    @Override
    public Map<String, MgrCustomTask> getCustomTasksMap() throws EarthException {
        try {
            Map<String, MgrCustomTask> dataMap = new LinkedHashMap<>();

            BooleanBuilder condition = new BooleanBuilder();
            Predicate predicate = QMgrCustomTask.newInstance().className.isNotEmpty();
            condition.and(predicate);

            List<MgrCustomTask> listAll = this.search(Constant.EARTH_WORKSPACE_ID, condition);
            for (MgrCustomTask mgrCustomTask : listAll) {
                dataMap.put(mgrCustomTask.getCustomTaskId(), mgrCustomTask);
            }
            return dataMap;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }

    @Override
    public Map<String, MgrCustomTask> getCustomTasksMapForSchedule() throws EarthException {
        try {

            Map<String, MgrCustomTask> dataMap = new LinkedHashMap<>();

            BooleanBuilder condition = new BooleanBuilder();
            Predicate predicate = QMgrCustomTask.newInstance().customTaskType.eq(CustomTaskType.SCHEDULE.getId());
            condition.and(predicate);

            List<MgrCustomTask> listAll = this.search(Constant.EARTH_WORKSPACE_ID, condition);
            if (listAll != null) {
                for (MgrCustomTask mgrCustomTask : listAll) {
                    dataMap.put(mgrCustomTask.getCustomTaskId(), mgrCustomTask);
                }
            }

            return dataMap;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }

    @Override
    public List<String> getCustomTaskIdsForSchedule() throws EarthException {
        BooleanBuilder condition = new BooleanBuilder();
        Predicate predicate = QMgrCustomTask.newInstance().customTaskType.eq(CustomTaskType.SCHEDULE.getId());
        condition.and(predicate);
        List<MgrCustomTask> mgrCustomTasks = this.search(Constant.EARTH_WORKSPACE_ID, condition);
        return mgrCustomTasks.stream().map(MgrCustomTask::getCustomTaskId).collect(Collectors.toList());
    }

    @Override
    public String getClassName(String workSpaceId, String eventId) throws EarthException {
        QMgrCustomTask qMgrCustomTask = QMgrCustomTask.newInstance();
        QCtlEvent qCtlEvent = QCtlEvent.newInstance();
        QMgrTask qMgrTask = QMgrTask.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workSpaceId);
        return ConversionUtil.castObject(executeWithException(() -> {
            return earthQueryFactory
                    .select(qMgrCustomTask.className)
                    .from(qMgrCustomTask)
                    .innerJoin(qMgrTask).on(qMgrCustomTask.customTaskId.eq(qMgrTask.customTaskId))
                    .innerJoin(qCtlEvent).on(qMgrTask.taskId.eq(qCtlEvent.taskId))
                    .where(qCtlEvent.eventId.eq(eventId)).fetchOne();
        }), String.class);
    }
}
