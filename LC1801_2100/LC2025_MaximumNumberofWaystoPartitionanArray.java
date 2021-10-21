package LC1801_2100;
import java.util.*;
public class LC2025_MaximumNumberofWaystoPartitionanArray {
    /**
     * You are given a 0-indexed integer array nums of length n. The number of ways to partition nums is the number
     * of pivot indices that satisfy both conditions:
     *
     * 1 <= pivot < n
     * nums[0] + nums[1] + ... + nums[pivot - 1] == nums[pivot] + nums[pivot + 1] + ... + nums[n - 1]
     * You are also given an integer k. You can choose to change the value of one element of nums to k, or to leave the
     * array unchanged.
     *
     * Return the maximum possible number of ways to partition nums to satisfy both conditions after changing at most
     * one element.
     *
     * Input: nums = [2,-1,2], k = 3
     * Output: 1
     *
     * Constraints:
     *
     * n == nums.length
     * 2 <= n <= 10^5
     * -10^5 <= k, nums[i] <= 10^5
     * @param nums
     * @param k
     * @return
     */
    // S1: Prefix + Hash
    // time = O(n), space = O(n)
    public int waysToPartition(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            presum[i] = presum[i - 1] + nums[i - 1];
            if (i < n ) map.put(presum[i], map.getOrDefault(presum[i], 0) + 1);
        }

        // no change
        int max = 0;
        if (presum[n] % 2 == 0) max = map.getOrDefault(presum[n] / 2, 0);

        HashMap<Integer, Integer> prev = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int diff = k - nums[i];
            int newSum = presum[n] + diff;
            if (newSum % 2 == 0) {
                max = Math.max(max, prev.getOrDefault(newSum / 2, 0) + map.getOrDefault(newSum / 2 - diff, 0));
            }
            map.put(presum[i + 1], map.getOrDefault(presum[i + 1], 0) - 1);
            prev.put(presum[i + 1], prev.getOrDefault(presum[i + 1], 0) + 1);
        }
        return max;
    }

    // S2: Prefix + Hash
    // time = O(n), space = O(n)
    public int waysToPartition2(int[] nums, int k) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        long sum = 0;
        for (int num : nums) sum += num;

        long[] pre = new long[n];
        pre[0] = nums[0];
        for (int i = 1; i < n; i++) pre[i] = pre[i - 1] + nums[i];

        long[] suf = new long[n];
        suf[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) suf[i] = suf[i + 1] + nums[i];

        int[] res = new int[n];
        HashMap<Long, Integer> map = new HashMap<>(); // presum value -> freq

        // 从前往后
        for (int i = 0; i < n; i++) { // 遍历每个改动点
            long new_sum = sum - nums[i] + k;
            if (new_sum % 2 == 0) res[i] += map.getOrDefault(new_sum / 2, 0);
            map.put(pre[i], map.getOrDefault(pre[i], 0) + 1);
        }

        map.clear();
        // 从后往前
        for (int i = n - 1; i >= 0; i--) { // 遍历每个改动点
            long new_sum = sum - nums[i] + k;
            if (new_sum % 2 == 0) res[i] += map.getOrDefault(new_sum / 2, 0);
            map.put(suf[i], map.getOrDefault(suf[i], 0) + 1);
        }

        int res0 = 0;
        for (int i = 0; i < n - 1; i++) {
            if (sum % 2 == 0 && pre[i] == sum / 2) res0++;
        }

        int ans = res0;
        for (int i = 0; i < n; i++) ans = Math.max(ans, res[i]);
        return ans;
    }
}
/**
 * x x x x x | x x x x
 * 找个点，使得前缀和 == 后缀和
 * 但这里后缀和是完全依赖于前缀和，因为总的sum事固定的，total sum已经有了 => 只要看前缀和是否为total sum / 2
 * x x x y x x x x x   new_sum
 * 并不是所有的presum都要更新
 * 在y前面的presum没有变化，用原来之前的就可以了。如果切分点在y的左边，就完全不用看新的presum，看有多少个可以切分的位置
 * 也有可能切分点在右边，这种情况下，只要看它的后缀和是不是new_sum的一半，有的话就对应一种切分方法
 * 所以永远只要看老的前缀和后缀和，不需要更新
 * one pass,每个i都check一遍 => O(n)
 * 前缀和与hash的组合使用
 */