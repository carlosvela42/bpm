package co.jp.nej.earth.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Message implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String code;
    private String content;
    private MessageTye messageType = MessageTye.ERROR;
    private boolean isChecked = false;
//    Binding value to html: true or false
    @JsonIgnore
    private String isCheckedString;

    public Message() {}

    public Message(String code, String content) {
        this.code = code;
        this.content = code + ": " + content;
    }

    public Message(String code, String content, MessageTye messageType) {
        this(code, content);
        this.messageType = messageType;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageTye getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTye messageType) {
        this.messageType = messageType;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getIsCheckedString() {
        return isCheckedString;
    }

    public void setIsCheckedString(String isCheckedString) {
        this.isCheckedString = isCheckedString;
    }

    public enum  MessageTye {
        ERROR("1"), WARNING("2"), INFO("3");

        private String value;

        MessageTye(String value) {
            this.setValue(value);
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public boolean isError() {
        return MessageTye.ERROR.equals(this.getMessageType());
    }

    public boolean isInfo() {
        return MessageTye.INFO.equals(this.getMessageType());
    }

    public static Set<String> getListWarning(List<Message> messages) {
        Set<String> set = new HashSet<>();
        if(!CollectionUtils.isEmpty(messages)) {
            for(Message message : messages) {
                if("true".equals(message.getIsCheckedString())){
                    set.add(message.getCode());
                }
            }
        }
        return set;
    }

    public static List<Message> updateCheckedWarning(List<Message> messages, Set<String> set) {
        for(Message message : messages) {
            if(set.contains(message.getCode())){
                message.setChecked(true);
            }
        }
        return messages;
    }

    public static boolean isAllWarningChecked(List<Message> messages) {
        for(Message message : messages) {
            if(message.isError() || !message.isChecked()){
                return false;
            }
        }
        return true;
    }

    public static boolean isAllWarningOrEmpty(List<Message> messages) {
        if(!CollectionUtils.isEmpty(messages)) {
            for(Message message : messages) {
                if(message.isError()){
                    return false;
                }
            }
        }
        return true;
    }
}
