package co.jp.nej.earth.model.form;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cuongtm on 2017/08/08.
 */
public class BaseForm<T>  extends BaseModel<T> {
    private List<Message> messages = new ArrayList<>();

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
