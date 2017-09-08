package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.entity.MgrTask;
import co.jp.nej.earth.model.sql.QMgrCustomTask;
import co.jp.nej.earth.model.sql.QMgrTask;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.EStringUtil;
import com.querydsl.core.types.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaskDaoImpl extends BaseDaoImpl<MgrTask> implements TaskDao {

    private static final Logger LOG = LoggerFactory.getLogger(TaskDaoImpl.class);

    public TaskDaoImpl() throws Exception {
        super();
    }

    @Override
    public Map<String, String> getAllTasks(String workspaceId) throws EarthException {
        try {
            Map<String, String> mapTaks = new HashMap<>();
            List<MgrTask> mgrTasks = this.findAll(workspaceId);
            for (MgrTask mgrTask : mgrTasks) {
                mapTaks.put(mgrTask.getTaskId(), mgrTask.getTaskName());
            }
            return mapTaks;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }

    /**
     * Get Task by process
     *
     * @param workspaceId Workspace ID
     * @param processId Process ID
     * @return List MgrTask
     * @throws EarthException
     */
    @Override
    public List<MgrTask> getTaskByProcess(String workspaceId, Integer processId)
        throws EarthException {
        return ConversionUtil.castList(executeWithException(() -> {
            QMgrTask qMgrTask = QMgrTask.newInstance();
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            return earthQueryFactory
                .select(Projections.bean(MgrTask.class, qMgrTask.all()))
                .from(qMgrTask)
                .where(
                    qMgrTask.processId.eq(String.valueOf(processId)),
                    qMgrTask.customTaskId.isNull()
                )
                .fetch();
        }), MgrTask.class);
    }

    @Override
    public String getTypeOfTask(String workspaceId, String taskId) throws EarthException {
        QMgrTask qMgrTask = QMgrTask.newInstance();
        QMgrCustomTask qMgrCustomTask = QMgrCustomTask.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        return ConversionUtil.castObject(executeWithException(() -> {
            return earthQueryFactory
                .select(qMgrCustomTask.customTaskType)
                .from(qMgrCustomTask)
                .innerJoin(qMgrTask).on(qMgrCustomTask.customTaskId.eq(qMgrTask.customTaskId))
                .where(qMgrTask.taskId.eq(taskId)).fetchOne();
        }), String.class);
    }

    /**
     * Get Process by taskID
     *
     * @param workspaceId
     * @param taskId
     * @return
     * @throws EarthException
     */
    @Override
    public Integer getProcessByTaskId(String workspaceId, String taskId) throws EarthException {
        QMgrTask qMgrTask = QMgrTask.newInstance();
        EarthQueryFactory query = ConnectionManager.getEarthQueryFactory(workspaceId);
        String processId = ConversionUtil.castObject(executeWithException(() -> {
            return
                query
                    .select(qMgrTask.processId)
                    .from(qMgrTask)
                    .where(qMgrTask.taskId.eq(taskId)).fetchOne();
        }), String.class);
        return EStringUtil.parseInt(processId);
    }
}
