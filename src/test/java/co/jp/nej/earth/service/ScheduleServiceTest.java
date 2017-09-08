package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrSchedule;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EStringUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScheduleServiceTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(ScheduleServiceTest.class);

    private static final int RECORD = 5;
    private static final int NUMBER = 122;
    private static final String WORKSPACE_ID = "2";
    private static final String WORKSPACE_ID_ERROR = Constant.SYSTEM_WORKSPACE_ID;
    private static final int PROCESS_ID = 1;
    private static final String TASK_ID = "1";
    private static final String HOST_NAME = "MINHTV_HOST_NAME_";
    private static final String ENABLE = "enable";
    private static final String END_TIME = "2017/05/05 12:12:15";
    private static final String END_TIME_ERROR = "2017/05/04 12:12:15";
    private static final String END_TIME_ERROR_FORMAT = "04/2017/05/04 12:12:15";
    private static final String START_TIME = "2017/05/05 12:12:12";

    private List<MgrSchedule> mgrSchedules = new ArrayList<>();
    private List<String> scheduleIds = new ArrayList<>();


    @Before
    public void before() throws EarthException {
        LOG.info("Before ScheduleServiceTest");
        for (int i = 1; i <= RECORD; i++) {
            MgrSchedule mgrSchedule = new MgrSchedule(String.valueOf(NUMBER + i), TASK_ID, HOST_NAME + i,
                    String.valueOf(i), ENABLE, START_TIME, END_TIME, START_TIME,
                    String.valueOf(i), String.valueOf(i),
                    String.valueOf(i), String.valueOf(i),
                    DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            mgrSchedules.add(mgrSchedule);
            scheduleService.insertOne(WORKSPACE_ID, mgrSchedule);
            scheduleIds.add(String.valueOf(NUMBER + i));
        }
        LOG.info("After ScheduleServiceTest : " + mgrSchedules.size() + " records");
    }

    @After
    public void after() throws EarthException {
        LOG.info("After : " + scheduleIds.size());
        scheduleService.deleteList(WORKSPACE_ID, scheduleIds);
        LOG.info("After : " + scheduleIds.size());
    }

    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void getAll() throws EarthException {
        List<MgrSchedule> mgrSchedules = scheduleService.getSchedulesByWorkspaceId(WORKSPACE_ID);
        LOG.info("Get All schedules : " + mgrSchedules.size() + " records");
        Assert.assertTrue(mgrSchedules.size() >= RECORD);
    }

    @Test
    public void getAll_False() throws EarthException {
        List<MgrSchedule> mgrSchedules = new ArrayList<>();
        try {
            mgrSchedules = scheduleService.getSchedulesByWorkspaceId(WORKSPACE_ID_ERROR);
            LOG.info("Get All schedules : " + mgrSchedules.size() + " records");
            Assert.assertTrue(mgrSchedules == null);
        } catch (Exception ex) {
            LOG.info("Get All schedules : " + ex.getMessage());
            Assert.assertTrue(ex != null);
        }

    }

    @Test
    public void getById() throws EarthException {
        Map<String, Object> detail = null;
        LOG.info("ScheduleId : " + mgrSchedules.get(0).getScheduleId());
        detail = scheduleService.showDetail(WORKSPACE_ID, mgrSchedules.get(0).getScheduleId());
        MgrSchedule mgrSchedule = ConversionUtil.castObject(detail.get("mgrSchedule"),
                MgrSchedule.class);
        LOG.info("ScheduleId : " + mgrSchedule.getScheduleId());
        Assert.assertTrue(EStringUtil.equals(mgrSchedule.getScheduleId(), mgrSchedules.get(0).getScheduleId()));
    }

    @Test
    public void getById_False() throws EarthException {
        Map<String, Object> detail = null;
        LOG.info("ScheduleId : " + mgrSchedules.get(0).getScheduleId());
        try {
            detail = scheduleService.showDetail(WORKSPACE_ID_ERROR, mgrSchedules.get(0).getScheduleId());
            MgrSchedule mgrSchedule = ConversionUtil.castObject(detail.get("mgrSchedule"),
                    MgrSchedule.class);
            LOG.info("ScheduleId : " + mgrSchedule);
            Assert.assertTrue(mgrSchedule == null);
        } catch (Exception ex) {
            LOG.info("Get All schedules : " + ex.getMessage());
            Assert.assertTrue(ex != null);
        }

    }


    @Test
    public void insertOne() throws EarthException {
        boolean insert = false;
        MgrSchedule mgrSchedule = new MgrSchedule(String.valueOf(NUMBER + RECORD + 1), TASK_ID,
                HOST_NAME + RECORD + 1, String.valueOf(RECORD + 1), ENABLE,
                START_TIME, END_TIME, START_TIME, String.valueOf(RECORD + 1), String.valueOf(RECORD + 1),
                String.valueOf(RECORD + 1), String.valueOf(RECORD + 1),
                DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        List<Message> messages = scheduleService.validate(WORKSPACE_ID, mgrSchedule, true);
        Assert.assertFalse(messages != null && messages.size() > 0);
        insert = scheduleService.insertOne(WORKSPACE_ID, mgrSchedule);
        scheduleIds.add(String.valueOf(NUMBER + RECORD + 1));
        LOG.info("Insert SCHEDULE : " + mgrSchedule.getScheduleId());
        Assert.assertTrue(insert);
    }

    @Test
    public void insertOne_Exist() throws EarthException {
        MgrSchedule mgrSchedule = new MgrSchedule(String.valueOf(NUMBER + RECORD), TASK_ID,
                HOST_NAME + RECORD + 1, String.valueOf(RECORD + 1), ENABLE,
                START_TIME, END_TIME_ERROR, START_TIME, String.valueOf(RECORD + 1), String.valueOf(RECORD + 1),
                String.valueOf(RECORD + 1), String.valueOf(RECORD + 1),
                DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        List<Message> messages = scheduleService.validate(WORKSPACE_ID, mgrSchedule, true);
        Assert.assertTrue(messages != null && messages.size() > 0);
        LOG.info("Error : " + messages.size());
    }

    @Test
    public void insertOne_False() throws EarthException {
        MgrSchedule mgrSchedule = new MgrSchedule(String.valueOf(NUMBER + RECORD + 1), TASK_ID,
                HOST_NAME + RECORD + 1, String.valueOf(RECORD + 1), ENABLE,
                START_TIME, END_TIME_ERROR, START_TIME, String.valueOf(RECORD + 1), String.valueOf(RECORD + 1),
                String.valueOf(RECORD + 1), String.valueOf(RECORD + 1),
                DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        List<Message> messages = scheduleService.validate(WORKSPACE_ID, mgrSchedule, true);
        Assert.assertTrue(messages != null && messages.size() > 0);
        LOG.info("Error : " + messages.size());
    }

    @Test
    public void updateOne() throws EarthException {
        boolean update = false;
        MgrSchedule mgrSchedule = new MgrSchedule(String.valueOf(NUMBER + RECORD), TASK_ID,
                HOST_NAME + RECORD + 1, String.valueOf(RECORD + 1), ENABLE,
                START_TIME, END_TIME, START_TIME, String.valueOf(RECORD + 1), String.valueOf(RECORD + 1),
                String.valueOf(RECORD + 1), String.valueOf(RECORD + 1),
                DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        List<Message> messages = scheduleService.validate(WORKSPACE_ID, mgrSchedule, false);
        Assert.assertFalse(messages != null && messages.size() > 0);
        update = scheduleService.updateOne(WORKSPACE_ID, mgrSchedule);
        LOG.info("Update SCHEDULE : " + mgrSchedule.getScheduleId());
        Assert.assertTrue(update);
    }

    @Test
    public void updateOne_False() throws EarthException {
        MgrSchedule mgrSchedule = new MgrSchedule(String.valueOf(NUMBER + RECORD), TASK_ID,
                HOST_NAME + RECORD + 1, String.valueOf(RECORD + 1), ENABLE,
                START_TIME, END_TIME_ERROR_FORMAT, START_TIME, String.valueOf(RECORD + 1), String.valueOf(RECORD + 1),
                String.valueOf(RECORD + 1), String.valueOf(RECORD + 1),
                DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        List<Message> messages = scheduleService.validate(WORKSPACE_ID, mgrSchedule, false);
        Assert.assertTrue(messages != null && messages.size() > 0);
        LOG.info("Error : " + messages.size());
    }

    @Test
    public void deleteList() throws EarthException {
        boolean delete = scheduleService.deleteList(WORKSPACE_ID, scheduleIds);
        LOG.info("Delete Schedule : " + delete);
        Assert.assertTrue(delete);
    }

}
