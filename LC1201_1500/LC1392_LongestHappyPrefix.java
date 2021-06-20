package LC1201_1500;
import java.util.*;
public class LC1392_LongestHappyPrefix {
    /**
     * A string is called a happy prefix if is a non-empty prefix which is also a suffix (excluding itself).
     *
     * Given a string s, return the longest happy prefix of s. Return an empty string "" if no such prefix exists.
     *
     * Input: s = "level"
     * Output: "l"
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s contains only lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public String longestPrefix(String s) {
        // corner case
        if (s == null || s.length() == 0) return "";

        int n = s.length();
        int[] dp = new int[n];
        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            // computer dp[i]
            int j = dp[i - 1];
            while (j >= 1 && s.charAt(j) != s.charAt(i)) { // j不能越界
                j = dp[j - 1];
            }
            dp[i] = j + (s.charAt(i) == s.charAt(j) ? 1 : 0);
        }
        int len = dp[n - 1];
        return s.substring(0, len);
    }
}
/**
 * 找后缀数组
 * suffix[i]: the maximum length k s.t. s[0:k-1] = s[i-k+1:i]
 * suffix[n-1]
 * KMP: search substring
 * dp[i]
 * * * * * * * * * * * * * * * * * *   X  ________________________________  * * * * * * * * * * * * * * * * *   Y
 *                                j-1, j                                                                   i-1, i
 * dp[i] = ?
 * j = dp[i-1]
 *
 * if (s[j] == s[i]) dp[i] = j + 1 = dp[i-1] + 1
 * j' = dp[j-1]
 *
 * + + + + + Z ____________ + + + + +  X  ________________________________  _______________________ + + + + +   Y
 *        j'-1                    j-1, j                                                                   i-1, i
 *
 * if (s[j'] == s[i]: dp[i] = j' + 1
 * j'' = dp[j'-1]
 * if (s[j''] == s[i]): dp[i] = j'' + 1;
 * ......
 */
