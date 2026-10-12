package kz.iitu.springlab.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NoDigitsValidator implements ConstraintValidator<NoDigits, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext ctx) {
        if (value == null) return true;      // если поле пустое, пропускаем
        return value.chars().noneMatch(Character::isDigit); // проверяет, что нет цифр
    }
}
