package LC1801_2100;
import java.util.*;
public class LC1888_MinimumNumberofFlipstoMaketheBinaryStringAlternating {
    /**
     * You are given a binary string s. You are allowed to perform two types of operations on the string in any sequence:
     *
     * Type-1: Remove the character at the start of the string s and append it to the end of the string.
     * Type-2: Pick any character in s and flip its value, i.e., if its value is '0' it becomes '1' and vice-versa.
     * Return the minimum number of type-2 operations you need to perform such that s becomes alternating.
     *
     * The string is called alternating if no two adjacent characters are equal.
     *
     * For example, the strings "010" and "1010" are alternating, while the string "0100" is not.
     *
     * Input: s = "111000"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s[i] is either '0' or '1'.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public static int minFlips(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        char[] arr = new char[2 * n], arr1 = new char[2 * n], arr2 = new char[2 * n];
        int val1 = 0, val2 = 0, res = Integer.MAX_VALUE;

        for (int i = 0; i < 2 * n; i++) {
            arr[i] = s.charAt(i % n);
            arr1[i] = (i % 2 == 0) ? '1' : '0';
            arr2[i] = (i % 2 == 0) ? '0' : '1';
            if (arr1[i] != arr[i]) val1++;
            if (arr2[i] != arr[i]) val2++;
            // the leftmost element is outside the sliding window, we need to subtract it if we did 'flip' before.
            if (i >= n) {
                if (arr1[i - n] != arr[i - n]) val1--;
                if (arr2[i - n] != arr[i - n]) val2--;
            }
            if (i >= n - 1) res = Math.min(Math.min(val1, val2), res);
        }
        return res;
    }
}
