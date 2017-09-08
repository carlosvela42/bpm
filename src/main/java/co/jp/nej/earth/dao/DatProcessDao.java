package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.DatProcess;

public interface DatProcessDao extends BaseDao<DatProcess> {
    long updateProcess(String workspaceId, DatProcess datProcess) throws EarthException;
    Integer getMaxId(String workspaceId, String workItemId) throws EarthException;
    boolean isExistWorkItemInProcess(String workspaceId, Integer processId, String workItemId) throws EarthException;
}
