package co.jp.nej.earth.util;

public class NumberUtil {

    public static final int BIT_16 = 16;
    public static final int BASE_64 = 4;
    public static final int INT = 3;

    public static Integer ceil(Integer dividend, Integer divisor) {
        Integer quotient = 0;
        Integer mod = dividend % divisor;
        if (mod == 0) {
            quotient = dividend / divisor;
        } else {
            quotient = (dividend + divisor - mod) / divisor;
        }
        return quotient;
    }

    public static Integer roundPlus(Integer dividend, Integer divisor) {
        Integer quotient = 0;
        Integer mod = dividend % divisor;
        if (mod == 0) {
            quotient = dividend;
        } else {
            quotient = dividend + divisor - mod;
        }
        return quotient;
    }

    public static Integer round(Integer dividend, Integer divisor) {
        Integer quotient = 0;
        Integer mod = dividend % divisor;
        if (mod == 0) {
            quotient = dividend;
        } else {
            quotient = dividend - mod;
        }
        return quotient;
    }

    public static Integer sizeField(Integer size) {
        Integer value = 0;
        value = ceil(roundPlus(size, BIT_16), INT) * BASE_64;
        return value;
    }

    public static Integer sizeDeField(Integer size) {
        Integer value = 0;
        value = round((ceil(size, BASE_64) * INT), BIT_16);
        return value;
    }

}
