package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.StrCal;
import co.jp.nej.earth.model.sql.QStrCal;
import org.springframework.stereotype.Repository;

@Repository
public class LicenseHistoryDaoImplTest extends BaseDaoImpl<StrCal> implements LicenseHistoryDaoTest {

    public LicenseHistoryDaoImplTest() throws Exception {
        super();
    }

    public boolean deleteAll() throws EarthException {
        QStrCal qStrCal = QStrCal.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
        long delete = earthQueryFactory.delete(qStrCal).execute();
        return delete > 0;
    }
}
