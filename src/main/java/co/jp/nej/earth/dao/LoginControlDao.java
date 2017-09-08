package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.CtlLogin;
import co.jp.nej.earth.model.entity.StrCal;

import java.util.List;

public interface LoginControlDao extends BaseDao<CtlLogin> {

    List<StrCal> getNumberOnlineUserByProfile(String userId) throws EarthException;

    long updateOutTime(String sessionId, String outTime) throws EarthException;

    long deleteListByUserIds(List<String> userIds) throws EarthException;

    List<StrCal> getLicenceOfEntireSystem() throws EarthException;
}