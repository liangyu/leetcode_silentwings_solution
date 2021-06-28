package LC001_300;
import java.util.*;
public class LC174_DungeonGame {
    /**
     * The demons had captured the princess and imprisoned her in the bottom-right corner of a dungeon. The dungeon
     * consists of m x n rooms laid out in a 2D grid. Our valiant knight was initially positioned in the top-left room
     * and must fight his way through dungeon to rescue the princess.
     *
     * The knight has an initial health point represented by a positive integer. If at any point his health point drops
     * to 0 or below, he dies immediately.
     *
     * Some of the rooms are guarded by demons (represented by negative integers), so the knight loses health upon
     * ntering these rooms; other rooms are either empty (represented as 0) or contain magic orbs that increase the
     * knight's health (represented by positive integers).
     *
     * To reach the princess as quickly as possible, the knight decides to move only rightward or downward in each step.
     *
     * Return the knight's minimum initial health so that he can rescue the princess.
     *
     * Note that any room can contain threats or power-ups, even the first room the knight enters and the bottom-right
     * room where the princess is imprisoned.
     *
     * Input: dungeon = [[-2,-3,3],[-5,-10,1],[10,30,-5]]
     * Output: 7
     *
     * Constraints:
     *
     * m == dungeon.length
     * n == dungeon[i].length
     * 1 <= m, n <= 200
     * -1000 <= dungeon[i][j] <= 1000
     * @param dungeon
     * @return
     */
    // time = O(m * n), space = O(m * n)
    public int calculateMinimumHP(int[][] dungeon) {
        // corner case
        if (dungeon == null || dungeon.length == 0 || dungeon[0] == null || dungeon[0].length == 0) return 0;

        int m = dungeon.length, n = dungeon[0].length;
        int[][] dp = new int[m][n];

        // if last cell is negative, then minHealth + dungeon[m - 1][n - 1] == 1 =>
        // minHealth = 1 - dungeon[m - 1][n - 1]
        // if last cell is positive, then minHealth == 1
        // thus, if dungeon[m - 1][n - 1] < 0 => minHealth = 1 - dungeon[m - 1][n - 1]
        // if dungeon[m - 1][n - 1] >= 0 => minHealth can't be 1 - dungeon[m - 1][n - 1] < 0, must be at least 1
        // thus we use Math.max(1 - dungeon[m - 1][n - 1], 1) here.
        dp[m - 1][n - 1] = Math.max(1 - dungeon[m - 1][n - 1], 1);

        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = Math.max(dp[i + 1][n - 1] - dungeon[i][n - 1], 1);
        }

        for (int j = n - 2; j >= 0; j--) {
            dp[m - 1][j] = Math.max(dp[m - 1][j + 1] - dungeon[m - 1][j], 1);
        }

        for (int i = m - 2; i >= 0; i--) {
            for (int j = n - 2; j>= 0; j--) {
                int down = Math.max(dp[i + 1][j] - dungeon[i][j], 1);
                int right = Math.max(dp[i][j + 1] - dungeon[i][j], 1);
                dp[i][j] = Math.min(down, right); // 2 directions => we pick the minimum one
            }
        }
        return dp[0][0];
    }
}
/**
 * dp[i][j] = min{dp[i][j+1] - dungeion[j][j+1], dp[i+1][j] - dungeon[i+1][j]}
 * dp[i][j] 不能 <= 0 => dp[i][j] = Math.max(dp[i][j], 1)
 * 右下往左上推
 */