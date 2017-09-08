package co.jp.nej.earth.model.ws;

import java.util.ArrayList;
import java.util.List;

import co.jp.nej.earth.model.Message;

public class Response {
    private boolean result = false;
    private List<Message> messages = new ArrayList<>();

    /**
     *
     */
    public Response() {
        super();
    }

    public Response(String strMessage) {
        setMessages(strMessage);
    }

    public Response(Message message) {
        setMessages(message);
    }

    /**
     * @param messages
     */
    public Response(List<Message> messages) {
        super();
        this.messages = messages;
    }

    /**
     * @param result
     */
    public Response(boolean result) {
        super();
        this.result = result;
    }

    /**
     * @param result
     * @param messages
     */
    public Response(boolean result, List<Message> messages) {
        super();
        this.result = result;
        this.messages = messages;
    }

    /**
     * @return the result
     */
    public boolean isResult() {
        return result;
    }

    /**
     * @param result the result to set
     */
    public void setResult(boolean result) {
        this.result = result;
    }

    /**
     * @return the messages
     */
    public List<Message> getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    /**
     * @param m the messages to set
     */
    public void setMessages(Message m) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        messages.add(m);
    }

    /**
     * @param me the messages to set
     */
    public void setMessages(String me) {
        setMessages(new Message(null, me));
    }
}
