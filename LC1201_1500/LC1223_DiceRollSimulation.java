package LC1201_1500;

public class LC1223_DiceRollSimulation {
    /**
     * A die simulator generates a random number from 1 to 6 for each roll. You introduced a constraint to the generator
     * such that it cannot roll the number i more than rollMax[i] (1-indexed) consecutive times.
     *
     * Given an array of integers rollMax and an integer n, return the number of distinct sequences that can be
     * obtained with exact n rolls. Since the answer may be too large, return it modulo 10^9 + 7.
     *
     * Two sequences are considered different if at least one element differs from each other.
     *
     * Input: n = 2, rollMax = [1,1,2,2,2,3]
     * Output: 34
     *
     * Constraints:
     *
     * 1 <= n <= 5000
     * rollMax.length == 6
     * 1 <= rollMax[i] <= 15
     * @param n
     * @param rollMax
     * @return
     */
    // time = O(n), space = O(n)
    public int dieSimulator(int n, int[] rollMax) {
        int[][][] dp = new int[n][6][16];
        int M = (int)(1e9 + 7);

        // init
        for (int j = 0; j < 6; j++) dp[0][j][1] = 1;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 6; j++) {
                for (int k = 1; k <= rollMax[j]; k++) {
                    if (k > 1) dp[i][j][k] = dp[i - 1][j][k - 1];
                    else {
                        for (int jj = 0; jj < 6; jj++) {
                            if (jj == j) continue; // excluding dp[i-1][j][?]
                            for (int kk = 1; kk <= rollMax[jj]; kk++) {
                                dp[i][j][k] += dp[i - 1][jj][kk];
                                dp[i][j][k] %= M;
                            }
                        }
                    }
                }
            }
        }

        int res = 0;
        for (int j = 0; j < 6; j++) {
            for (int k = 1; k <= rollMax[j]; k++) {
                res += dp[n - 1][j][k];
                res %= M;
            }
        }
        return res;
    }
}
/**
 * dp[i][j] = sum(dp[i-1][j']) j'!=j
 * dp[i][j][k]: 第i个元素是j，并且j已经连续出现k次
 * dp[i][6][2] = dp[i-1][6][1]
 * dp[i][6][1] = dp[i-1][1][?] ...dp[i-1][5][?] excluding dp[i-1][6][?]
 */