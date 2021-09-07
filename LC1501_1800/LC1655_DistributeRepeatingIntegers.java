package LC1501_1800;
import java.util.*;
public class LC1655_DistributeRepeatingIntegers {
    /**
     * You are given an array of n integers, nums, where there are at most 50 unique values in the array. You are also
     * given an array of m customer order quantities, quantity, where quantity[i] is the amount of integers the ith
     * customer ordered. Determine if it is possible to distribute nums such that:
     *
     * The ith customer gets exactly quantity[i] integers,
     * The integers the ith customer gets are all equal, and
     * Every customer is satisfied.
     * Return true if it is possible to distribute nums according to the above conditions.
     *
     * Input: nums = [1,2,3,4], quantity = [2]
     * Output: false
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^5
     * 1 <= nums[i] <= 1000
     * m == quantity.length
     * 1 <= m <= 10
     * 1 <= quantity[i] <= 10^5
     * There are at most 50 unique values in nums.
     * @param nums
     * @param quantity
     * @return
     */
    // time = O(n * 3^m), space = O(n * 2^m)
    public boolean canDistribute(int[] nums, int[] quantity) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : nums) map.put(x, map.getOrDefault(x, 0) + 1);

        List<Integer> count = new ArrayList<>();
        for (int key : map.keySet()) count.add(map.get(key));

        int n = count.size(), m = quantity.length;
        count.add(0, 0); // 有意义的从1开始

        boolean[][] dp = new boolean[n + 1][1 << m];
        for (int i = 0; i <= n; i++) dp[i][0] = true;

        for (int i = 1; i <= n; i++) { // O(n)
            for (int state = 1; state < (1 << m); state++) { // O(3^m) 一共三种状态
                // update dp[i][state]
                if (dp[i - 1][state]) dp[i][state] = true;
                for (int subset = state; subset > 0; subset = (subset - 1) & state) {
                    if (!dp[i - 1][state - subset]) continue;
                    if (canSatisfySubset(count.get(i), quantity, subset)) {
                        dp[i][state] = true;
                        break;
                    }
                }
            }
        }
        return dp[n][(1 << m) - 1];
    }

    private boolean canSatisfySubset(int count, int[] quantity, int subset) {
        int m = quantity.length, sum = 0;
        for (int i = 0; i < m; i++) {
            if (((subset >> i) & 1) == 1) sum += quantity[i];
        }
        return count >= sum;
    }
}
/**
 * nums[i] -> count[i]: how many objects for the i-th object
 * quantity[i]: how many same objects required by the i-th customer
 * bitmask: 2^m => all the states that present who are satisfied
 * dp[i][state]: whether we can satisfy customers of state by using the first i objects
 * 50 * 2^m = 50 * 1024
 * ans = dp[n][(1 << m) - 1]
 * dp[i][state] = true if
 * => we can use count[i] to satisfy a subset of state
 * => we can use the first i-1 objects to satisfy state - subset => dp[i - 1][state - subset] == true
 * count[i] >= quantity[subset_1]+quantity[subset_2]+...
 * dp[i][state]为true的两个条件是：
 * 1. 我们能找到state里面的一个子集subset，其对应的客户需求的物品总数都能被第i件物品所满足，
 * 2. 对应的前驱状态dp[i-1][state-subset]也为true，即前i-1件物品能满足state-subset所对应的客户（即除去第i件物品满足的subset客户）。
 * 特别需要注意的是，dp[i][state]为true其实还有另外一种方式：就是如果dp[i-1][state]已经为true的话，我们甚至可以跳过subset的搜索。
 * => O(3^m) 一共三种状态：
 * 1. 在state和subset里面
 * 2. 在state，但不在subset里
 * 3. 不在state里
 */
