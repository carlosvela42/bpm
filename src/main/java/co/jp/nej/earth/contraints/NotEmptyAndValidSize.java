package co.jp.nej.earth.contraints;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Constraint(validatedBy = NotEmptyAndValidSizeValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmptyAndValidSize {

    String message() default "Wrong data of string field";

    String messageNotEmpty() default "Field can't be empty";

    String messageLengthMax() default "Max length of field";

    String messageLengthMin() default "Min length of field";

    boolean notEmpty() default false;

    int min() default 0;

    int max() default Integer.MAX_VALUE;

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
