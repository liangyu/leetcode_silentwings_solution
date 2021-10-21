package LC901_1200;
import java.util.*;
public class LC909_SnakesandLadders {
    /**
     * You are given an n x n integer matrix board where the cells are labeled from 1 to n2 in a Boustrophedon style
     * starting from the bottom left of the board (i.e. board[n - 1][0]) and alternating direction each row.
     *
     * You start on square 1 of the board. In each move, starting from square curr, do the following:
     *
     * Choose a destination square next with a label in the range [curr + 1, min(curr + 6, n^2)].
     * This choice simulates the result of a standard 6-sided die roll: i.e., there are always at most 6 destinations,
     * regardless of the size of the board.
     * If next has a snake or ladder, you must move to the destination of that snake or ladder. Otherwise, you move to
     * next.
     * The game ends when you reach the square n2.
     * A board square on row r and column c has a snake or ladder if board[r][c] != -1. The destination of that snake
     * or ladder is board[r][c]. Squares 1 and n2 do not have a snake or ladder.
     *
     * Note that you only take a snake or ladder at most once per move. If the destination to a snake or ladder is the
     * start of another snake or ladder, you do not follow the subsequent snake or ladder.
     *
     * For example, suppose the board is [[-1,4],[-1,3]], and on the first move, your destination square is 2. You
     * follow the ladder to square 3, but do not follow the subsequent ladder to 4.
     * Return the least number of moves required to reach the square n2. If it is not possible to reach the square,
     * return -1.
     *
     * Input: board = [[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,-1,-1,-1,-1,-1],[-1,35,-1,-1,13,-1],
     * [-1,-1,-1,-1,-1,-1],[-1,15,-1,-1,-1,-1]]
     * Output: 4
     *
     * Constraints:
     *
     * n == board.length == board[i].length
     * 2 <= n <= 20
     * grid[i][j] is either -1 or in the range [1, n^2].
     * The squares labeled 1 and n2 do not have any ladders or snakes.
     * @param board
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public int snakesAndLadders(int[][] board) {
        // corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return 0;

        int n = board.length;
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1); // {x, y}
        boolean[] visited = new boolean[n * n + 1];
        visited[1] = true;

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                if (cur == n * n) return step;
                for (int k = 1; k <= Math.min(6, n * n - cur); k++) {
                    int val = cur + k;
                    int[] next = convert(board, val);
                    int r = next[0], c = next[1];
                    if (board[r][c] != -1) val = board[r][c];
                    if (!visited[val]) {
                        queue.offer(val);
                        visited[val] = true;
                    }
                }
            }
            step++;
        }
        return -1;
    }

    private int[] convert(int[][] board, int val) {
        int n = board.length;
        int i = (val - 1) / n, j = (val - 1) % n;
        int row = n - 1 - i, col = i % 2 == 0 ? j : n - 1 - j;
        return new int[]{row, col};
    }
}
