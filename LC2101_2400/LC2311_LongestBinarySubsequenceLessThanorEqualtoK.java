package LC2101_2400;
import java.util.*;
public class LC2311_LongestBinarySubsequenceLessThanorEqualtoK {
    /**
     * You are given a binary string s and a positive integer k.
     *
     * Return the length of the longest subsequence of s that makes up a binary number less than or equal to k.
     *
     * Note:
     *
     * The subsequence can contain leading zeroes.
     * The empty string is considered to be equal to 0.
     * A subsequence is a string that can be derived from another string by deleting some or no characters without
     * changing the order of the remaining characters.
     *
     * Input: s = "1001010", k = 5
     * Output: 5
     *
     * Input: s = "00101001", k = 1
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s[i] is either '0' or '1'.
     * 1 <= k <= 10^9
     * @param s
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int longestSubsequence(String s, int k) {
        int n = s.length(), zero = 0;
        List<Integer> one = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);
            if (c == '0') zero++;
            else one.add(n - 1 - i);
        }

        long val = 0;
        int count = 0;
        for (int x : one) {
            val += (int) Math.pow(2, x);
            if (val > k) break;
            else count++;
        }
        return zero + count;
    }
}
/**
 *                  1 x x x x x
 *                    j
 * x x x x x x x x [x x x x x x]
 *                  i
 */