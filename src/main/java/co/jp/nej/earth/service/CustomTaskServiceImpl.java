package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.CustomTaskDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrCustomTask;
import co.jp.nej.earth.util.ConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implement for Custom task
 *
 * @author daopq
 * @version 1.0
 */
@Service
public class CustomTaskServiceImpl extends BaseService implements CustomTaskService {

    @Autowired
    private CustomTaskDao customTaskDao;

    /**
     * Get all Custom task
     *
     * @return
     * @throws EarthException
     */
    @Override
    public List<MgrCustomTask> getAllCustomTasks()throws EarthException {
        return (List<MgrCustomTask>) ConversionUtil.castList(executeTransaction(Constant.EARTH_WORKSPACE_ID, () -> {
            return customTaskDao.getAllCustomTasks();
        }), MgrCustomTask.class);
    }
}
