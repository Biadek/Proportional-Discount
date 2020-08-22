import calculator.DiscountCalculator;
import calculator.ProportionalDiscount;
import model.DiscountItem;
import model.Product;
import validator.ItemValidator;
import validator.Validator;

import java.math.BigDecimal;
import java.util.List;

public class Main {

    private static void presentTheResults(List<DiscountItem> items, List<BigDecimal> result, BigDecimal discount) {
        System.out.println("Discounts: ");
        result.forEach(System.out::println);

        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal sumBefore = BigDecimal.ZERO;

        for (BigDecimal b : result) {
            sum = sum.add(b);
        }

        for (DiscountItem p : items) {
            sumBefore = sumBefore.add(p.getPrice());
        }

        System.out.println("\nSum of product prices before discount: " + sumBefore);
        System.out.println("Total discount: " + discount);
        System.out.println("Sum of individual discounts: " + sum);
    }



    public static void main(String[] args) {

        List<DiscountItem> items = List.of(
                new Product("Product 1", new BigDecimal("500.00")),
                new Product("Product 2", new BigDecimal("1500.00")));
        BigDecimal discount = new BigDecimal("100.00");

        Validator validator = new ItemValidator();
        DiscountCalculator discountCalculator = new ProportionalDiscount(items, validator, discount);

        List<BigDecimal> result = discountCalculator.calculate();

        if (result != null) {
            presentTheResults(items, result, discount);
        }
    }
}
