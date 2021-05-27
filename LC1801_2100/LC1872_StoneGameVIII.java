package LC1801_2100;
import java.util.*;
public class LC1872_StoneGameVIII {
    /**
     * Alice and Bob take turns playing a game, with Alice starting first.
     *
     * There are n stones arranged in a row. On each player's turn, while the number of stones is more than one, they
     * will do the following:
     *
     * Choose an integer x > 1, and remove the leftmost x stones from the row.
     * Add the sum of the removed stones' values to the player's score.
     * Place a new stone, whose value is equal to that sum, on the left side of the row.
     * The game stops when only one stone is left in the row.
     *
     * The score difference between Alice and Bob is (Alice's score - Bob's score). Alice's goal is to maximize the
     * score difference, and Bob's goal is the minimize the score difference.
     *
     * Given an integer array stones of length n where stones[i] represents the value of the ith stone from the left,
     * return the score difference between Alice and Bob if they both play optimally.
     *
     * Input: stones = [-1,2,-3,4,-5]
     * Output: 5
     *
     * Constraints:
     *
     * n == stones.length
     * 2 <= n <= 10^5
     * -10^4 <= stones[i] <= 10^4
     * @param stones
     * @return
     */
    // time = O(n), space = O(n)
    public int stoneGameVIII(int[] stones) {
        // corner case
        if (stones == null || stones.length == 0) return 0;

        int n = stones.length;
        int[] presum = new int[n + 1]; // 0, 1 ~ n
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + stones[i - 1];
        int[] dp = new int[n + 1];
        dp[1] = 0;
        dp[2] = presum[n] - dp[1];
        for (int i = 3; i <= n; i++) {
            dp[i] = Math.max(dp[i - 1], presum[n - i + 2] - dp[i - 1]);
        }
        return dp[n];
    }
}
/**
 * dp[i]: with i stones left, the maximum score difference for the starter
 * 序列：i 与index有关
 * dp[1] = 0
 * o => dp[1] = 0;
 * o o => [x x x x x o] o => x > 1 只能取2 -> dp[2] = presum[n] - dp[1] (对手的净剩分)
 * o o o -> [x x x x o] o o => dp[3] = max{}
 * 1. pick 3 balls: presum[n] - dp[1]
 * 2. pick 2 balls: presum[n - 1] - dp[2]
 * [x x x o] o o o => dp[4] = max{dp[3], presum[n - 2] - dp[3]}
 * 1. pick 4 balls: presum[n] - dp[1]
 * 2. pick 3 balls: presum[n - 1] - dp[2]
 * 3. pick 2 balls: presum[n - 2] - dp[3]
 *
 * dp[i] = max{dp[i - 1], presum[n - i + 2] - dp[i - 1]}
 * return dp[n]
 */
