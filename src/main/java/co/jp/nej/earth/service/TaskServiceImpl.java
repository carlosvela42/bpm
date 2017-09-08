package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.TaskDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrTask;
import co.jp.nej.earth.util.ConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by p-dcv-minhtv on 5/16/2017.
 */
@Service
public class TaskServiceImpl extends BaseService implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public List<MgrTask> getAll() throws EarthException {
        return (List<MgrTask>) ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return taskDao.findAll(Constant.EARTH_WORKSPACE_ID);
        }), MgrTask.class);
    }

    /**
     * Get Task by Process
     *
     * @param workspaceId Workspace ID`
     * @param processId ProcessID
     * @return List MgrTask
     * @throws EarthException
     */
    @Override
    public List<MgrTask> getTaskByProcess(String workspaceId, Integer processId) throws EarthException {
        return ConversionUtil.castList(executeTransaction(workspaceId, () -> {
            return taskDao.getTaskByProcess(workspaceId, processId);
        }), MgrTask.class);
    }
}
