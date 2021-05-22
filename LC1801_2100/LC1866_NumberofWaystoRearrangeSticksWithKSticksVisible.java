package LC1801_2100;
import java.util.*;
public class LC1866_NumberofWaystoRearrangeSticksWithKSticksVisible {
    /**
     * There are n uniquely-sized sticks whose lengths are integers from 1 to n. You want to arrange the sticks such
     * that exactly k sticks are visible from the left. A stick is visible from the left if there are no longer sticks
     * to the left of it.
     *
     * For example, if the sticks are arranged [1,3,2,5,4], then the sticks with lengths 1, 3, and 5 are visible from
     * the left.
     * Given n and k, return the number of such arrangements. Since the answer may be large, return it modulo 109 + 7.
     *
     * Input: n = 3, k = 2
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= n <= 1000
     * 1 <= k <= n
     * @param n
     * @param k
     * @return
     */
    // S1: recursion
    // time = O(n * k), space = O(n * k);
    int M = (int)1e9 + 7;
    public int rearrangeSticks(int n, int k) {
        long[][] dp = new long[n + 1][k + 1];
        return (int)helper(dp, n, k);
    }

    private long helper(long[][] dp, int n, int k) {
        if (n == k) return 1;
        if (k == 0) return 0;

        if (dp[n][k] == 0) dp[n][k] = (helper(dp, n - 1, k - 1) + helper(dp, n - 1, k) * (n - 1)) % M;
        return dp[n][k];
    }

    // S2: dp
    // time = O(n * k), space = O(n * k)
    public int rearrangeSticks2(int n, int k) {
        int M = (int)1e9 + 7;
        long[][] dp = new long[1005][1005];
        dp[0][0]= 1; // 0个元素构成0个环排列，是valid -> 1
        // dp[0][1] = 0; // 没有元素构成1个环排列 -> 0，可以不写，默认就是0

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= Math.min(k, n); j++) {
                dp[i][j] = (dp[i - 1][j - 1] + dp[i - 1][j] * (i - 1)) % M;
            }
        }
        return (int)dp[n][k];
    }
}
/**
 * For case that n stick to see k from left,
 * we considering the last element in the list:
 * case 1: if it's the largest,
 * then n-1 stick to see k-1 (excluding the last).
 * dp[n][k] += dp[n - 1][k - 1]
 *
 * case 2: if it's not the largest,
 * n - 1 choices for the last elements
 * then n-1 stick to see k (excluding the last).
 * dp[n][k] += dp[n - 1][k] * (n - 1)
 *
 * Edge case 1
 * if n == k, return 1;
 * Edge case 2
 * if k == 0, return 0;
 *
 * # Using n numbers to construct a permutation
 * n!
 * 123, 132, 213, 231, 312, 321
 *
 * # Using n numbers to construct a circular permutation => 首尾相接
 * 123 231 312 相对顺序都是一样的  => 环排列在全排列的基础上去重 => 全排列n!/n = (n - 1)!
 * 推论：considered as fixing the head, running permutation for the rest.
 * 本质上就只有2个环排列 123 (231, 312), 132 (321, 213)
 *
 * # Chossing m from n numbers to construct a permutation
 * A(n, m) = n!/(n-m)!
 *
 * # Chossing m from n numbers to construct a circular permutation
 * H(n, m) = (n!/(n-m)!)/m
 *
 * Using n numbers to construct m permutations:
 * dp[i][j] = ?
 *
 * # Using n numbers to construct m circular permutation
 * dp[i][j]: the number of ways that we can use first i numbers to construct j circular permutations
 * 1. If i-th element is for a new circular permutation
 *    dp[i-1][j-1]
 * 2. insert the i-th element to the previous j circular permutations
 *    dp[i-1][j] * (i-1) 每个位置都能插
 * dp[i][j] = dp[i-1][j-1] + dp[i-1][j] * (i-1)
 * Sterling I
 * S[n][k]
 * [a1 x ... x][a2 x ... x][a3 x ... x]...[ak x ...x]
 * n个数字分成k个区间，区间里最大的元素要放在区间里的第一位，这个区间要按照区间老大排列
 * 本质上用n个元素构造k个环排列：S[n][k]
 * 环排列与环排列之间的顺序是固定的，只要分成k个区间即可，唯一的自由度就是除去老大区间内的其他元素排列
 *
 * dp[i][j]: the number of ways that we use the first i sticks to constuct a permutation w/ j visible sticks
 * 1. if i-th element is a new visible stick -> 新开了一个区间，当了新区间的老大
 * dp[i-1][j-1]
 * 2. If i-th element is part of the previous j intervals,
 * dp[i-1][j] * (i-1) 只有i-1种加法加入进来，总是能插入后调整为j个合法的区间
 */
