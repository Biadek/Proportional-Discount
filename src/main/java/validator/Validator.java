package validator;

import model.DiscountItem;

import java.util.List;

public interface Validator {

    boolean validateItems(List<DiscountItem> discountItems);
}
