package task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyCacheTests {
    @Test
    void should_ReturnValueFromCache_When_CallCachedMethodSecondAndMoreTimes() {
        Fraction fraction = new Fraction(4, 8);
        Fractionable fractionable = Utils.cache(fraction);
        Assertions.assertEquals(0, fraction.getOriginalMethodCalledCounter());
        fractionable.doubleValue();
        Assertions.assertEquals(1, fraction.getOriginalMethodCalledCounter());
        fractionable.doubleValue();
        fractionable.doubleValue();
        Assertions.assertEquals(1, fraction.getOriginalMethodCalledCounter());
    }

    @Test
    void should_ReturnCorrectValue_When_ReturnValueFromCache() {
        Fraction fraction = new Fraction(4, 8);
        Fractionable fractionable = Utils.cache(fraction);
        double initialValue = fractionable.doubleValue();

        Assertions.assertEquals(initialValue, fractionable.doubleValue());
        Assertions.assertEquals(initialValue, fractionable.doubleValue());
    }

    @Test
    void should_ClearCacheWhen_CallMutatorMethod() {
        Fraction fraction = new Fraction(4, 8);
        Fractionable fractionable = Utils.cache(fraction);
        fractionable.doubleValue();
        fractionable.doubleValue();
        fractionable.doubleValue();
        Assertions.assertEquals(1, fraction.getOriginalMethodCalledCounter());

        fractionable.setNum(10);
        Assertions.assertEquals(0, fraction.getOriginalMethodCalledCounter());
        fractionable.doubleValue();
        Assertions.assertEquals(1, fraction.getOriginalMethodCalledCounter());
        fractionable.setDenum(10);
        Assertions.assertEquals(0, fraction.getOriginalMethodCalledCounter());
    }

    @Test
    void should_UpdateCache_When_CallCachedMethodWithDifferentParams() {
        Fraction fraction = new Fraction(4, 8);
        Fractionable fractionable = Utils.cache(fraction);

        fractionable.doubleValue(10, "comment");
        fractionable.doubleValue(10, "comment");
        Assertions.assertEquals(1, fraction.getOriginalMethodCalledCounter());
        fractionable.doubleValue(20, "comment");
        fractionable.doubleValue(20, "comment");
        fractionable.doubleValue(20, "comment");
        Assertions.assertEquals(2, fraction.getOriginalMethodCalledCounter());

    }
}
