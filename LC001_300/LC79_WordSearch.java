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
    private int[][] directions = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    public boolean exist(char[][] board, String word) {
        int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, i, j, visited, word, 0)) return true;
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, int x, int y, boolean[][] visited, String word, int curPos) {
        int m = board.length, n = board[0].length;
        // base case
        if (curPos == word.length()) return true;
        if (x < 0 || x >= m || y < 0 || y >= n) return false;
        if (visited[x][y]) return false;
        if (board[x][y] != word.charAt(curPos)) return false;

        visited[x][y] = true;
        for (int[] dir : directions) {
            int i = x + dir[0];
            int j = y + dir[1];
            if (dfs(board, i, j, visited, word, curPos + 1)) return true;
        }
        visited[x][y] = false;
        return false;
    }
}
