package co.jp.nej.earth.model.ws;

import java.util.List;

import co.jp.nej.earth.model.Message;

public class GetThumbernailResponse extends Response {
    private String thumbernail;
    /**
     *
     */
    public GetThumbernailResponse() {
        super();
    }

    /**
    *
    */
    public GetThumbernailResponse(String message) {
        super(message);
    }

    public GetThumbernailResponse(List<Message> messages) {
        super(messages);
    }

    /**
     * @param binaryData
     */
    public GetThumbernailResponse(boolean result, String thumbernail) {
        super(result);
        this.thumbernail = thumbernail;
    }

    /**
     * @return the thumbernail
     */
    public String getThumbernail() {
        return thumbernail;
    }

    /**
     * @param thumbernail the thumbernail to set
     */
    public void setThumbernail(String thumbernail) {
        this.thumbernail = thumbernail;
    }
}
