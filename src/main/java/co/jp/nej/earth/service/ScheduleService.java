package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.AgentSchedule;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrSchedule;
//import co.jp.nej.earth.model.entity.MgrProcessService;

import java.util.List;
import java.util.Map;

public interface ScheduleService {

    List<MgrSchedule> getSchedulesByWorkspaceId(String workspaceId) throws EarthException;

    List<Message> validateDelete(String workspaceId, List<String> scheduleIds) throws EarthException;

    boolean deleteList(String workspaceId, List<String> scheduleIds) throws EarthException;

    boolean insertOne(String workspaceId, MgrSchedule mgrSchedule) throws EarthException;

    boolean updateOne(String workspaceId, MgrSchedule mgrSchedule) throws EarthException;

    List<Message> validate(String workspaceId, MgrSchedule mgrSchedule, boolean insert) throws EarthException;

    Map<String, Object> getInfo(String workspaceId) throws EarthException;

    Map<String, Object> showDetail(String workspaceId, String scheduleId) throws EarthException;

    int getWorkspaceByProcessServiceId(int processServiceId) throws EarthException;

    List<AgentSchedule> getScheduleByProcessServiceId(int processServiceId) throws EarthException;

    //List<MgrProcessService> getWorkspaceForProcessService(String workspaceId, int processServiceID)
    //        throws EarthException;

    boolean updateNextRunDateByScheduleId(String workspaceId, String scheduleId,
        String nextTime) throws EarthException;

    boolean updateNextRunDateByID(String workspaceId, MgrSchedule mgrSchedule) throws EarthException;
}
