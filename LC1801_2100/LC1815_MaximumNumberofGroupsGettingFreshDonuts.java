package LC1801_2100;
import java.util.*;
public class LC1815_MaximumNumberofGroupsGettingFreshDonuts {
    /**
     * There is a donuts shop that bakes donuts in batches of batchSize. They have a rule where they must serve all of
     * the donuts of a batch before serving any donuts of the next batch. You are given an integer batchSize and an
     * integer array groups, where groups[i] denotes that there is a group of groups[i] customers that will visit the
     * shop. Each customer will get exactly one donut.
     *
     * When a group visits the shop, all customers of the group must be served before serving any of the following
     * groups. A group will be happy if they all get fresh donuts. That is, the first customer of the group does not
     * receive a donut that was left over from the previous group.
     *
     * You can freely rearrange the ordering of the groups. Return the maximum possible number of happy groups after
     * rearranging the groups.
     *
     * Input: batchSize = 3, groups = [1,2,3,4,5,6]
     * Output: 4
     *
     * Input: batchSize = 3, groups = [1,2,3,4,5,6]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= batchSize <= 9
     * 1 <= groups.length <= 30
     * 1 <= groups[i] <= 10^9
     * @param batchSize
     * @param groups
     * @return
     */
    // S1: dfs
    // time = O(n + 2^k), space = O(k)
    public int maxHappyGroups(int batchSize, int[] groups) {
        // corner case
        if (groups == null || groups.length == 0 || batchSize <= 0) return 0;

        int res = 0;
        int[] arr = new int[batchSize];
        for (int group : groups) {
            if (group % batchSize == 0) res++;
            else arr[group % batchSize]++;
        }

        HashMap<String, Integer> map = new HashMap<>();
        return res + dfs(arr, 0, map);
    }

    private int dfs(int[] arr, int left, HashMap<String, Integer> map) {
        String key = Arrays.toString(arr) + left;
        if (map.containsKey(key)) return map.get(key);
        else {
            int res = 0;
            for (int i = 0; i < arr.length; i++) {
                if (arr[i] > 0) { // cannot consume a whole batch
                    arr[i]--;
                    // case 1: current group must be happy as they will consume a fresh batch
                    if (left == 0) {
                        res = Math.max(res, dfs(arr, i % arr.length, map) + 1);
                    } else { // case 2: current group won't be happy as they will start with what is left from last batch
                        res = Math.max(res, dfs(arr, (left + i) % arr.length, map));
                    }
                    arr[i]++;
                }
            }
            map.put(key, res);
            return res;
        }
    }

    // S2: dfs + bitmask
    // time = O(n + 2^k), space = O(k)
    int batch, n;
    HashMap<Long, Integer> map;
    public int maxHappyGroups2(int batchSize, int[] groups) {
        n = groups.length;
        batch = batchSize;
        map = new HashMap<>();
        int[] count = new int[batchSize];
        for (int x : groups) {
            count[x % batchSize]++;
        }

        long state = 0;
        for (int i = 1; i < count.length; i++) {
            state += (long) count[i] << (i * 5); // 最多一组30人，需要5个bit位表示 (2^4 = 16, 2^5 = 32)
        }
        return count[0] + dfs(state, 0, count[0]); // count[0]不用进入dfs,可以直接拿出来加上
    }

    private int dfs(long state, int presum, int i) { // presum: current batch number; i: number of groups treated so far
        // base case
        if (i == n) return 0;
        if (map.containsKey(state)) return map.get(state);

        int res = 0;
        int bonus = presum % batch == 0 ? 1 : 0;
        for (int j = 1; j < batch; j++) { // iterate through all remaining untreated group of different batch size
            if ((state >> (5 * j)) % 32 == 0) continue; // no groups of this batch size are left untreated
            state -= 1L << (5 * j); // deduct this group from the remaining untreated groups
            res = Math.max(res, dfs(state, (presum + j) % batch, i + 1)); // update current batch size
            state += 1L << (5 * j); // setback
        }
        map.put(state, res + bonus);
        return res + bonus;
    }
}
/**
 * 1 <= groups.length <= 30 -> 非常少，NP问题，暴力搜索
 * dfs / bitmask
 * 30*29*28... = 30! -> 太大
 * 其实我们看的其实是 前缀和 % batch
 * 1 <= batchSize <= 9 => 我们眼中最多只有9种选择 => 9^30
 * 1 <= groups.length <= 30 => 越来越少的情况
 * 到底有多少种permutation呢？只考虑对batchSize的余数
 * 1 1 1 1 1 ... = 1^30
 * 4 4 4 4 4 ... = 4^8
 * 但实际上并没有这么多，因为这30个元素是分布在9个bin里面的，这是一个约束。
 * 只有当每个bin的频次均匀分布时，才对应着最复杂的情况，即(30/9)^9 = 50805种。
 * 试想一下，如果30个元素属于同一个bin里面，那么其实只有30种key。
 * 综上所述，dfs是复杂度可行的算法。
 * 把int[]count压缩成一个长整型 -> 5个bit位，最多31 (0~31)
 * 最多9个bin，每5个bit就表示一个bin里的数字 => 5 * 9 = 45 => long state compression 效率非常高
 */
