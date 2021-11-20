package LC301_600;

public class LC488_ZumaGame {
    /**
     * You are playing a variation of the game Zuma.
     *
     * In this variation of Zuma, there is a single row of colored balls on a board, where each ball can be colored
     * red 'R', yellow 'Y', blue 'B', green 'G', or white 'W'. You also have several colored balls in your hand.
     *
     * Your goal is to clear all of the balls from the board. On each turn:
     *
     * Pick any ball from your hand and insert it in between two balls in the row or on either end of the row.
     * If there is a group of three or more consecutive balls of the same color, remove the group of balls from the board.
     * If this removal causes more groups of three or more of the same color to form, then continue removing each group
     * until there are none left.
     * If there are no more balls on the board, then you win the game.
     * Repeat this process until you either win or do not have any more balls in your hand.
     * Given a string board, representing the row of balls on the board, and a string hand, representing the balls in
     * your hand, return the minimum number of balls you have to insert to clear all the balls from the board. If you
     * cannot clear all the balls from the board using the balls in your hand, return -1.
     *
     * Input: board = "WRRBBW", hand = "RB"
     * Output: -1
     *
     * Constraints:
     *
     * 1 <= board.length <= 16
     * 1 <= hand.length <= 5
     * board and hand consist of the characters 'R', 'Y', 'B', 'G', and 'W'.
     * The initial row of balls on the board will not have any groups of three or more consecutive balls of the same color.
     * @param board
     * @param hand
     * @return
     */
    public int findMinStep(String board, String hand) {
        dfs(board, hand);
    }
}
/**
 * dfs
 */
