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
        if (numerator == 0) return "0";

        StringBuilder sb = new StringBuilder();
        sb.append((numerator > 0) ^ (denominator > 0) ? "-" : "");
        long num = Math.abs((long)numerator); // 注意：long要放在numerator与denominator之前！！！
        long den = Math.abs((long)denominator);
        sb.append(num / den);
        num %= den;
        if (num == 0) return sb.toString();

        sb.append(".");

        HashMap<Long, Integer> map = new HashMap<>();
        map.put(num, sb.length());

        while (num != 0) {
            num *= 10;
            sb.append(num / den);
            num %= den;
            if (map.containsKey(num)) {
                int index = map.get(num);
                sb.insert(index, "(");
                sb.append(")");
                break;
            } else {
                map.put(num, sb.length());
            }
        }
        return sb.toString();
    }
}
