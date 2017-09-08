package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrCustomTask;

import java.util.List;
import java.util.Map;

/**
 * Get List custom task
 *
 * @author daopq
 * @version  1.0
 */
public interface CustomTaskDao extends BaseDao<MgrCustomTask> {
    List<MgrCustomTask> getAllCustomTasks() throws EarthException;
    Map<String, MgrCustomTask> getCustomTasksMap() throws EarthException;
    String getClassName(String workSpaceId, String eventId) throws EarthException;
    List<String> getCustomTaskIdsForSchedule() throws EarthException;
    Map<String, MgrCustomTask> getCustomTasksMapForSchedule() throws EarthException;
}
