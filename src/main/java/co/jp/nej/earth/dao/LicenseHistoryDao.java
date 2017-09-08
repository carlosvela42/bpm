package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.StrCal;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;

import java.util.List;

public interface LicenseHistoryDao extends BaseDao<StrCal> {

    List<StrCal> searchColumn(String workspaceId, Predicate condition, Long offset, Long limit,
                              List<OrderSpecifier<?>> orderBys, Path<?>[] groupBys) throws EarthException;
}
