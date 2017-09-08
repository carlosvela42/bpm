package co.jp.nej.earth.dao;

import java.util.List;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.StrCal;

public interface StrCalDao {
    long deleteListByProfileIds(List<String> profileIds) throws EarthException;

    List<StrCal> getListByProfileIds(List<String> profileIds) throws EarthException;
}
