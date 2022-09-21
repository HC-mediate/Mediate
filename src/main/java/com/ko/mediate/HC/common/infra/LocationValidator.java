package com.ko.mediate.HC.common.infra;

import com.ko.mediate.HC.common.domain.Location;
import com.ko.mediate.HC.common.domain.LocationValid;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

@Component
public class LocationValidator implements ConstraintValidator<LocationValid, Location> {

    @Override
    public void initialize(LocationValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Location value, ConstraintValidatorContext context) {
        if (Objects.isNull(value)
                || Objects.isNull(value.getLongitude())
                || Objects.isNull(value.getLatitude())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("위도 혹은 경도가 비어있습니다.").addConstraintViolation();
            return false;
        }
        if (value.getLatitude() < 33.0 || value.getLatitude() > 43.0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("위도 범위를 벗어났습니다. 33~43").addConstraintViolation();
            return false;
        } else if (value.getLongitude() < 124.0 || value.getLongitude() > 132.0) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate("경도 범위를 벗어났습니다. 124~132")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
