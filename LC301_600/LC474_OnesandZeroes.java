package LC301_600;

public class LC474_OnesandZeroes {
    /**
     * You are given an array of binary strings strs and two integers m and n.
     *
     * Return the size of the largest subset of strs such that there are at most m 0's and n 1's in the subset.
     *
     * A set x is a subset of a set y if all elements of x are also elements of y.
     *
     * Input: strs = ["10","0001","111001","1","0"], m = 5, n = 3
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= strs.length <= 600
     * 1 <= strs[i].length <= 100
     * strs[i] consists only of digits '0' and '1'.
     * 1 <= m, n <= 100
     * @param strs
     * @param m
     * @param n
     * @return
     */
    // S1: dp
    // time = O(m * n), space = O(m * n)
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];

        for (String str : strs) {
            int a = 0, b = 0;
            for (char c : str.toCharArray()) {
                if (c == '0') a++;
                else b++;
            }
            for (int i = m; i >= a; i--) {
                for (int j = n; j >= b; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - a][j - b] + 1);
                }
            }
        }
        return dp[m][n];
    }
}
/**
 * 二维费用背包问题
 * 每个0-1串看成物品：
 * 0的个数：重量
 * 1的个数：体积
 * 价值：1
 * m, n
 * 二维费用的背包问题
 * dp[i][j]: i个1和j个0最多可以装多少件 => dp[m][n]
 * 一个一个元素考虑
 * 考虑第三个元素的时候：
 * min(dp[i-4][j-2] + 1, dp[i][j])
 */