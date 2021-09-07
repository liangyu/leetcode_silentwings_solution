package LC1801_2100;
import java.util.*;
public class LC1838_FrequencyoftheMostFrequentElement {
    /**
     * The frequency of an element is the number of times it occurs in an array.
     *
     * You are given an integer array nums and an integer k. In one operation, you can choose an index of nums and
     * increment the element at that index by 1.
     *
     * Return the maximum possible frequency of an element after performing at most k operations.
     *
     * Input: nums = [1,2,4], k = 5
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * 1 <= k <= 10^5
     * @param nums
     * @param k
     * @return
     */
    // S1: Sliding Window
    // time = O(nlogn), space = O(1)
    public int maxFrequency(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        Arrays.sort(nums);

        int max = 0, start = 0;
        long sum = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            while (nums[i] * (i - start + 1) - sum > k) { // 超出了k的范围，则收缩左边界，否则继续扩大右边界
                sum -= nums[start];
                start++;
            }
            max = Math.max(max, i - start + 1);
        }
        return max;
    }

    // S1.2: Two Pointers 第二种写法
    // time = O(nlogn), space = O(1)
    public int maxFrequency2(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return 0;

        Arrays.sort(nums);
        int j = 0;
        int count = 0;
        int ret = 1; // 至少频次为1
        for (int i = 1; i < nums.length; i++) {
            count += (i - j) * (nums[i] - nums[i - 1]);
            while (count > k) {
                count -= nums[i] - nums[j];
                j++;
            }
            ret = Math.max(ret, i - j + 1);
        }
        return ret;
    }
}
/**
 * [1, 2, (3), 4]
 * 操作后最终得到的最高频率元素一定是数组中既有的元素
 * 元素前面紧邻的元素变成该元素 -> 滑窗
 *  x x [j x x i-1 i i+1] x x
 *  nums[i] => [j:i], count
 *  nums[i + 1] => [jj: i + 1], count += (i - j + 1) * (nums[i + 1] - nums[i])
 *                              count -= nums[i + 1] - nums[j]
 *  排序 + 双指针
 *  x x [j x x x i-1 i]
 *
 */


