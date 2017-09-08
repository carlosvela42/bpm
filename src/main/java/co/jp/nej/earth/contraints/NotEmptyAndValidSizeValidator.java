package co.jp.nej.earth.contraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by cuongtm on 2017/07/27.
 */
public class NotEmptyAndValidSizeValidator implements ConstraintValidator<NotEmptyAndValidSize, String> {

    private Boolean notEmpty;
    private Integer min;
    private Integer max;
    private String messageNotEmpty;
    private String messageLengthMax;
    private String messageLengthMin;

    @Override
    public void initialize(NotEmptyAndValidSize field) {
        notEmpty = field.notEmpty();
        min = field.min();
        max = field.max();
        messageNotEmpty = field.messageNotEmpty();
        messageLengthMax = field.messageLengthMax();
        messageLengthMin = field.messageLengthMin();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        if (notEmpty && value.isEmpty()) {
            context.buildConstraintViolationWithTemplate(messageNotEmpty).addConstraintViolation();
            return false;
        }

        if ((min > 0) && (value != null) && (value.length() < min)) {
            context.buildConstraintViolationWithTemplate(messageLengthMin).addConstraintViolation();
            return false;
        }

        if ((max < Integer.MAX_VALUE) && (value != null) && (value.length() > max)) {
            context.buildConstraintViolationWithTemplate(messageLengthMax).addConstraintViolation();
            return false;
        }

        return true;
    }
}
