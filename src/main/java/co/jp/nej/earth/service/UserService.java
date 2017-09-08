package co.jp.nej.earth.service;


import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MessagesWithToken;
import co.jp.nej.earth.model.UsersProfile;
import co.jp.nej.earth.model.entity.CtlLogin;
import co.jp.nej.earth.model.entity.MgrUser;
import co.jp.nej.earth.model.enums.Channel;
import com.querydsl.core.types.Path;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {
    List<Message> login(String userId, String password, HttpSession session, Channel channel) throws EarthException;

    MessagesWithToken loginBatch(String userId, String password, String processId) throws EarthException;

    boolean logout(HttpSession session) throws EarthException;

    boolean logoutBatch(String userId, String token) throws EarthException;

    List<MgrUser> getAll() throws EarthException;

    List<UsersProfile> getUsersProfileId(String profileId) throws EarthException;

    List<Message> validate(MgrUser mgrUser, boolean insert) throws EarthException;

    boolean insertOne(MgrUser mgrUser) throws EarthException;

    boolean updateOne(MgrUser mgrUser) throws EarthException;

    boolean deleteList(List<String> userIds) throws EarthException;

    Map<String, Object> getDetail(String userId) throws EarthException;

    CtlLogin getCtlLoginDetail(Map<Path<?>, Object> condition) throws EarthException;

    long deleteCtlLogin(Map<Path<?>, Object> condition) throws EarthException;

    long deleteCtlLogins(List<Map<Path<?>, Object>> condition) throws EarthException;

    long deleteAllCtlLogins() throws EarthException;

    long addCtlLogin(CtlLogin login) throws EarthException;

    long updateCtlLogin(Map<Path<?>, Object> condition, Map<Path<?>, Object> updateMap) throws EarthException;

}
