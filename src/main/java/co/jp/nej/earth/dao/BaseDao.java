package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.BaseModel;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;

import java.util.List;
import java.util.Map;

/**
 * Base DAO Model.
 *
 * @param <T>
 * @author landd
 */
public interface BaseDao<T extends BaseModel<T>> {

    /**
     * Get all record of type T.
     *
     * @param workspaceId the workspace where item reside
     * @return List of T record
     * @throws EarthException General Exception
     */
    List<T> findAll(String workspaceId, Long offset, Long limit, List<OrderSpecifier<?>> orderBys,
                    Path<?>[] groupbys) throws EarthException;

    List<T> findAll(String workspaceId) throws EarthException;

    List<T> findAll(String workspaceId, OrderSpecifier<?> orderBy) throws EarthException;

    List<T> search(String workspaceId, Predicate condition) throws EarthException;

    List<T> search(String workspaceId, Predicate condition, Long offset, Long limit,
                   List<OrderSpecifier<?>> orderBys, Path<?>[] groupbys) throws EarthException;

    T findOne(Map<Path<?>, Object> keyMap) throws EarthException;

    T findOne(String workspaceId, Map<Path<?>, Object> keyMap) throws EarthException;

    long delete(Map<Path<?>, Object> keyMap) throws EarthException;

    long delete(String workspaceId, Map<Path<?>, Object> keyMap) throws EarthException;

    long deleteList(String workspaceId, List<Map<Path<?>, Object>> keyMap) throws EarthException;

    long deleteAll(String workspaceId) throws EarthException;

    long add(T t) throws EarthException;

    long add(String workspaceId, T t) throws EarthException;

    long update(String workspaceId, Map<Path<?>, Object> keyMap, Map<Path<?>, Object> valueMap) throws EarthException;
}
