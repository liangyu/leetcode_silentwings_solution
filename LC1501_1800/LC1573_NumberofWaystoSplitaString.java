package LC1501_1800;
import java.util.*;
public class LC1573_NumberofWaystoSplitaString {
    /**
     * Given a binary string s (a string consisting only of '0's and '1's), we can split s into 3 non-empty strings s1,
     * s2, s3 (s1+ s2+ s3 = s).
     *
     * Return the number of ways s can be split such that the number of characters '1' is the same in s1, s2, and s3.
     *
     * Since the answer may be too large, return it modulo 10^9 + 7.
     *
     * Input: s = "10101"
     * Output: 4
     *
     * Constraints:
     *
     * 3 <= s.length <= 10^5
     * s[i] is '0' or '1'.
     * @param s
     * @return
     */
    // time = O(n), space = O(n)
    public int numWays(String s) {
        long n = s.length();
        int count = 0;
        long M = (long)(1e9 + 7);
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                count++;
                map.put(count, i);
            }
        }

        if (count % 3 != 0) return 0;
        if (count == 0) return (int)((n - 1) * (n - 2) / 2 % M); // 全是0的情况 => C(n, 2)

        long x = map.get(count / 3 + 1) - map.get(count / 3); // 第2段和第1段之间
        long y = map.get(count / 3 * 2 + 1) - map.get(count / 3 * 2); // 第3段和第2段之间
        return (int)(x * y % M);
    }
}
/**
 * 0101 | [0000] | 101 | [000] | 10010 -> 6个
 * 本质快速定位下1在哪里
 * 怎么快速定位呢？count -> index
 * 其实在之前统计的时候，就可以用一个hash表存储count到index的映射，这样可以快速定位。
 * 左砍和右砍相互独立，相乘
 * corner case:
 * 0 0 0 0 0
 */
