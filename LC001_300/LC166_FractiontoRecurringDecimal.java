package LC001_300;
import java.util.*;
public class LC166_FractiontoRecurringDecimal {
    /**
     * Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.
     *
     * If the fractional part is repeating, enclose the repeating part in parentheses.
     *
     * If multiple answers are possible, return any of them.
     *
     * It is guaranteed that the length of the answer string is less than 104 for all the given inputs.
     *
     * Input: numerator = 1, denominator = 2
     * Output: "0.5"
     *
     * Constraints:
     *
     * -2^31 <= numerator, denominator <= 2^31 - 1
     * denominator != 0
     * @param numerator
     * @param denominator
     * @return
     */
    // time = O(n), space = O(n)
    public String fractionToDecimal(int numerator, int denominator) {
        long x = numerator, y = denominator;
        if (x % y == 0) return String.valueOf(x / y);

        StringBuilder sb = new StringBuilder();
        if ((x < 0) ^ (y < 0)) sb.append('-');
        x = Math.abs(x);
        y = Math.abs(y);

        sb.append(x / y).append('.');
        x %= y;

        HashMap<Long, Integer> map = new HashMap<>();
        while (x != 0 && !map.containsKey(x)) {
            map.put(x, sb.length());
            x *= 10;
            sb.append(x / y);
            x %= y;
        }
        if (x == 0) return sb.toString();
        sb.insert(map.get(x), "(");
        sb.append(')');
        return sb.toString();
    }
}
/**
 * 1/7 = 0.(142857)
 * 1/7  => 0  1    0
 * 10/7 => 1  3    1
 * 30/7 => 4  2    2
 * 20/7 => 2  6    3
 * 60/7 => 8  4    4
 * 40/7 => 5  5    5
 * 50/7 => 7  1    6
 *
 * 模拟下高精度除法
 * 最多循环n次
 */