package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrProcessService;
import co.jp.nej.earth.model.sql.QMgrProcessService;
import com.querydsl.core.types.Path;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ProcessServiceDaoImpl extends BaseDaoImpl<MgrProcessService> implements ProcessServiceDao {

    private static final QMgrProcessService qMgrProcessService = QMgrProcessService.newInstance();

    public ProcessServiceDaoImpl() throws Exception {
        super();
    }

    /**
     * Get MgrProcessService by ID
     *
     * @param processServiceId
     * @return
     * @throws EarthException
     */
    @Override
    public MgrProcessService getById(Integer processServiceId) throws EarthException {
        try {
            Map<Path<?>, Object> condition = new HashMap<>();
            condition.put(qMgrProcessService.processIServiceId, processServiceId);
            return this.findOne(Constant.EARTH_WORKSPACE_ID, condition);
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }
}
