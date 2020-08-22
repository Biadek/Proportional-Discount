package calculator;

import model.DiscountItem;
import validator.Validator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ProportionalDiscount extends DiscountCalculator {

    public ProportionalDiscount(List<DiscountItem> discountItems, Validator validator, BigDecimal totalDiscount) {
        super(discountItems, validator, totalDiscount);
    }

    @Override
    protected List<BigDecimal> calculateDiscountForProducts() {
        BigDecimal sumOfPrices = sumPrices(discountItems);
        List<BigDecimal> unitDiscounts = new ArrayList<>();
        BigDecimal sumOfUnitDiscounts = BigDecimal.ZERO;

        for (DiscountItem p : discountItems) {
            BigDecimal unitDiscount = p.getPrice().multiply(totalDiscount);
            unitDiscount = unitDiscount.divide(sumOfPrices, 2, RoundingMode.FLOOR);

            unitDiscounts.add(unitDiscount);

            sumOfUnitDiscounts = sumOfUnitDiscounts.add(unitDiscount);
        }
        addDifference(discountItems, unitDiscounts, totalDiscount, sumOfUnitDiscounts);

        return unitDiscounts;

    }

    private void addDifference(List<DiscountItem> discountItems, List<BigDecimal> unitDiscounts, BigDecimal discount, BigDecimal sumOfUnitDiscounts) {
        BigDecimal difference = discount.subtract(sumOfUnitDiscounts);

        for (int i = discountItems.size() - 1; difference.compareTo(BigDecimal.ZERO) > 0 && i >= 0; i--) {
            BigDecimal discountWithDifference = unitDiscounts.get(i).add(difference);
            BigDecimal oldPrice = discountItems.get(i).getPrice();
            difference = difference.subtract(oldPrice.subtract(unitDiscounts.get(i)));

            if (discountWithDifference.compareTo(oldPrice) > 0) { // the discount is greater than the base price of the product
                unitDiscounts.set(i, oldPrice);
            } else {
                unitDiscounts.set(i, discountWithDifference);
            }
        }
    }
}
