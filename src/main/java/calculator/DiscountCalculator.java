package calculator;

import model.DiscountItem;
import validator.Validator;

import java.math.BigDecimal;
import java.util.List;

public abstract class DiscountCalculator {

    protected List<DiscountItem> discountItems;
    protected Validator validator;
    protected BigDecimal totalDiscount;

    public DiscountCalculator(List<DiscountItem> discountItems, Validator validator, BigDecimal totalDiscount) {
        this.discountItems = discountItems;
        this.validator = validator;
        this.totalDiscount = totalDiscount;
    }

    abstract List<BigDecimal> calculateDiscountForProducts();

    public List<BigDecimal> calculate() {
        if (checkTotalDiscount() && checkPriceOfProductsAndDiscount() && validator.validateItems(discountItems)) {
            return calculateDiscountForProducts();
        }
        return null;
    }


    private boolean checkTotalDiscount() {
        return totalDiscount != null && totalDiscount.compareTo(BigDecimal.ZERO) >= 0 && totalDiscount.scale() <=2;
    }

    private boolean checkPriceOfProductsAndDiscount() {
        BigDecimal sum = sumPrices(discountItems);
        return sum.compareTo(BigDecimal.ZERO) > 0 && sum.compareTo(totalDiscount) >= 0;
    }

    protected BigDecimal sumPrices(List<DiscountItem> discountItems) {
        BigDecimal sum = BigDecimal.ZERO;
        for (DiscountItem p : discountItems) {
            sum = sum.add(p.getPrice());
        }
        return sum;
    }

    public void setDiscountItems(List<DiscountItem> discountItems) {
        this.discountItems = discountItems;
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }
}
