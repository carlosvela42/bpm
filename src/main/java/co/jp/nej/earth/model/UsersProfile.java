package co.jp.nej.earth.model;

import java.io.Serializable;

public class UsersProfile implements Serializable {

    private String userId;
    private String name;
    private String userIdChoose;

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

    public String getUserIdChoose() {
        return userIdChoose;
    }

    public void setUserIdChoose(String userIdChoose) {
        this.userIdChoose = userIdChoose;
    }
}
