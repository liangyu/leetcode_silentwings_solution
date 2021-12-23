package LC601_900;

public class LC807_MaxIncreasetoKeepCitySkyline {
    /**
     * There is a city composed of n x n blocks, where each block contains a single building shaped like a vertical
     * square prism. You are given a 0-indexed n x n integer matrix grid where grid[r][c] represents the height of the
     * building located in the block at row r and column c.
     *
     * A city's skyline is the the outer contour formed by all the building when viewing the side of the city from a
     * distance. The skyline from each cardinal direction north, east, south, and west may be different.
     *
     * We are allowed to increase the height of any number of buildings by any amount (the amount can be different per
     * building). The height of a 0-height building can also be increased. However, increasing the height of a building
     * should not affect the city's skyline from any cardinal direction.
     *
     * Return the maximum total sum that the height of the buildings can be increased by without changing the city's
     * skyline from any cardinal direction.
     *
     * Input: grid = [[3,0,8,4],[2,4,5,7],[9,2,6,3],[0,3,1,0]]
     * Output: 35
     *
     * Constraints:
     *
     * n == grid.length
     * n == grid[r].length
     * 2 <= n <= 50
     * 0 <= grid[r][c] <= 100
     * @param grid
     * @return
     */
    // time = O(n^2), space = O(n)
    public int maxIncreaseKeepingSkyline(int[][] grid) {
        int n = grid.length;
        int[] rm = new int[n], cm = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rm[i] = Math.max(rm[i], grid[i][j]); // the max row for each row
                cm[j] = Math.max(cm[j], grid[i][j]); // the max height for each col
            }
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res += Math.min(rm[i], cm[j]) - grid[i][j];
            }
        }
        return res;
    }
}
/**
 * 从左侧和右侧看，城市天际线等于矩阵 grid 的每一行的建筑物高度最大值；
 * 从顶部和底部看，城市天际线等于矩阵 grid 的每一列的建筑物高度最大值。
 * 只要不改变每一行和每一列的建筑物高度最大值，就能保持城市天际线，因此可以使用贪心的思想计算建筑物高度可以增加的最大总和。
 * 为了保持城市天际线，该建筑物增加后的高度不能超过其所在行和所在列的建筑物高度最大值，
 * 即该建筑物增加后的最大高度是min(rowMax[i],colMax[j])。
 * 由于该建筑物的原始高度是 grid[i][j]，因此该建筑物高度可以增加的最大值是min(rowMax[i],colMax[j])−grid[i][j]。
 */
