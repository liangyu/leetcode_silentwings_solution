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
    public int lengthOfLIS(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            res = Math.max(res, dp[i]);
        }
        return res;
    }

    // S2: BS (最优解)
    // time = O(nlogn), space = O(n)
    public int lengthOfLIS2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        List<Integer> buffer = new ArrayList<>();

        for (int x : nums) {
            int idx = upperBound(buffer, x);
            if (idx == buffer.size()) buffer.add(x);
            else buffer.set(idx, x);
        }
        return buffer.size();
    }
    // find the 1st pos >= target
    private int upperBound(List<Integer> buffer, int target) {
        // corner case
        if (buffer.size() == 0) return 0;
        int left = 0, right = buffer.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (buffer.get(mid) < target) left = mid + 1;
            else right = mid;
        }
        return buffer.get(left) >= target ? left : left + 1;
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

    // S3: TreeSet
    // time = O(nlogn), space = O(n)
    public int lengthOfLIS4(int[] nums) {
        TreeSet<Integer> set = new TreeSet<>();

        for (int x : nums) {
            Integer ck = set.ceiling(x);
            if (ck != null) set.remove(ck);
            set.add(x);
        }
        return set.size();
    }
}
/**
 * dp 第二类dp基本型 O(n^2)
 * 无后效性: 看到i的时候，脑子里就只有0~i，其他都不用过，i 后面的东西不影响你的dp[i]
 * dp[i]: the length of longest increasing subsequence for nums[0:i] ending with nums[i]
 * nums[i]肯定是老大，那老二是谁呢？哪个的手下最多，就挑谁是老二。
 *
 * S2: BS， 有点贪心的思想
 * 1 2 9 10    4
 * 1 2 4 5 6 7 8
 * 4不会影响当前结果，但是它有个用处，如果以后出现比4大但是比9小的数字的话
 * 希望现在LIS尽量小
 * 利用现在的元素取尽量小
 * 维护一个递增的序列，如果有新的数，不能让LIS更长的话，那我就用它来降低，使LIS变得更矮
 */