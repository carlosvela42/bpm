package co.jp.nej.earth.dao;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.MenuAccessRight;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.entity.CtlMenu;
import co.jp.nej.earth.model.entity.MgrMenuP;
import co.jp.nej.earth.model.entity.MgrMenuU;

import java.util.List;
import java.util.Map;

public interface MenuAuthorityDao extends BaseDao<CtlMenu> {

    Map<String, MenuAccessRight> getMixAuthority(String userId) throws EarthException;

    long deleteListByUserIds(List<String> userIds) throws EarthException;

    long deleteListByUserIds(String workspaceId, List<String> userIds) throws EarthException;

    long deleteListByProfileIds(List<String> profileIds) throws EarthException;

    long insertMixAuthority(String menuId, List<UserAccessRight> userAccessRights) throws EarthException;

    long deleteAllMixAuthority(List<String> menuIds) throws EarthException;

    List<UserAccessRight> getUserAuthority(String menuId) throws EarthException;

    List<UserAccessRight> getUserAuthorityByProfiles(String menuId) throws EarthException;

    List<ProfileAccessRight> getProfileAuthority(String menuId) throws EarthException;

    long deleteAllUserAuthority(String functionId) throws EarthException;

    long deleteAllProfileAuthority(String functionId) throws EarthException;

    long insertUserAuthority(String functionId, List<UserAccessRight> userAccessRights) throws EarthException;

    long insertProfileAuthority(String functionId, List<ProfileAccessRight> profileAccessRights)
            throws EarthException;

    long insertMenuU(MgrMenuU mgrMenuU) throws EarthException;

    long insertMenuP(MgrMenuP mgrMenuP) throws EarthException;

}
