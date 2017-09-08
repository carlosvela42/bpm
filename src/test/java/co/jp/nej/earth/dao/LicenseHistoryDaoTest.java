package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.StrCal;

public interface LicenseHistoryDaoTest extends BaseDao<StrCal> {

    boolean deleteAll() throws EarthException;
}
