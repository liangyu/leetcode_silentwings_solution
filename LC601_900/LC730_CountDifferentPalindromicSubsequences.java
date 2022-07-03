package LC601_900;
import java.util.*;
public class LC730_CountDifferentPalindromicSubsequences {
    /**
     * Given a string s, return the number of different non-empty palindromic subsequences in s. Since the answer may
     * be very large, return it modulo 109 + 7.
     *
     * A subsequence of a string is obtained by deleting zero or more characters from the string.
     *
     * A sequence is palindromic if it is equal to the sequence reversed.
     *
     * Two sequences a1, a2, ... and b1, b2, ... are different if there is some i for which ai != bi.
     *
     * Input: s = "bccb"
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s[i] is either 'a', 'b', 'c', or 'd'.
     * @param s
     * @return
     */
    // S1: dp
    // time = O(n^2), space = O(n^2)
    public int countPalindromicSubsequences(String s) {
        int n = s.length();
        int M = (int)(1e9 + 7);
        s = "#" + s;
        int[][] dp = new int[n + 1][n + 1];
        int[][] prev = new int[n + 1][4], next = new int[n + 1][4];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(next[i], Integer.MAX_VALUE / 2);
            Arrays.fill(prev[i], Integer.MIN_VALUE / 2);
        }

        for (int k = 0; k < 4; k++) {
            int i = 0;
            for (int j = 1; j <= n; j++) {
                if (s.charAt(j) - 'a' != k) continue;
                while (i <= j) {
                    next[i][k] = j;
                    i++;
                }
            }
        }

        for (int k = 0; k < 4; k++) {
            int i = n;
            for (int j = n; j >= 1; j--) {
                if (s.charAt(j) - 'a' != k) continue;
                while (i >= j) {
                    prev[i][k] = j;
                    i--;
                }
            }
        }

        for (int i = 1; i <= n; i++) dp[i][i] = 1;

        for (int len = 2; len <= n; len++) {
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                for (int k = 0; k < 4; k++) {
                    int p = next[i][k];
                    int q = prev[j][k];
                    if (p < q) dp[i][j] += dp[p + 1][q - 1] + 1;
                    if (p <= j) dp[i][j]++; // 单个b, i，j里有b
                    dp[i][j] %= M;
                }
            }
        }
        return dp[1][n];
    }

    // S2: dp
    // time = O(n^2), space = O(n^2)
    public int countPalindromicSubsequences2(String s) {
        int n = s.length(), M = (int)(1e9 + 7);
        int[][] f = new int[n][n];
        for (int[] x : f) Arrays.fill(x, 1); // 初始化都有一个"空串"，所以初始为1，最后再结尾再去掉空串！
        for (int i = 0; i < n; i++) f[i][i]++; // 加上长度为1的回文子序列！

        for (int len = 2; len <= n; len++) {
            Deque<Integer>[] dq = new Deque[4];
            for (int i = 0; i < 4; i++) dq[i] = new LinkedList<>();
            for (int i = 0; i < len - 1; i++) dq[s.charAt(i) - 'a'].offerLast(i);
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                dq[s.charAt(j) - 'a'].offerLast(j);
                for (int k = 0; k < 4; k++) {
                    if (dq[k].size() > 0) {
                        f[i][j]++; // 长度为1的回文子序列
                        int l = dq[k].peekFirst(); // 找到l与r的位置
                        int r = dq[k].peekLast();
                        if (l < r) f[i][j] = (f[i][j] + f[l + 1][r - 1]) % M;
                    }
                }
                dq[s.charAt(i) - 'a'].pollFirst();
            }
        }
        return f[0][n - 1] - 1; // 最后去掉"空串"
    }
}
/**
 * f(i,j) 不同回文子序列的数量(含空)
 * 只可能包含4个字符，划分为4类：
 * a-a, b-b, c-c, d-d
 * 这四类一定没有交集
 * 找到第一个a和最后一个a => f(L+1,R-1) + 1 (1个a)
 *
 * 区间型dp
 * dp[i][j] 能match的话，就转移
 * 回文，一头一尾相等
 * dp[i][j]: the number of different non-empty palindromic subsequences in s[j:i]
 *
 */