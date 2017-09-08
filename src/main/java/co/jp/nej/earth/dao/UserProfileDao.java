package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrUserProfile;

import java.util.List;

/**
 * Created by minhtv on 3/29/2017.
 */
public interface UserProfileDao extends BaseDao<MgrUserProfile> {
    long deleteListByUserIds(List<String> userIds) throws EarthException;

    long deleteListByProfileIds(List<String> profileIds) throws EarthException;

    List<MgrUserProfile> getListByProfileIds(List<String> profileIds) throws EarthException;

}
