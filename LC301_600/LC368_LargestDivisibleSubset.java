package LC301_600;
import java.util.*;
public class LC368_LargestDivisibleSubset {
    /**
     * Given a set of distinct positive integers nums, return the largest subset answer such that every pair
     * (answer[i], answer[j]) of elements in this subset satisfies:
     *
     * answer[i] % answer[j] == 0, or
     * answer[j] % answer[i] == 0
     * If there are multiple solutions, return any of them.
     *
     * Input: nums = [1,2,3]
     * Output: [1,2]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 1 <= nums[i] <= 2 * 10^9
     * All the integers in nums are unique.
     * @param nums
     * @return
     */
    // time = O(n^2), space = O(n)
    public List<Integer> largestDivisibleSubset(int[] nums) {
        List<Integer> res = new LinkedList<>();
        // corner case
        if (nums == null || nums.length == 0) return res;

        Arrays.sort(nums);
        int n = nums.length;
        int[] dp = new int[n]; // dp[i]: considering nums[0:i], the largest devisible subset ending with nums[i]
        int[] prev = new int[n];
        Arrays.fill(dp, 1); // 任何元素都可以自己作为一个subset
        Arrays.fill(prev, -1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] % nums[j] == 0) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    if (dp[i] == dp[j] + 1) {
                        prev[i] = j;
                    }
                }
            }
        }

        int max = 0, idx = -1;
        for (int i = 0; i < n; i++) {
            if (dp[i] > max) {
                max = dp[i];
                idx = i;
            }
        }

        while (idx != -1) {
            res.add(0, nums[idx]);
            idx = prev[idx]; // 回推
        }
        return res;
    }
}
/**
 * 2 3 4 9 36
 * 36接在3后面
 * 36也可以接在9后面
 * 找subset，从小到大排一些
 * 选定4作为第二大元素后，4和36之间就不能选了
 * 基本型II dp -> 今天的状态跟前面某个状态有关，找个最优的状态
 */