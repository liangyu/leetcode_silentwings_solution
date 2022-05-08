package LC2101_2400;

public class LC2223_SumofScoresofBuiltStrings {
    /**
     * You are building a string s of length n one character at a time, prepending each new character to the front of
     * the string. The strings are labeled from 1 to n, where the string with length i is labeled si.
     *
     * For example, for s = "abaca", s1 == "a", s2 == "ca", s3 == "aca", etc.
     * The score of si is the length of the longest common prefix between si and sn (Note that s == sn).
     *
     * Given the final string s, return the sum of the score of every si.
     *
     * Input: s = "babab"
     * Output: 9
     *
     * Input: s = "azbazbzaz"
     * Output: 14
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists of lowercase English letters.
     *
     * @param s
     * @return
     */
    // S1: 扩展KMP (z-function)
    // time = O(n), space = O(n)
    public long sumScores(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        char[] chars = s.toCharArray();
        int n = chars.length, x = 0, y = 0;
        int[] z = new int[n];
        long res = n;
        for (int i = 1; i < n; i++) {
            z[i] = Math.max(0, Math.min(z[i - x], y - i + 1));
            while (i + z[i] < n && chars[z[i]] == chars[i + z[i]]) {
                x = i;
                y = i + z[i];
                z[i]++;
            }
            res += z[i];
        }
        return res;
    }

    // S2: Rolling Hash
    // time = O(nlogn), space = O(n)
    long[] h, p;
    public long sumScores2(String s) {
        int n = s.length();
        long hash = 0;
        h = new long[n];
        p = new long[n];

        for (int i = 0; i < n; i++) {
            hash = hash * 26 + (s.charAt(i) - 'a');
            h[i] = hash;
        }

        p[0] = 1;
        for (int i = 1; i < n; i++) p[i] = p[i - 1] * 26;

        long res = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) != s.charAt(0)) continue;
            int left = 1, right = n - i;
            while (left < right) {
                int mid = right - (right - left) / 2;
                if (getHash(i, mid) != getHash(0, mid)) { // too long
                    right = mid - 1;
                } else left = mid;
            }
            res += left;
        }
        return res;
    }

    private long getHash(int i, int len) {
        if (i == 0) return h[len - 1];
        return h[i + len - 1] - h[i - 1] * p[len];
    }
}
/**
 * KMP给出的dp[i]表示以s[i]结尾的最长后缀，使得其等于s的前缀
 * rolling hash + b.s -> 意想不到的效果
 * 用b.s.定位到最长的length,使得2段相等
 * 一个个字符校验，判断logn次，显然是不合算的
 * => rolling hash
 * 比较两个字符串是否相等，除了一个个字符比较之外，还有就是把字符串编码成一个number，
 * abcde -> 12345
 * 两个数字相比，O(1)时间就能判断出来
 * xyz = 23 * 26^2 + 24 * 26^1 + 25 * 26^0 (26进制)
 * 给的是区间，逐位相加，希望所有的编码进行预处理，给首尾就能确定这段区间字符串的编码
 * 如何O(1)时间读出来呢？
 * 有点像prefix sum的思想
 * bcz = bcd bcz - bcd * 26^3  抬高3位
 * H[5] - H[2] * 26^3
 *   j     i-1
 * S[0:L]
 * rolling hash有风险，需要取模
 * 可能有hash collision
 * 10^5 -> 有风险
 */