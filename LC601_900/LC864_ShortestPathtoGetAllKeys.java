package LC601_900;
import java.util.*;
public class LC864_ShortestPathtoGetAllKeys {
    /**
     * You are given an m x n grid grid where:
     *
     * '.' is an empty cell.
     * '#' is a wall.
     * '@' is the starting point.
     * Lowercase letters represent keys.
     * Uppercase letters represent locks.
     * You start at the starting point and one move consists of walking one space in one of the four cardinal directions.
     * You cannot walk outside the grid, or walk into a wall.
     *
     * If you walk over a key, you can pick it up and you cannot walk over a lock unless you have its corresponding key.
     *
     * For some 1 <= k <= 6, there is exactly one lowercase and one uppercase letter of the first k letters of the
     * English alphabet in the grid. This means that there is exactly one key for each lock, and one lock for each key;
     * and also that the letters used to represent the keys and locks were chosen in the same order as the English alphabet.
     *
     * Return the lowest number of moves to acquire all keys. If it is impossible, return -1.
     *
     * Input: grid = ["@.a.#","###.#","b.A.B"]
     * Output: 8
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 30
     * grid[i][j] is either an English letter, '.', '#', or '@'.
     * The number of keys in the grid is in the range [1, 6].
     * Each key in the grid is unique.
     * Each key in the grid has a matching lock.
     * @param grid
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int shortestPathAllKeys(String[] grid) {
        // corner case
        if (grid == null || grid.length == 0) return 0;

        int m = grid.length, n = grid[0].length(), count = 0;
        Queue<int[]> queue = new LinkedList<>(); // {x, y, state}
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i].charAt(j) == '@') {
                    queue.offer(new int[]{i * n + j, 0});
                } else if (grid[i].charAt(j) >= 'a' && grid[i].charAt(j) <= 'f') count++;
            }
        }

        boolean[][][] visited = new boolean[m][n][1 << count];
        int x = queue.peek()[0] / n, y = queue.peek()[0]  % n;
        visited[x][y][0] = true;

        int finalState = 0;
        for (int i = 0; i < count; i++) {
            finalState |= 1 << i;
        }

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] cur = queue.poll();
                int i = cur[0] / n, j = cur[0] % n;
                int state = cur[1];
                for (int[] dir : DIRECTIONS) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    int newState = state;
                    if (ii < 0 || ii >= m || jj < 0 || jj >= n) continue;
                    if (grid[ii].charAt(jj) == '#') continue;
                    if (grid[ii].charAt(jj) >= 'A' && grid[ii].charAt(jj) <= 'F' && ((state >> (grid[ii].charAt(jj) - 'A')) & 1) == 0) {
                        continue; // doesn't have key
                    }
                    if (grid[ii].charAt(jj) >= 'a' && grid[ii].charAt(jj) <= 'f') {
                        newState |= 1 << (grid[ii].charAt(jj) - 'a'); // pick up the key and update the state
                    }
                    if (visited[ii][jj][newState]) continue;
                    if (newState == finalState) return step + 1;
                    queue.offer(new int[]{ii * n + jj, newState});
                    visited[ii][jj][newState] = true;
                }
            }
            step++;
        }
        return -1;
    }
}
/**
 * ref: LC847
 * 1个格子会不止一次碰到，bfs没法去重
 * 多次放到队列中
 * 加入一个状态，node可以随便走，但是状态不同
 * 真正去重的是去除完全重复的状态
 * 30*30*2^6 = 900 * 64 ~ 10^5
 */