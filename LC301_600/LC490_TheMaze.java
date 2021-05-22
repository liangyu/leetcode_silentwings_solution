package LC301_600;
import java.util.*;
public class LC490_TheMaze {
    /**
     * There is a ball in a maze with empty spaces (represented as 0) and walls (represented as 1). The ball can go
     * through the empty spaces by rolling up, down, left or right, but it won't stop rolling until hitting a wall.
     * When the ball stops, it could choose the next direction.
     *
     * Given the m x n maze, the ball's start position and the destination, where start = [startrow, startcol] and
     * estination = [destinationrow, destinationcol], return true if the ball can stop at the destination, otherwise
     * return false.
     *
     * You may assume that the borders of the maze are all walls (see examples).
     *
     * Input: maze = [[0,0,1,0,0],[0,0,0,0,0],[0,0,0,1,0],[1,1,0,1,1],[0,0,0,0,0]], start = [0,4], destination = [4,4]
     * Output: true
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
    public boolean hasPath(int[][] maze, int[] start, int[] destination) {
        int row = maze.length, col = maze[0].length;
        boolean[][] visited = new boolean[row][col];

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(start[0] * col + start[1]);
        visited[start[0]][start[1]] = true;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int i = cur / col, j = cur % col;
            if (i == destination[0] && j == destination[1]) return true;
            for (int[] dir : DIRECTIONS) { // pick one direction
                // i, j will change after each pick of direction, so each for loop we need to set it back
                i = cur / col;
                j = cur % col;
                while (i + dir[0] >= 0 && i + dir[0] < row && j + dir[1] >= 0 && j + dir[1] < col && maze[i + dir[0]][j + dir[1]] == 0) {
                    i += dir[0];
                    j += dir[1];
                }
                if (!visited[i][j]) {
                    queue.offer(i * col + j);
                    visited[i][j] = true;
                }
            }
        }
        return false;
    }
}
