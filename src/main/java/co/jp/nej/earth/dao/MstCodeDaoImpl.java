package co.jp.nej.earth.dao;

import static com.querydsl.core.group.GroupBy.groupBy;

import java.util.Map;

import org.springframework.stereotype.Repository;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MstCode;
import co.jp.nej.earth.model.sql.QMstCode;

@Repository
public class MstCodeDaoImpl extends BaseDaoImpl<MstCode> implements MstCodeDao {

    public MstCodeDaoImpl() throws Exception {
        super();
    }

    @Override
    public Map<String, String> getMstCodesBySection(String section) throws EarthException {
        QMstCode qMstCode = QMstCode.newInstance();
        return ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID)
                .select(qMstCode.codeId, qMstCode.codeValue).from(qMstCode).where(qMstCode.section.eq(section))
                .orderBy(qMstCode.codeId.asc())
                .transform(groupBy(qMstCode.codeId).as(qMstCode.codeValue));
    }

    @Override
    public String getMstCodesByCodeId(String section, String codeId) throws EarthException {
        QMstCode qMstCode = QMstCode.newInstance();
        return ConnectionManager.getEarthQueryFactory(Constant.EARTH_WORKSPACE_ID).select(qMstCode.codeValue)
                .from(qMstCode).where(qMstCode.section.eq(section).and(qMstCode.codeId.eq(codeId)))
                .orderBy(qMstCode.codeId.asc())
                .fetchFirst();

    }

}
