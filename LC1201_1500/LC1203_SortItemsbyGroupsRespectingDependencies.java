package LC1201_1500;
import java.util.*;
public class LC1203_SortItemsbyGroupsRespectingDependencies {
    /**
     * There are n items each belonging to zero or one of m groups where group[i] is the group that the i-th item
     * belongs to and it's equal to -1 if the i-th item belongs to no group. The items and the groups are zero indexed.
     * A group can have no item belonging to it.
     *
     * Return a sorted list of the items such that:
     *
     * The items that belong to the same group are next to each other in the sorted list.
     * There are some relations between these items where beforeItems[i] is a list containing all the items that should
     * come before the i-th item in the sorted array (to the left of the i-th item).
     * Return any solution if there is more than one solution and return an empty list if there is no solution.
     *
     * Input: n = 8, m = 2, group = [-1,-1,1,0,0,1,0,-1], beforeItems = [[],[6],[5],[6],[3,6],[],[],[]]
     * Output: [6,3,4,1,5,2,0,7]
     *
     * Constraints:
     *
     * 1 <= m <= n <= 3 * 10^4
     * group.length == beforeItems.length == n
     * -1 <= group[i] <= m - 1
     * 0 <= beforeItems[i].length <= n - 1
     * 0 <= beforeItems[i][j] <= n - 1
     * i != beforeItems[i][j]
     * beforeItems[i] does not contain duplicates elements.
     * @param n
     * @param m
     * @param group
     * @param beforeItems
     * @return
     */
    // time = O(m + n^2 + Egroup + Eitem), space = O(m + n^2)
    public int[] sortItems(int n, int m, int[] group, List<List<Integer>> beforeItems) {
        HashMap<Integer, List<Integer>> groupToItem = new HashMap<>();
        // redefine the groupId of individual item
        int idx = m; // new groupId start from m
        for (int i = 0; i < n; i++) {
            if (group[i] == -1) {
                group[i] = idx;
                idx++;
            }
            // map {group : List<Item>}
            groupToItem.putIfAbsent(group[i], new ArrayList<>());
            groupToItem.get(group[i]).add(i);
        }

        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        HashMap<Integer, Integer> indegree = new HashMap<>();
        // build graph within each group
        for (int i = 0; i < n; i++) {
            for (int j : beforeItems.get(i)) {
                if (group[i] != group[j]) continue; // j and i do not belong to the same group
                // j -> i
                map.putIfAbsent(j, new HashSet<>());
                map.get(j).add(i); // j -> i
                indegree.put(i, indegree.getOrDefault(i, 0) + 1);
            }
        }

        // topological sort items within "each group"
        for (int groupId : groupToItem.keySet()) {
            List<Integer> itemList = groupToItem.get(groupId);
            List<Integer> orderedList = topoSort(map, indegree, itemList);
            if (orderedList.size() != itemList.size()) return new int[0]; // sort failed!
            groupToItem.put(groupId, orderedList);
        }

        map.clear();
        indegree.clear();
        // build graph between each group
        for (int i = 0; i < n; i++) {
            for (int j : beforeItems.get(i)) {
                // j -> i
                if (group[i] == group[j]) continue;
                map.putIfAbsent(group[j], new HashSet<>());
                if (!map.get(group[j]).contains(group[i])) {
                    map.get(group[j]).add(group[i]);
                    indegree.put(group[i], indegree.getOrDefault(group[i], 0) + 1);
                }
            }
        }

        // topological sort items between each group
        HashSet<Integer> groupIds = new HashSet<>();
        for (int i = 0; i < n; i++) groupIds.add(group[i]);
        List<Integer> groupList = new ArrayList<>(groupIds);
        List<Integer> orderedGroup = topoSort(map, indegree, groupList);

        // output to res list
        List<Integer> res = new ArrayList<>();
        for (int groupId : orderedGroup) {
            for (int item : groupToItem.get(groupId)) {
                res.add(item);
            }
        }

        int[] ans = new int[res.size()];
        for (int i = 0; i < res.size(); i++) ans[i] = res.get(i);
        return ans;
    }

    private List<Integer> topoSort(HashMap<Integer, HashSet<Integer>> map, HashMap<Integer, Integer> indegree, List<Integer> list) {
        List<Integer> res = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        for (int x : list) {
            if (indegree.getOrDefault(x, 0) == 0) queue.offer(x);
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            res.add(cur);
            for (int next : map.getOrDefault(cur, new HashSet<>())) {
                indegree.put(next, indegree.get(next) - 1);
                if (indegree.get(next) == 0) queue.offer(next);
            }
        }
        return res.size() == list.size() ? res : new ArrayList<>();
    }
}
/**
 * 本题本质是两遍拓扑排序
 * 首先，我们收集每个group内的节点关系，在每个组内进行拓扑排序。
 * 其次，对于任何跨组的一对节点的先后顺序，本质上反映的就是两个组的先后顺序。
 * 因此收集完所有的组间顺序的要求后，可以重复利用拓扑排序的代码，对组进行排序。
 * 最终，安排两层循环，大循环按照已经排好的组的顺序、小循环按照已经排好的组内节点的顺序，依次输出所有的节点，就是答案。
 * for group : groupOrdered
 *      for node in group
 *          res += node
 */
