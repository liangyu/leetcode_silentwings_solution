package LC1801_2100;
import java.util.*;
public class LC1960_MaximumProductoftheLengthofTwoPalindromicSubstrings {
    /**
     * You are given a 0-indexed string s and are tasked with finding two non-intersecting palindromic substrings of
     * odd length such that the product of their lengths is maximized.
     *
     * More formally, you want to choose four integers i, j, k, l such that 0 <= i <= j < k <= l < s.length and both
     * the substrings s[i...j] and s[k...l] are palindromes and have odd lengths. s[i...j] denotes a substring from
     * index i to index j inclusive.
     *
     * Return the maximum possible product of the lengths of the two non-intersecting palindromic substrings.
     *
     * A palindrome is a string that is the same forward and backward. A substring is a contiguous sequence of
     * characters in a string.
     *
     * Input: s = "ababbb"
     * Output: 9
     *
     * Constraints:
     *
     * 2 <= s.length <= 10^5
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public long maxProduct(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        int maxRight = -1, maxCenter = -1;
        int[] P = new int[n];

        for (int i = 0; i < n; i++) {
            int r = 0;
            if (i <= maxRight) {
                int j = maxCenter * 2 - i;
                r = Math.min(P[j], maxRight - i);
            } else r = 0;
            while (i - r >= 0 && i + r < n && s.charAt(i - r) == s.charAt(i + r)) r++;
            P[i] = r - 1;
            if (P[i] + i > maxRight) {
                maxRight = P[i] + i;
                maxCenter = i;
            }
        }

        int[] left = new int[n];
        int j = 0;
        left[0] = 1;
        for (int i = 1; i < n; i++) {
            while (j < n && j + P[j] < i) j++;
            left[i] = Math.max(left[i - 1], (i - j) * 2 + 1);
        }

        int[] right = new int[n];
        j = n - 1;
        right[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            while (j >= 0 && j - P[j] > i) j--;
            right[i] = Math.max(right[i + 1], (j - i) * 2 + 1);
        }

        long res = 0;
        for (int i = 0; i < n - 1; i++) {
            res = Math.max(res, (long)left[i] * (long)right[i + 1]);
        }
        return res;
    }
}
/**
 * 线性时间，Manacher
 * 回文子串
 * x x [c a b a c] d
 *          ^
 * 这里只要求奇数的回文子串
 * P[i]: the longest extended radius of the palindromic substring centered at i
 * Manacher: O(n)
 * x x x x x | x x x x
 * 遍历每个分界点的位置，把乘积算出来，找一个最大值
 * left[i]: the longest palindromic substring in s[0:i]
 * right[i]: the longest palindormic substring in s[i:n-1]
 * find i s.t. maximize {left[i] * right[i+1]}
 * 10^5 -> 暴力无法解决 => Manacher
 * [x x x x x x x x x] x x x x x
 *                     i
 *       {_________________}
 *               j
 * left[i] >= max{left[i-1], (i-j)*2+1}  => 找一个尽可能小的j, j 单调从小到大走
 * j 永远不会往后退，其移动也是单调的
 * for (int i = 0; i < N; i++) {
 *     while (j < n && j + P[j] < i) j++;
 *     left[i] = Math.max(left[i-1], (i-j)*2+1);
 * }
 * maxRight
 * maxCenter
 * x {x x x x x x x x} x x x
 *       mC   i  mR
 * r = min{P[j], maxRight-i}
 * r++
 *
 * 如何遍历两个互不相交的回文串。
 * 一个比较自然的想法就是用前缀、后缀数组。
 * 令left[i]表示s[0:i]里的最长回文串长度、right[i]表示s[i:n-1]里的最长回文串长度，最终答案就是遍历i寻找最大的left[i]*right[i+1]即可.
 * 显然left[i]必然会继承于left[i-1]. 其次我们还要考虑那些包括了s[i]的回文串。
 * 所以我们其实要找的是最小的位置j，使得以j为中心的回文串能覆盖到i。
 * 这样以j为中心、长度为(i-j)*2+1的字符串是left[i]所没有覆盖到的、最长的一个回文串。
 * 只要从小到大顺着过一遍，当恰好j+P[j]>=i时停下来即可。
 * 此外我们还发现，当我们从小到大考察i时，j的考察方向也是单调递增的。
 * 所以意味着我们用o(N)的时间就可以把left[i]数组求出来。
 */