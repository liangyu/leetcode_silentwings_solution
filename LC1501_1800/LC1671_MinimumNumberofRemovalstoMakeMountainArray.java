package LC1501_1800;
import java.util.*;
public class LC1671_MinimumNumberofRemovalstoMakeMountainArray {
    /**
     * You may recall that an array arr is a mountain array if and only if:
     *
     * arr.length >= 3
     * There exists some index i (0-indexed) with 0 < i < arr.length - 1 such that:
     * arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
     * arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
     * Given an integer array nums, return the minimum number of elements to remove to make nums a mountain array.
     *
     * Input: nums = [1,3,1]
     * Output: 0
     *
     * Input: nums = [2,1,1,5,6,2,3,1]
     * Output: 3
     *
     * Constraints:
     *
     * 3 <= nums.length <= 1000
     * 1 <= nums[i] <= 10^9
     * It is guaranteed that you can make a mountain array out of nums.
     * @param nums
     * @return
     */
    // time = O(n^2), space = O(n)
    public int minimumMountainRemovals(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(left, 1);
        Arrays.fill(right, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    left[i] = Math.max(left[i], left[j] + 1);
                }
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = n - 1; j > i; j--) {
                if (nums[j] < nums[i]) {
                    right[i] = Math.max(right[i], right[j] + 1);
                }
            }
        }

        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            if (left[i] >= 2 && right[i] >= 2) {
                maxLen = Math.max(maxLen, left[i] + right[i] - 1);
            }
        }
        return n - maxLen;
    }

    // S2:
    // time = O(nlogn), space = O(n)
    public int minimumMountainRemovals2(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        int[] right = new int[n];
        Arrays.fill(left, 1);
        Arrays.fill(right, 1);

        List<Integer> buffer = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int idx = helper(buffer, nums[i]);
            if (idx == buffer.size()) buffer.add(nums[i]);
            else buffer.set(idx, nums[i]);
            left[i] = idx + 1;
        }

        buffer.clear();
        for (int i = n - 1; i >= 0; i--) {
            int idx = helper(buffer, nums[i]);
            if (idx == buffer.size()) buffer.add(nums[i]);
            else buffer.set(idx, nums[i]);
            right[i] = idx + 1;
        }

        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            if (left[i] >= 2 && right[i] >= 2) {
                maxLen = Math.max(maxLen, left[i] + right[i] - 1);
            }
        }
        return n - maxLen;
    }

    private int helper(List<Integer> buffer, int t) {
        if (buffer.size() == 0) return 0;

        int left = 0, right = buffer.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (buffer.get(mid) < t) left = mid + 1;
            else right = mid;
        }
        return buffer.get(left) >= t ? left : left + 1;
    }
}
/**
 * mountain array -> 只有一个尖峰
 * if 5 is peak，在5前面找一个递增序列，而且递增序列越长越好；在5的右边找一个递减序列，或者从右往左递增序列，长度越长越好
 * consider nums[i] as the peak
 * array length = leftLIS[i] + rightLIS[i] - 1
 * 如果高效计算LIS？
 * O(n^2) -> dp 无后效性，每一个前缀都能算出来
 * x x x x i x x x
 * leftLIS[i]
 * dp[i] = max(dp[j] + 1)   for all nums[j] < nums[i]
 * O(nlogn)
 * maintain an increasing stack
 * nums = 1 3 (5) 7 4  => 1 3 4 7
 * 1 3 5 7 -> 1 3 4 7
 * 对后面有好处，高度降低 -> 更有利于后面LIS增长
 */