package LC601_900;

import java.util.ArrayList;
import java.util.*;
public class LC727_MinimumWindowSubsequence {
    /**
     * Given strings s1 and s2, return the minimum contiguous substring part of s1, so that s2 is a subsequence of the
     * part.
     *
     * If there is no such window in s1 that covers all characters in s2, return the empty string "". If there are
     * multiple such minimum-length windows, return the one with the left-most starting index.
     *
     * Input: s1 = "abcdebdde", s2 = "bde"
     * Output: "bcde"
     *
     * Constraints:
     *
     * 1 <= s1.length <= 2 * 10^4
     * 1 <= s2.length <= 100
     * s1 and s2 consist of lowercase English letters.
     * @param s1
     * @param s2
     * @return
     */
    // S1: state machine
    // time = O(s * n), space = O(26m)
    public String minWindow(String s1, String s2) {
        int m = s1.length();
        s1 = "#" + s1; // s1[1:m] add a dummy head "#"

        int[][] next = new int[m + 1][26];
        // init
        for (int ch = 0; ch < 26; ch++) next[m][ch] = -1;
        for (int i = m - 1; i >= 0; i--) {
            for (int ch = 0; ch < 26; ch++) {
                next[i][ch] = next[i + 1][ch];
            }
            next[i][s1.charAt(i + 1) - 'a'] = i + 1;
        }

        List<Integer> start = new ArrayList<>();
        for (int i = 1; i <= m; i++) {
            if (s1.charAt(i) == s2.charAt(0)) start.add(i);
        }

        String res = "";
        for (int i : start) {
            int j = i - 1;
            boolean flag = true;
            for (char ch : s2.toCharArray()) {
                j = next[j][ch - 'a'];
                if (j == -1) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                int len = j - i + 1;
                if (res.length() == 0 || len < res.length()) res = s1.substring(i, i + len);
            }
        }
        return res;
    }

    // S2: DP
    // time = O(m * n), space = O(m * n)
    public String minWindow2(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        s1 = "#" + s1;
        s2 = "#" + s2;

        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) Arrays.fill(dp[i], Integer.MAX_VALUE / 2); // 涉及到+1, -1 => max/2

        for (int j = 1; j <= n; j++) dp[0][j] = Integer.MAX_VALUE / 2;
        for (int i = 0; i <= m; i++) dp[i][0] = 0;
        // dp[x][0] = 0
        // dp[0][0] = 0

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i) == s2.charAt(j)) dp[i][j] = dp[i - 1][j - 1] + 1;
                else dp[i][j] = dp[i - 1][j] + 1;
            }
        }
        int len = Integer.MAX_VALUE / 2, pos = -1;
        for (int i = 1; i <= m; i++) {
            if (dp[i][n] < len) {
                len = dp[i][n];
                pos = i;
            }
        }
        return len == Integer.MAX_VALUE / 2 ? "" : s1.substring(pos - len + 1, pos + 1);
    }
}
/**
 * 双序列dp -> O(m*n)
 * dp[i][j]: the minimum subsequence length k, s.t. s2[1:j] is a subsequence of s1[i-k+1:i]
 * if (s[i] == s2[j]) dp[i][j] = dp[i-1][j-1]+1
 * else dp[i][j] = dp[i-1][j]+1
 * dp[i][n] i 不确定，不知道ending在哪里，必须要扫一遍i看长度
 * return min{dp[i][n]} i = 1,2,...m
 * s1 x x x x x [x x x x x] i
 * s2 [y y y] j
 * 最优解第一个字母肯定是s2的首字母
 * 贪心的概念，希望尽量匹配的紧凑
 * 在b后面找最接近的d
 * 从b开始找一个区间 -> 双指针 bcde -> bde
 * naive: 找到b，然后双指针 O(m*(m+n)) 确定开头位置需要O(m)
 * 与双序列dp差不多
 *
 * 状态机：b的"右边第一个出现"d的位置
 * next[i][ch]: look right from position i, the position of the nearest ch
 * 4 = next[1][d]
 * 5 = next[4][e]
 * O(s * n)
 * next[i][?] = next[i+1][?]
 * next[i][s1[i+1]] = i+1
 */
