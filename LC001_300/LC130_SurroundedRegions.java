package LC001_300;
import java.util.*;
public class LC130_SurroundedRegions {
    /**
     * Given an m x n matrix board containing 'X' and 'O', capture all regions surrounded by 'X'.
     *
     * A region is captured by flipping all 'O's into 'X's in that surrounded region.
     *
     * Input: board = [["X","X","X","X"],["X","O","O","X"],["X","X","O","X"],["X","O","X","X"]]
     * Output: [["X","X","X","X"],["X","X","X","X"],["X","X","X","X"],["X","O","X","X"]]
     *
     * Constraints:
     *
     * m == board.length
     * n == board[i].length
     * 1 <= m, n <= 200
     * board[i][j] is 'X' or 'O'.
     * @param board
     */
    // time = O(m * n), space = O(m * n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public void solve(char[][] board) {
        // corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return;

        int row = board.length, col = board[0].length;
        Queue<Integer> queue = new LinkedList<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if ((i == 0 || i == row - 1 || j == 0 || j == col - 1) && board[i][j] == 'O') {
                    queue.offer(i * col + j);
                    board[i][j] = 'Y';
                    bfs(board, queue);
                }
            }
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                else if (board[i][j] == 'Y') board[i][j] = 'O';
            }
        }
    }

    private void bfs(char[][] board, Queue<Integer> queue) {
        int row = board.length, col = board[0].length;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            int i = cur / col, j = cur % col;
            for (int[] dir : DIRECTIONS) {
                int ii = i + dir[0];
                int jj = j + dir[1];
                if (ii >= 0 && ii < row && jj >= 0 && jj < col && board[ii][jj] == 'O') {
                    queue.offer(ii * col + jj);
                    board[ii][jj] = 'Y';
                }
            }
        }
    }
}
/**
 * 直接在沿海地带找，有O的话肯定不会被X包围，反着来
 */
