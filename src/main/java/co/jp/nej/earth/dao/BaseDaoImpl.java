package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.manager.connection.ConnectionManager;
import co.jp.nej.earth.manager.connection.EarthQueryFactory;
import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.enums.ColumnNames;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EModelUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.sql.RelationalPathBase;
import com.querydsl.sql.SQLQuery;
import com.querydsl.sql.dml.SQLInsertClause;
import com.querydsl.sql.dml.SQLUpdateClause;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public abstract class BaseDaoImpl<T extends BaseModel<T>> implements BaseDao<T> {

    static final long SEARCH_LIMIT_DEFAULT = 100L;

    protected static final Logger LOG = LoggerFactory.getLogger(BaseDaoImpl.class);

    private T instance;

    @SuppressWarnings("unchecked")
    public BaseDaoImpl() throws Exception {
        this.instance = ((Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
                .getActualTypeArguments()[0]).newInstance();
    }

    /**
     * Find all without any conditions: group by, order by, limit, offset.
     */
    public List<T> findAll(String workspaceId) throws EarthException {
        return findAll(workspaceId, null, null, null, null);
    }

    /**
     * Find all order by.
     */
    public List<T> findAll(String workspaceId, OrderSpecifier<?> orderBy) throws EarthException {
        List<OrderSpecifier<?>> orderBys = new ArrayList<>();
        orderBys.add(orderBy);
        return findAll(workspaceId, null, null, orderBys, null);
    }

    /**
     * Find all entity record
     */
    public List<T> findAll(String workspaceId, Long offset, Long limit, List<OrderSpecifier<?>> orderBys,
                           Path<?>[] groupbys) throws EarthException {
        return search(workspaceId, null, offset, limit, orderBys, groupbys);
    }

    /**
     * Search without any conditions: group by, order by, limit, offset.
     */
    public List<T> search(String workspaceId, Predicate condition) throws EarthException {
        return search(workspaceId, condition, null, null, null, null);
    }

    @SuppressWarnings("unchecked")
    public List<T> search(String workspaceId, Predicate condition, Long offset, Long limit,
                          List<OrderSpecifier<?>> orderBys, Path<?>[] groupbys) throws EarthException {

        EarthQueryFactory queryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        return (List<T>) executeWithException(() -> {
            LOG.debug("Call {}.search with workspace: {}", instance.getClass().getSimpleName(), workspaceId);

            RelationalPathBase<T> qObj = this.instance.getqObj();

            long off = (offset == null || offset < 0) ? 0L : offset.longValue();
            long lim = (limit == null || limit <= 0) ? SEARCH_LIMIT_DEFAULT : limit.longValue();

            QBean<?> selectList = Projections.bean(instance.getClass(), qObj.all());

            SQLQuery<?> query = queryFactory.select(selectList).from(qObj).offset(off);

            if (condition != null) {
                query = query.where(condition);
            }

            if (groupbys != null) {
                query = query.groupBy(groupbys);
            }

            if (orderBys != null && orderBys.size() > 0) {
                for (OrderSpecifier<?> orderBy : orderBys) {
                    query = query.orderBy(orderBy);
                }
            }

            query = query.limit(lim);

            LOG.info("SQL STRING: " + query.getSQL().getSQL());
            return query.fetch();
        });
    }

    public T findOne(Map<Path<?>, Object> keyMap) throws EarthException {
        String workspaceId = co.jp.nej.earth.model.constant.Constant.EARTH_WORKSPACE_ID;
        return findOne(workspaceId, keyMap);
    }

    @SuppressWarnings("unchecked")
    public T findOne(String workspaceId, Map<Path<?>, Object> keyMap) throws EarthException {
        EarthQueryFactory queryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        return (T) executeWithException(() -> {
            LOG.info("Call {}.findOne with workspace: {}", instance.getClass().getSimpleName(), workspaceId);

            RelationalPathBase<T> qObj = this.instance.getqObj();
            @SuppressWarnings("rawtypes")
            QBean<T> selectList = (QBean) Projections.bean(instance.getClass(), qObj.all());

            Predicate predicate = EModelUtil.buildCondition(keyMap, qObj, false);

            LOG.debug(EModelUtil.cleanQuery(queryFactory.select(selectList).from(qObj).where(predicate).getSQL()));
            LOG.info(queryFactory.select(selectList).from(qObj).where(predicate).getSQL().getSQL());
            T result = queryFactory.select(selectList).from(qObj).where(predicate).fetchOne();

            return result;
        });
    }

    public long delete(Map<Path<?>, Object> keyMap) throws EarthException {
        String workspaceId = co.jp.nej.earth.model.constant.Constant.EARTH_WORKSPACE_ID;
        return delete(workspaceId, keyMap);
    }

    public long delete(String workspaceId, Map<Path<?>, Object> keyMap) throws EarthException {

        LOG.info("Call {}.delete with workspace: {}", instance.getClass().getSimpleName(), workspaceId);

        EarthQueryFactory queryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        return (long) executeWithException(() -> {
            RelationalPathBase<T> qObj = this.instance.getqObj();

            Predicate predicate = EModelUtil.buildCondition(keyMap, qObj, true);

            return queryFactory.delete(qObj).where(predicate).execute();
        });
    }

    @Override
    public long deleteList(String workspaceId, List<Map<Path<?>, Object>> keyMaps) throws EarthException {

        LOG.info("Call {}.deleteList with workspace: {}", instance.getClass().getSimpleName(), workspaceId);

        // if nothing to delete then consider as delete success
        if (keyMaps == null || keyMaps.size() == 0) {
            return 0;
        }

        int nSuccess = 0;
        for (Map<Path<?>, Object> keyMap : keyMaps) {

            // Count number of success record delete
            nSuccess += delete(workspaceId, keyMap);
        }
        return nSuccess;
    }

    @Override
    public long deleteAll(String workspaceId) throws EarthException {

        LOG.info("Call {}.deleteAll with workspace: {}", instance.getClass().getSimpleName(), workspaceId);

        EarthQueryFactory queryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        return (long) executeWithException(() -> {
            RelationalPathBase<T> qObj = this.instance.getqObj();
            return queryFactory.delete(qObj).execute();
        });
    }

    @Override
    public long add(T t) throws EarthException {
        String workspaceId = co.jp.nej.earth.model.constant.Constant.EARTH_WORKSPACE_ID;
        return add(workspaceId, t);
    }

    @Override
    public long add(String workspaceId, T t) throws EarthException {
        if (workspaceId == null) {
            workspaceId = co.jp.nej.earth.model.constant.Constant.EARTH_WORKSPACE_ID;
        }
        LOG.info("Call {}.add with workspace: {}", instance.getClass().getSimpleName(), workspaceId);
        RelationalPathBase<T> qObj = this.instance.getqObj();
        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        return (long) executeWithException(() -> {
            return insertClause(earthQueryFactory, qObj, t).execute();
        });
    }

    @Override
    public long update(String workspaceId, Map<Path<?>, Object> keyMap, Map<Path<?>, Object> updateMap)
            throws EarthException {

        EarthQueryFactory earthQueryFactory = ConnectionManager.getEarthQueryFactory(workspaceId);
        return (long) executeWithException(() -> {
            LOG.info("Call {}.update with workspace: {}", instance.getClass().getSimpleName(), workspaceId);

            RelationalPathBase<T> qObj = this.instance.getqObj();
            Predicate predicate = EModelUtil.buildCondition(keyMap, qObj, true);
            List<Path<?>> paths = new ArrayList<>();
            List<Object> values = new ArrayList<>();
            Iterator<Entry<Path<?>, Object>> it = updateMap.entrySet().iterator();

            boolean hasLastUpdateTime = false;
            while (it.hasNext()) {
                Map.Entry<Path<?>, Object> pair = (Map.Entry<Path<?>, Object>) it.next();
                if (pair.getKey().getMetadata().getName().equals(ColumnNames.LAST_UPDATE_TIME.toString())) {
                    hasLastUpdateTime = true;
                }
                paths.add(pair.getKey());
                values.add(pair.getValue());
            }

            if (!hasLastUpdateTime) {
                StringPath lastUpdateTime = Expressions.stringPath(ColumnNames.LAST_UPDATE_TIME.toString());
                paths.add(lastUpdateTime);
                values.add(DateUtil.getCurrentDateString());
            }

            return earthQueryFactory.update(qObj).set(paths, values).where(predicate).execute();
        });
    }

    protected SQLUpdateClause updateClause(EarthQueryFactory earthQueryFactory, RelationalPathBase<T> qObject)
            throws EarthException {
        StringPath lastUpdateTime = Expressions.stringPath(ColumnNames.LAST_UPDATE_TIME.toString());

        return earthQueryFactory.update(qObject).set(lastUpdateTime, DateUtil.getCurrentDateString());
    }

    protected SQLInsertClause insertClause(EarthQueryFactory earthQueryFactory, RelationalPathBase<T> qObject, T t)
            throws EarthException {
        try {
            Field aField = this.instance.getClass().getSuperclass()
                    .getDeclaredField(ColumnNames.LAST_UPDATE_TIME.toString());
            aField.setAccessible(true);
            aField.set(t, DateUtil.getCurrentDateString());
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            throw new EarthException(e);
        }
        return earthQueryFactory.insert(qObject).populate(t);
    }

    public interface DaoCaller {
        Object execute() throws EarthException;
    }

    protected Object executeWithException(DaoCaller caller) throws EarthException {
        try {
            return caller.execute();
        } catch (Exception ex) {
            throw new EarthException(ex);
        }
    }
}
