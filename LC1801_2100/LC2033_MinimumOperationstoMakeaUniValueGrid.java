package LC1801_2100;
import java.util.*;
public class LC2033_MinimumOperationstoMakeaUniValueGrid {
    /**
     * You are given a 2D integer grid of size m x n and an integer x. In one operation, you can add x to or subtract
     * x from any element in the grid.
     *
     * A uni-value grid is a grid where all the elements of it are equal.
     *
     * Return the minimum number of operations to make the grid uni-value. If it is not possible, return -1.
     *
     * Input: grid = [[2,4],[6,8]], x = 2
     * Output: 4
     *
     * Constraints:
     *
     * m == grid.length
     * n == grid[i].length
     * 1 <= m, n <= 10^5
     * 1 <= m * n <= 10^5
     * 1 <= x, grid[i][j] <= 10^4
     * @param grid
     * @param x
     * @return
     */
    // S1: Math
    // time = O(m * n * log(m * n)), space = O(m * n)
    public int minOperations(int[][] grid, int x) {
        // corner case
        if (grid == null || grid.length == 0 || grid[0] == null || grid[0].length == 0) return 0;

        int m = grid.length, n = grid[0].length;
        int[] nums = new int[m * n];
        int idx = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                nums[idx++] = grid[i][j];
            }
        }

        Arrays.sort(nums);
        int k = nums.length, res = 0;
        int median = nums[k / 2];
        for (int num : nums) {
            int diff = Math.abs(num - median);
            if (diff % x != 0) return -1;
            res += diff / x;
        }
        return res;
    }

    // S2: Math
    // time = O(m * n * log(m * n)), space = O(m * n)
    public int minOperations2(int[][] grid, int x) {
        int m = grid.length, n = grid[0].length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                min = Math.min(min, grid[i][j]);
            }
        }

        List<Integer> nums = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if ((grid[i][j] - min) % x != 0) return -1;
                nums.add(grid[i][j]);
            }
        }
        Collections.sort(nums);

        int k = nums.size();
        int res = 0;
        for (int a : nums) {
            res += Math.abs(a - nums.get(k / 2)) / x;
        }
        return res;
    }
}
/**
 * 所有值与最小值之差一定是x的倍数
 * 找一个最小的解
 * 与矩阵无关，直接可以拍扁
 * minimize op(2 -> y) + op(4 -> y) + op(6 -> y) + op(8 -> y)
 * minimize |2 - y| + |4 - y| + |6 - y| + |8 - y| + |9 - y|
 * 中位数定理，原型就是LC296
 * arr = {2,4,6,8}, 距离之和相等
 * y = median(arr)
 * 中位数定理：中位数到所有元素的距离之和是最小的
 * 这个函数只有一个极值点
 */