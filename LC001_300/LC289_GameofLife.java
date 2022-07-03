package LC001_300;
import java.util.*;
public class LC289_GameofLife {
    /**
     * The board is made up of an m x n grid of cells, where each cell has an initial state: live (represented by a 1)
     * or dead (represented by a 0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal)
     * using the following four rules (taken from the above Wikipedia article):
     *
     * Any live cell with fewer than two live neighbors dies as if caused by under-population.
     * Any live cell with two or three live neighbors lives on to the next generation.
     * Any live cell with more than three live neighbors dies, as if by over-population.
     * Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     *
     * The next state is created by applying the above rules simultaneously to every cell in the current state, where
     * births and deaths occur simultaneously. Given the current state of the m x n grid board, return the next state.
     *
     * Constraints:
     *
     * m == board.length
     * n == board[i].length
     * 1 <= m, n <= 25
     * board[i][j] is 0 or 1.
     *
     * @param board
     */
    // time = O(m * n), space = O(1)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, -1}, {1, 1,}, {-1, 1}, {1, -1}};
    public void gameOfLife(int[][] board) {
        // corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return;

        int row = board.length, col = board[0].length;

        // 1st iteration: modify the changing locations into intermediate value
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int live = 0;
                for (int[] dir : DIRECTIONS) {
                    int ii = i + dir[0];
                    int jj = j + dir[1];
                    if (ii < 0 || ii >= row || jj < 0 || jj >= col) continue;
                    // 0 -> 1 record with -1, if 1 -> 0 then record with 2 as intermediate value
                    if (board[ii][jj] == 1 || board[ii][jj] == 2) live++; // 2表示变化前改位置是1，代表有一个live cell
                }
                if (board[i][j] == 0) { // current cell is dead
                    if (live == 3) board[i][j] = -1; // 0 -> 1
                } else { // current cell is alive
                    if (live < 2 || live > 3) board[i][j] = 2; // 1 -> 0
                }
            }
        }

        // 2nd iteration: -1 -> 1, 2 -> 0
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == -1) board[i][j] = 1;
                else if (board[i][j] == 2) board[i][j] = 0;
            }
        }
    }

    // S2
    // time = O(m * n), space = O(1)
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
    public void gameOfLife2(int[][] board) {
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int live = 0;
                for (int[] dir : directions) {
                    int x = i + dir[0];
                    int y = j + dir[1];
                    if (x < 0 || x >= m || y < 0 || y >= n) continue;
                    if ((board[x][y] & 1) == 1) live++;
                }

                int next = 0;
                if (board[i][j] == 0) {
                    if (live == 3) next = 1;
                    else next = 0;
                } else {
                    if (live < 2 || live > 3) next = 0;
                    else next = 1;
                }
                board[i][j] |= next << 1;
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] >>= 1;
            }
        }
    }
}

/**
 * 2D board [matrix] 问题的2个技巧：
 * 1. matrix中某个位置状态如果发生改变，那么解法一般就是两次遍历整个matrix。第一次遍历时，用一个不可能出现在原matrix中的中间值来保存状态
 * 的变化(这样在此次遍历过程中，不会影响到其他位置的判断)；第二遍遍历时，把中间值刷新成变化后应该变成的值。本题中，由于要in-place操作，所以
 * 选择合适的中间状态值很关键，在这里 0 -> 1 我们选择用-1，而1 -> 0 我们则选择用2来表示。
 * 2. 遍历到某个位置时，需要查看它周边的位置，这个时候用int[][] DIRECTIONS来处理坐标偏移值就很方便。
 */
