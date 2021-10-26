package LC1801_2100;
import java.util.*;
public class LC1810_MinimumPathCostinaHiddenGrid {
    /**
     * This is an interactive problem.
     *
     * There is a robot in a hidden grid, and you are trying to get it from its starting cell to the target cell in this
     * grid. The grid is of size m x n, and each cell in the grid is either empty or blocked. It is guaranteed that the
     * starting cell and the target cell are different, and neither of them is blocked.
     *
     * Each cell has a cost that you need to pay each time you move to the cell. The starting cell's cost is not applied
     * before the robot moves.
     *
     * You want to find the minimum total cost to move the robot to the target cell. However, you do not know the grid's
     * dimensions, the starting cell, nor the target cell. You are only allowed to ask queries to the GridMaster object.
     *
     * The GridMaster class has the following functions:
     *
     * boolean canMove(char direction) Returns true if the robot can move in that direction. Otherwise, it returns false.
     * int move(char direction) Moves the robot in that direction and returns the cost of moving to that cell. If this
     * move would move the robot to a blocked cell or off the grid, the move will be ignored, the robot will remain in
     * the same position, and the function will return -1.
     * boolean isTarget() Returns true if the robot is currently on the target cell. Otherwise, it returns false.
     * Note that direction in the above functions should be a character from {'U','D','L','R'}, representing the
     * directions up, down, left, and right, respectively.
     *
     * Return the minimum total cost to get the robot from its initial starting cell to the target cell. If there is no
     * valid path between the cells, return -1.
     *
     * Custom testing:
     *
     * The test input is read as a 2D matrix grid of size m x n and four integers r1, c1, r2, and c2 where:
     *
     * grid[i][j] == 0 indicates that the cell (i, j) is blocked.
     * grid[i][j] >= 1 indicates that the cell (i, j) is empty and grid[i][j] is the cost to move to that cell.
     * (r1, c1) is the starting cell of the robot.
     * (r2, c2) is the target cell of the robot.
     * Remember that you will not have this information in your code.
     *
     * Input: grid = [[2,3],[1,1]], r1 = 0, c1 = 1, r2 = 1, c2 = 0
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n, m <= 100
     * m == grid.length
     * n == grid[i].length
     * 0 <= grid[i][j] <= 100
     * @param master
     * @return
     */
    private int tx = -1, ty = -1;
    private int[][] directions = new int[][]{{-1, 0}, {0, -1}, {0, 1}, {1, 0}};
    public int findShortestPath(GridMaster master) {
        int[][] cost = new int[201][201];
        boolean[][] visited = new boolean[201][201];

        for (int i = 0; i < 201; i++) Arrays.fill(cost[i], -1);
        String d = "ULRD";
        dfs(master, 100, 100, cost, visited, d);
        for (int i = 0; i < 201; i++) Arrays.fill(visited[i], false);

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]); // {cost, x, y}
        pq.offer(new int[]{0, 100, 100});

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int c = cur[0], x = cur[1], y = cur[2];
            if (x == tx && y == ty) return c;

            for (int[] dir : directions) {
                int i = x + dir[0];
                int j = y + dir[1];
                if (cost[i][j] == -1) continue;
                if (i < 0 || i >= 200 || j < 0 || j >= 200) continue;
                if (visited[i][j]) continue;
                pq.offer(new int[]{c + cost[i][j], i, j});
                visited[i][j] = true;
            }
        }
        return -1;
    }

    private void dfs(GridMaster master, int x, int y, int[][] cost, boolean[][] visited, String d) {
        // base case
        if (master.isTarget()) {
            tx = x;
            ty = y;
        }

        for (int k = 0; k < 4; k++) {
            int i = x + directions[k][0];
            int j = y + directions[k][1];
            if (visited[i][j]) continue;
            visited[i][j] = true;
            if (master.canMove(d.charAt(k))) {
                int c = master.move(d.charAt(k));
                cost[i][j] = c;
                dfs(master, i, j, cost, visited, d);
                master.move(d.charAt(3 - k)); // setback
            }
        }
    }
}
/**
 * // This is the GridMaster's API interface.
 * // You should not implement it, or speculate about its implementation
 * class GridMaster {
 *     boolean canMove(char direction);
 *     int move(char direction);
 *     boolean isTarget();
 * }
 */

/**
 * 由于先要进行探索起点和终点，上下左右四个方向都有可能，所以开辟一个200x200的二维矩阵，并将起始点设置为(100,100)。
 * 先通过DFS走遍所有的格子，标记每个格子的cost和是否是障碍物，以及终点的位置。
 * 然后再从起点开始，用Dijkstra算法求得起点到终点的最小权重路径。
 */
