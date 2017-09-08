package co.jp.nej.earth.model.form;

import co.jp.nej.earth.contraints.NotEmptyAndValidSize;
import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.MgrUser;

import javax.validation.constraints.Pattern;

public class UserForm extends BaseModel<MgrUser> {
    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,user.id",
        messageLengthMax = "E0026,user.id,255")
    @Pattern(regexp = Constant.Regexp.ALPHABETS_VALIDATION, message = "E0004,user.id")
    private String userId;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,user.name",
        messageLengthMax = "E0026,user.name,255")
    private String name;

    @NotEmptyAndValidSize(max = Constant.Regexp.MAX_LENGTH_PASS, messageLengthMax = "E0026,user.password,515")
    private String password;

    @NotEmptyAndValidSize(max = Constant.Regexp.MAX_LENGTH_PASS, messageLengthMax = "E0026,user.confirmPassword,515")
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
