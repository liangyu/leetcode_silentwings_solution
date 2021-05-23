package LC1501_1800;
import java.util.*;
public class LC1706_WhereWilltheBallFall {
    /**
     * You have a 2-D grid of size m x n representing a box, and you have n balls. The box is open on the top and
     * bottom sides.
     *
     * Each cell in the box has a diagonal board spanning two corners of the cell that can redirect a ball to the
     * right or to the left.
     *
     * A board that redirects the ball to the right spans the top-left corner to the bottom-right corner and is
     * represented in the grid as 1.
     * A board that redirects the ball to the left spans the top-right corner to the bottom-left corner and is
     * represented in the grid as -1.
     * We drop one ball at the top of each column of the box. Each ball can get stuck in the box or fall out of the
     * bottom. A ball gets stuck if it hits a "V" shaped pattern between two boards or if a board redirects the ball
     * into either wall of the box.
     *
     * Return an array answer of size n where answer[i] is the column that the ball falls out of at the bottom after
     * dropping the ball from the ith column at the top, or -1 if the ball gets stuck in the box.
     *
     * Input: grid = [[1,1,1,-1,-1],[1,1,1,-1,-1],[-1,-1,-1,1,1],[1,1,1,1,-1],[-1,-1,-1,-1,-1]]
     * Output: [1,-1,-1,-1,-1]
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 100
     * grid[i][j] is 1 or -1.
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(1)
    public int[] findBall(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return new int[0];

        int m = grid.length, n = grid[0].length;
        int[] res = new int[n];
        Arrays.fill(res, -1);

        for (int i = 0; i < n; i++) {
            int x = 0, y = i;
            while (x < m) {
                if (grid[x][y] == 1) {
                    if (y == n - 1) break;
                    else if (grid[x][y + 1] == -1) break;
                    else {
                        x++;
                        y++;
                    }
                } else {
                    if (y == 0) break;
                    else if (grid[x][y - 1] == 1) break;
                    else {
                        x++;
                        y--;
                    }
                }
            }
            if (x == m) res[i] = y;
        }
        return res;
    }
}
/**
 * 轨迹是确定的，本质上是道模拟题
 * 把规则写成代码，就知道下一步会走到哪里
 * 关键：规则怎么写？
 * 每次解决从这一行到下一行的哪个位置 -> 递归解决
 * 如果左边是从左上到右下，那就要看右边的3种情况：
 * 1. 左上到右下：滚到右边的下一行
 * 2. 右上到左下：V字形，卡死
 * 3. 隔板：卡死
 * 右边同理，所以总共是6种情况
 */
