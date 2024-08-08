package br.com.fiap.paymentapi.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DataDeValidadeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataDeValidade {
    String message() default "Data de validade inválida";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
