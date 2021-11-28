package LC1801_2100;

public class LC2087_MinimumCostHomecomingofaRobotinaGrid {
    /**
     * There is an m x n grid, where (0, 0) is the top-left cell and (m - 1, n - 1) is the bottom-right cell. You are
     * given an integer array startPos where startPos = [startrow, startcol] indicates that initially, a robot is at the
     * cell (startrow, startcol). You are also given an integer array homePos where homePos = [homerow, homecol]
     * indicates that its home is at the cell (homerow, homecol).
     *
     * The robot needs to go to its home. It can move one cell in four directions: left, right, up, or down, and it can
     * not move outside the boundary. Every move incurs some cost. You are further given two 0-indexed integer arrays:
     * rowCosts of length m and colCosts of length n.
     *
     * If the robot moves up or down into a cell whose row is r, then this move costs rowCosts[r].
     * If the robot moves left or right into a cell whose column is c, then this move costs colCosts[c].
     * Return the minimum total cost for this robot to return home.
     *
     * Input: startPos = [1, 0], homePos = [2, 3], rowCosts = [5, 4, 3], colCosts = [8, 2, 6, 7]
     * Output: 18
     *
     * Constraints:
     *
     * m == rowCosts.length
     * n == colCosts.length
     * 1 <= m, n <= 10^5
     * 0 <= rowCosts[r], colCosts[c] <= 10^4
     * startPos.length == 2
     * homePos.length == 2
     * 0 <= startrow, homerow < m
     * 0 <= startcol, homecol < n
     * @param startPos
     * @param homePos
     * @param rowCosts
     * @param colCosts
     * @return
     */
    // time = O(m + n), space = O(1)
    public int minCost(int[] startPos, int[] homePos, int[] rowCosts, int[] colCosts) {
        int m = rowCosts.length, n = colCosts.length;
        int sx = startPos[0], sy = startPos[1];
        int tx = homePos[0], ty = homePos[1];
        int cost = 0;

        if (sx <= tx) cost += helper(rowCosts, sx + 1, tx);
        if (sx > tx) cost += helper(rowCosts, tx, sx - 1);
        if (sy <= ty) cost += helper(colCosts, sy + 1, ty);
        if (sy > ty) cost += helper(colCosts, ty, sy - 1);
        return cost;
    }

    private int helper(int[] nums, int start, int end) {
        int cost = 0;
        for (int i = start; i <= end; i++) {
            cost += nums[i];
        }
        return cost;
    }
}
