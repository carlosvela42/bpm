package co.jp.nej.earth.model.ws;

import org.hibernate.validator.constraints.NotEmpty;

public class LoginRequest {
    @NotEmpty(message = "E0001,userId")
    private String userId;
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
