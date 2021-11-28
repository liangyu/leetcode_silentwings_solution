package LC1801_2100;

public class LC2078_TwoFurthestHousesWithDifferentColors {
    /**
     * There are n houses evenly lined up on the street, and each house is beautifully painted. You are given a
     * 0-indexed integer array colors of length n, where colors[i] represents the color of the ith house.
     *
     * Return the maximum distance between two houses with different colors.
     *
     * The distance between the ith and jth houses is abs(i - j), where abs(x) is the absolute value of x.
     *
     * Input: colors = [1,1,1,6,1,1,1]
     * Output: 3
     *
     * Constraints:
     *
     * n == colors.length
     * 2 <= n <= 100
     * 0 <= colors[i] <= 100
     * Test data are generated such that at least two houses have different colors.
     * @param colors
     * @return
     */
    // time = O(n^2), space = O(1)
    public int maxDistance(int[] colors) {
        int n = colors.length;

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j > i; j--) {
                if (colors[i] != colors[j]) {
                    res = Math.max(res, j - i);
                    break;
                }
            }
        }
        return res;
    }
}
