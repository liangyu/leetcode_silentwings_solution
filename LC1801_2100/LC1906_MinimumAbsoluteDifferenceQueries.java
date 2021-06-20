package LC1801_2100;
import java.util.*;
public class LC1906_MinimumAbsoluteDifferenceQueries {
    /**
     * The minimum absolute difference of an array a is defined as the minimum value of |a[i] - a[j]|, where
     * 0 <= i < j < a.length and a[i] != a[j]. If all elements of a are the same, the minimum absolute difference is -1.
     *
     * For example, the minimum absolute difference of the array [5,2,3,7,2] is |2 - 3| = 1. Note that it is not 0
     * because a[i] and a[j] must be different.
     * You are given an integer array nums and the array queries where queries[i] = [li, ri]. For each query i, compute
     * the minimum absolute difference of the subarray nums[li...ri] containing the elements of nums between the 0-based
     * indices li and ri (inclusive).
     *
     * Return an array ans where ans[i] is the answer to the ith query.
     *
     * A subarray is a contiguous sequence of elements in an array.
     *
     * The value of |x| is defined as:
     *
     * x if x >= 0.
     * -x if x < 0.
     *
     * Input: nums = [1,3,4,8], queries = [[0,1],[1,2],[2,3],[0,3]]
     * Output: [2,1,4,1]
     *
     * Constraints:
     *
     * 2 <= nums.length <= 10^5
     * 1 <= nums[i] <= 100
     * 1 <= queries.length <= 2 * 10^4
     * 0 <= li < ri < nums.length
     * @param nums
     * @param queries
     * @return
     */
    // time = O(m + n), space = O(m + n)
    public int[] minDifference(int[] nums, int[][] queries) {
        int[] res = new int[queries.length];
        int[][] prefix = new int[nums.length + 1][101];
        int[] count = new int[101];
        for (int i = 0; i < nums.length; i++) {
            count[nums[i]]++;
            for (int j = 1; j <= 100; j++) prefix[i + 1][j] = count[j];
        }

        for (int i = 0; i < queries.length; i++) {
            int start = queries[i][0], end = queries[i][1];
            int[] temp = new int[101];
            for (int j = 0; j < 101; j++) temp[j] = prefix[end + 1][j] - prefix[start][j];
            int prev = 0, minAbs = Integer.MAX_VALUE;
            for (int j = 1; j < 101; j++) {
                if (temp[j] == 0) continue;
                if (prev > 0 && j - prev < minAbs) minAbs = j - prev;
                prev = j;
            }
            res[i] = (minAbs == Integer.MAX_VALUE ? -1 : minAbs);
        }
        return res;
    }
}
