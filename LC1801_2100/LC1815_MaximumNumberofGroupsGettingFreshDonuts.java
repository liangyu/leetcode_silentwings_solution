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
}
