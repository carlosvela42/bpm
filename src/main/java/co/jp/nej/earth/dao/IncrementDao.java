package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant.EarthId;
import co.jp.nej.earth.model.entity.MgrIncrement;

public interface IncrementDao extends BaseDao<MgrIncrement> {
    Integer getByType(EarthId type) throws EarthException;
}
