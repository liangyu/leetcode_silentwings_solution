package LC1801_2100;

public class LC2018_CheckifWordCanBePlacedInCrossword {
    /**
     * You are given an m x n matrix board, representing the current state of a crossword puzzle. The crossword contains
     * lowercase English letters (from solved words), ' ' to represent any empty cells, and '#' to represent any blocked
     * cells.
     *
     * A word can be placed horizontally (left to right or right to left) or vertically (top to bottom or bottom to top)
     * in the board if:
     *
     * It does not occupy a cell containing the character '#'.
     * The cell each letter is placed in must either be ' ' (empty) or match the letter already on the board.
     * There must not be any empty cells ' ' or other lowercase letters directly left or right of the word if the word
     * was placed horizontally.
     * There must not be any empty cells ' ' or other lowercase letters directly above or below the word if the word
     * was placed vertically.
     * Given a string word, return true if word can be placed in board, or false otherwise.
     *
     * Input: board = [["#", " ", "#"], [" ", " ", "#"], ["#", "c", " "]], word = "abc"
     * Output: true
     *
     * Constraints:
     *
     * m == board.length
     * n == board[i].length
     * 1 <= m * n <= 2 * 10^5
     * board[i][j] will be ' ', '#', or a lowercase English letter.
     * 1 <= word.length <= max(m, n)
     * word will contain only lowercase English letters.
     * @param board
     * @param word
     * @return
     */
    // time = O(m * n^2), space = O(m * n)
    public boolean placeWordInCrossword(char[][] board, String word) {
        // corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return false;

        int m = board.length, n = board[0].length;
        char[][] board2 = new char[n][m];
        for (int j = 0; j < n; j++)
            for (int i = 0; i < m; i++) {
                board2[j][i] = board[i][j];
        }
        return helper(board, word) || helper(board2, word);
    }

    private boolean helper(char[][] board, String word) {
        for (char[] chars : board) {
            String[] strs = String.valueOf(chars).split("#");
            StringBuilder sb = new StringBuilder(word);
            String revWord = sb.reverse().toString();
            String[] words = new String[]{word, revWord};
            for (String w : words) {
                for (String s : strs) {
                    if (s.length() == w.length()) {
                        int idx = 0;
                        while (idx < s.length() && (s.charAt(idx) == ' ' || s.charAt(idx) == w.charAt(idx))) idx++;
                        if (idx == s.length()) return true;
                    }
                }
            }
        }
        return false;
    }

    // S2
    // time = O(m * n * k), space = O(1)
    private int[][] dir;
    public boolean placeWordInCrossword2(char[][] board, String word) {
        // corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return false;

        dir = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] != ' ' && board[i][j] != word.charAt(0)) continue;
                for (int k = 0; k < 4; k++) {
                    int x = i - dir[k][0];
                    int y = j - dir[k][1];
                    if (x >= 0 && x < m && y >= 0 && y < n && board[x][y] != '#') continue;
                    if (match(board, i, j, k, word)) return true;
                }
            }
        }
        return false;
    }

    private boolean match(char[][] board, int i, int j, int k, String word) {
        int m = board.length, n = board[0].length;
        for (int t = 0; t < word.length(); t++) {
            int x = i + dir[k][0] * t;
            int y = j + dir[k][1] * t;
            if (x < 0 || x >= m || y < 0 || y >= n) return false;
            if (board[x][y] != word.charAt(t) && board[x][y] != ' ') return false;
        }
        int t = word.length();
        int x = i + dir[k][0] * t;
        int y = j + dir[k][1] * t;
        if (x >= 0 && x < m && y >= 0 && y < n && board[x][y] != '#') return false;
        return true;
    }
}
/**
 * 暴力解
 * 顶天立地完整匹配一个word
 * 网格并不是特别大
 * 并不需要很多重复的搜索
 * starting points
 * 1. stand by the boundary or by a "#"
 * 2. itself must be word[0] or " "
 * 3. search direction must be opposite to the boundary or "#"
 *
 * matching requirement
 * 1. match word, allowing " "
 * 2. after matching, move forward by one step and be in the boundary or "#"
 */
