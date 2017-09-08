package co.jp.nej.earth.service;


import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrTask;

import java.util.List;

public interface TaskService {

    List<MgrTask> getAll() throws EarthException;

    List<MgrTask> getTaskByProcess(String workspaceId, Integer processId) throws EarthException;
}

