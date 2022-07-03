package LC2101_2400;

public class LC2312_SellingPiecesofWood {
    /**
     * You are given two integers m and n that represent the height and width of a rectangular piece of wood. You are
     * also given a 2D integer array prices, where prices[i] = [hi, wi, pricei] indicates you can sell a rectangular
     * piece of wood of height hi and width wi for pricei dollars.
     *
     * To cut a piece of wood, you must make a vertical or horizontal cut across the entire height or width of the
     * piece to split it into two smaller pieces. After cutting a piece of wood into some number of smaller pieces, you
     * can sell pieces according to prices. You may sell multiple pieces of the same shape, and you do not have to sell
     * all the shapes. The grain of the wood makes a difference, so you cannot rotate a piece to swap its height and
     * width.
     *
     * Return the maximum money you can earn after cutting an m x n piece of wood.
     *
     * Note that you can cut the piece of wood as many times as you want.
     *
     * Input: m = 3, n = 5, prices = [[1,4,2],[2,2,7],[2,1,3]]
     * Output: 19
     *
     * Input: m = 4, n = 6, prices = [[3,2,10],[1,4,2],[4,1,3]]
     * Output: 32
     *
     * Constraints:
     *
     * 1 <= m, n <= 200
     * 1 <= prices.length <= 2 * 10^4
     * prices[i].length == 3
     * 1 <= hi <= m
     * 1 <= wi <= n
     * 1 <= pricei <= 10^6
     * All the shapes of wood (hi, wi) are pairwise distinct.
     * @param m
     * @param n
     * @param prices
     * @return
     */
    // time = O(m * n * (m + n)), space = O(m * n)
    public long sellingWood(int m, int n, int[][] prices) {
        long[][] f = new long[m + 1][n + 1];
        for (int[] p : prices) {
            int h = p[0], w = p[1], v = p[2];
            f[h][w] = v;
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                for (int k = 1; k < i; k++) {
                    f[i][j] = Math.max(f[i][j], f[k][j] + f[i - k][j]);
                }

                for (int k = 1; k < j; k++) {
                    f[i][j] = Math.max(f[i][j], f[i][k] + f[i][j - k]);
                }
            }
        }
        return f[m][n];
    }
}
/**
 * 原型：AC321 棋盘分割 简化版
 * 二维区间dp
 * 状态表示 f[i,j]
 * 统计一类方案
 * 1.集合：所有高为i,宽为j的板子的分割方案的集合
 * 2.属性：max 最大值
 * 状态计算：集合的划分
 * k * j: f(k,j)
 * (i-k) * j: f(i-k,j)
 *
 * dp[i][j] = max{dp[][] + dp[][]} / prices
 */
