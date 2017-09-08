package co.jp.nej.earth.service;

import co.jp.nej.earth.dao.ProcessServiceDao;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for ProcessService
 *
 * @author DaoPQ
 * @version 1.0
 */
@Service
public class ProcessIServiceServiceImpl extends BaseService implements ProcessIServiceService {

    @Autowired
    private ProcessServiceDao processServiceDao;

    /**
     * Get WorkSpaceId by ProcessServiceID
     *
     * @param processIServiceId
     * @return workSpace ID
     * @throws EarthException
     */
    @Override
    public Integer getWorkSpaceIdByProcessServiceId(Integer processIServiceId) throws EarthException {
        return (Integer) this.executeTransaction(() -> {
            MgrProcessService mgrProcessService = processServiceDao.getById(processIServiceId);
            if (mgrProcessService == null) {
                return null;
            }
            return mgrProcessService.getWorkspaceId();
        });
    }
}
