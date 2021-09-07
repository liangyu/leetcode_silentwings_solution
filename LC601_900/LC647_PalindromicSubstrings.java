package LC601_900;
import java.util.*;
public class LC647_PalindromicSubstrings {
    /**
     * Given a string, your task is to count how many palindromic substrings in this string.
     *
     * The substrings with different start indexes or end indexes are counted as different substrings even they consist
     * of same characters.
     *
     * Input: "abc"
     * Output: 3
     *
     * Note:
     *
     * The input string length won't exceed 1000.
     * @param s
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n^2)
    public int countSubstrings(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int res = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= i; j++) {
                if (s.charAt(j) == s.charAt(i) && (i - j < 2 || dp[j + 1][i - 1])) {
                    dp[j][i] = true;
                    res++;
                }
            }
        }
        return res;
    }

    // S2: 中心扩散法
    // time = O(n^2), space = O(1)
    public int countSubstrings2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int res = 0;
        int n = s.length();

        for (int center = 0; center < 2 * n - 1; center++) {
            int left = center / 2, right = left + center % 2;
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                res++;
                left--;
                right++;
            }
        }
        return res;
    }

    // S3: Manacher
    // time = O(n), space = O(n)
    public int countSubstrings3(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        StringBuilder sb = new StringBuilder();
        sb.append('#');
        for (char ch : s.toCharArray()) sb.append(ch).append('#');

        int n = sb.length();
        int[] P = new int[n];
        int maxRight = -1, maxCenter = -1;

        for (int i = 0; i < n; i++) {
            int r = 0;
            if (i <= maxRight) {
                int j = maxCenter * 2 - i;
                r = Math.min(P[j], maxRight - i);
                while (i - r >= 0 && i + r < n && sb.charAt(i - r) == sb.charAt(i + r)) r++;
            } else {
                r = 0;
                while (i - r >= 0 && i + r < n && sb.charAt(i - r) == sb.charAt(i + r)) r++;
            }
            P[i] = r - 1;
            if (i + P[i] > maxRight) {
                maxRight = i + P[i];
                maxCenter = i;
            }
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            res += (P[i] + 1) / 2;
        }
        return res;
    }
}
/**
 * Manacher
 * 确定中心位置
 * f c b a b c e x  => a  bab   cbabc
 *       ^ ^ ^ ^
 *       3 1 1 2
 * 偶数，看的是2个字符之间的空隙。
 * d [c b a_a b c] e e
 *         ^ ^    ^ ^
 *         3 0    0 1
 *
 * Manacher
 * 先考虑奇数的回文substring
 * P[i]: the longest extended radius of palindromic substrings centered at i
 * x x x x x [x x x x x] x x x x x x
 *                i
 * P值是一个个算的
 * 已知这些值的P，求P值
 * maxRight: 找个右边界最远的P值
 * maxCenter
 * x x {x x x x x x x x x x x} x x x x
 *   ---*---*--         *   *
 *        j      ctr      i
 * P[i] 至少是1，半径至少为2
 * 每次遇到一个新的i，半径不一定需要暴力的从0开始走，可以借鉴center和P[j]的信息来给一个初始值，比如在这里就是1
 * P[i] = Math.min(P[j], maxRight-i)
 * P[i] -> r++
 * 线性时间求出所有每个字符作为中心的时候，长度为奇数的回文字符串最长是多少
 * time = O(n), 均摊后是O(1)
 *
 * # a # b # b # a #
 * 以'#'为中心，往左右扩展，回文半径最长
 */