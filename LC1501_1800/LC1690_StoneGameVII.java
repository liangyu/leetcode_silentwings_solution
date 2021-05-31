package LC1501_1800;
import java.util.*;
public class LC1690_StoneGameVII {
    /**
     * Alice and Bob take turns playing a game, with Alice starting first.
     *
     * There are n stones arranged in a row. On each player's turn, they can remove either the leftmost stone or the
     * rightmost stone from the row and receive points equal to the sum of the remaining stones' values in the row. The
     * winner is the one with the higher score when there are no stones left to remove.
     *
     * Bob found that he will always lose this game (poor Bob, he always loses), so he decided to minimize the score's
     * difference. Alice's goal is to maximize the difference in the score.
     *
     * Given an array of integers stones where stones[i] represents the value of the ith stone from the left, return the
     * difference in Alice and Bob's score if they both play optimally.
     *
     * Input: stones = [5,3,1,4,2]
     * Output: 6
     *
     * Constraints:
     *
     * n == stones.length
     * 2 <= n <= 1000
     * 1 <= stones[i] <= 1000
     * @param stones
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public int stoneGameVII(int[] stones) {
        // corner case
        if (stones == null || stones.length == 0) return 0;

        int n = stones.length;
        int[][] dp = new int[n + 1][n + 1];
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + stones[i - 1];

        for (int i = 1; i <= n; i++) dp[i][i] = 0;
        for (int i = 1; i + 1 <= n; i++) dp[i][i + 1] = Math.max(stones[i - 1], stones[i]);

        for (int len = 3; len <= n; len++) { // 先解决小区间问题，再解决大区间问题
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                // update dp[i][j]
                dp[i][j] = Math.max(presum[j] - presum[i] - dp[i + 1][j], presum[j - 1] - presum[i - 1] - dp[i][j - 1]);
            }
        }
        return dp[1][n];
    }
}
/**
 *  区间型dp
 *  dp[i][j]: the maximum difference in Alice and Bob's score when playing in [i:j]
 *
 *  x [x x x x x x x]
 *  i              j
 *
 *  dp[i][j] = max{sum[i+1:j] - dp[i+1][j], sum[i:j-1] - dp[i][j-1]}
 *  遇到区间和就采用前缀和数组
 */
