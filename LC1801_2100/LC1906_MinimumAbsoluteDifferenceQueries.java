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
    // S1
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

    // S2
    // time = O(100 * (m + n)) = O(m + n), space = O(m + n)
    public int[] minDifference2(int[] nums, int[][] queries) {
        int n = nums.length;
        int[][] presum = new int[101][n];

        for (int k = 1; k <= 100; k++) {
            for (int i = 0; i < n; i++) {
                presum[k][i] = (i == 0 ? 0 : presum[k][i - 1]) + (nums[i] == k ? 1 : 0) ;
            }
        }

        int[] res = new int[queries.length];
        for (int j = 0; j < queries.length; j++) {
            int left = queries[j][0], right = queries[j][1];
            List<Integer> arr = new ArrayList<>();
            for (int k = 1; k <= 100; k++) {
                int count = presum[k][right] - (left == 0 ? 0 : presum[k][left - 1]);
                if (count > 0) arr.add(k);
                if (arr.size() > 2 && arr.get(arr.size() - 1) - arr.get(arr.size() - 2) == 1) break;
            }
            if (arr.size() <= 1) res[j] = -1;
            else {
                int gap = Integer.MAX_VALUE;
                for (int i = 1; i < arr.size(); i++) {
                    gap = Math.min(gap, arr.get(i) - arr.get(i - 1));
                }
                res[j] = gap;
            }
        }
        return res;
    }
}
/**
 * nums[i] <= 100  遍历元素的值
 * x x x [x x x x x] x x x
 * 问一个区间里元素出现的频次，2种方法：
 * 1. segment tree -》 O(n) + O(logn)
 * 2. frequency presum => O(n) + O(1) -> presum[right] - presum[left-1]
 * 100 * n
 * Q*100
 * time = 100*(Q+N)
 * 没有nums[i] <= 100 就变成codeForce F 3100分 -> use 主席树 time = Q*(logN)^2
 */
