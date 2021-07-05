package LC1201_1500;

public class LC1220_CountVowelsPermutation {
    /**
     * Given an integer n, your task is to count how many strings of length n can be formed under the following rules:
     *
     * Each character is a lower case vowel ('a', 'e', 'i', 'o', 'u')
     * Each vowel 'a' may only be followed by an 'e'.
     * Each vowel 'e' may only be followed by an 'a' or an 'i'.
     * Each vowel 'i' may not be followed by another 'i'.
     * Each vowel 'o' may only be followed by an 'i' or a 'u'.
     * Each vowel 'u' may only be followed by an 'a'.
     * Since the answer may be too large, return it modulo 10^9 + 7.
     *
     * Input: n = 1
     * Output: 5
     *
     * Input: n = 5
     * Output: 68
     *
     * Constraints:
     *
     * 1 <= n <= 2 * 10^4
     * @param n
     * @return
     */
    // time = O(n), space = O(n)
    final int M = (int)(1e9 + 7);
    public int countVowelPermutation(int n) {
        long[][] dp = new long[n + 1][5];
        for (int i = 0; i < 5; i++) dp[1][i] = 1;

        for (int i = 2; i <= n; i++) {
            dp[i][0] = (dp[i - 1][1] + dp[i - 1][2] + dp[i - 1][4]) % M;
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % M;
            dp[i][2] = (dp[i - 1][1] + dp[i - 1][3]) % M;
            dp[i][3] = dp[i - 1][2] % M;
            dp[i][4] = (dp[i - 1][2] + dp[i - 1][3]) % M;
        }
        long res = 0;
        for (int i = 0; i < 5; i++) res += dp[n][i];
        return (int)(res % M);
    }
}
/**
 * ref:LC935
 */