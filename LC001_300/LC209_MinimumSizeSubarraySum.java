package LC001_300;
import java.util.*;
public class LC209_MinimumSizeSubarraySum {
    /**
     * Given an array of positive integers nums and a positive integer target, return the minimal length of a contiguous
     * subarray [numsl, numsl+1, ..., numsr-1, numsr] of which the sum is greater than or equal to target. If there is
     * no such subarray, return 0 instead.
     *
     * Input: target = 7, nums = [2,3,1,2,4,3]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= target <= 10^9
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     *
     *
     * Follow up: If you have figured out the O(n) solution, try coding another solution of which the time complexity is
     * O(n log(n)).
     * @param target
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int minSubArrayLen(int target, int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, res = Integer.MAX_VALUE, sum = 0, start = 0;

        for (int i = 0; i < n; i++) {
            sum += nums[i];
            while (start <= i && sum >= target) {
                res = Math.min(res, i - start + 1);
                sum -= nums[start++];
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    // S2: TreeMap
    // time = o(nlogn), space = O(n)
    public int minSubArrayLen2(int target, int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        TreeMap<Integer, Integer> map = new TreeMap<>();
        int sum = 0, n = nums.length, res = n + 1;
        for (int i = 0; i < n; i++) { // O(n)
            sum += nums[i];
            Integer fk = map.floorKey(sum - target); // o(logn)
            if (fk != null) {
                int start = map.get(fk);
                res = Math.min(res, i - start);
            }
            map.put(sum, i);
        }
        return res == n + 1 ? 0 : res;
    }

    // S3: B.S.
    // time = O(nlogn), space = O(n)
    public int minSubArrayLen3(int target, int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] sum = new int[n + 1];

        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
        }

        for (int i = 1; i <= n; i++) {
            int find = sum[i - 1] + target; // 向右找上界的那个前缀和
            int idx = helper(sum, 0, sum.length - 1, find);
            if (idx != - 1) res = Math.min(res, idx - i + 1);
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    private int helper(int[] sum, int start, int end, int target) {
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (sum[mid] < target) start = mid + 1;
            else end = mid - 1; // 找到左边界，这样去和i相减才能获得最短距离
        }
        return start == sum.length ? -1 : start; // if start == sum.length，表明end没动过，start直接越过end出界，没找到！
    }
}