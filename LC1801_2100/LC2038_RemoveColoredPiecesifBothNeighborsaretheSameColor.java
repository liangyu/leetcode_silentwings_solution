package LC1801_2100;

public class LC2038_RemoveColoredPiecesifBothNeighborsaretheSameColor {
    /**
     * There are n pieces arranged in a line, and each piece is colored either by 'A' or by 'B'. You are given a string
     * colors of length n where colors[i] is the color of the ith piece.
     *
     * Alice and Bob are playing a game where they take alternating turns removing pieces from the line. In this game,
     * Alice moves first.
     *
     * Alice is only allowed to remove a piece colored 'A' if both its neighbors are also colored 'A'. She is not
     * allowed to remove pieces that are colored 'B'.
     * Bob is only allowed to remove a piece colored 'B' if both its neighbors are also colored 'B'. He is not allowed
     * to remove pieces that are colored 'A'.
     * Alice and Bob cannot remove pieces from the edge of the line.
     * If a player cannot make a move on their turn, that player loses and the other player wins.
     * Assuming Alice and Bob play optimally, return true if Alice wins, or return false if Bob wins.
     *
     * Input: colors = "AAABABB"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= colors.length <= 10^5
     * colors consists of only the letters 'A' and 'B'
     * @param colors
     * @return
     */
    // time = O(n), space = O(1)
    public boolean winnerOfGame(String colors) {
        int n = colors.length(), countA = 0, countB = 0, i = 0;
        while (i < n) {
            int j = i;
            if (colors.charAt(j) == 'A') {
                while (j < n && colors.charAt(j) == 'A') j++;
                int count = j - i;
                if (count > 2) countA += count - 2;
            } else {
                while (j < n && colors.charAt(j) == 'B') j++;
                int count = j - i;
                if (count > 2) countB += count - 2;
            }
            i = j;
        }
        return countA > countB ? true : false;
    }
}
