package LC001_300;
import java.util.*;
public class LC286_WallsandGates {
    /**
     * You are given an m x n grid rooms initialized with these three possible values.
     *
     * -1 A wall or an obstacle.
     * 0 A gate.
     * INF Infinity means an empty room. We use the value 2^31 - 1 = 2147483647 to represent INF as you may assume that
     * the distance to a gate is less than 2147483647.
     * Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be
     * filled with INF.
     *
     * Input: rooms = [[2147483647,-1,0,2147483647],[2147483647,2147483647,2147483647,-1],[2147483647,-1,2147483647,-1],
     * [0,-1,2147483647,2147483647]]
     * Output: [[3,-1,0,1],[2,2,1,-1],[1,-1,2,-1],[0,-1,3,4]]
     *
     * Constraints:
     *
     * m == rooms.length
     * n == rooms[i].length
     * 1 <= m, n <= 250
     * rooms[i][j] is -1, 0, or 2^31 - 1.
     * @param rooms
     */
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public void wallsAndGates(int[][] rooms) {
        // corner case
        if (rooms == null || rooms.length == 0 || rooms[0] == null || rooms[0].length == 0) return;

        int m = rooms.length, n = rooms[0].length;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rooms[i][j] == 0) {
                    queue.offer(i * n + j);
                }
            }
        }

        int minLen = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                int i = cur / n, j = cur % n;
                for (int[] dir: DIRECTIONS) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    if (ii >= 0 && ii < m && jj >= 0 && jj < n && rooms[ii][jj] == Integer.MAX_VALUE) {
                        queue.offer(ii * n + jj);
                        rooms[ii][jj] = minLen;
                    }
                }
            }
            minLen++;
        }
    }
}
