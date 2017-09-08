package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.AgentSchedule;
import co.jp.nej.earth.model.MgrSchedule;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrCustomTask;
import co.jp.nej.earth.model.entity.MgrTask;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.enums.EnableDisable;
import co.jp.nej.earth.model.sql.QMgrSchedule;
import co.jp.nej.earth.model.sql.QMgrTask;
import co.jp.nej.earth.util.DateUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.sql.SQLQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import co.jp.nej.earth.model.entity.MgrProcessService;
//import co.jp.nej.earth.model.sql.QMgrProcessService;

@Repository
public class ScheduleDaoImpl extends BaseDaoImpl<MgrSchedule> implements ScheduleDao {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleDaoImpl.class);

    @Autowired
    private CustomTaskDao customTaskDao;


    public ScheduleDaoImpl() throws Exception {
        super();
    }


    @Override
    public List<AgentSchedule> getSchedulesByProcessServiceId(String workspaceId, int processServiceId)
        throws EarthException {
        QMgrSchedule qMgrSchedule = QMgrSchedule.newInstance();
        QMgrTask qMgrTask = QMgrTask.newInstance();

        List<AgentSchedule> listSchedule = new ArrayList<>();
        Map<String,MgrCustomTask> customTaskMap = customTaskDao.getCustomTasksMapForSchedule();
        if(customTaskMap.size() == 0) {
            return listSchedule;
        }
        try {

            BooleanBuilder condition = new BooleanBuilder();
            Predicate predicate = qMgrSchedule.processIServiceId.eq(Integer.toString(processServiceId));
            condition.and(predicate);

            String currentDate = DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            BooleanBuilder conditionEndDate = new BooleanBuilder();
            Predicate predicateEndTime = qMgrSchedule.endTime.goe(currentDate);
            Predicate predicateEndTimeNull = qMgrSchedule.endTime.isNull();
            conditionEndDate.and(predicateEndTimeNull).or(predicateEndTime);
            condition.and(conditionEndDate);

            Predicate predicateEnabled = qMgrSchedule.enableDisable.eq(EnableDisable.ENABLE.getId());
            condition.and(predicateEnabled);

            //Predicate predicateCustomTaskId = qMgrTask.customTaskId.isNotNull();
            //condition.and(predicateCustomTaskId);

            SQLQuery<Tuple> query = ConnectionManager.getEarthQueryFactory(workspaceId)
                    .select(qMgrSchedule.scheduleId, qMgrTask.taskId, qMgrSchedule.startTime, qMgrSchedule.endTime,
                            qMgrSchedule.enableDisable, qMgrSchedule.nextRunDate, qMgrSchedule.runIntervalDay,
                            qMgrSchedule.runIntervalDay, qMgrSchedule.runIntervalHour, qMgrSchedule.runIntervalMinute,
                            qMgrSchedule.runIntervalSecond,qMgrSchedule.processIServiceId, qMgrTask.customTaskId)
                    .from(qMgrSchedule)
                        .innerJoin(qMgrTask).on(qMgrSchedule.taskId.eq(qMgrTask.taskId))
                    .orderBy(qMgrSchedule.scheduleId.asc(), qMgrTask.taskId.asc())
                    .where(condition);

            LOG.info(query.getSQL().getSQL());
            ResultSet resultSet = query.getResults();

            while (resultSet.next()) {
                AgentSchedule agentSchedule = new AgentSchedule();
                agentSchedule.setScheduleId(resultSet.getString(ColumnNames.SCHEDULE_ID.toString()));
                agentSchedule.setProcessIServiceId(resultSet.getString(ColumnNames.PROCESS_ISERVICEID.toString()));
                agentSchedule.setTaskId(resultSet.getString(ColumnNames.TASK_ID.toString()));
                agentSchedule.setStartTime(resultSet.getString(ColumnNames.START_TIME.toString()));
                agentSchedule.setEndTime(resultSet.getString(ColumnNames.END_TIME.toString()));
                agentSchedule.setEnableDisable(resultSet.getString(ColumnNames.ENABLE_DISABLE.toString()));
                agentSchedule.setNextRunDate(resultSet.getString(ColumnNames.NEXT_RUN_DATE.toString()));
                agentSchedule.setRunIntervalDay(resultSet.getString(ColumnNames.RUN_INTERVAL_DAY.toString()));
                agentSchedule.setRunIntervalHour(resultSet.getString(ColumnNames.RUN_INTERVAL_HOUR.toString()));
                agentSchedule.setRunIntervalMinute(resultSet.getString(ColumnNames.RUN_INTERVAL_MINUTE.toString()));
                agentSchedule.setRunIntervalSecond(resultSet.getString(ColumnNames.RUN_INTERVAL_SECOND.toString()));
                agentSchedule.setClassName(
                        customTaskMap.get(resultSet.getString(ColumnNames.CUSTOM_TASK_ID.toString())).getClassName());
                listSchedule.add(agentSchedule);

            }
            return listSchedule;
        } catch (Exception e) {
            LOG.error(e.getMessage());
            throw new EarthException(e);
        }
    }

    @Override
    public List<MgrSchedule> getSchedulesByWorkspaceId(String workspaceId, Map<String, String> tasks)
            throws EarthException {
        try {
            QMgrSchedule qMgrSchedule = QMgrSchedule.newInstance();
            QMgrTask qMgrTask = QMgrTask.newInstance();

            ResultSet resultSet = ConnectionManager.getEarthQueryFactory(workspaceId)
                    .select(qMgrSchedule.scheduleId, qMgrSchedule.taskId,
                            qMgrSchedule.processIServiceId, qMgrSchedule.hostName, qMgrSchedule.startTime,
                            qMgrSchedule.endTime, qMgrSchedule.enableDisable, qMgrSchedule.nextRunDate,
                            qMgrTask.taskName)
                    .from(qMgrSchedule)
                    .innerJoin(qMgrTask).on(qMgrSchedule.taskId.eq(qMgrTask.taskId))
                    .orderBy(qMgrSchedule.scheduleId.asc(), qMgrSchedule.taskId.asc(),
                            qMgrSchedule.processIServiceId.asc())
                    .getResults();
            List<MgrSchedule> mgrSchedules = new ArrayList<>();
            while (resultSet.next()) {
                MgrSchedule mgrSchedule = new MgrSchedule(resultSet.getString(ColumnNames.SCHEDULE_ID.toString()),
                        resultSet.getString(ColumnNames.TASK_ID.toString()),
                        resultSet.getString(ColumnNames.HOST_NAME.toString()),
                        resultSet.getString(ColumnNames.PROCESS_ISERVICEID.toString()),
                        resultSet.getString(ColumnNames.START_TIME.toString()),
                        resultSet.getString(ColumnNames.END_TIME.toString()),
                        resultSet.getString(ColumnNames.ENABLE_DISABLE.toString()),
                        resultSet.getString(ColumnNames.NEXT_RUN_DATE.toString()));

                MgrTask task = new MgrTask();
                task.setTaskId(resultSet.getString(ColumnNames.TASK_ID.toString()));
                task.setTaskName(resultSet.getString(ColumnNames.TASK_NAME.toString()));
                mgrSchedule.setTask(task);

                mgrSchedules.add(mgrSchedule);
            }
            return mgrSchedules;
        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new EarthException(ex);
        }
    }

    @Override
    public boolean enabledScheduleExists(String workspaceId, List<String> scheduleIds) throws EarthException {
        QMgrSchedule qMgrSchedule = QMgrSchedule.newInstance();
        EarthQueryFactory factory = ConnectionManager.getEarthQueryFactory(workspaceId);

        long num = factory.select().from(qMgrSchedule).where(qMgrSchedule.enableDisable.eq(EnableDisable.ENABLE
            .getId())).where(qMgrSchedule.scheduleId.in(scheduleIds))
            .fetchCount();
        return num > 0;
    }
}
