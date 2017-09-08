package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.entity.MgrProfile;

import java.util.List;
import java.util.Map;

/**
 * Created by minhtv on 3/30/2017.
 */
public interface ProfileService {
    List<MgrProfile> getAll() throws EarthException;

    List<MgrProfile> getProfilesByUserId(String userId) throws EarthException;

    Map<String, Object> getDetail(String profileId) throws EarthException;

    List<Message> validate(MgrProfile mgrProfile, boolean insert);

    boolean insertAndAssignUsers(MgrProfile mgrProfile, List<String> userIds) throws EarthException;

    boolean insertOne(MgrProfile mgrProfile) throws EarthException;

    boolean updateAndAssignUsers(MgrProfile mgrProfile, List<String> userIds) throws EarthException;

    boolean deleteList(List<String> profileIds) throws EarthException;

//    boolean deleteListProfiles(List<String> profileIds) throws EarthException;

    boolean assignUsers(String profileId, List<String> userIds) throws EarthException;
}
