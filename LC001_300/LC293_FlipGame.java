package LC001_300;
import java.util.*;
public class LC293_FlipGame {
    /**
     * You are playing a Flip Game with your friend.
     *
     * You are given a string currentState that contains only '+' and '-'. You and your friend take turns to flip two
     * consecutive "++" into "--". The game ends when a person can no longer make a move, and therefore the other
     * person will be the winner.
     *
     * Return all possible states of the string currentState after one valid move. You may return the answer in any
     * order. If there is no valid move, return an empty list [].
     *
     * Input: currentState = "++++"
     * Output: ["--++","+--+","++--"]
     *
     * Constraints:
     *
     * 1 <= currentState.length <= 500
     * currentState[i] is either '+' or '-'.
     * @param currentState
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public List<String> generatePossibleNextMoves(String currentState) {
        List<String> res = new ArrayList<>();
        // corner case
        if (currentState == null || currentState.length() < 2) return res;

        int n = currentState.length();
        char[] arr = currentState.toCharArray();
        for (int i = 1; i < n; i++) {
            if (arr[i - 1] == '+' && arr[i] == '+') {
                arr[i - 1] = '-';
                arr[i] = '-';
                res.add(String.valueOf(arr));
                arr[i] = '+';
                arr[i - 1] = '+';
            }
        }
        return res;
    }

    // S2
    // time = O(n), space = O(n)
    public List<String> generatePossibleNextMoves2(String s) {
        List<String> res = new ArrayList<>();
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == '+' && s.charAt(i - 1) == '+') {
                res.add(s.substring(0, i - 1) + "--" + s.substring(i + 1, s.length()));
            }
        }
        return res;
    }
}
