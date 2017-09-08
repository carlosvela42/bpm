package co.jp.nej.earth.model.form;

import co.jp.nej.earth.model.constant.Constant;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class UserDetailForm {

    @NotEmpty(message = "E0001,UserId")
    @Pattern(regexp = Constant.Regexp.ALPHABETS_VALIDATION, message = "E0004,UserId")
    private String userId;

    @NotEmpty(message = "E0001,UserName")
    private String name;

    private String password;

    private String confirmPassword;

    private boolean changePassword;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isChangePassword() {
        return changePassword;
    }

    public void setChangePassword(boolean changePassword) {
        this.changePassword = changePassword;
    }
}
