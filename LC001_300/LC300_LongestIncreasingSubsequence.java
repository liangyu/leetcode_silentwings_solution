package LC001_300;
import java.util.*;
public class LC300_LongestIncreasingSubsequence {
    /**
     * Given an unsorted array of integers, find the length of longest increasing subsequence.
     *
     * Example:
     *
     * Input: [10,9,2,5,3,7,101,18]
     * Output: 4
     * Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.
     *
     * Note:
     *
     * There may be more than one LIS combination, it is only necessary for you to return the length.
     * Your algorithm should run in O(n2) complexity.
     * Follow up: Could you improve it to O(n log n) time complexity?
     *
     * @param nums
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n)
    public int lengthOfLIS1(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int len = nums.length;
        int[] dp = new int[len]; // dp[i]: [0, i] len of LIS
        Arrays.fill(dp, 1);
        int max = 1;

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    // S2: BS (最优解)
    // time = O(nlogn), space = O(n)
    public int lengthOfLIS(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) {
            return 0;
        }

        List<Integer> buffer = new ArrayList<>();
        int len = nums.length;
        for (int i = 0; i < len; i++) {  // --> O(n)
            int idx = getIdx(buffer, nums[i]);
            if (idx == buffer.size()) buffer.add(nums[i]);
            else buffer.set(idx, nums[i]);
        }
        return buffer.size();
    }

    private int getIdx(List<Integer> buffer, int target) {
        int start = 0, end = buffer.size() - 1;
        while (start <= end) { // --> O(logn)
            int mid = start + (end - start) / 2;
            if (buffer.get(mid) == target) return mid;
            else if (buffer.get(mid) < target) start = mid + 1;
            else end = mid - 1;
        }
        return start;
    }

    // S2.2: BS (prefer!)
    // time = O(nlogn), space = O(n)
    public int lengthOfLIS3(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] buffer = new int[n];

        int p = 0; // 当前LIS的长度
        for (int i = 0; i < n; i++) {
            int left = 0, right = p;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (buffer[mid] < nums[i]) left = mid + 1;
                else right = mid;
            }
            if (left == p) p++;
            buffer[left] = nums[i];
        }
        return p;
    }
}
