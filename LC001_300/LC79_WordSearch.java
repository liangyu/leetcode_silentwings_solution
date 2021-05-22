package LC001_300;
import java.util.*;
public class LC79_WordSearch {
    /**
     * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
     *
     * The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally
     * or vertically neighboring. The same letter cell may not be used more than once.
     *
     * Input: board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"
     * Output: true
     *
     * Constraints:
     *
     * m == board.length
     * n = board[i].length
     * 1 <= m, n <= 6
     * 1 <= word.length <= 15
     * board and word consists of only lowercase and uppercase English letters.
     *
     *
     * Follow up: Could you use search pruning to make your solution faster with a larger board?
     * @param board
     * @param word
     * @return
     */
    // time = O(m * n * 3^k), space = O(m * n) * k
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public boolean exist(char[][] board, String word) {
        // corner case
        if (board == null || board.length == 0) return false;
        if (word == null || word.length() == 0) return true;

        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, i, j, word, 0, visited)) return true;
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, int i, int j, String word, int idx, boolean[][] visited) {
        int m = board.length, n = board[0].length;
        // base case - success
        if (idx == word.length()) return true;

        // base case - fail
        if (i < 0 || i >= m || j < 0 || j >= n || visited[i][j] || board[i][j] != word.charAt(idx)) {
            return false;
        }

        boolean res = false;
        visited[i][j] = true;
        for (int[] dir : DIRECTIONS) {
            int ii = i + dir[0];
            int jj = j + dir[1];
            res = res || dfs(board, ii, jj, word, idx + 1, visited);
        }
        visited[i][j] = false;
        return res;
    }
}
