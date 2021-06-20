package LC301_600;
import java.util.*;
public class LC459_RepeatedSubstringPattern {
    /**
     * Given a string s, check if it can be constructed by taking a substring of it and appending multiple copies of
     * the substring together.
     *
     * Input: s = "abab"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^4
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public boolean repeatedSubstringPattern(String s) {
        // corner case
        if (s == null || s.length() == 0) return false;

        int n = s.length();
        int[] dp = new int[n];
        dp[0] = 0; // 只有一位并不是它的真前缀，而是它本身，所以dp[0] = 0

        for (int i = 1; i < n; i++) {
            // compute dp[i]
            int j = dp[i - 1];
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = dp[j - 1];
            }
            dp[i] = j + (s.charAt(i) == s.charAt(j) ? 1 : 0); // dp[i] -> k
        }
        int k = dp[n - 1];
        return k > 0 && n % (n - k) == 0; // 注意k不能等于0，k == 0代表没找到
    }
}
/**
 * dp[i]: the max length k s.t. s[0:k-1] = s[i-k+1:i]
 * [x x x] x [x x i]  find real prefix, 最长就是 i - 1
 * [x x x x x x] i
 * x [x x x x x i]
 * KMP: 字符串搜索
 * dp[n-1]
 *
 * S: * [* * *] [* * *] [* * *] [_____]
 * S: _ [_____] [* * *] [* * *] [* * *]
 *                                   n-1
 * k = dp[n-1]
 * 找出最长的后缀字符串，且长度k恰好 => n % (n-k) == 0 (充要条件)
 * n-k是最小循环节，因为根据后缀数组定义，得到的k是最长的，因此n-k即是最短的循环节
 * ref: 1392
 */
