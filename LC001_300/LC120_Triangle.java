package LC001_300;
import java.util.*;
public class LC120_Triangle {
    /**
     * Given a triangle array, return the minimum path sum from top to bottom.
     *
     * For each step, you may move to an adjacent number of the row below. More formally, if you are on index i on the
     * current row, you may move to either index i or index i + 1 on the next row.
     *
     * Input: triangle = [[2],[3,4],[6,5,7],[4,1,8,3]]
     * Output: 11
     *
     * Constraints:
     *
     * 1 <= triangle.length <= 200
     * triangle[0].length == 1
     * triangle[i].length == triangle[i - 1].length + 1
     * -10^4 <= triangle[i][j] <= 10^4
     *
     *
     * Follow up: Could you do this using only O(n) extra space, where n is the total number of rows in the triangle?
     * @param triangle
     * @return
     */
    // time = O(n^2), space = O(n)
    public int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] dp = new int[n];
        dp[0] = triangle.get(0).get(0);

        for (int i = 1; i < n; i++) {
            int[] temp = dp.clone();
            for (int j = 0; j <= i; j++) {
                if (j == 0) {
                    dp[j] = temp[0] + triangle.get(i).get(j);
                } else if (j == i) {
                    dp[j] = temp[i - 1] + triangle.get(i).get(j);
                } else {
                    dp[j] = Math.min(temp[j - 1], temp[j]) + triangle.get(i).get(j);
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            res = Math.min(dp[i], res);
        }
        return res;
    }
}
