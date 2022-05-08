package LC2101_2400;

public class LC2209_MinimumWhiteTilesAfterCoveringWithCarpets {
    /**
     * You are given a 0-indexed binary string floor, which represents the colors of tiles on a floor:
     *
     * floor[i] = '0' denotes that the ith tile of the floor is colored black.
     * On the other hand, floor[i] = '1' denotes that the ith tile of the floor is colored white.
     * You are also given numCarpets and carpetLen. You have numCarpets black carpets, each of length carpetLen tiles.
     * Cover the tiles with the given carpets such that the number of white tiles still visible is minimum. Carpets may
     * overlap one another.
     *
     * Return the minimum number of white tiles still visible.
     *
     * Input: floor = "10110101", numCarpets = 2, carpetLen = 2
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= carpetLen <= floor.length <= 1000
     * floor[i] is either '0' or '1'.
     * 1 <= numCarpets <= 1000
     * @param floor
     * @param numCarpets
     * @param carpetLen
     * @return
     */
    // S1: dp
    // time = O(m * n), space = O(m * n)
    public int minimumWhiteTiles(String floor, int numCarpets, int carpetLen) {
        int n = floor.length(), m = numCarpets, k = carpetLen;
        if (m * k >= n) return 0;

        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + (floor.charAt(i - 1) == '1' ? 1 : 0);
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                dp[i][j] = dp[i - 1][j];
                if (i >= k) dp[i][j] = Math.max(dp[i][j], dp[i - k][j - 1] + presum[i] - presum[i - k]);
            }
        }
        return presum[n] - dp[n][m];
    }

    // S2: dp
    // time = O(m * n), space = O(m * n)
    public int minimumWhiteTiles2(String floor, int numCarpets, int carpetLen) {
        int n = floor.length(), m = numCarpets, k = carpetLen;
        if (m * k >= n) return 0;

        int[][] dp = new int[n + 1][m + 1];

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                dp[i][j] = Integer.MAX_VALUE / 2;
                dp[i][j] = Math.min(dp[i][j], dp[i - 1][j] + (floor.charAt(i - 1) == '1' ? 1 : 0));
                if (j >= 1) dp[i][j] = Math.min(dp[i][j], i - k < 0 ? 0 : dp[i - k][j - 1]);
            }
        }
        return dp[n][m];
    }
}
/**
 * 1000 * 1000 => O(m * n)
 * dp[i][j]: the minimum number white tiles still visible for the first i tiles and using j carpets
 * dp[i][j]: =>
 * 1. the i-th tile is covered by the j-th carpet dp[i-len][j-1]
 * 2. the i-th tile is not covered by the j-th carpet dp[i-1][j] + 1/0
 * => min
 */