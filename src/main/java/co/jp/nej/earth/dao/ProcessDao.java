package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.MgrProcess;

/**
 * @author p-tvo-sonta
 */
public interface ProcessDao extends BaseDao<MgrProcess> {
    boolean isExistProcess(String workspaceId, Integer processId) throws EarthException;
}
