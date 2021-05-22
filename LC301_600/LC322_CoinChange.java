package LC301_600;
import java.util.*;
public class LC322_CoinChange {
    /**
     * You are given coins of different denominations and a total amount of money amount. Write a function to compute
     * the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by
     * any combination of the coins, return -1.
     *
     * You may assume that you have an infinite number of each kind of coin.
     *
     * Input: coins = [1,2,5], amount = 11
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= coins.length <= 12
     * 1 <= coins[i] <= 2^31 - 1
     * 0 <= amount <= 10^4
     *
     * @param coins
     * @param amount
     * @return
     */
    // time = O(k * n), space = O(k) k: the amount
    public int coinChange(int[] coins, int amount) {
        // corner case
        if (coins == null || coins.length == 0 || amount < 0) return 0;

        int[] dp = new int[amount + 1];
        // dp[0] = 0; // dp[0]在这里不需要使用，它的值给什么都无所谓

        for (int i = 1; i <= amount; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < coins.length; j++) {
                if (i >= coins[j] && dp[i - coins[j]] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
