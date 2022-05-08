package LC301_600;

public class LC343_IntegerBreak {
    /**
     * Given an integer n, break it into the sum of k positive integers, where k >= 2, and maximize the product of those
     * integers.
     *
     * Return the maximum product you can get.
     *
     * Input: n = 2
     * Output: 1
     *
     * Constraints:
     *
     * 2 <= n <= 58
     * @param n
     * @return
     */
    // S1: dp
    // time = O(n^2), space = O(n)
    public int integerBreak(int n) {
        if (n == 2) return 1;
        if (n == 3) return 2;

        int[] dp = new int[n + 1];

        // init
        dp[2] = 2;
        dp[3] = 3;

        for (int i = 4; i <= n; i++) {
            for (int j = 2; j <= i / 2; j++) {
                dp[i] = Math.max(dp[i], dp[j] * dp[i - j]);
            }
        }
        return dp[n];
    }

    // S2: Math
    public int integerBreak2(int n) {
        if (n == 2) return 1;
        if (n == 3) return 2;

        if (n % 3 == 0) return (int) Math.pow(3, n / 3);
        else if (n % 3 == 1) return (int) Math.pow(3, n / 3 - 1) * 4; // 乘以1永远都是不合算的！
        return (int) Math.pow(3, n / 3) * 2;
    }
}
/**
 * M = (x1+x2+x3) + (x4+x5+...)
 * 先考虑2个数加和的乘积，最后break down就是最优解
 * 一开始没有必要考虑分很多份
 * S2: Math
 * M -> M/2 * M/2
 * M, n => pow(M/n, n)
 * M / n = e = 2.73
 */