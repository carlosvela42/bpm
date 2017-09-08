package co.jp.nej.earth.service;


import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrCustomTask;

import java.util.List;

/**
 * Service for custom task
 *
 * @author daopq
 * @version 1.0
 */
public interface CustomTaskService {
    List<MgrCustomTask> getAllCustomTasks() throws EarthException;
}
