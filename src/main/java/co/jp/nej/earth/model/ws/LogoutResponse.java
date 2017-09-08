package co.jp.nej.earth.model.ws;

import java.util.List;

import co.jp.nej.earth.model.Message;

public class LogoutResponse extends Response {

    public LogoutResponse(boolean result, List<Message> messages) {
        super(result, messages);
    }

}
