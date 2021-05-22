package LC1801_2100;
import java.util.*;
public class LC1852_DistinctNumbersinEachSubarray {
    /**
     * Given an integer array nums and an integer k, you are asked to construct the array ans of size n-k+1 where
     * ans[i] is the number of distinct numbers in the subarray nums[i:i+k-1] = [nums[i], nums[i+1], ..., nums[i+k-1]].
     *
     * Return the array ans.
     *
     * Input: nums = [1,2,3,2,2,1,3], k = 3
     * Output: [3,2,2,2,3]
     *
     * Constraints:
     *
     * 1 <= k <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param nums
     * @param k
     * @return
     */
    // time = O(n), space = O(n)
    public int[] distinctNumbers(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return new int[0];

        int n = nums.length;
        int[] res = new int[n - k + 1];

        int start = 0, i = 0, idx = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        while (i < n) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            while (i - start + 1 < k) {
                i++;
                map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            }
            res[idx++] = map.size();
            if (map.get(nums[start]) - 1 == 0) map.remove(nums[start]);
            else map.put(nums[start], map.get(nums[start]) - 1);
            i++;
            start++;
        }
        return res;
    }
}
