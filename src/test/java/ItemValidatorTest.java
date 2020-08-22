import model.DiscountItem;
import model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import validator.ItemValidator;
import validator.Validator;

import java.math.BigDecimal;
import java.util.List;

public class ItemValidatorTest {

    public static Validator validator;

    @BeforeAll
    public static void init() {
        validator = new ItemValidator();
    }

    @Test
    public void correctData() {
        //given
        List<DiscountItem> items = List.of(
                new Product("Product 1", new BigDecimal("500.00")),
                new Product("Product 2", new BigDecimal("1500.00")));
        //when
        boolean result = validator.validateItems(items);

        //then
        Assertions.assertTrue(result);
    }

    @Test
    public void tooLargeList() {
        //given
        List<DiscountItem> items = List.of(
                new Product("Product 1", new BigDecimal("500.00")),
                new Product("Product 2", new BigDecimal("220.34")),
                new Product("Product 3", new BigDecimal("10.00")),
                new Product("Product 4", new BigDecimal("63.76")),
                new Product("Product 5", new BigDecimal("62.00")),
                new Product("Product 6", new BigDecimal("523.84")));
        //when
        boolean result = validator.validateItems(items);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void nullList() {
        //given
        List<DiscountItem> items = null;

        //when
        boolean result = validator.validateItems(items);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void negativePriceOfProduct() {
        //given
        List<DiscountItem> items = List.of(
                new Product("Product 1", new BigDecimal("-10.00")),
                new Product("Product 2", new BigDecimal("22.34")));
        //when
        boolean result = validator.validateItems(items);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void nullPriceOfProduct() {
        //given
        List<DiscountItem> items = List.of(
                new Product("Product 1", null),
                new Product("Product 2", new BigDecimal("22.34")));
        //when
        boolean result = validator.validateItems(items);

        //then
        Assertions.assertFalse(result);
    }

    @Test
    public void wrongCurrencyFormat() {
        //given
        List<DiscountItem> items = List.of(
                new Product("Product 1", new BigDecimal("113.251")));
        //when
        boolean result = validator.validateItems(items);

        //then
        Assertions.assertFalse(result);
    }

}
