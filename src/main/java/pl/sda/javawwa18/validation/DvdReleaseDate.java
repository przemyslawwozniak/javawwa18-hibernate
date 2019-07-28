package pl.sda.javawwa18.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DefaultDvdReleaseDateValidator.class)
@Documented
public @interface DvdReleaseDate {
    String message() default "This movie is said to be released before 20/12/1996";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
