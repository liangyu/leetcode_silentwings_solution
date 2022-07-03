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
        int total = 0;
        for (int x : nums) total += x;
        if (total % k != 0) return false;
        boolean[] visited = new boolean[nums.length];
        Arrays.sort(nums);
        int i = 0, j = nums.length - 1;
        while (i < j) swap(nums, i++, j--);
        return dfs(nums, 0, 0, 0, total / k, k, visited);
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    private boolean dfs(int[] nums, int cur, int sum, int group, int side, int k, boolean[] visited) {
        if (group == k) return true;
        if (sum > side) return false;
        if (sum == side) return dfs(nums, 0, 0, group + 1, side, k, visited);

        int last = -1;
        for (int i = cur; i < nums.length; i++) {
            if (visited[i]) continue;
            if (nums[i] == last) continue;
            visited[i] = true;
            last = nums[i];
            if (dfs(nums, i + 1, sum + nums[i], group, side, k, visited)) return true;
            visited[i] = false;
            if (sum == 0 || sum + nums[i] == side) return false;
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
 * 1. 从大到小枚举：搜索分支数少，更快的搜的越深，剪枝越快
 * 2. nums[i] == nums[i - 1] 并且失败，当前也一定失败
 * 3.当前第一个数失败了，一定失败
 * 4. 当前最后一个数失败了，一定失败
 */