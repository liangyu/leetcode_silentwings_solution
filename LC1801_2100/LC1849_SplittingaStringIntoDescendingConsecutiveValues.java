package LC1801_2100;
import java.util.*;
public class LC1849_SplittingaStringIntoDescendingConsecutiveValues {
    /**
     * You are given a string s that consists of only digits.
     *
     * Check if we can split s into two or more non-empty substrings such that the numerical values of the substrings
     * are in descending order and the difference between numerical values of every two adjacent substrings is equal
     * to 1.
     *
     * For example, the string s = "0090089" can be split into ["0090", "089"] with numerical values [90,89]. The
     * values are in descending order and adjacent values differ by 1, so this way is valid.
     * Another example, the string s = "001" can be split into ["0", "01"], ["00", "1"], or ["0", "0", "1"]. However
     * all the ways are invalid because they have numerical values [0,1], [0,1], and [0,0,1] respectively, all of
     * which are not in descending order.
     * Return true if it is possible to split s as described above, or false otherwise.
     *
     * A substring is a contiguous sequence of characters in a string.
     *
     * Input: s = "1234"
     * Output: false
     *
     * Constraints:
     *
     * 1 <= s.length <= 20
     * s only consists of digits.
     * @param s
     * @return
     */
    // time = O(2^n), space = O(n)
    public boolean splitString(String s) {
        // corner case
        if (s == null || s.length() == 0) return false;

        long num = 0;
        for (int len = 1; len < s.length(); len++) {
            num = num * 10 + s.charAt(len - 1) - '0'; // 处理大数时用拼数的方法提高效率！
            if (num >= 1e10) break;
            if (dfs(s, len, num)) return true;
        }
        return false;
    }

    private boolean dfs(String s, int cur, long num) {
        if (cur == s.length()) return true;

        long num2 = 0;
        for (int len = 1; cur + len <= s.length(); len++) {
            num2 = num2 * 10 + s.charAt(cur + len - 1) - '0';
            if (num2 >= 1e10) break;
            if (num - 1 == num2 && dfs(s, cur + len, num2)) return true;
        }
        return false;
    }
}
/**
 * 第一个尝试的区间不能大于10位数，可以提前剪枝break
 */