package co.jp.nej.earth.model.form;

import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.UserAccessRight;

import java.util.List;

public class TemplateAuthorityForm {

    private String templateId;
    private String templateName;
    private List<UserAccessRight> userAccessRights;
    private List<ProfileAccessRight> profileAccessRights;

    public TemplateAuthorityForm() {
    }

    public TemplateAuthorityForm(String templateId, String templateName, List<UserAccessRight> userAccessRights,
                                 List<ProfileAccessRight> profileAccessRights) {
        super();
        this.templateId = templateId;
        this.templateName = templateName;
        this.userAccessRights = userAccessRights;
        this.profileAccessRights = profileAccessRights;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public List<UserAccessRight> getUserAccessRights() {
        return userAccessRights;
    }

    public void setUserAccessRights(List<UserAccessRight> userAccessRights) {
        this.userAccessRights = userAccessRights;
    }

    public List<ProfileAccessRight> getProfileAccessRights() {
        return profileAccessRights;
    }

    public void setProfileAccessRights(List<ProfileAccessRight> profileAccessRights) {
        this.profileAccessRights = profileAccessRights;
    }
}
