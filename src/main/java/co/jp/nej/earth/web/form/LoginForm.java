package co.jp.nej.earth.web.form;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginForm {

    @NotEmpty(message = "E0001,userId")
    private String userId;

    //@NotEmpty(message = "E0001,password")
    private String password;

    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
