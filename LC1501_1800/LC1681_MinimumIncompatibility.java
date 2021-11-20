package LC1501_1800;
import java.util.*;
public class LC1681_MinimumIncompatibility {
    /**
     * You are given an integer array nums and an integer k. You are asked to distribute this array into k subsets of
     * equal size such that there are no two equal elements in the same subset.
     *
     * A subset's incompatibility is the difference between the maximum and minimum elements in that array.
     *
     * Return the minimum possible sum of incompatibilities of the k subsets after distributing the array optimally,
     * or return -1 if it is not possible.
     *
     * A subset is a group integers that appear in the array with no particular order.
     *
     * Input: nums = [1,2,1,4], k = 2
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= k <= nums.length <= 16
     * nums.length is divisible by k
     * 1 <= nums[i] <= nums.length
     * @param nums
     * @param k
     * @return
     */
    // S1: dfs
    // time = O(3^n), space = O(n)
    int res = Integer.MAX_VALUE;
    boolean[] visited;
    int n, k;
    public int minimumIncompatibility(int[] nums, int k) {
        int n = nums.length;
        this.n = n;
        this.k = k;
        visited = new boolean[n];
        int[] count = new int[17];
        for (int x : nums) { // O(n)
            count[x]++;
            if (count[x] > k) return -1;
        }

        Arrays.sort(nums); // O(nlogn)

        visited[0] = true;
        dfs(nums, 0, 1, nums[0], nums[0], 0); // O(3^n)
        return res;
    }

    private void dfs(int[] nums, int idx, int count, int low, int high, int sum) {
        if (count == n / k) {
            int j = 0;
            while (j < n && visited[j]) j++;
            if (j == n) {
                res = Math.min(res, sum + high - low);
                return;
            } else {
                visited[j] = true;
                dfs(nums, j, 1, nums[j], nums[j], sum + high - low);
                visited[j] = false;
            }
        } else {
            int last = -1;
            for (int i = idx + 1; i < n; i++) {
                if (visited[i]) continue;
                if (nums[i] == nums[idx]) continue; // 从小到大排序过了
                if (nums[i] == last) continue;
                visited[i] = true;
                dfs(nums, i, count + 1, low, nums[i], sum);
                last = nums[i];
                visited[i] = false; // setback
            }
        }
    }

    // S2: DP + state compression
    // time = O(3^n), space = O(2^n)
    public int minimumIncompatibility2(int[] nums, int k) {
        int n = nums.length;
        int[] count = new int[17];
        for (int x : nums) { // O(n)
            count[x]++;
            if (count[x] > k) return -1;
        }
        List<Integer> states = new ArrayList<>();
        List<Integer> values = new ArrayList<>();
        // Gospers Hack: iterate all the m-bit state where there k 1-bits
        int state = (1 << n / k) - 1;
        while (state < (1 << n)) {
            int incomp = 0;
            int[] vals = new int[]{state, incomp};
            // do something
            if (!containDuplicate(nums, vals)) {
                states.add(vals[0]);
                values.add(vals[1]);
            }

            int c = state & -state;
            int r = state + c;
            state = (((r ^ state) >> 2) / c) | r;
        }

        // pick up only valid dpstates and discard those unvalid ones
        List<Integer> dpstates = new ArrayList<>();
        for (int dpstate = 0; dpstate < (1 << n); dpstate++) {
            if (Integer.bitCount(dpstate) % (n / k) == 0) dpstates.add(dpstate);
        }
        Collections.sort(dpstates, (o1, o2) -> o2 - o1); // reverse order

        int m = states.size();
        // backpack
        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);
        dp[0] = 0;

        for (int i = 0; i < m; i++) {
            for (int dpstate : dpstates) { // 只保留那些1-bit数时4，8，12...等合法的state
                if ((dpstate & states.get(i)) == states.get(i)) {
                    dp[dpstate] = Math.min(dp[dpstate], dp[dpstate - states.get(i)] + values.get(i));
                }
            }
        }
        return dp[(1 << n) - 1];
    }

    private boolean containDuplicate(int[] nums, int[] vals) {
        List<Integer> p = new ArrayList<>();
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (((vals[0] >> i) & 1) == 1) {
                p.add(nums[i]);
            }
        }
        Collections.sort(p);
        for (int i = 1; i < p.size(); i++) {
            if (p.get(i) == p.get(i - 1)) return true;
        }
        vals[1] = p.get(p.size() - 1) - p.get(0);
        return false;
    }
}
/**
 * 本题初看有一种错觉，以为小的数字应该尽量和小的数字分在一组，大的数字应该尽量和大的数字分在一组。
 * 但是这个贪心策略不成立。
 * 比如：[1,1,2,2,3,3,6,8]，4, 贪心策略得到[1,2],[1,2],[3,6],[3,8]，但是最优的答案是[1,2],[1,3],[2,3],[6,8].
 * 结合本题的提示nums.size()<16，说明这题只能用暴力探索。
 * 首先我们判定一下无解的情况：当任何一个元素出现的次数大于k的话，说明至少有一组会重复出现两次该元素。于是可以提前返回false。
 * 某个元素太多了， 出现k+1次，所以注定会出现一个subset里有重复的元素
 * 在排除无解的情况后，我们就可以放心地用DFS暴力搜索：在数组里连续搜索到n/k个不同的元素后，再从头开始搜索找n/k个不同的元素，直至所有的数组元素都搜到。
 * dfs搜索算法中有一个常见而且非常重要的剪枝技巧。
 * 比如nums = [1,2,2,3,4,6], k=2, 当你搜索完[1,2,x]之后，不会为第二个数再次搜索2。
 * 所以需要在搜索第二个数的时候，用一个last来避免重复搜到相同的数。
 *
 * S2: bitmask + dp
 * C(n,n/k) => C(16,8) = 12870 -> 10^5
 * 最多只有10^5种组合
 * 另外要求每个subset里不能有相同元素,删掉后就没多少了
 * m = C(n,n/k) - # of subsets containing duplicated elements
 * states[]
 * state = 011100000
 * pick k states from states[], st. union of them equals 11..11
 * find the minimum incompatibility for all k states => 01背包问题，m个物品挑k个，有约束，总和要最小
 * dp[i][dpstate]: the minimum sum of incompatibility when picked elements form dpstate by considering the first i elements
 * for (int i = 0; i < m; i++) // pick from states {
 *      for (int depstate = 0; dpstate < (1 << n); dpstate++) {
 *          if (states[i] is not a subset of dpstate) continue;
 *          dp[dpstate] = min{dp_old[dpstate], dp_old[dpstate-states[i]]}
 *      }
 * }
 * return dp[m - 1][(1 << n) - 1]
 * dpstate = 10001110011
 */