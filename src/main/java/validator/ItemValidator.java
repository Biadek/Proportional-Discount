package validator;

import model.DiscountItem;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ItemValidator implements validator.Validator {

    private static final Validator VALIDATOR = Validation.buildDefaultValidatorFactory().getValidator();
    private static final Integer MAXLISTSIZE = 5;


    @Override
    public boolean validateItems(List<DiscountItem> discountItems) {
        if (discountItems == null || discountItems.size() > MAXLISTSIZE) {
            return false;
        }

        Set<ConstraintViolation<DiscountItem>> violations = new HashSet<>();
        for (DiscountItem p : discountItems) {
            violations.addAll(VALIDATOR.validate(p));
        }
        return violations.size() == 0;
    }
}