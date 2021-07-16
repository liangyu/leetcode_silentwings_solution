package LC1801_2100;
import java.util.*;
public class LC1926_NearestExitfromEntranceinMaze {
    /**
     * You are given an m x n matrix maze (0-indexed) with empty cells (represented as '.') and walls
     * (represented as '+'). You are also given the entrance of the maze, where entrance = [entrancerow, entrancecol]
     * denotes the row and column of the cell you are initially standing at.
     *
     * In one step, you can move one cell up, down, left, or right. You cannot step into a cell with a wall, and you
     * cannot step outside the maze. Your goal is to find the nearest exit from the entrance. An exit is defined as an
     * empty cell that is at the border of the maze. The entrance does not count as an exit.
     *
     * Return the number of steps in the shortest path from the entrance to the nearest exit, or -1 if no such path exists.
     *
     * Input: maze = [["+","+",".","+"],[".",".",".","+"],["+","+","+","."]], entrance = [1,2]
     * Output: 1
     *
     * Constraints:
     *
     * maze.length == m
     * maze[i].length == n
     * 1 <= m, n <= 100
     * maze[i][j] is either '.' or '+'.
     * entrance.length == 2
     * 0 <= entrancerow < m
     * 0 <= entrancecol < n
     * entrance will always be an empty cell.
     * @param maze
     * @param entrance
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public int nearestExit(char[][] maze, int[] entrance) {
        int m = maze.length, n = maze[0].length;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(entrance[0] * n + entrance[1]);
        boolean[][] visited = new boolean[m][n];
        visited[entrance[0]][entrance[1]] = true;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                int i = cur / n, j = cur % n;
                if (isExit(maze, entrance, i, j)) return step;
                for (int[] dir : DIRECTIONS) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    if (ii >= 0 && ii < m && jj >= 0 && jj < n && !visited[ii][jj] && maze[ii][jj] != '+') {
                        queue.offer(ii * n + jj);
                        visited[ii][jj] = true;
                    }
                }
            }
            step++;
        }
        return -1;
    }

    private boolean isExit(char[][] maze, int[] entrance, int i, int j) {
        int m = maze.length, n = maze[0].length;
        if (i == entrance[0] && j == entrance[1]) return false;
        if ((i == 0 || i == m - 1) && maze[i][j] == '.') return true;
        if ((j == 0 || j == n - 1) && maze[i][j] == '.') return true;
        return false;
    }
}
