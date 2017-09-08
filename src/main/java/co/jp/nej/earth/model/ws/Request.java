package co.jp.nej.earth.model.ws;

import org.hibernate.validator.constraints.NotEmpty;

public class Request {

    @NotEmpty(message = "E0001,token")
    private String token;

    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the sesssionId to set
     */
    public void setToken(String token) {
        this.token = token;
    }

}
