import calculator.DiscountCalculator;
import calculator.ProportionalDiscount;
import model.DiscountItem;
import model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import validator.ItemValidator;
import validator.Validator;

import java.math.BigDecimal;
import java.util.List;

public class ProportionalDiscountTest {

    public static Validator validator;

    @BeforeAll
    public static void init() {
        validator = new ItemValidator();
    }

    @Test
    public void discountWithoutDifference() {
        //given
        List<DiscountItem> discountItems = List.of(
                new Product("Product 1", new BigDecimal("500.00")),
                new Product("Product 2", new BigDecimal("1500.00")));
        BigDecimal discount = new BigDecimal("100.00");
        DiscountCalculator discountCalculator = new ProportionalDiscount(discountItems, validator, discount);

        //when
        List<BigDecimal> result = discountCalculator.calculate();

        //then
        Assertions.assertEquals(discount, sumDiscounts(result));
    }

    @Test
    public void differenceGreaterThanBasePriceOfTheLastProduct() {
        //given
        List<DiscountItem> discountItems = List.of(
                new Product("Product 1", new BigDecimal("582.44")),
                new Product("Product 2", new BigDecimal("1.99")),
                new Product("Product 3", new BigDecimal("78.92")),
                new Product("Product 4", new BigDecimal("150.89")),
                new Product("Product 5", new BigDecimal("0.40"))
        );
        BigDecimal discount = new BigDecimal("789.77");
        DiscountCalculator discountCalculator = new ProportionalDiscount(discountItems, validator, discount);

        //when
        List<BigDecimal> result = discountCalculator.calculate();

        //then
        Assertions.assertEquals(discount, sumDiscounts(result));
    }

    @Test
    public void differenceSmallerThanBasePriceOfTheLastProduct() {
        //given
        List<DiscountItem> discountItems = List.of(
                new Product("Product 1", new BigDecimal("33.33")),
                new Product("Product 2", new BigDecimal("21.24")),
                new Product("Product 3", new BigDecimal("1.00")),
                new Product("Product 4", new BigDecimal("14.09")),
                new Product("Product 5", new BigDecimal("7.82"))
        );
        BigDecimal discount = new BigDecimal("11.80");
        DiscountCalculator discountCalculator = new ProportionalDiscount(discountItems, validator, discount);

        //when
        List<BigDecimal> result = discountCalculator.calculate();

        //then
        Assertions.assertEquals(discount, sumDiscounts(result));
    }

    @Test
    public void discountGreaterThanSumOfPrices() {
        //given
        List<DiscountItem> discountItems = List.of(
                new Product("Product 1", new BigDecimal("72.99")),
                new Product("Product 2", new BigDecimal("0.99"))
        );
        BigDecimal discount = new BigDecimal("100.01");
        DiscountCalculator discountCalculator = new ProportionalDiscount(discountItems, validator, discount);

        //when
        List<BigDecimal> result = discountCalculator.calculate();

        //then
        Assertions.assertNull(result);
    }

    @Test
    public void sumOfPricesEqualsZero() {
        //given
        List<DiscountItem> discountItems = List.of(
                new Product("Product 1", new BigDecimal("0.00")),
                new Product("Product 2", new BigDecimal("0.00"))
        );
        BigDecimal discount = new BigDecimal("0.00");
        DiscountCalculator discountCalculator = new ProportionalDiscount(discountItems, validator, discount);

        //when
        List<BigDecimal> result = discountCalculator.calculate();

        //then
        Assertions.assertNull(result);
    }

    private BigDecimal sumDiscounts(List<BigDecimal> discounts) {
        BigDecimal sum = BigDecimal.ZERO;

        for (BigDecimal b : discounts) {
            sum = sum.add(b);
        }
        return sum;
    }

}
