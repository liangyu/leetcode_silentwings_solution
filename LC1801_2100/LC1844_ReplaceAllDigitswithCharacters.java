package LC1801_2100;
import java.util.*;
public class LC1844_ReplaceAllDigitswithCharacters {
    /**
     * You are given a 0-indexed string s that has lowercase English letters in its even indices and digits in its odd
     * indices.
     *
     * There is a function shift(c, x), where c is a character and x is a digit, that returns the xth character after c.
     *
     * For example, shift('a', 5) = 'f' and shift('x', 0) = 'x'.
     * For every odd index i, you want to replace the digit s[i] with shift(s[i-1], s[i]).
     *
     * Return s after replacing all digits. It is guaranteed that shift(s[i-1], s[i]) will never exceed 'z'.
     *
     * Input: s = "a1c1e1"
     * Output: "abcdef"
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s consists only of lowercase English letters and digits.
     * shift(s[i-1], s[i]) <= 'z' for all odd indices i.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String replaceDigits(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        char[] arr = s.toCharArray();
        for (int i = 1; i < arr.length; i += 2) {
            arr[i] = (char)(arr[i - 1] + arr[i] - '0');
        }
        return String.valueOf(arr);
    }
}
