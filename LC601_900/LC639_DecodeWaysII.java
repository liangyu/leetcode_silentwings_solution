package LC601_900;

public class LC639_DecodeWaysII {
    /**
     * A message containing letters from A-Z can be encoded into numbers using the following mapping:
     *
     * 'A' -> "1"
     * 'B' -> "2"
     * ...
     * 'Z' -> "26"
     * To decode an encoded message, all the digits must be grouped then mapped back into letters using the reverse of
     * the mapping above (there may be multiple ways). For example, "11106" can be mapped into:
     *
     * "AAJF" with the grouping (1 1 10 6)
     * "KJF" with the grouping (11 10 6)
     * Note that the grouping (1 11 06) is invalid because "06" cannot be mapped into 'F' since "6" is different from "06".
     *
     * In addition to the mapping above, an encoded message may contain the '*' character, which can represent any digit
     * from '1' to '9' ('0' is excluded). For example, the encoded message "1*" may represent any of the encoded
     * messages "11", "12", "13", "14", "15", "16", "17", "18", or "19". Decoding "1*" is equivalent to decoding any of
     * the encoded messages it can represent.
     *
     * Given a string s containing digits and the '*' character, return the number of ways to decode it.
     *
     * Since the answer may be very large, return it modulo 10^9 + 7.
     *
     * Input: s = "*"
     * Output: 9
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s[i] is a digit or '*'.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public int numDecodings(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        s = "#" + s;
        final int M = (int)(1e9 + 7);
        long[] dp = new long[n + 1];
        dp[0] = 1;
        if (s.charAt(1) == '*') dp[1] = 9;
        else if (s.charAt(1) == '0') dp[1] = 0;
        else dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            if (Character.isDigit(s.charAt(i))) {
                if (s.charAt(i) != '0') dp[i] += dp[i - 1] * 1; // 单位译码
                if (Character.isDigit(s.charAt(i - 1))) {
                    int num = (s.charAt(i - 1) - '0') * 10 + s.charAt(i) - '0';
                    if (num >= 10 && num <= 26) dp[i] += dp[i - 2] * 1;
                } else { // *
                    if (s.charAt(i) <= '6') dp[i] += dp[i - 2] * 2;
                    else dp[i] += dp[i - 2] * 1;
                }
            } else {
                dp[i] += dp[i - 1] * 9;
                if (Character.isDigit(s.charAt(i - 1))) {
                    if (s.charAt(i - 1) == '1') dp[i] += dp[i - 2] * 9;
                    else if (s.charAt(i - 1) == '2') dp[i] += dp[i - 2] * 6;
                } else {
                    dp[i] += dp[i - 2] * 15;
                }
            }
            dp[i] %= M;
        }
        return (int)dp[n];
    }
}
/**
 * 引入通配符，* only stands for 1 ~ 9， not including 0
 * 12 => AB, L
 * x x x x x 1 1
 * 2种可能，单位解码  dp[i] += dp[i - 1] * 1
 * dp[i] += dp[i - 2] * 1
 *
 * x x x x x *
 * dp[i] += dp[i - 1] * 9
 * dp[i] += dp[i - 2] * 9
 * dp[i] += dp[i - 2] * 15 (前一位是*)
 */
