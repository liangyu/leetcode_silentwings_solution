package LC001_300;
import java.util.*;
public class LC13_RomantoInteger {
    /**
     * Given a roman numeral, convert it to an integer.
     *
     * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
     *
     * Symbol       Value
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     *
     * Input: s = "MCMXCIV"
     * Output: 1994
     *
     * Constraints:
     *
     * 1 <= s.length <= 15
     * s contains only the characters ('I', 'V', 'X', 'L', 'C', 'D', 'M').
     * It is guaranteed that s is a valid roman numeral in the range [1, 3999].
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int romanToInt(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int res = toNumber(s.charAt(0));

        for (int i = 1; i < s.length(); i++) {
            if (toNumber(s.charAt(i)) > toNumber(s.charAt(i - 1))) {
                res += toNumber(s.charAt(i)) - 2 * toNumber(s.charAt(i - 1)); // 注意因为前面已经加过第i - 1位，所以这里要减去2倍的上一位
            } else {
                res += toNumber(s.charAt(i));
            }
        }
        return res;
    }

    private int toNumber(char c) {
        int res = 0;

        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
        }
        return res;
    }
}
