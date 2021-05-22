package LC601_900;
import java.util.*;
public class LC750_NumberOfCornerRectangles {
    /**
     * Given a grid where each entry is only 0 or 1, find the number of corner rectangles.
     *
     * A corner rectangle is 4 distinct 1s on the grid that form an axis-aligned rectangle. Note that only the corners
     * need to have the value 1. Also, all four 1s used must be distinct.
     *
     * Input: grid =
     * [[1, 0, 0, 1, 0],
     *  [0, 0, 1, 0, 1],
     *  [0, 0, 0, 1, 0],
     *  [1, 0, 1, 0, 1]]
     * Output: 1
     *
     * Input: grid =
     * [[1, 1, 1],
     *  [1, 1, 1],
     *  [1, 1, 1]]
     * Output: 9
     *
     * Note:
     *
     * The number of rows and columns of grid will each be in the range [1, 200].
     * Each grid[i][j] will be either 0 or 1.
     * The number of 1s in the grid will be at most 6000.
     *
     * @param grid
     * @return
     */
    // S1: Count Corners
    // time = O(m^2 * n), space = O(1)
    public int countCornerRectangles(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        int res = 0;

        for (int i = 0; i < m; i++) {
            for (int j = i + 1; j < m; j++) { // 观察两行之间，同一列上有无都是1的corner pair，有的话就+1
                int count = 0;
                for (int k = 0; k < n; k++) {
                    if (grid[i][k] == 1 && grid[j][k] == 1) {
                        count++; // 有一对corner pair就 + 1，然后将这些corner pair排列组合
                    }
                }
                res += count * (count - 1) / 2; // 如果是一对的话，无法形成长方形，符合条件，答案为0；2对的话正好是1
            }
        }
        return res;
    }

    // S2: DP
    // time = O(m * n^2), space = O(n^2) 最快的解法！！！
    public int countCornerRectangles2(int[][] grid) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[n][n];
        int res = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) continue; // 如果是0，不可能形成要求的长方形，直接pass
                for (int k = j + 1; k < n; k++) {
                    if (grid[i][k] == 1) {
                        res += dp[j][k]; // res加上上一行(i - 1)时的corner pair(j, k)的数目，如果是起始行，那么显然是0
                        dp[j][k]++; // 累加上当前行的corner pair(j, k)，因此 + 1
                    }
                }
            }
        }
        return res;
    }
}

/**
 * S1: Count Corners
 * To find an axis-aligned rectangle, my idea is to fix two rows (or two columns) first,
 * then check column by column to find "1" on both rows.
 * Say you find n pairs, then just pick any 2 of those to form an axis-aligned rectangle
 * (calculating how many in total is just high school math, hint: combination).
 *
 * S2: DP
 * According to the question description, as long as we have two corner points of the upper line of a rectangle
 * and corresponding two corner points of the bottom line, it forms a rectangle.
 * So we can scan each row of the input matrix, every time we find two corner points in this row
 * and treat them as the bottom line of a rectangle, we try to find how many upper lines it can have
 * in the previous scanned lines. Thus, we use a 2D array dp[n][n] to track the number of pairs of corner points
 * before the current row with position in i, j, then the number of rectangles with a bottom line made up of
 * the current two corner points will be dp[i][j]. After that, update dp[i][j] by incrementing one.
 */

