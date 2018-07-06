package superhero.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

@Constraint(validatedBy = {SuperheroValidator.class})
@Target({PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface IsTrueSuperhero {

    String message() default "cannot update a contests which doesn't have the status New";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
