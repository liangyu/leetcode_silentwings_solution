package LC001_300;
import java.util.*;
public class LC91_DecodeWays {
    /**
     * A message containing letters from A-Z is being encoded to numbers using the following mapping:
     *
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     * Given a non-empty string containing only digits, determine the total number of ways to decode it.
     *
     * The answer is guaranteed to fit in a 32-bit integer.
     *
     * Input: s = "226"
     * Output: 3
     *
     * @param s
     * @return
     */
    // S1: DP
    // time = O(n), space = O(n)
    public int numDecodings(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int len = s.length();
        int[] dp = new int[len + 1];
        dp[0] = 1;

        for (int i = 1; i <= len; i++) {
            // case 1: decode the last digit only
            char c = s.charAt(i - 1);
            if (c >= '1' && c <= '9') dp[i] += dp[i - 1];

            // case 2: decode the last two digits
            if (i > 1) {
                int val = (s.charAt(i - 2) - '0') * 10 + s.charAt(i - 1) - '0';
                if (val >= 10 && val <= 26) dp[i] += dp[i - 2];
            }
        }
        return dp[len];
    }

    // S2: DP2 + rolling array (最优解！！！）
    // time = O(n), space = O(1)
    public int numDecodings2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int len = s.length();
        int older = 0, old = 1, now = 0; // older -> chars[i - 2], old -> chars[i - 1], now -> chars[i]

        for (int i = 1; i <= len; i++) {
            // case 1: decode the last digit only
            char c = s.charAt(i - 1);
            now = 0;
            if (c >= '1' && c <= '9') now += old;

            // case 2: decode the last two digits
            if (i > 1) {
                int val = (s.charAt(i - 2) - '0') * 10 + s.charAt(i - 1) - '0';
                if (val >= 10 && val <= 26) now += older;
            }
            older = old;
            old = now;
        }
        return now;
    }
}
