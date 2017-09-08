package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.CtlLogin;
import co.jp.nej.earth.web.form.SearchByColumnsForm;
import com.querydsl.core.types.OrderSpecifier;

import java.util.List;

public interface LoginStatusService {

    List<CtlLogin> getAll(Long offset, Long limit, OrderSpecifier<String> orderByColumn) throws EarthException;

    List<CtlLogin> getAllByColumn(SearchByColumnsForm searchByColumnsForm, OrderSpecifier<String> orderByColumn)
        throws EarthException;

    boolean deleteList(List<String> sessionIds) throws EarthException;
}