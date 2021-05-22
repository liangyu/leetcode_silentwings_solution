package LC301_600;
import java.util.*;
public class LC505_TheMazeII {
    /**
     * There is a ball in a maze with empty spaces (represented as 0) and walls (represented as 1). The ball can go
     * through the empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall.
     * When the ball stops, it could choose the next direction.
     *
     * Given the m x n maze, the ball's start position and the destination, where start = [startrow, startcol] and
     * destination = [destinationrow, destinationcol], return the shortest distance for the ball to stop at the
     * destination. If the ball cannot stop at destination, return -1.
     *
     * The distance is the number of empty spaces traveled by the ball from the start position (excluded) to the
     * destination (included).
     *
     * You may assume that the borders of the maze are all walls (see examples).
     *
     * Input: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [4,4]
     * Output: 12
     *
     * Constraints:
     *
     * m == maze.length
     * n == maze[i].length
     * 1 <= m, n <= 100
     * maze[i][j] is 0 or 1.
     * start.length == 2
     * destination.length == 2
     * 0 <= startrow, destinationrow <= m
     * 0 <= startcol, destinationcol <= n
     * Both the ball and the destination exist in an empty space, and they will not be in the same position initially.
     * The maze contains at least 2 empty spaces.
     * @param maze
     * @param start
     * @param destination
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int shortestDistance(int[][] maze, int[] start, int[] destination) {
        int m = maze.length, n = maze[0].length;
        int[][] dist = new int[m][n];
        for (int[] d : dist) Arrays.fill(d, -1);
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start[0] * n + start[1]);
        dist[start[0]][start[1]] = 0;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (int[] dir : DIRECTIONS) {
                int i = cur / n, j = cur % n;
                int minLen = dist[i][j];
                while (i + dir[0] >= 0 && i + dir[0] < m && j + dir[1] >= 0 && j + dir[1] < n && maze[i + dir[0]][j + dir[1]] == 0) {
                    i += dir[0];
                    j += dir[1];
                    minLen++;
                }
                if (dist[i][j] == -1 || dist[i][j] > minLen) {
                    dist[i][j] = minLen;
                    queue.offer(i * n + j);
                }
            }
        }
        return dist[destination[0]][destination[1]];
    }
}
