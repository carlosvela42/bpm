package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.CustomTaskDao;
import co.jp.nej.earth.dao.ProcessDao;
import co.jp.nej.earth.dao.ProcessServiceDao;
import co.jp.nej.earth.dao.ScheduleDao;
import co.jp.nej.earth.dao.TaskDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.AgentSchedule;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrSchedule;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.model.entity.MgrProcessService;
import co.jp.nej.earth.model.entity.MgrTask;
import co.jp.nej.earth.model.enums.EnableDisable;
import co.jp.nej.earth.model.sql.QMgrProcess;
import co.jp.nej.earth.model.sql.QMgrProcessService;
import co.jp.nej.earth.model.sql.QMgrSchedule;
import co.jp.nej.earth.model.sql.QMgrTask;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EMessageResource;
import co.jp.nej.earth.util.EStringUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScheduleServiceImpl extends BaseService implements ScheduleService {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private CustomTaskDao customTaskDao;

    @Autowired
    private ProcessDao processDao;

    @Autowired
    private ProcessServiceDao processServiceDao;

    @Autowired
    private EMessageResource eMessageResource;

    @Override
    public List<MgrSchedule> getSchedulesByWorkspaceId(String workspaceId) throws EarthException {
        return ConversionUtil.castList(executeTransaction(() -> {
            Map<String, String> mapTasks = taskDao.getAllTasks(workspaceId);
            List<MgrSchedule> mgrSchedules = scheduleDao.getSchedulesByWorkspaceId(workspaceId, mapTasks);
            for(MgrSchedule mgrSchedule:mgrSchedules) {
                mgrSchedule.setStartTime(DateUtil.convertStringToDateFormat(mgrSchedule.getStartTime()));
                mgrSchedule.setEndTime(DateUtil.convertStringToDateFormat(mgrSchedule.getEndTime()));
                mgrSchedule.setNextRunDate(DateUtil.convertStringToDateFormat(mgrSchedule.getNextRunDate()));
            }
            return mgrSchedules;
        }), MgrSchedule.class);
    }

    @Override
    public List<Message> validateDelete(String workspaceId, List<String> scheduleIds) throws EarthException {
        List<Message> messages = new ArrayList<>();
//        check if any tobe-delete schedule is enable
        boolean enabledScheduleExists = (boolean) executeTransaction(workspaceId, () -> {
            return scheduleDao.enabledScheduleExists(workspaceId, scheduleIds);
        });

        if(enabledScheduleExists) {
            messages.add(new Message(ErrorCode.E0028,
                eMessageResource.get(ErrorCode.E0028, new String[] { })));
        }

        return messages;
    }

    @Override
    public boolean deleteList(String workspaceId, List<String> scheduleIds) throws EarthException {
        QMgrSchedule qMgrSchedule = QMgrSchedule.newInstance();
        return (boolean) executeTransaction(workspaceId, () -> {
            List<Map<Path<?>, Object>> conditions = new ArrayList<>();
            for (String scheduleId : scheduleIds) {
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qMgrSchedule.scheduleId, scheduleId);
                condition.put(qMgrSchedule.enableDisable, EnableDisable.DISABLE.getId());
                conditions.add(condition);
            }
            return scheduleDao.deleteList(workspaceId, conditions) == scheduleIds.size();
        });
    }

    @Override
    public boolean insertOne(String workspaceId, MgrSchedule mgrScheduleInput) throws EarthException {
        MgrSchedule mgrSchedule = formatTimeForSchedule(mgrScheduleInput);
        return (boolean) executeTransaction(workspaceId, () -> {
            return scheduleDao.add(workspaceId, mgrSchedule) > 0L;
        });
    }

    @Override
    public boolean updateOne(String workspaceId, MgrSchedule mgrScheduleInput) throws EarthException {
        QMgrSchedule qMgrSchedule = QMgrSchedule.newInstance();
        MgrSchedule mgrSchedule = formatTimeForSchedule(mgrScheduleInput);
        return (boolean) executeTransaction(workspaceId, () -> {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrSchedule.scheduleId, mgrSchedule.getScheduleId());
            Map<Path<?>, Object> valueMap = new HashMap<>();
            valueMap.put(qMgrSchedule.hostName, mgrSchedule.getHostName());
            valueMap.put(qMgrSchedule.processIServiceId, mgrSchedule.getProcessIServiceId());
            valueMap.put(qMgrSchedule.taskId, mgrSchedule.getTaskId());
            valueMap.put(qMgrSchedule.enableDisable, mgrSchedule.getEnableDisable());
            try {
                mgrSchedule.setStartTime(DateUtil.convertDateTimeFormat(mgrSchedule.getStartTime(),
                    Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
                mgrSchedule.setEndTime(DateUtil.convertDateTimeFormat(mgrSchedule.getEndTime(),
                    Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            } catch (Exception e) {
                e.printStackTrace();
            }
            valueMap.put(qMgrSchedule.startTime, mgrSchedule.getStartTime());
            valueMap.put(qMgrSchedule.endTime, mgrSchedule.getEndTime());
            valueMap.put(qMgrSchedule.runIntervalDay, mgrSchedule.getRunIntervalDay());
            valueMap.put(qMgrSchedule.runIntervalHour, mgrSchedule.getRunIntervalHour());
            valueMap.put(qMgrSchedule.runIntervalMinute, mgrSchedule.getRunIntervalMinute());
            valueMap.put(qMgrSchedule.runIntervalSecond, mgrSchedule.getRunIntervalSecond());

            return scheduleDao.update(workspaceId, condition, valueMap) > 0L;
        });
    }

    @Override
    public List<Message> validate(String workspaceId, MgrSchedule mgrSchedule, boolean insert) throws EarthException {
        List<Message> messages = new ArrayList<>();
        if (!EStringUtil.isEmpty(mgrSchedule.getProcessIServiceId())
                && !EStringUtil.isNumeric(mgrSchedule.getProcessIServiceId())) {
            messages.add(new Message(ErrorCode.E0011,
                    eMessageResource.get(ErrorCode.E0011, new String[] { "process.service" })));
        }
        if (!EStringUtil.isEmpty(mgrSchedule.getTaskId()) && !EStringUtil.isNumeric(mgrSchedule.getTaskId())) {
            messages.add(new Message(ErrorCode.E0011,
                    eMessageResource.get(ErrorCode.E0011, new String[] { "task.name" })));
        }
        if (!EStringUtil.isEmpty(mgrSchedule.getRunIntervalDay())
                && !EStringUtil.isNumeric(mgrSchedule.getRunIntervalDay())) {
            messages.add(new Message(ErrorCode.E0011,
                    eMessageResource.get(ErrorCode.E0011, new String[] { "schedule.day" })));
        }
        if (!EStringUtil.isEmpty(mgrSchedule.getRunIntervalHour())
                && !EStringUtil.isNumeric(mgrSchedule.getRunIntervalHour())) {
            messages.add(new Message(ErrorCode.E0011,
                    eMessageResource.get(ErrorCode.E0011, new String[] { "schedule.hour" })));
        }
        if (!EStringUtil.isEmpty(mgrSchedule.getRunIntervalMinute())
                && !EStringUtil.isNumeric(mgrSchedule.getRunIntervalMinute())) {
            messages.add(new Message(ErrorCode.E0011,
                    eMessageResource.get(ErrorCode.E0011, new String[] { "schedule.minute" })));
        }
        if (!EStringUtil.isEmpty(mgrSchedule.getRunIntervalSecond())
                && !EStringUtil.isNumeric(mgrSchedule.getRunIntervalSecond())) {
            messages.add(new Message(ErrorCode.E0011,
                    eMessageResource.get(ErrorCode.E0011, new String[] { "schedule.second" })));
        }
        boolean isFormatSuccess = true;
        if (!EStringUtil.isEmpty(mgrSchedule.getStartTime()) && !DateUtil.isDate(mgrSchedule.getStartTime())) {
            isFormatSuccess = false;
            messages.add(new Message(ErrorCode.E0011,
                    eMessageResource.get(ErrorCode.E0011, new String[] { "schedule.startDateTime" })));
        }
        if (!EStringUtil.isEmpty(mgrSchedule.getEndTime()) && !DateUtil.isDate(mgrSchedule.getEndTime())) {
            isFormatSuccess = false;
            messages.add(new Message(ErrorCode.E0011,
                    eMessageResource.get(ErrorCode.E0011, new String[] { "schedule.endDateTime" })));
        }
        try {
            if (isFormatSuccess) {
                if (!EStringUtil.isEmpty(mgrSchedule.getStartTime()) && !EStringUtil.isEmpty(mgrSchedule.getEndTime())
                        && !DateUtil
                                .convertStringSimpleDateFormat(mgrSchedule.getStartTime(),
                                        Constant.DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS)
                                .before(DateUtil.convertStringSimpleDateFormat(mgrSchedule.getEndTime(),
                                        Constant.DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS))) {
                    messages.add(new Message(ErrorCode.E0006, eMessageResource.get(ErrorCode.E0006,
                            new String[] { "schedule.startDateTime", "schedule.endDateTime" })));
                }
            }
        } catch (Exception e) {
            throw new EarthException(e.getMessage());
        }
        if (insert) {
            QMgrSchedule qMgrSchedule = QMgrSchedule.newInstance();
            boolean isExist = (boolean) executeTransaction(workspaceId, () -> {
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qMgrSchedule.scheduleId, mgrSchedule.getScheduleId());
                return scheduleDao.findOne(workspaceId, condition) != null;
            });
            if (isExist) {
                Message message = new Message(Constant.ErrorCode.E0003,
                        eMessageResource.get(Constant.ErrorCode.E0003,
                                new String[] { mgrSchedule.getScheduleId(), "schedule.id" }));
                messages.add(message);
                return messages;
            }
        }
        return messages;
    }

    @Override
    public Map<String, Object> getInfo(String workspaceId) throws EarthException {
        HashMap<String, Object> result = new HashMap<>();

        return ConversionUtil.castObject(executeTransaction(workspaceId, () -> {
            Map<String, Object> maps = new HashMap<>();
            try {
                List<String> customTaskIds =
                    ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID,() -> {
                        return customTaskDao.getCustomTaskIdsForSchedule();
                    }), String.class);
                BooleanBuilder condition = new BooleanBuilder();
                Predicate predicate = QMgrTask.newInstance().customTaskId.in(customTaskIds);
                condition.and(predicate);
                QMgrTask qMgrTask = QMgrTask.newInstance();
                List<OrderSpecifier<?>> orderBys = new ArrayList<>();
                orderBys.add(qMgrTask.taskId.asc());
                List<MgrTask> tasks = taskDao.search(workspaceId, condition,
                    null, null, orderBys, null);
                maps.put("mgrTasks", tasks);

                maps.put("mgrProcesses", processDao.findAll(workspaceId, QMgrProcess.newInstance().processId.asc()));

                maps.put("mgrProcessServices", processServiceDao.findAll(Constant.EARTH_WORKSPACE_ID,
                    QMgrProcessService.newInstance().processIServiceId.asc()));

                maps.put("scheduleId", Constant.ID_DEFAULT);
                return maps;
            } catch (Exception ex) {
                throw new EarthException(ex);
            }
        }), result.getClass());
    }

    @Override
    public Map<String, Object> showDetail(String workspaceId, String scheduleId) throws EarthException {
        HashMap<String, Object> result = new HashMap<>();
        return ConversionUtil.castObject(executeTransaction(workspaceId, () -> {
            try {
                Map<String, Object> maps = new HashMap<>();
                QMgrSchedule qMgrSchedule = QMgrSchedule.newInstance();
                QMgrTask qMgrTask = QMgrTask.newInstance();
                maps = getInfo(workspaceId);
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qMgrSchedule.scheduleId, scheduleId);
                MgrSchedule mgrSchedule = scheduleDao.findOne(workspaceId, condition);
                if (mgrSchedule != null) {
                    mgrSchedule.setStartTime(DateUtil.convertStringToDateFormat(mgrSchedule.getStartTime()));
                    mgrSchedule.setEndTime(DateUtil.convertStringToDateFormat(mgrSchedule.getEndTime()));
                    mgrSchedule.setNextRunDate(DateUtil.convertStringToDateFormat(mgrSchedule.getNextRunDate()));

                    Map<Path<?>, Object> taskCondition = new HashMap<>();
                    taskCondition.put(qMgrTask.taskId, mgrSchedule.getTaskId());
                    MgrTask task = taskDao.findOne(workspaceId, taskCondition);
                    mgrSchedule.setTask(task);

                    maps.put("mgrSchedule", mgrSchedule);
                }

                return maps;
            } catch (Exception ex) {
                throw new EarthException(ex);
            }
        }), result.getClass());
    }

    @Override
    public List<AgentSchedule> getScheduleByProcessServiceId(int processServiceId) throws EarthException {
        int workspaceId = getWorkspaceByProcessServiceId(processServiceId);
        if(workspaceId>0) {
            String workspaceIdInString = Integer.toString(workspaceId);
            return ConversionUtil.castList(executeTransaction(workspaceIdInString, () -> {
                return scheduleDao.getSchedulesByProcessServiceId(workspaceIdInString,processServiceId);
            }), AgentSchedule.class);
        }
        return null;
    }


    @Override
    public int getWorkspaceByProcessServiceId(int processServiceId) throws EarthException {
        return (int) executeTransaction(() -> {
            int workspaceId = 0;

            try {
                QMgrProcessService qMgrProcessService = QMgrProcessService.newInstance();
                Map<Path<?>, Object> condition = new HashMap<>();
                condition.put(qMgrProcessService.processIServiceId, processServiceId);
                MgrProcessService mgrProcessService = processServiceDao.findOne(condition);
                workspaceId = mgrProcessService.getWorkspaceId();
            } catch (Exception ex) {
                throw new EarthException(ex);
            }
            return workspaceId;
        });
    }

    @Override
    public boolean updateNextRunDateByScheduleId(String workspaceId, String scheduleId,
        String nextTime) throws EarthException {

        QMgrSchedule qMgrSchedule = QMgrSchedule.newInstance();
        return (boolean) executeTransaction(workspaceId, () -> {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrSchedule.scheduleId,scheduleId);
            Map<Path<?>, Object> valueMap = new HashMap<>();
            valueMap.put(qMgrSchedule.nextRunDate, nextTime);

            return scheduleDao.update(workspaceId, condition, valueMap) > 0L;
        });
    }

    @Override
    public boolean updateNextRunDateByID(String workspaceId, MgrSchedule mgrSchedule) throws EarthException {
        QMgrSchedule qMgrSchedule = QMgrSchedule.newInstance();
        return (boolean) executeTransaction(workspaceId, () -> {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrSchedule.scheduleId, mgrSchedule.getScheduleId());
            Map<Path<?>, Object> valueMap = new HashMap<>();
            valueMap.put(qMgrSchedule.nextRunDate, mgrSchedule.getNextRunDate());
            valueMap.put(qMgrSchedule.enableDisable, mgrSchedule.getEnableDisable());

            return scheduleDao.update(workspaceId, condition, valueMap) > 0L;
        });
    }

    /**
     * Format date time for schedule
     *
     * @param mgrSchedule
     * @return
     * @throws EarthException
     */
    private MgrSchedule formatTimeForSchedule(MgrSchedule mgrSchedule) throws EarthException {
        try {
            String startTime = null;
            if (EStringUtil.isNotEmpty(mgrSchedule.getStartTime())) {

                startTime = DateUtil.convertDateTimeFormat(mgrSchedule.getStartTime(),
                    Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS);

            }

            String endTime = null;
            if (EStringUtil.isNotEmpty(mgrSchedule.getEndTime())) {
                endTime = DateUtil.convertDateTimeFormat(mgrSchedule.getEndTime(),
                    Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            }

            String nextRunDate = null;
            if (EStringUtil.isNotEmpty(mgrSchedule.getNextRunDate())) {
                nextRunDate = DateUtil.convertDateTimeFormat(mgrSchedule.getNextRunDate(),
                    Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            }

            mgrSchedule.setStartTime(startTime);
            mgrSchedule.setEndTime(endTime);
            mgrSchedule.setNextRunDate(nextRunDate);
        } catch (ParseException e) {
            throw new EarthException(e);
        }
        return mgrSchedule;
    }
}

