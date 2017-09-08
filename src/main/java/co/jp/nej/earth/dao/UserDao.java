package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.UsersProfile;
import co.jp.nej.earth.model.entity.MgrUser;

import java.util.List;

public interface UserDao extends BaseDao<MgrUser> {
    MgrUser getById(String userId) throws EarthException;

    MgrUser getUserByIdAndPassword(String userId, String password) throws EarthException;

    List<MgrUser> getAll() throws EarthException;

    long updateOne(MgrUser mgrUser) throws EarthException;

    long deleteList(List<String> userIds) throws EarthException;

    List<UsersProfile> getUsersByProfileId(String profileId) throws EarthException;

    List<String> getUserIdsByProfileId(String profileId) throws EarthException;
}