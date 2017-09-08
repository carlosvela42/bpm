package co.jp.nej.earth.dao;


import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.entity.StrLogAccess;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.model.sql.QStrLogAccess;
import co.jp.nej.earth.util.ConversionUtil;
import co.jp.nej.earth.util.DateUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.sql.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;

@Repository
public class StrLogAccessDaoImpl extends BaseDaoImpl<StrLogAccess> implements StrLogAccessDao {

    @Autowired
    private WorkspaceDao workspaceDao;

    private static final QStrLogAccess qStrLogAccess = QStrLogAccess.newInstance();

    public StrLogAccessDaoImpl() throws Exception {
        super();
    }

    @Override
    public List<StrLogAccess> getListByWorkspaceId(String workspaceId) throws EarthException {
        try {
            List<StrLogAccess> strLogAccesses = new ArrayList<StrLogAccess>();
            QBean<StrLogAccess> selectList = Projections.bean(StrLogAccess.class, qStrLogAccess.all());
            EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
            strLogAccesses = earthQueryFactory.select(selectList).from(qStrLogAccess).fetch();
            return strLogAccesses;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public List<StrLogAccess> searchColumn(String workspaceId, Predicate condition, Long offset, Long limit,
                                           List<OrderSpecifier<?>> orderBys, Path<?>[] groupBys) throws EarthException {
        List<StrLogAccess> strLogAccesses = new ArrayList<StrLogAccess>();
        EarthQueryFactory queryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        QStrLogAccess qStrLogAccess = QStrLogAccess.newInstance();
        QBean<StrLogAccess> selectList = Projections.bean(StrLogAccess.class, qStrLogAccess.all());
        strLogAccesses = ConversionUtil.castList(executeWithException(() -> {
            List<StrLogAccess> strLogAccessesNew = new ArrayList<StrLogAccess>();
            long off = (offset == null || offset < 0) ? 0L : offset;
            long lim = (limit == null || limit <= 0) ? co.jp.nej.earth.model.constant.Constant.Limit
                .SEARCH_LIMIT_DEFAULT : limit;

            SQLQuery<?> query = queryFactory.select(selectList).from(qStrLogAccess)
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

            try {
                ResultSet resultSet = query.getResults();
                while (resultSet.next()) {
                    StrLogAccess strLogAccess = new StrLogAccess();
                    strLogAccess.setEventId(resultSet.getString(ColumnNames.EVENT_ID.toString()));
                    strLogAccess.setProcessTime(DateUtil.convertStringToDateFormat(
                        resultSet.getString(ColumnNames.PROCESS_TIME.toString())));
                    strLogAccess.setUserId(resultSet.getString(ColumnNames.USER_ID.toString()));
                    strLogAccess.setWorkitemId(resultSet.getString(ColumnNames.WORKITEM_ID.toString()));
                    strLogAccess.setHistoryNo(parseInt(resultSet.getString(ColumnNames.HISTORY_NO.toString())));
                    strLogAccess.setTaskId(resultSet.getString(ColumnNames.TASK_ID.toString()));
                    strLogAccessesNew.add(strLogAccess);
                }
            } catch (Exception e) {
                throw new EarthException(e);
            }

            return strLogAccessesNew;

        }), StrLogAccess.class);
        return strLogAccesses;
    }

    @Override
    public boolean deleteListByUserIds(List<String> userIds) throws EarthException {
        try {
            List<MgrWorkspace> mgrWorkspaces = workspaceDao.getAll();
            for (MgrWorkspace mgrWorkspace : mgrWorkspaces) {
                List<Map<Path<?>, Object>> conditions = new ArrayList<>();
                for (String userId : userIds) {
                    Map<Path<?>, Object> condition = new HashMap<>();
                    condition.put(qStrLogAccess.userId, userId);
                    conditions.add(condition);
                }
                this.deleteList(mgrWorkspace.getStringWorkspaceId(), conditions);
            }
            return true;
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }

    @Override
    public Integer getMaxHistoryNo(String workspaceId, String workItemId) throws EarthException {
        try {
            QStrLogAccess qStrLogAccess = QStrLogAccess.newInstance();
            Integer maxHistoryNo = ConnectionManager.getEarthQueryFactory(workspaceId)
                .select(qStrLogAccess.historyNo.max())
                .from(qStrLogAccess).where(qStrLogAccess.workitemId.eq(workItemId)).fetchOne();
            return maxHistoryNo == null ? 0 : maxHistoryNo;
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
    }


    @Override
    public String getMaxId(String workspaceId) throws EarthException {
        try {
            QStrLogAccess qStrLogAccess = QStrLogAccess.newInstance();
            return ConnectionManager.getEarthQueryFactory(workspaceId)
                .select(qStrLogAccess.eventId.max())
                .from(qStrLogAccess).fetchOne();
        } catch (Exception ex) {
            throw new EarthException(ex.getMessage());
        }
    }
}
