package LC1501_1800;

public class LC1688_CountofMatchesinTournament {
    /**
     * You are given an integer n, the number of teams in a tournament that has strange rules:
     *
     * If the current number of teams is even, each team gets paired with another team. A total of n / 2 matches are
     * played, and n / 2 teams advance to the next round.
     * If the current number of teams is odd, one team randomly advances in the tournament, and the rest gets paired.
     * A total of (n - 1) / 2 matches are played, and (n - 1) / 2 + 1 teams advance to the next round.
     * Return the number of matches played in the tournament until a winner is decided.
     *
     * Input: n = 7
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= n <= 200
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int numberOfMatches(int n) {
        int count = 0;
        while (n > 1) {
            if (n % 2 == 1) {
                count += (n - 1) / 2;
                n = (n - 1) / 2 + 1;
            } else {
                count += n / 2;
                n /= 2;
            }
        }
        return count;
    }
}
