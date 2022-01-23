package LC1201_1500;
import java.util.*;
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
    // time = O(n), space = O(1)
    public int countVowelPermutation(int n) {
        long[] dp = new long[5];
        Arrays.fill(dp, 1L);

        long M = (long)(1e9 + 7);
        for (int i = n - 2; i >= 0; i--) {
            long[] dp_temp = dp.clone();
            dp[0] = dp_temp[1];
            dp[1] = (dp_temp[0] + dp_temp[2]) % M;
            dp[2] = (dp_temp[0] + dp_temp[1] + dp_temp[3] + dp_temp[4]) % M;
            dp[3] = (dp_temp[2] + dp_temp[4]) % M;
            dp[4] = dp_temp[0];
        }

        long res = 0;
        for (long x : dp) res = (res + x) % M;
        return (int) res;
    }
}
/**
 * ref:LC935
 * dp[i][0]: how many strings of [i:n-1] can be formed with s[i] = 'a'
 * dp[i][1]: how many strings of [i:n-1] can be formed with s[i] = 'e'
 * dp[i]['a'] = dp[i+1]['e']
 * dp[i]['e'] = dp[i+1]['a'] + dp[i+1]['i']
 * 从后往前构造
 */