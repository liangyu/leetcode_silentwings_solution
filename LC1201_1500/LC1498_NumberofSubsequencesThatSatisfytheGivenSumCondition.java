package LC1201_1500;
import java.util.*;
public class LC1498_NumberofSubsequencesThatSatisfytheGivenSumCondition {
    /**
     * Given an array of integers nums and an integer target.
     *
     * Return the number of non-empty subsequences of nums such that the sum of the minimum and maximum element on it
     * is less or equal to target. Since the answer may be too large, return it modulo 10^9 + 7.
     *
     * Input: nums = [3,5,6,7], target = 9
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^6
     * 1 <= target <= 10^6
     * @param nums
     * @param target
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int numSubseq(int[] nums, int target) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        Arrays.sort(nums);

        int n = nums.length;
        long M = (long)(1e9 + 7);

        long[] power = new long[n + 1];
        Arrays.fill(power, 1);
        for (int i = 1; i <= n; i++) {
            power[i] = power[i - 1] * 2 % M;
        }

        int j = n - 1;
        long res = 0;
        for (int i = 0; i < n; i++) {
            while (j >= i && nums[i] + nums[j] > target) j--;
            if (j < i) break;
            res = (res + power[j - i]) % M;
        }
        return (int)res;
    }
}
/**
 * subsequence: 不连续
 * subarray 一段连续的子区间，只要考虑左右边界即可，中间都是连续的，中间区间情况就很清楚了
 * subsequence 中间元素可选可不选
 * 这道题特别的地方：只关心subsequence最小和最大值，中间并不关心，可选可不选
 * 关键：选什么数顺序并不重要！！！顺序并没有太大的意义 => 可排序！排序前后结果是一模一样的。
 * 排序好的数组中，不管怎么选min和max，min在前面，max在后面这样的规律
 * 先固定一个左边界，看是否有一个高效的办法确定右边界
 * 左边界 = 3， target = 9 => 右边界 <= 6  -> 5和6可选可不选
 * 碰到比较大的情况，右指针往左边移
 * O(n)时间确定右边界
 * x x x x x x x
 *   i     j
 * 相向双指针，i永远朝右边移动，而j永远朝左边移动 => O(n)
 * ret += 2^(j - i)
 * 这道题本质与subarray很像，因为它只与左右边界有关，中间只要取power即可。
 */