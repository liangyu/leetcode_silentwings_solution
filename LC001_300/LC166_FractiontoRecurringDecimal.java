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
        long a = numerator, b = denominator;
        if (a == 0) return "0";
        int sign = 1;
        if (a < 0) {
            sign *= -1;
            a = Math.abs(a);
        }
        if (b < 0) {
            sign *= -1;
            b = Math.abs(b);
        }

        StringBuilder sb = new StringBuilder();
        if (sign == -1) sb.append('-');
        sb.append(a / b);

        if (a % b == 0) return sb.toString();
        else sb.append('.');

        long c = a % b;

        HashMap<Long, Integer> map = new HashMap<>();
        while (c != 0 && !map.containsKey(c)) {
            map.put(c, sb.length());
            sb.append(c * 10 / b);
            c = c * 10 % b;
        }

        if (c == 0) return sb.toString();
        sb.insert(map.get(c), "(");
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
 */