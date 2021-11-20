package LC1501_1800;
import java.util.*;
public class LC1778_ShortestPathinaHiddenGrid {
    /**
     * This is an interactive problem.
     *
     * There is a robot in a hidden grid, and you are trying to get it from its starting cell to the target cell in this
     * grid. The grid is of size m x n, and each cell in the grid is either empty or blocked. It is guaranteed that the
     * starting cell and the target cell are different, and neither of them is blocked.
     *
     * You want to find the minimum distance to the target cell. However, you do not know the grid's dimensions, the
     * starting cell, nor the target cell. You are only allowed to ask queries to the GridMaster object.
     *
     * Thr GridMaster class has the following functions:
     *
     * boolean canMove(char direction) Returns true if the robot can move in that direction. Otherwise, it returns false.
     * void move(char direction) Moves the robot in that direction. If this move would move the robot to a blocked cell
     * or off the grid, the move will be ignored, and the robot will remain in the same position.
     * boolean isTarget() Returns true if the robot is currently on the target cell. Otherwise, it returns false.
     * Note that direction in the above functions should be a character from {'U','D','L','R'}, representing the
     * directions up, down, left, and right, respectively.
     *
     * Return the minimum distance between the robot's initial starting cell and the target cell. If there is no valid
     * path between the cells, return -1.
     *
     * Custom testing:
     *
     * The test input is read as a 2D matrix grid of size m x n where:
     *
     * grid[i][j] == -1 indicates that the robot is in cell (i, j) (the starting cell).
     * grid[i][j] == 0 indicates that the cell (i, j) is blocked.
     * grid[i][j] == 1 indicates that the cell (i, j) is empty.
     * grid[i][j] == 2 indicates that the cell (i, j) is the target cell.
     * There is exactly one -1 and 2 in grid. Remember that you will not have this information in your code.
     *
     * Input: grid = [[1,2],[-1,0]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n, m <= 500
     * m == grid.length
     * n == grid[i].length
     * grid[i][j] is either -1, 0, 1, or 2.
     * There is exactly one -1 in grid.
     * There is exactly one 2 in grid.
     * @param master
     * @return
     */
    // time = O(1), space = O(1)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int findShortestPath(GridMaster master) {
        int[][] grid = new int[1001][1001];
        boolean[][] visited = new boolean[1001][1001];

        visited[500][500] = true;
        dfs(master, grid, 500, 500, visited);

        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{500, 500});
        for (int i = 0; i < visited.length; i++) Arrays.fill(visited[i], false);
        visited[500][500] = true;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int x = cur[0], y = cur[1];
                if (grid[x][y] == 2) return step;
                for (int[] dir : directions) {
                    int i = x + dir[0];
                    int j = y + dir[1];
                    if (grid[i][j] == 0) continue;
                    if (visited[i][j]) continue;
                    queue.offer(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
            step++;
        }
        return -1;
    }

    private void dfs(GridMaster master, int[][] grid, int x, int y, boolean[][] visited) {
        char[] d = new char[]{'U', 'R', 'D', 'L'};

        grid[x][y] = 1;
        if (master.isTarget()) {
            grid[x][y] = 2;
            return;
        }

        for (int k = 0; k < 4; k++) {
            int i = x + directions[k][0];
            int j = y + directions[k][1];
            if (visited[i][j]) continue;
            if (!master.canMove(d[k])) {
                grid[i][j] = 0;
                continue;
            } else {
                visited[i][j] = true;
                master.move(d[k]);
                dfs(master, grid, i, j, visited);
                int kk = k <= 1 ? k + 2 : k - 2;
                master.move(d[kk]);
            }
        }
    }
}
/**
 * // This is the GridMaster's API interface.
 * // You should not implement it, or speculate about its implementation
 * class GridMaster {
 *     boolean canMove(char direction);
 *     void move(char direction);
 *     boolean isTarget();
 * }
 */
/**
 * 本题巧妙地将DFS和BFS结合起来。
 *
 * 因为机器人只能单线程操作，只能到了一个位置再移动到另一个位置，无法像BFS那样并行地让多个机器人朝不同方向扩散。
 * 所以本题只能用DFS的方法，指挥机器人一步一步“深度优先+回溯”地探索完整个房间，标记每个格子的状态。
 * DFS结束后将房间的平面图记录在内存，就可以用BFS求到终点的最短路径。
 */
