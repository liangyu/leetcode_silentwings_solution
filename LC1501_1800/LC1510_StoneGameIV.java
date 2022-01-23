package LC1501_1800;
import java.util.*;
public class LC1510_StoneGameIV {
    /**
     * Alice and Bob take turns playing a game, with Alice starting first.
     *
     * Initially, there are n stones in a pile.  On each player's turn, that player makes a move consisting of removing
     * any non-zero square number of stones in the pile.
     *
     * Also, if a player cannot make a move, he/she loses the game.
     *
     * Given a positive integer n. Return True if and only if Alice wins the game otherwise return False, assuming both
     * players play optimally.
     *
     * Input: n = 1
     * Output: true
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * @param n
     * @return
     */
    // S1: recursion + pruning
    // time = O(n * n^(1/2)), space = O(n)
    Boolean[] dp;
    public boolean winnerSquareGame(int n) {
        dp = new Boolean[n + 1];
        return solve(n);
    }

    private boolean solve(int n) {
        if (dp[n] != null) return dp[n];

        for (int i = 1; i * i <= n; i++) {
            if (!solve(n - i * i)) {
                dp[n] = true;
                return true;
            }
        }
        dp[n] = false;
        return false;
    }

    // S2: dp
    // time = O(n * n^(1/2)), space = O(n)
    public boolean winnerSquareGame2(int n) {
        boolean[] dp = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                if (!dp[i - j * j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }
}
/**
 * n: n-1, n-4, n-9, ...
 * dp[n] => dp[n-1], dp[n-4], ...
 * 试图找到一个让对手必输的决策，否则对手必赢
 */
