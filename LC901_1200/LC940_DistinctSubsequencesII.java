package LC901_1200;

public class LC940_DistinctSubsequencesII {
    /**
     * Given a string s, return the number of distinct non-empty subsequences of s. Since the answer may be very large,
     * return it modulo 10^9 + 7.
     *
     * A subsequence of a string is a new string that is formed from the original string by deleting some (can be none)
     * of the characters without disturbing the relative positions of the remaining characters. (i.e., "ace" is a
     * subsequence of "abcde" while "aec" is not.
     *
     * Input: s = "abc"
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= s.length <= 2000
     * s consists of lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n_, space = O(n)
    public int distinctSubseqII(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        long M = (long)(1e9 + 7);
        int n = s.length();
        s = "#" + s; // 1-index
        long[] dp = new long[n + 1];
        int[] last = new int[26];
        dp[0] = 1; // 可以构造一个empty subsequence null

        for (int i = 1; i <= n; i++) {
            int j = last[s.charAt(i) - 'a'];
            dp[i] = (dp[i - 1] * 2 % M - (j >= 1 ? dp[j - 1] : 0) + M) % M; // 可能减出一个负数来，所以可以 + M扳回正数
            last[s.charAt(i) - 'a'] = i;
        }
        return (int) dp[n] - 1; // dp本身包括null，题目要求非空，所以这里要-1
    }
}
/**
 * string经典dp题 -> 自动机
 * dp[i]: the number of distinct subsequences ending with s[i]  (x)
 * dp[i]: the number of distinct subsequence ending at i by s[1:i]  不一定要以s[i]结尾
 * x x x x x x i
 * 着眼于最后一个位置
 * [x x x x x x] i
 * x x x x   (1)
 * x x x
 * x x
 * null
 * 1. do not use s[i]
 * dp[i] += dp[i - 1]
 * 2. use s[i]
 * x x x x a
 * x x x a    (2)
 * x x a
 * a
 * dp[i] += dp[i - 1]
 * => dp[i] = dp[i - 1] * 2
 * (1) 与 (2) 有可能重复，如果(1)的末尾也是一个a
 * [x x x x a x] a
 *          j    i
 * dp[i] = dp[i - 1] * 2 - dp[j - 1]   重复的种类就是取决于[x x x] a
 * last[i]['a']: 在i位置之前，最后一次出现'a'的位置是什么
 * last[i]['b']
 * O(n) * 26
 *      1 2 3
 *      a b a
 * 'a'  0 1 1
 * 'b'  0 0 2
 * 'c'  0 0 0
 * ...
 * j = last[s[i]]
 * # a b c
 * dp[3] = dp[2] * 2 - dp[0 - 1]   c之前没有任何重复 -> 无影响，不会造成任何重复，-> 0
 */
