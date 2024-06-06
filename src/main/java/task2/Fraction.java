package task2;

import lombok.Getter;

public class Fraction implements Fractionable {
    @Getter
    private int cacheUsedCounter = 0;

    private int num;
    private int denum;

    public Fraction(int num, int denum) {
        this.num = num;
        this.denum = denum;
    }

    @Cache
    @Override
    public double doubleValue() {
        cacheUsedCounter++;
        return (double) num / denum;
    }

    @Mutator
    @Override
    public void setNum(int num) {
        cacheUsedCounter = 0;
        this.num = num;
    }


    @Mutator
    @Override
    public void setDenum(int denum) {
        cacheUsedCounter = 0;
        this.denum = denum;
    }
}
