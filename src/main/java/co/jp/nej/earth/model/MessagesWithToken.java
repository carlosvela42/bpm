package co.jp.nej.earth.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains list message and token after login
 */
public class MessagesWithToken implements Serializable {
    private String token;
    private List<Message> messages;

    public MessagesWithToken() {
        messages = new ArrayList<>();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
