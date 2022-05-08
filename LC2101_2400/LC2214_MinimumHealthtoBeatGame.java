package LC2101_2400;

public class LC2214_MinimumHealthtoBeatGame {
    /**
     * You are playing a game that has n levels numbered from 0 to n - 1. You are given a 0-indexed integer array damage
     * where damage[i] is the amount of health you will lose to complete the ith level.
     *
     * You are also given an integer armor. You may use your armor ability at most once during the game on any level
     * which will protect you from at most armor damage.
     *
     * You must complete the levels in order and your health must be greater than 0 at all times to beat the game.
     *
     * Return the minimum health you need to start with to beat the game.
     *
     * Input: damage = [2,7,4,3], armor = 4
     * Output: 13
     *
     * Input: damage = [2,5,3,4], armor = 7
     * Output: 10
     *
     * Constraints:
     *
     * n == damage.length
     * 1 <= n <= 10^5
     * 0 <= damage[i] <= 10^5
     * 0 <= armor <= 10^5
     * @param damage
     * @param armor
     * @return
     */
    // S1: DP
    // time = O(n), space = O(n)
    public long minimumHealth(int[] damage, int armor) {
        int n = damage.length;
        long[][] dp = new long[n + 1][2];

        for (int i = 1; i <= n; i++) {
            dp[i][0] = dp[i - 1][0] + damage[i - 1];
            dp[i][1] = Math.min(dp[i - 1][1] + damage[i - 1], dp[i - 1][0] + Math.max(damage[i - 1] - armor, 0));
        }
        return Math.min(dp[n][0], dp[n][1]) + 1;
    }

    // S2: DP + Rolling Matrix
    // time = O(n), space = O(1)
    public long minimumHealth2(int[] damage, int armor) {
        int n = damage.length;
        long notUse = 0, use = 0;

        for (int i = 0; i < n; i++) {
            long temp_notUse = notUse, temp_use = use;
            notUse = temp_notUse + damage[i];
            use = Math.min(temp_use + damage[i], temp_notUse + Math.max(damage[i] - armor, 0));
        }
        return Math.min(notUse, use) + 1;
    }
}
