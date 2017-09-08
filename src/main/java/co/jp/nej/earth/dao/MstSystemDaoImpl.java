package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MstSystem;
import co.jp.nej.earth.model.sql.QMstSystem;
import co.jp.nej.earth.util.DateUtil;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MstSystemDaoImpl implements MstSystemDao {

    public List<MstSystem> getMstSystem() throws EarthException {
        QMstSystem qMstSystem = QMstSystem.newInstance();

        QBean<MstSystem> selectList = Projections.bean(MstSystem.class, qMstSystem.all());

        List<MstSystem> listMstSystem = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
                .select(selectList).from(qMstSystem).fetch();
        return listMstSystem;
    }

    @Override
    public List<MstSystem> getMstSystemBySection(String section) throws EarthException {
        try {
            QMstSystem qMstSystem = QMstSystem.newInstance();

            QBean<MstSystem> selectList = Projections.bean(MstSystem.class, qMstSystem.all());

            List<MstSystem> listMstSystem = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
                    .select(selectList).from(qMstSystem).where(qMstSystem.section.eq(section)).fetch();
            return listMstSystem;
        } catch (Exception ex) {
            throw new EarthException(ex);

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MstSystem getMstSystemBySectionAndVariable(String section, String variable) throws EarthException {
        QMstSystem qMstSystem = QMstSystem.newInstance();
        QBean<MstSystem> selectList = Projections.bean(MstSystem.class, qMstSystem.all());
        // get system config value
        return ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID).select(selectList).from(qMstSystem)
                .where(qMstSystem.section.eq(section).and(qMstSystem.variableName.eq(variable))).fetchOne();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int updateMstSystem(String section, String variable, String configValue) throws EarthException {
        QMstSystem qMstSystem = QMstSystem.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID);
        try {
            return (int) earthQueryFactory.update(qMstSystem).set(qMstSystem.configValue, configValue)
                    .set(qMstSystem.lastUpdateTime, DateUtil.getCurrentDateString())
                    .where(qMstSystem.section.eq(section).and(qMstSystem.variableName.eq(variable))).execute();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int insertMstSystem(String section, String variable, String configValue) throws EarthException {
        QMstSystem qMstSystem = QMstSystem.newInstance();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory();
        try {
            return (int) earthQueryFactory.insert(qMstSystem)
                    .set(qMstSystem.section, section)
                    .set(qMstSystem.variableName, variable)
                    .set(qMstSystem.configValue, configValue)
                    .set(qMstSystem.lastUpdateTime, DateUtil.getCurrentDateString())
                    .execute();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }
}
