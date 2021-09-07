package LC1801_2100;
import java.util.*;
public class LC1981_MinimizetheDifferenceBetweenTargetandChosenElements {
    /**
     * You are given an m x n integer matrix mat and an integer target.
     *
     * Choose one integer from each row in the matrix such that the absolute difference between target and the sum of
     * the chosen elements is minimized.
     *
     * Return the minimum absolute difference.
     *
     * The absolute difference between two numbers a and b is the absolute value of a - b.
     *
     * Input: mat = [[1,2,3],[4,5,6],[7,8,9]], target = 13
     * Output: 0
     *
     * Constraints:
     *
     * m == mat.length
     * n == mat[i].length
     * 1 <= m, n <= 70
     * 1 <= mat[i][j] <= 70
     * 1 <= target <= 800
     * @param mat
     * @param target
     * @return
     */
    // time = O(m * n), space = O(m * n)
    private int res = Integer.MAX_VALUE;
    public int minimizeTheDifference(int[][] mat, int target) {
        // corner case
        if (mat == null || mat.length == 0 || mat[0] == null || mat[0].length == 0) return 0;

        int m = mat.length, n = mat[0].length;
        boolean[][] dp = new boolean[m][5000];
        helper(mat, dp, 0, 0, target);
        return res;
    }

    private void helper(int[][] mat, boolean[][] dp, int row, int sum, int target) {
        int m = mat.length, n = mat[0].length;
        if (dp[row][sum]) return;
        if (row == m - 1) {
            for (int j = 0; j < n; j++) {
                res = Math.min(res, Math.abs(sum + mat[row][j] - target));
            }
            dp[row][sum] = true;
            return;
        }

        for (int j = 0; j < n; j++) {
            helper(mat, dp, row + 1, sum + mat[row][j], target);
        }
        dp[row][sum] = true;
    }

    // S2：backpack
    // time = O(m * n), space = O(m * n)
    public int minimizeTheDifference2(int[][] mat, int target) {
        int m = mat.length, n = mat[0].length;

        HashSet<Integer> cap = new HashSet<>();
        cap.add(0); // 目前什么都没选，总重量是0，这是个种子

        for (int i = 0; i < m; i++) { // O(m)
            HashSet<Integer> temp = new HashSet<>();
            int great = -1;
            for (int x : cap) {
                for (int y : mat[i]) {
                    if (x + y <= target) temp.add(x + y);
                    else {
                        if (great == -1 || x + y < great) great = x + y;
                    }
                }
            }
            if (great != -1) temp.add(great);
            cap.clear();
            for (int num : temp) cap.add(num);
        }
        int diff = Integer.MAX_VALUE;
        for (int x : cap) {
            diff = Math.min(diff, Math.abs(x - target));
        }
        return diff;
    }
}
/**
 * 背包问题，容量比较小
 * 1 <= mat[i][j] <= 70  => 总容量不会超过4900
 * 背包问题解空间上有优势
 * 考虑占据容量的可能性
 * 1，2，3
 * 5，6，7， 6，7，8， 7，8，9  -> 相当多重复的元素 => 5,6,7,8,9
 * 任选9种可能性
 * 12，13，14， 13，14，15， 14，15，16， 15，16，17， 16，17，18
 * => 12,13,14,15,16,17,18
 * 暴力搜索，空间不一样
 * m个回合，每个回合两两组合，容量：所选物品的总和 ~ 4900
 * m * 4900 * 70 = 2*10^7 -> 怎么优化？
 * 1 <= target <= 800
 * 允许超过target，但是离target越近越好，比如805永远都会比810更优秀，如果target是800
 * 70 * 801 * 70 = 3924900 = 3 * 10^6
 * dp[capacity]
 */