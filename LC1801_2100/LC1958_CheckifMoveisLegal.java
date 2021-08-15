package LC1801_2100;

public class LC1958_CheckifMoveisLegal {
    /**
     * You are given a 0-indexed 8 x 8 grid board, where board[r][c] represents the cell (r, c) on a game board. On the
     * board, free cells are represented by '.', white cells are represented by 'W', and black cells are represented
     * by 'B'.
     *
     * Each move in this game consists of choosing a free cell and changing it to the color you are playing as
     * (either white or black). However, a move is only legal if, after changing it, the cell becomes the endpoint
     * of a good line (horizontal, vertical, or diagonal).
     *
     * A good line is a line of three or more cells (including the endpoints) where the endpoints of the line are
     * one color, and the remaining cells in the middle are the opposite color (no cells in the line are free).
     * You can find examples for good lines in the figure below:
     *
     * Given two integers rMove and cMove and a character color representing the color you are playing as (white or
     * black), return true if changing cell (rMove, cMove) to color color is a legal move, or false if it is not legal.
     *
     * Input: board = [[".",".",".","B",".",".",".","."],[".",".",".","W",".",".",".","."],
     * [".",".",".","W",".",".",".","."],[".",".",".","W",".",".",".","."],["W","B","B",".","W","W","W","B"],
     * [".",".",".","B",".",".",".","."],[".",".",".","B",".",".",".","."],[".",".",".","W",".",".",".","."]],
     * rMove = 4, cMove = 3, color = "B"
     * Output: true
     *
     * Constraints:
     *
     * board.length == board[r].length == 8
     * 0 <= rMove, cMove < 8
     * board[rMove][cMove] == '.'
     * color is either 'B' or 'W'.
     * @param board
     * @param rMove
     * @param cMove
     * @param color
     * @return
     */
    // time = O(n), space = O(n)
    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}, {-1, 1}, {1, -1}, {-1, -1}, {1, 1}};
    public boolean checkMove(char[][] board, int rMove, int cMove, char color) {
        // corner case
        if (board == null || board.length == 0 || board[0] == null || board[0].length == 0) return false;

        int n = board.length;
        char newColor = color == 'W' ? 'B' : 'W';
        for (int[] dir : DIRECTIONS) {
            int i = rMove + dir[0];
            int j = cMove + dir[1];
            if (i < 0 || i >= n || j < 0 || j >= n || board[i][j] != newColor) continue;
            else if (dfs(board, i + dir[0], j + dir[1], color, dir[0], dir[1])) return true;
        }
        return false;
    }

    private boolean dfs(char[][] board, int i, int j, int color, int r, int c) {
        int n = board.length;
        if (i < 0 || i >= n || j < 0 || j >= n || board[i][j] == '.') return false;
        if (board[i][j] == color) return true;
        return dfs(board, i + r, j + c, color, r, c); // r and c are kept to record the direction it is heading to
    }
}
