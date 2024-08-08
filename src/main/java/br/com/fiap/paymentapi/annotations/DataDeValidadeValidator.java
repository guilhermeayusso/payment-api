package br.com.fiap.paymentapi.annotations;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataDeValidadeValidator implements ConstraintValidator<DataDeValidade, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            return true; // Consider empty values as valid. Use @NotEmpty to enforce non-empty constraint.
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth yearMonth = YearMonth.parse(value, formatter);
            YearMonth currentMonth = YearMonth.now();

            return !yearMonth.isBefore(currentMonth);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}

