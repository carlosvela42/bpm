package co.jp.nej.earth.model.form;

import co.jp.nej.earth.contraints.NotEmptyAndValidSize;
import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.UsersProfile;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrProfile;

import javax.validation.constraints.Pattern;
import java.util.List;

public class ProfileForm extends BaseModel<MgrProfile> {

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,profile.id",
        messageLengthMax = "E0026,profile.id," + Constant.Regexp.MAX_LENGTH)
    @Pattern(regexp = Constant.Regexp.ALPHABETS_VALIDATION, message = "E0004,profile.id")
    private String profileId;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,profile.description",
        messageLengthMax = "E0026,profile.description," + Constant.Regexp.MAX_LENGTH)
    private String description;

    private String availableLicenceCount;
    @NotEmptyAndValidSize(max = Constant.Regexp.MAX_LENGTH_LDAP,
        messageLengthMax = "E0026,profile.ldapIdentifier," + Constant.Regexp.MAX_LENGTH_LDAP)
    private String ldapIdentifier;

    private String userIds;
    private List<UsersProfile> usersProfiles;

    public ProfileForm() {

    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getAvailableLicenceCount() {
        return availableLicenceCount;
    }

    public void setAvailableLicenceCount(String availableLicenceCount) {
        this.availableLicenceCount = availableLicenceCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLdapIdentifier() {
        return ldapIdentifier;
    }

    public void setLdapIdentifier(String ldapIdentifier) {
        this.ldapIdentifier = ldapIdentifier;
    }

    public String getUserIds() {
        return userIds;
    }

    public void setUserIds(String userIds) {
        this.userIds = userIds;
    }

    public List<UsersProfile> getUsersProfiles() {
        return usersProfiles;
    }

    public void setUsersProfiles(List<UsersProfile> usersProfiles) {
        this.usersProfiles = usersProfiles;
    }
}
