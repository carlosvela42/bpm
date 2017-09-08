package co.jp.nej.earth.dao;

import java.util.List;
import java.util.Map;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.entity.CtlTemplate;
import co.jp.nej.earth.model.enums.AccessRight;

public interface TemplateAuthorityDao extends BaseDao<CtlTemplate> {

    Map<TemplateKey, AccessRight> getMixAuthority(String userId, String workspaceId) throws EarthException;

    long deleteListByUserIds(String workspaceId, List<String> userIds) throws EarthException;

    long deleteListByProfileIds(String workspaceId, List<String> profileIds) throws EarthException;

    long insertMixAuthority(TemplateKey templateKey, List<UserAccessRight> userAccessRights) throws EarthException;

    long deleteAllMixAuthority(TemplateKey templateKey) throws EarthException;

    long searchAllMixAuthority(TemplateKey templateKey) throws EarthException;

    List<TemplateKey> getTemplateKeysByProfile(String workspaceId, String profileId) throws EarthException;

    List<UserAccessRight> getUserAuthority(TemplateKey templateKey) throws EarthException;

    List<ProfileAccessRight> getProfileAuthority(TemplateKey templateKey) throws EarthException;

    List<String> getProfiles(TemplateKey templateKey) throws EarthException;

    long deleteAllUserAuthority(TemplateKey templateKey) throws EarthException;

    long insertUserAuthority(TemplateKey templateKey, List<UserAccessRight> userAccessRights) throws EarthException;

    long deleteAllProfileAuthority(TemplateKey templateKey) throws EarthException;

    long insertProfileAuthority(TemplateKey templateKey, List<ProfileAccessRight> profileAccessRights)
            throws EarthException;

    long countMixAuthority(TemplateKey templateKey) throws EarthException;

    long deleteMixAuthority(String workspaceId, List<String> templateIds) throws EarthException;

    long deleteUAuthority(String workspaceId, List<String> templateIds) throws EarthException;

    long deletePAuthority(String workspaceId, List<String> templateIds) throws EarthException;
}
