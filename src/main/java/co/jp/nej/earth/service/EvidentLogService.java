package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.StrLogAccess;
import co.jp.nej.earth.web.form.SearchByColumnsForm;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;

public interface EvidentLogService {

    List<StrLogAccess> getListByWorkspaceId(String workspaceId, Long offset, Long limit,
                                            List<OrderSpecifier<?>> orderBys) throws EarthException;

    List<StrLogAccess> getListByWorkspaceIdColumn(String workspaceId, SearchByColumnsForm searchByColumnsForm,
                                            List<OrderSpecifier<?>> orderBys) throws EarthException;
}
