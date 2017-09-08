package co.jp.nej.earth.model.form;

import co.jp.nej.earth.model.ProfileAccessRight;
import co.jp.nej.earth.model.UserAccessRight;

import java.util.List;

public class MenuAuthorityForm {

    private String functionId;
    private String functionName;
    private String functionCategoryId;
    private String functionCategoryName;
    private List<UserAccessRight> userAccessRights;
    private List<ProfileAccessRight> profileAccessRights;

    public MenuAuthorityForm() {

    }

    public MenuAuthorityForm(String functionId, String functionName, String functionCategoryId,
                             String functionCategoryName, List<UserAccessRight> userAccessRights,
                             List<ProfileAccessRight> profileAccessRights) {
        this.functionId = functionId;
        this.functionName = functionName;
        this.functionCategoryId = functionCategoryId;
        this.functionCategoryName = functionCategoryName;
        this.userAccessRights = userAccessRights;
        this.profileAccessRights = profileAccessRights;
    }

    public String getFunctionId() {
        return functionId;
    }

    public void setFunctionId(String functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionCategoryId() {
        return functionCategoryId;
    }

    public void setFunctionCategoryId(String functionCategoryId) {
        this.functionCategoryId = functionCategoryId;
    }

    public String getFunctionCategoryName() {
        return functionCategoryName;
    }

    public void setFunctionCategoryName(String functionCategoryName) {
        this.functionCategoryName = functionCategoryName;
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
