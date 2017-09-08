package co.jp.nej.earth.util;

import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.constant.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public class ValidatorUtil {
    @Autowired
    private EMessageResource eMessageResource;

    /*public List<Message> validate(BindingResult result) {
        List<Message> messages = new ArrayList<Message>();

        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                if (fieldError.getCode().equals(Constant.ErrorCode.E_TYPE_MISMATCH)) {
                    String fieldName = fieldError.getField();
                    String[] params = { eMessageResource.get(fieldName) };
                    messages.add(new Message(fieldError.getCode(),
                            eMessageResource.get(Constant.ErrorCode.E_TYPE_MISMATCH, params)));
                } else {
                    String messageInput = fieldError.getDefaultMessage();
                    String messageCode = EStringUtil.EMPTY;
                    String[] params = null;
                    if (!EStringUtil.isEmpty(messageInput)) {
                        String[] arrMessages = messageInput.split(",");
                        messageCode = arrMessages[0].trim();
                        if (arrMessages.length > 1) {
                            params = new String[arrMessages.length - 1];
                            for (int i = 1; i < arrMessages.length; i++) {
                                params[i - 1] = arrMessages[i].trim();
                            }
                        }
                    }
                    messages.add(new Message(fieldError.getCode(), eMessageResource.get(messageCode, params)));
                }
            }

        }

        return messages;
    }*/

    public List<Message> validate(BindingResult result) {
        return validate(result, null);
    }

    public List<Message> validate(BindingResult result, List<String> fieldOrders) {
        List<Message> messages = new ArrayList<Message>();

        if (result.hasErrors()) {
            List<FieldError> fieldErrors =new ArrayList<FieldError>(result.getFieldErrors());
            if((fieldOrders != null) && (fieldOrders.size() >0)) {
                Collections.sort(fieldErrors, new FieldErrorComparer(fieldOrders));
            }
            for (FieldError fieldError : fieldErrors) {
                if (fieldError.getCode().equals(Constant.ErrorCode.E_TYPE_MISMATCH)) {
                    String fieldName = fieldError.getField();
                    String[] params = { eMessageResource.get(fieldName) };
                    messages.add(new Message(Constant.ErrorCode.E0008,
                            eMessageResource.get(Constant.ErrorCode.E0008, params)));
                } else {
                    String messageInput = fieldError.getDefaultMessage();
                    String messageCode = EStringUtil.EMPTY;
                    String[] params = null;
                    if (!EStringUtil.isEmpty(messageInput)) {
                        String[] arrMessages = messageInput.split(",");
                        messageCode = arrMessages[0].trim();
                        if (arrMessages.length > 1) {
                            params = new String[arrMessages.length - 1];
                            for (int i = 1; i < arrMessages.length; i++) {
                                params[i - 1] = arrMessages[i].trim();
                            }
                        }
                    }
                    messages.add(new Message(messageCode, eMessageResource.get(messageCode, params)));
                }
            }
        }

        return messages;
    }

    private class FieldErrorComparer implements Comparator<FieldError> {
        private List<String> orders;

        public void setOrders(List<String> orders) {
            this.orders = orders;
        }

        FieldErrorComparer(List<String> fieldOrders) {
            this.setOrders(fieldOrders);
        }

        @Override
        public int compare(FieldError fe1, FieldError fe2) {

            String field1 = fe1.getField();
            String field2 = fe2.getField();

            if ((orders != null) && (orders.size() > 0)) {
                int field1Index = orders.indexOf(field1);
                int field2Index = orders.indexOf(field2);
                return Integer.valueOf(field1Index).compareTo(field2Index);
            }

            return field1.compareTo(field2);

        }
    }

}
