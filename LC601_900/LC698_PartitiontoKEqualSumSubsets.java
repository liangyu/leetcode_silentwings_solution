package LC601_900;
import java.util.*;
public class LC698_PartitiontoKEqualSumSubsets {
    /**
     * Given an integer array nums and an integer k, return true if it is possible to divide this array into k
     * non-empty subsets whose sums are all equal.
     *
     * Input: nums = [4,3,2,3,5,2,1], k = 4
     * Output: true
     *
     * Constraints:
     *
     * 1 <= k <= nums.length <= 16
     * 1 <= nums[i] <= 10^4
     * The frequency of each element is in the range [1, 4].
     * @param nums
     * @param k
     * @return
     */
    // time = O(n * 2^n), space = O(n * 2^n)
    public boolean canPartitionKSubsets(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0 || k <= 0) return false;

        Arrays.sort(nums);
        int n = nums.length, total = 0;
        for (int num : nums) total += num;
        if (total % k != 0) return false;

        boolean[] visited = new boolean[n];
        return dfs(nums, 0, 0, 0, total, k, visited);
    }

    private boolean dfs(int[] nums, int cur, int group, int sum, int total, int k, boolean[] visited) {
        // base case
        if (group == k) return true;
        if (sum > total / k) return false;
        if (sum == total / k) {
            return dfs(nums, 0, group + 1, 0, total, k, visited);
        }

        int last = -1;
        for (int i = cur; i < nums.length; i++) {
            if (visited[i]) continue;
            if (nums[i] == last) continue; // 剪枝
            visited[i] = true;
            last = nums[i];
            if (dfs(nums, i + 1, group, sum + nums[i], total, k, visited)) return true;
            visited[i] = false;
        }
        return false;
    }
}
/**
 * 暴力搜索，NP问题
 * 大致2个方向：DFS，state compression (优点：比较方便穷举, 缺点：不太容易剪枝)
 * 用状态压缩很难做，k-subset，01比特位很难，用类似枚举子集的办法，又变成dfs了，状态压缩不用想了
 * 用dfs更直观
 * 搜子集，从第一个元素看起，可选可不选
 * 标准dfs
 * 1,2,2,2,2,2,2,3,4
 * 有很多一样的元素 => 回溯，如果2走不通，那么后面再选2一样走不通 => 都浪费了，剪枝剪掉
 * 排序 => 相同元素总是相邻的
 * 小优化：倒序 => 大元素在前，更容易 > total / k而尽早剪枝
 * [1, ]
 */