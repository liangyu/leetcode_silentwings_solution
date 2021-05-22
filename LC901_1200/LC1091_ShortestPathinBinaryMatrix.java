package LC901_1200;
import java.util.*;
public class LC1091_ShortestPathinBinaryMatrix {
    /**
     * In an N by N square grid, each cell is either empty (0) or blocked (1).
     *
     * A clear path from top-left to bottom-right has length k if and only if it is composed of cells
     * C_1, C_2, ..., C_k such that:
     *
     * Adjacent cells C_i and C_{i+1} are connected 8-directionally (ie., they are different and share an edge or corner)
     * C_1 is at location (0, 0) (ie. has value grid[0][0])
     * C_k is at location (N-1, N-1) (ie. has value grid[N-1][N-1])
     * If C_i is located at (r, c), then grid[r][c] is empty (ie. grid[r][c] == 0).
     * Return the length of the shortest such clear path from top-left to bottom-right.  If such a path does not exist,
     * return -1.
     *
     * Input: [[0,0,0],[1,1,0],[1,1,0]]
     * Output: 4
     *
     * Note:
     *
     * 1 <= grid.length == grid[0].length <= 100
     * grid[r][c] is 0 or 1
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    public int shortestPathBinaryMatrix(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return -1;

        int row = grid.length, col = grid[0].length;
        if (grid[0][0] == 1 || grid[row - 1][col - 1] == 1) return -1;

        Queue<Integer> queue = new LinkedList<>();
        boolean[][] visited = new boolean[row][col];
        queue.offer(0);
        visited[0][0] = true;

        int minLen = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                int i = cur / col, j = cur % col;
                if (i == row - 1 && j == col - 1) return minLen;
                for (int[] dir : DIRECTIONS) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    if (ii >= 0 && ii < row && jj >= 0 && jj < col && !visited[ii][jj] && grid[ii][jj] == 0) {
                        queue.offer(ii * col + jj);
                        visited[ii][jj] = true;
                    }
                }
            }
            minLen++;
        }
        return -1;
    }
}
