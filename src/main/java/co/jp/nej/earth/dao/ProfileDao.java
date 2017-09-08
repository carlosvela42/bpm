package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.entity.MgrProfile;

import java.util.List;

public interface ProfileDao extends BaseDao<MgrProfile> {
    List<MgrProfile> getProfilesByUserId(String userId) throws EarthException;

    MgrProfile getById(String profileId) throws EarthException;

    List<MgrProfile> getAll() throws EarthException;

    long deleteList(List<String> profileIds) throws EarthException;

    long insertOne(MgrProfile mgrProfile) throws EarthException;

    long assignUsers(String profileId, List<String> userIds) throws EarthException;

//    long unAssignAllUsers(String profileIds) throws EarthException;

    long updateOne(MgrProfile mgrProfile) throws EarthException;
}
