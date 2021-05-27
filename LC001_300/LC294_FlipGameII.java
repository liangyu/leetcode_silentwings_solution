package LC001_300;
import java.util.*;
public class LC294_FlipGameII {
    /**
     * You are playing a Flip Game with your friend.
     *
     * You are given a string currentState that contains only '+' and '-'. You and your friend take turns to flip two
     * consecutive "++" into "--". The game ends when a person can no longer make a move, and therefore the other person
     * will be the winner.
     *
     * Return true if the starting player can guarantee a win, and false otherwise.
     *
     * Input: currentState = "++++"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= currentState.length <= 60
     * currentState[i] is either '+' or '-'.
     * @param currentState
     * @return
     */
    // S1: DFS
    // time = O(2^(n - 1)), space = O(n)
    public boolean canWin(String currentState) {
        // corner case
        if (currentState == null || currentState.length() < 2) return false;

        char[] board = currentState.toCharArray();
        return dfs(board);
    }

    private boolean dfs(char[] board) {
        for (int i = 0; i < board.length - 1; i++){
            if (board[i] == '+' && board[i + 1] == '+') {
                board[i] = '-';
                board[i + 1] = '-';
                boolean res = dfs(board);
                board[i + 1] = '+';
                board[i] = '+';
                if (!res) return true;
            }
        }
        return false;
    }

    // S2: DFS + pruning
    // time = O(2^(n - 1)), space = O(n)
    public boolean canWin2(String currentState) {
        // corner case
        if (currentState == null || currentState.length() < 2) return false;

        char[] board = currentState.toCharArray();
        HashMap<String, Boolean> map = new HashMap<>();
        return helper(board, map);
    }

    private boolean helper(char[] board, HashMap<String, Boolean> map) {
        for (int i = 0; i < board.length - 1; i++){
            Boolean res = map.get(String.valueOf(board));
            if (res != null) return res;

            if (board[i] == '+' && board[i + 1] == '+') {
                board[i] = '-';
                board[i + 1] = '-';
                res = helper(board, map);
                board[i + 1] = '+';
                board[i] = '+';
                if (!res) {
                    map.put(String.valueOf(board), true);
                    return true;
                }
            }
        }
        map.put(String.valueOf(board), false);
        return false;
    }
}
