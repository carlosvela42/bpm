package co.jp.nej.earth.dao;

import org.springframework.stereotype.Repository;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.EarthId;
import co.jp.nej.earth.model.entity.MgrIncrement;
import co.jp.nej.earth.model.sql.QMgrIncrement;
import co.jp.nej.earth.model.sql.QStrLogAccess;
@Repository
public class IncrementDaoImpl extends BaseDaoImpl<MgrIncrement> implements IncrementDao {

    private static final QMgrIncrement qMgrIncrement = QMgrIncrement.newInstance();

    public IncrementDaoImpl() throws Exception {
        super();
    }

    public Integer getByType(EarthId type) throws EarthException {
        try {
            QStrLogAccess qStrLogAccess = QStrLogAccess.newInstance();
            return ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
                .select(qMgrIncrement.incrementData.max())
                .where(qMgrIncrement.incrementType.eq(type.getValue()))
                .from(qMgrIncrement).fetchOne() ;
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
    }
}
