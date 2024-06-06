package task2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MyCacheTests {
    @Test
    void should_ReturnValueFromCache_When_CallCachedMethodSecondAndMoreTimes() {
        Fraction fraction = new Fraction(4, 8);
        Fractionable fractionable = Utils.cache(fraction);
        Assertions.assertEquals(0, fraction.getCacheUsedCounter());
        fractionable.doubleValue();
        Assertions.assertEquals(1, fraction.getCacheUsedCounter());
        fractionable.doubleValue();
        fractionable.doubleValue();
        Assertions.assertEquals(1, fraction.getCacheUsedCounter());
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
        Assertions.assertEquals(1, fraction.getCacheUsedCounter());

        fractionable.setNum(10);
        Assertions.assertEquals(0, fraction.getCacheUsedCounter());
        fractionable.doubleValue();
        Assertions.assertEquals(1, fraction.getCacheUsedCounter());
        fractionable.setDenum(10);
        Assertions.assertEquals(0, fraction.getCacheUsedCounter());
    }
}
