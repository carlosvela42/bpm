package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.StrLogAccess;

/**
 * @author p-tvo-sonta
 */
public interface LogAccessService {

    /**
     * add log access
     *
     * @param logAccess
     * @param workspaceId
     * @return
     * @throws EarthException
     */
    boolean addLogAccess(StrLogAccess logAccess, String workspaceId) throws EarthException;

    /**
     * GetMaxHistoryNo
     *
     * @param workspaceId
     * @param eventId
     * @return
     * @throws EarthException
     */
    Integer getMaxHistoryNo(String workspaceId, String eventId) throws EarthException;
}
