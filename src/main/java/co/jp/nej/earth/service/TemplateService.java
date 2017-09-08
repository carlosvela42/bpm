package co.jp.nej.earth.service;

import java.util.List;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MgrWorkspace;
import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.UserAccessRight;
import co.jp.nej.earth.model.entity.MgrProfile;
import co.jp.nej.earth.model.entity.MgrTemplate;
import co.jp.nej.earth.model.entity.MgrUser;
import co.jp.nej.earth.model.enums.TemplateType;
import co.jp.nej.earth.web.form.SearchForm;

public interface TemplateService {
    List<MgrTemplate> getAllByWorkspace(String workspaceId, String userId) throws EarthException;

    List<MgrTemplate> getAllByWorkspace(String workspaceId,String templateType, String userId) throws EarthException;

    List<MgrWorkspace> getAllWorkspace() throws EarthException;

    List<MgrTemplate> getTemplateListInfo(String workspaceId) throws EarthException;

    List<UserAccessRight> getUserAuthority(TemplateKey templateKey) throws EarthException;

    List<ProfileAccessRight> getProfileAuthority(TemplateKey templateKey) throws EarthException;

    MgrTemplate getById(TemplateKey templateKey) throws EarthException;

    List<MgrUser> getAllUser() throws EarthException;

    List<MgrProfile> getAllProfile() throws EarthException;

    long saveAuthority(TemplateKey templateKey, List<UserAccessRight> tUser, List<ProfileAccessRight> tProfile)
            throws EarthException;

    List<MgrTemplate> getTemplateByType(String workspaceId, String templateType) throws EarthException;

    List<MgrTemplate> getTemplateByType(String workspaceId, String templateType,String userId) throws EarthException;

    boolean deleteTemplates(List<String> templateIds, String workspaceId) throws EarthException;

    boolean insertOne(String workspaceId, MgrTemplate mgrTemplate) throws EarthException;

    boolean updateOne(String workspaceId, MgrTemplate mgrTemplate) throws EarthException;

    List<Message> checkExistsTemplate(String workspaceId, MgrTemplate mgrTemplate, String dbUser) throws EarthException;

    String getFieldJson(String workspaceId, SearchForm searchForm) throws EarthException;

    /**
     * Get List MgrTemplate by Ids
     *
     * @param workspaceId  workspace ID
     * @param ids List template id
     * @param type Template type
     * @return List MgrTemplate if OK, otherwise throw EarthException
     */
    List<MgrTemplate> getByIdsAndType(String workspaceId, List<String> ids, TemplateType type) throws EarthException;
}