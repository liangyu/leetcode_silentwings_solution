package LC301_600;
import java.util.*;
public class LC523_ContinuousSubarraySum {
    /**
     * Given a list of non-negative numbers and a target integer k, write a function to check if the array has a
     * continuous subarray of size at least 2 that sums up to a multiple of k, that is, sums up to n*k where n is
     * also an integer.
     *
     * Input: [23, 2, 4, 6, 7],  k=6
     * Output: True
     *
     * Constraints:
     *
     * The length of the array won't exceed 10,000.
     * You may assume the sum of all the numbers is in the range of a signed 32-bit integer.
     *
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public boolean checkSubarraySum(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length <= 1) return false;
        if (k == 0) {
            for (int i = 0; i < nums.length - 1; i++) {
                if (nums[i] == 0 && nums[i + 1] == 0) return true;
            }
            return false;
        }

        int preSum1 = 0, preSum2 = 0; // preSum1加到j - 2为止, preSum1加到j为止，两者要对k同余！
        HashSet<Integer> set = new HashSet<>();
        set.add(0);

        for (int j = 0; j < nums.length; j++) {
            preSum2 += nums[j];
            if (j >= 2) {
                preSum1 += nums[j - 2];
                set.add(preSum1 % k);
            }
            if (j >= 1 && set.contains(preSum2 % k)) return true;
        }
        return false;
    }
}

/**
 * 大方向：subarray的和转化为2个prefix sum之差，非常常见！
 * sum(s[i:j]) = presum[j] - presum[i - 1]   => O(1)
 * 2个前缀和之差 => 这两个前缀和要对k同余
 * 至少含有2个元素 => i >= j + 2
 * [0, 1, ...... j - 2]  j - 1   j
 */
