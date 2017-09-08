package co.jp.nej.earth.service;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.entity.MgrMenu;

import java.util.List;

public interface MenuService {

    List<MgrMenu> getMenuByProfileId(List<String> profileIds) throws EarthException;

    List<MgrMenu> getAll() throws EarthException;

    MgrMenu getDetail(String functionId) throws EarthException;

    List<UserAccessRight> getUserAuthority(String functionId) throws EarthException;

    List<ProfileAccessRight> getProfileAuthority(String functionId) throws EarthException;

    long saveAuthority(String functionId, List<UserAccessRight> userAccessRights,
                       List<ProfileAccessRight> profileAccessRights)
            throws EarthException;
}