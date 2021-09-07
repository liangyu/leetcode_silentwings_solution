package LC1801_2100;

public class LC1987_NumberofUniqueGoodSubsequences {
    /**
     * You are given a binary string binary. A subsequence of binary is considered good if it is not empty and has no
     * leading zeros (with the exception of "0").
     *
     * Find the number of unique good subsequences of binary.
     *
     * For example, if binary = "001", then all the good subsequences are ["0", "0", "1"], so the unique good
     * subsequences are "0" and "1". Note that subsequences "00", "01", and "001" are not good because they have leading zeros.
     * Return the number of unique good subsequences of binary. Since the answer may be very large, return it modulo
     * 10^9 + 7.
     *
     * A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without
     * changing the order of the remaining elements.
     *
     * Input: binary = "001"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= binary.length <= 10^5
     * binary consists of only '0's and '1's.
     * @param binary
     * @return
     */
    // S1: dp
    // // time = O(n), space = O(1)
    public int numberOfUniqueGoodSubsequences(String binary) {
        // corner case
        if (binary == null || binary.length() == 0) return 0;

        long M = (long)(1e9 + 7);
        int n = binary.length();
        String s = "#" + binary;
        long[] dp = new long[n + 1];

        int m = 1;
        while (m <= n && s.charAt(m) == '0') m++;
        if (m == n + 1) return 1; // m越界 => 全是0，那就只有1种
        dp[m] = 1;

        int last0 = 0, last1 = 0; // 0这个位置没有意义，因为前面我们加了一个"#"

        for (int i = m + 1; i <= n; i++) {
            int j = s.charAt(i) == '0' ? last0 : last1;
            dp[i] = (dp[i - 1] * 2 % M - (j >= 1 ? dp[j - 1] : 0) + M) % M;
            if (s.charAt(i) == '0') last0 = i;
            else last1 = i;
        }
        return (int) dp[n] + (binary.indexOf('0') != -1 ? 1 : 0);
    }

    // S2: dp
    // time = O(n), space = O(1)
    public int numberOfUniqueGoodSubsequences2(String binary) {
        // corner case
        if (binary == null || binary.length() == 0) return 0;

        int M = (int)(1e9 + 7);
        int ends0 = 0, ends1 = 0, has0 = 0;
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') ends1 = (ends0 + ends1 + 1) % M;
            else {
                ends0 = (ends0 + ends1) % M;
                has0 = 1;
            }
        }
        return (ends0 + ends1 + has0) % M;
    }
}
/**
 * ref: LC940 follow-up
 * 非空子序列，不能以先导0开头
 * 先不考虑先导0的问题，从LC940出发
 * 属于字符串dp里特殊但非常经典的问题，模板题，解法比较经典，抱着学习的态度来记忆！！！
 * dp[i]：the number of unique subsequences from s[1:i]
 * [x x x x x x a x] a
 *              j    i
 * j: 离i最近的一个a
 * 1. do not use s[i]  不需要ending with s[i] => dp[i] += dp[i - 1]
 * x x x x a    (1)
 * x x x x
 * x x x x
 * x x
 * 2. use s[i]: dp[i] += dp[i - 1]
 * x x x x x a
 * x x x x a    (2)
 * x x x x a
 * x x a
 * (1) 与 (2) 可能重复，取决于前面的[x x x x]有多少个重复的 => dp[j - 1]
 *
 * for (int i = 1; i <= n; i++) {
 *      int j = s[i] == 0 ? last0[i] : last1[i]
 *      dp[i] = dp[i - 1] * 2 - dp[j - 1]
 * }
 * return dp[n] + (binary.find("0") != -1);
 * 核心在于开头的这些dp[i]
 * 0 0 0 0 0 0  1   x x x x x x x
 *             m+1
 * dp[1] ~ dp[m] = 0
 * dp[m + 1] = 1
 * 永远处理以1为开头的subsequence => 确定m在哪里，m就是第一个出现1的位置，剩下继续用老办法即可
 * dp[n]最后得到的是以1为开头的subsequence，那么有没有以0开头的subsequence呢？=> 可以，单独0
 *
 * S2
 * dp[i][0]: 前i个字符里能得到多少个以0为结尾的unique good subsequence
 * if (s[i] == '0')
 *      dp[i][0] = dp[i - 1][0] + dp[i - 1][1]; // 前i-1个字符能构成多少个unique subsequence，跟0和1结尾没关系，统统append s[i]
 * else
 *      dp[i][1] = dp[i - 1][0] + dp[i - 1][1] + 1; // 类似，+1是因为1可以自立门户，长度为1的subsequence
 * 这个理解有问题，但这个0不一定结尾在最后这个i上，虽然没有漏算，但是解释并没有那么容易
 *
 */