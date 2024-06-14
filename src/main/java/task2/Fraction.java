package task2;

import lombok.Getter;

/**
 *
 */
public class Fraction implements Fractionable {
    @Getter
    private int originalMethodCalledCounter = 0;

    private int num;
    private int denum;

    public Fraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Cache
    @Override
    public double doubleValue() {
        originalMethodCalledCounter++;
        return (double) num / denum;
    }

    @Cache
    @Override
    public double doubleValue(int value, String comment) {
        originalMethodCalledCounter++;
        return (double) (num / denum) + value;
    }

    @Mutator
    @Override
    public void setNum(int num) {
        originalMethodCalledCounter = 0;
        this.num = num;
    }


    @Mutator
    @Override
    public void setDenum(int denum) {
        originalMethodCalledCounter = 0;
        this.denum = denum;
    }
}
