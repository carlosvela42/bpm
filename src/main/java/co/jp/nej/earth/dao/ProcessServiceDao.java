package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrProcessService;

public interface ProcessServiceDao extends BaseDao<MgrProcessService> {
    MgrProcessService getById(Integer processServiceId) throws EarthException;
}
