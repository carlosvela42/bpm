package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.StrLogAccessDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.StrLogAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author p-tvo-sonta
 */
@Service
public class LogAccessServiceImpl extends BaseService implements LogAccessService {

    @Autowired
    private StrLogAccessDao strLogAccessDao;

    /**
     * {@inheritDoc}
     *
     * @throws EarthException
     */
    @Override
    public boolean addLogAccess(StrLogAccess logAccess, String workspaceId) throws EarthException {
        return (boolean) executeTransaction(workspaceId, () -> {
            return strLogAccessDao.add(workspaceId, logAccess) > 0;
        });
    }

    @Override
    public Integer getMaxHistoryNo(String workspaceId, String eventId) throws EarthException {
        return (Integer) executeTransaction(workspaceId, () -> {
            return strLogAccessDao.getMaxHistoryNo(workspaceId, eventId);
        });
    }
}
