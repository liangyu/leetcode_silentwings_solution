package LC901_1200;
import java.util.*;
public class LC1062_LongestRepeatingSubstring {
    /**
     * Given a string s, find out the length of the longest repeating substring(s). Return 0 if no repeating substring
     * exists.
     *
     * Input: s = "abcd"
     * Output: 0
     *
     * Constraints:
     *
     * The string s consists of only lowercase English letters from 'a' - 'z'.
     * 1 <= s.length <= 1500
     * @param s
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n^2)
    public int longestRepeatingSubstring(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int n = s.length();
        s = "#" + s;
        int[][] dp = new int[n + 1][n + 1];
        int res = 0;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (i != j && s.charAt(i) == s.charAt(j)) { // 注意：skip i == j,否则最后答案出来的是它本身
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                    res = Math.max(res, dp[i][j]); // the max may appear at any possible position
                }
            }
        }
        return res;
    }

    // S2: rolling hash(Rabin-Karp) + BS
    // time = O(n^2), space = O(n^2)
    public int longestRepeatingSubstring2(String s) {
        // corner case
        if (s == null || s.length() == 0) return 0;

        int left = 1, right = s.length() - 1; // 注意：上界不一定是s.length()/2, 比如"aaaaa",longest repeating is 4, not 2
        while (left < right) { // [left, right]
            int mid = left + (right - left) / 2 + 1;
            if (found(s, mid)) left = mid; // check logic, eg. left = 0, right = 1
            else right = mid - 1;
        }
        // 这题未必有解，所以出来时必须要再check一下！(反之，如果一定有解，出来的一定是收敛解！）
        return found(s, left) ? left : 0;
    }

    private boolean found(String s, int len) {
        long hash = 0, base = 26; // base at least need to be 26
        final int M = (int)(1e9 + 7);
        HashSet<Long> set = new HashSet<>();

        long pow_base_len = 1;
        for (int i = 0; i < len; i++) {
            pow_base_len = pow_base_len * base % M;
        }

        for (int i = 0; i < s.length(); i++) {
            hash = (hash * base + s.charAt(i) - 'a') % M;
            if (i >= len) hash = (hash - (s.charAt(i - len) - 'a') * pow_base_len % M + M) % M;
            if (i >= len - 1) {
                if (set.contains(hash)) return true;
                set.add(hash); // len = 4 => 0123就有了4位，这时的hash才是有效的
            }
        }
        return false;
    }
}
/**
 * ref: LC1044 没有任何区别
 * 差别在数据范围，那题10^5，这里是1500
 * 第一想法是dp
 * 要找的就是longest common substring   双序列型
 * dp[i][j]: the longest common substring for s1[0:i] substring ending at i and s2[0:j] substring ending at j
 * s1: xx[xxx i]
 * s2: [yyy j]
 * if (s1[i] == s2[j]) dp[i][j] = dp[i-1][j-1]+1；
 * 这题里面 i != j，不能等于它本身，所以 i == j 要skip掉
 *
 * S2: rolling hash + BS
 * use bs to guess the length
 * find a sliding window of this length in the string, then slide it
 * 对应的每个sliding window可以记录到一个set里面
 * 等到滑到下一个window的时候，就查看下现在这个滑窗的string是不是在之前的set里出现过
 * 出现过就表明两端相等长度的滑窗，它们的substring是一样的，就符合定义，返回true
 * 然后下一次猜length的时候就可以往上去猜一点；反之，如果没发现2个相等，说明太长了，就减少一些
 * length越短，越容易找到repeating substring
 * 大方向：用bs来试这个substring到底有多长
 * 子问题：给定一个length，能否快速找到两端substring是相等的
 * 把每个substring存到hash table里。但如果substring比较长，如何更高效的存呢？压缩转化成一个数字,存到set里效率更高。
 * abcd => 0123
 * bcde => 1234
 * 把它转化为一个26进制的数字。
 * wxyz => (22)(23)(24)(25) => ...
 * 数字如果太大，overflow? => %mod 1e9 + 7
 * 有可能hash collision => 赌下运气 1500个数而已
 * 下一个Key可以很方便地从上一个key得到而来，把最高位去掉，  把最低位加上去，这样生成新的Key时效率就会非常高
 * 撸一遍时间O(n)
 * time = O(nlogn),比dp解法更快
 */
