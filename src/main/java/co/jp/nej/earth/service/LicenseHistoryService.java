package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.StrCal;
import co.jp.nej.earth.web.form.SearchByColumnsForm;

import java.util.List;

public interface LicenseHistoryService {
    List<StrCal> search(Long offset, Long limit) throws EarthException;

    List<StrCal> search(SearchByColumnsForm searchByColumnsForm) throws EarthException;
}
