package LC2101_2400;
import java.util.*;
public class LC2218_MaximumValueofKCoinsFromPiles {
    /**
     * There are n piles of coins on a table. Each pile consists of a positive number of coins of assorted denominations.
     *
     * In one move, you can choose any coin on top of any pile, remove it, and add it to your wallet.
     *
     * Given a list piles, where piles[i] is a list of integers denoting the composition of the ith pile from top to
     * bottom, and a positive integer k, return the maximum total value of coins you can have in your wallet if you
     * choose exactly k coins optimally.
     *
     * Input: piles = [[1,100,3],[7,8,9]], k = 2
     * Output: 101
     *
     * Input: piles = [[100],[100],[100],[100],[100],[100],[1,1,1,1,1,1,700]], k = 7
     * Output: 706
     *
     * Constraints:
     *
     * n == piles.length
     * 1 <= n <= 1000
     * 1 <= piles[i][j] <= 10^5
     * 1 <= k <= sum(piles[i].length) <= 2000
     * @param piles
     * @param k
     * @return
     */
    // time = O(sum(piles[i].length) * k), space = O(max(n * m, n * k))
    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        int n = piles.size();
        int[][] dp = new int[n][k + 1];
        List<Integer>[] presum = new List[n]; // presum[i][t]: the sum of the first t coins in the i-th pile
        for (int i = 0; i < n; i++) presum[i] = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int sum = 0; // allow 0 coins
            presum[i].add(sum);
            for (int j = 0; j < piles.get(i).size(); j++) {
                sum += piles.get(i).get(j);
                presum[i].add(sum);
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= k; j++) {
                for (int t = 0; t <= Math.min(j, piles.get(i).size()); t++) {
                    dp[i][j] = Math.max(dp[i][j], (i == 0 ? 0 : dp[i - 1][j - t]) + presum[i].get(t));
                }
            }
        }
        return dp[n - 1][k];
    }
}
/**
 * 无后效性
 * dp[i][j]: the maximum total value of coins by the first i piles and choosing j coins
 * dp[i][j]: suppose we choose t coins in the i-th pile
 * dp[i][j] = max{dp[i-1][j-t] + pilesPresum[i][t]} for t = 0,1,2,...len(piles[i])
 */