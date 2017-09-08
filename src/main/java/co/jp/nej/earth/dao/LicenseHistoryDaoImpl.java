package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.StrCal;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.sql.QMstCode;
import co.jp.nej.earth.model.sql.QStrCal;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EStringUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQuery;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LicenseHistoryDaoImpl extends BaseDaoImpl<StrCal> implements LicenseHistoryDao {


    public LicenseHistoryDaoImpl() throws Exception {
        super();
    }

    @Override
    public List<StrCal> searchColumn(String workspaceId, Predicate condition, Long offset, Long limit,
                                     List<OrderSpecifier<?>> orderBys, Path<?>[] groupBys) throws EarthException {
        List<StrCal> strCals = new ArrayList<StrCal>();
        EarthQueryFactory queryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        QStrCal qStrCal = QStrCal.newInstance();
        QMstCode qMstCode = QMstCode.newInstance();
        QBean<StrCal> selectList = Projections.bean(StrCal.class, qStrCal.all());
        strCals = ConversionUtil.castList(executeWithException(() -> {
            List<StrCal> strCalNews = new ArrayList<StrCal>();
            long off = (offset == null || offset < 0) ? 0L : offset;
            long lim = (limit == null || limit <= 0) ? co.jp.nej.earth.model.constant.Constant.Limit
                .SEARCH_LIMIT_DEFAULT : limit;

            SQLQuery<?> query = queryFactory.select(selectList, qMstCode.codeValue).from(qStrCal)
                .innerJoin(qMstCode)
                .on(qStrCal.division.eq(qMstCode.codeId.stringValue()),
                    qMstCode.section.eq(Constant.MstCode.DIVISION))
                .offset(off);
            if (condition != null) {
                query = query.where(condition);
            }

            if (groupBys != null) {
                query = query.groupBy(groupBys);
            }

            if (orderBys != null && orderBys.size() > 0) {
                for (OrderSpecifier<?> orderBy : orderBys) {
                    query = query.orderBy(orderBy);
                }
            }
            query = query.limit(lim);
            ResultSet resultSet = query.getResults();
            try {
                while (resultSet.next()) {
                    StrCal strCal = new StrCal();
                    strCal.setAvailableLicenseCount(EStringUtil.parseInt(
                        resultSet.getString(ColumnNames.AVAILABLE_LICENSE_COUNT.toString())));
                    strCal.setDivision(resultSet.getString(ColumnNames.CODE_VALUE.toString()));
                    strCal.setProfileId(resultSet.getString(ColumnNames.PROFILE_ID.toString()));
                    strCal.setUseLicenseCount(EStringUtil.parseInt(resultSet.getString(ColumnNames.USE_LICENSE_COUNT
                        .toString()
                    )));
                    strCal.setProcessTime(DateUtil.convertStringToDateFormat(
                        resultSet.getString(ColumnNames.PROCESS_TIME.toString())));
                    strCalNews.add(strCal);
                }
            } catch (Exception e) {
                throw new EarthException(e);
            }

            return strCalNews;

        }), StrCal.class);
        return strCals;

    }
}