package LC001_300;
import java.util.*;
public class LC28_ImplementstrStr {
    /**
     * Implement strStr().
     *
     * Return the index of the first occurrence of needle in haystack, or -1 if needle is not part of haystack.
     *
     * Clarification:
     *
     * What should we return when needle is an empty string? This is a great question to ask during an interview.
     *
     * For the purpose of this problem, we will return 0 when needle is an empty string.
     * This is consistent to C's strstr() and Java's indexOf().
     *
     * Input: haystack = "hello", needle = "ll"
     * Output: 2
     *
     * Constraints:
     *
     * 0 <= haystack.length, needle.length <= 5 * 104
     * haystack and needle consist of only lower-case English characters.
     *
     * @param haystack
     * @param needle
     * @return
     */
    // S1: double for loop (prefer!!!)
    // time = O(m * n), space = O(1)
    public int strStr(String haystack, String needle) {
        // corner case
        if (haystack == null || needle == null) {
            return -1;
        }

        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            int j = 0;
            for (j = 0; j < needle.length(); j++) {
                if (haystack.charAt(i + j) != needle.charAt(j)) {
                    break;
                }
            }
            if (j == needle.length()) return i;
        }
        return -1;
    }

    // S2: Rabin-Karp 常数复杂度
    // time = O(n), space = O(1)
    public int strStr2(String haystack, String needle) {
        // corner case
        if (haystack == null || needle == null) return -1;
        if (needle.length() == 0) return 0;

        int len1 = haystack.length(), len2 = needle.length();
        if (len1 < len2) return -1;

        // base value for the rolling hash function
        int a = 26;
        // modulus value for the rolling hash function to avoid overflow
        long modulus = (long)Math.pow(2, 31);

        // compute the hash of the strings haystack and needle
        long h = 0, ref_h = 0;
        for (int i = 0; i < len2; i++) {
            h = (h * a + charToInt(i, haystack)) % modulus;
            ref_h = (ref_h * a + charToInt(i, needle)) % modulus;
        }
        if (h == ref_h) return 0;

        long diff = (long)Math.pow(a, len2) % modulus;

        for (int i = 1; i <= len1 - len2; i++) {
            h = (h * a - charToInt(i - 1, haystack) * diff + charToInt(i + len2 - 1, haystack)) % modulus;
            if (h == ref_h) return i;
        }
        return -1;
    }

    private int charToInt(int i, String s) {
        return s.charAt(i) - 'a';
    }

    // S3: KMP1
    // time = O(n), space = O(n)
    public int strStr3(String haystack, String needle) {
        String s = haystack;
        String p = needle;
        int n = s.length(), m = p.length();

        if (m == 0) return 0;
        if (n == 0) return -1;

        int[] dp = new int[n]; // 与目标串一样长
        dp[0] = (s.charAt(0) == p.charAt(0) ? 1 : 0);
        if (m == 1 && dp[0] == 1) return 0;
        int[] suffix = preprocess(p);

        for (int i = 1; i < n; i++) {
            int j = dp[i - 1];
            while (j > 0 && s.charAt(i) != p.charAt(j)) {
                j = suffix[j - 1];
            }
            dp[i] = j + (s.charAt(i) == p.charAt(j) ? 1 : 0);
            if (dp[i] == m) return i - m + 1;
        }
        return -1;
    }

    private int[] preprocess(String s) {
        // corner case
        if (s == null || s.length() == 0) return new int[0];

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
        return dp;
    }

    // S4: KMP2
    // time = O(n), space = O(n)
    public int strStr4(String s, String p) {
        int n = s.length(), m = p.length();
        s = "#" + s;
        p = "#" + p;

        int[] next = new int[m + 1];
        for (int i = 2, j = 0; i <= m; i++) {
            while (j > 0 & p.charAt(i) != p.charAt(j + 1)) {
                j = next[j];
            }
            if (p.charAt(i) == p.charAt(j + 1)) j++;
            next[i] = j;
        }

        for (int i = 1, j = 0; i <= n; i++) {
            while (j > 0 && s.charAt(i) != p.charAt(j + 1)) j = next[j];
            if (s.charAt(i) == p.charAt(j + 1)) j++;
            if (j == m) return i - m; // 返回起始位置
        }
        return -1;
    }
}
/**
 * s: target string
 * p: pattern string
 *
 * KMP:
 * 1. the suffix array of p
 * suffix[i]: the max length k s.t. p[0:k-1] = p[i-k+1:i]
 *
 * 2. dp[i]: the max length k s.t. p[0:k-1] = s[i-k+1:i]
 * when dp[i] = p.size() => match!
 *
 * dp[i] => dp[i-1]?
 * s:    ________________ * * * * * * * * * * * * * * * *  X _________
 *                                                     i-1 i
 * p:                     * * * * * * * * * * * * * * * *  Y _________
 *                                                     j-1 j
 * j = dp[i-1]
 * if (s[i] == p[j]) => dp[i] = dp[i-1]+1 = j+1
 *
 * suffix[j-1]
 * s:    ________________ _______________________ + + + +  X _________
 *                                                     i-1 i
 * p:                     + + + + Z _____________ + + + +  Y _________
 *                             j'-1                    j-1 j
 * j' = suffix[j-1]
 * if (s[i] == p[j']) dp[i] = j'+1
 * ...
 * j'' = suffix[j'-1]
 * if (s[i] == p[j'']) dp[i] = j''+1
 *
 * for (int i = 0; i < n; i++) {
 *     // compute dp[i]
 *     int j = dp[i - 1];
 *     while (j > 0 && s.charAt(i) != p.charAt(j)) {
 *         j = suffix[j - 1];
 *     }
 *     dp[i] = j + (s.charAt(i) == p.charAt(j) ? 1 : 0);
 *     if (dp[i] == p.length()) return i - p.length() + 1; // starting point
 * }
 *
 * next[i]: 所有p[1~i]的相等的前缀和后缀中长度的最大值
 * “非平凡前缀”：指除了最后一个字符以外，一个字符串的全部头部组合（前面连续的部分）。
 * 注意： KMP算法中前后缀指的都是非平凡，即不包括整个串。
 */
