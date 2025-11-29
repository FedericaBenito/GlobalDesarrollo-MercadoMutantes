package GLOBAL_MUTANTES.Validaciones;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidacionDNASecuenciaValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidacionDNASecuencia {
    String message() default "Secuencia DNA ingresada invalida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
