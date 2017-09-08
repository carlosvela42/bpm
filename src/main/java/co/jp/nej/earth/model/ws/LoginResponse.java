package co.jp.nej.earth.model.ws;

import java.util.List;

import co.jp.nej.earth.model.Message;

public class LoginResponse extends Response {
    private String token;

    public LoginResponse(boolean result, List<Message> messages) {
        super(result, messages);
    }

    public LoginResponse(boolean result, String token) {
        super(result);
        this.token = token;
    }

    /**
     * @return the token.
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token the token to set.
     */
    public void setToken(String token) {
        this.token = token;
    }
}
