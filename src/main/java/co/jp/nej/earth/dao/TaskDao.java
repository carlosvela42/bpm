package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrTask;

import java.util.List;
import java.util.Map;

public interface TaskDao extends BaseDao<MgrTask> {
    Map<String, String> getAllTasks(String workspaceId) throws EarthException;
    List<MgrTask> getTaskByProcess(String workspaceId, Integer processId) throws EarthException;
    String getTypeOfTask(String workspaceId, String taskId) throws EarthException;
    Integer getProcessByTaskId(String workspaceId, String taskId) throws EarthException;
}
