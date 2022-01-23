package LC1801_2100;
import java.util.*;
public class LC2003_SmallestMissingGeneticValueinEachSubtree {
    /**
     * There is a family tree rooted at 0 consisting of n nodes numbered 0 to n - 1. You are given a 0-indexed integer
     * array parents, where parents[i] is the parent for node i. Since node 0 is the root, parents[0] == -1.
     *
     * There are 105 genetic values, each represented by an integer in the inclusive range [1, 105]. You are given a
     * 0-indexed integer array nums, where nums[i] is a distinct genetic value for node i.
     *
     * Return an array ans of length n where ans[i] is the smallest genetic value that is missing from the subtree
     * rooted at node i.
     *
     * The subtree rooted at a node x contains node x and all of its descendant nodes.
     *
     * Input: parents = [-1,0,0,2], nums = [1,2,3,4]
     * Output: [5,1,1,1]
     *
     * Constraints:
     *
     * n == parents.length == nums.length
     * 2 <= n <= 10^5
     * 0 <= parents[i] <= n - 1 for i != 0
     * parents[0] == -1
     * parents represents a valid tree.
     * 1 <= nums[i] <= 10^5
     * Each nums[i] is distinct.
     * @param parents
     * @param nums
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public int[] smallestMissingValueSubtree(int[] parents, int[] nums) {
        int n = parents.length;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        HashSet<Integer> set = new HashSet<>();
        int q = 1;

        int node1 = -1;
        for (int i = 0; i < n; i++) { // O(n)
            if (parents[i] != -1) {
                map.putIfAbsent(parents[i], new ArrayList<>());
                map.get(parents[i]).add(i);
            }
            if (nums[i] == 1) node1 = i;
        }
        if (node1 == -1) { // 整棵树都没有1
            int[] res = new int[n];
            Arrays.fill(res, 1);
            return res;
        }

        int[] res = new int[n];
        if (map.containsKey(node1)) {
            for (int child : map.get(node1)) dfs1(map, child, res);
        }

        int node = node1;
        while (node != 0) {
            int p = parents[node];
            if (map.containsKey(p)) {
                for (int child : map.get(p)) {
                    if (child != node) dfs1(map, child, res);
                }
            }
            node = p; // node往上走一级
        }

        node = node1;
        while (node != -1) {
            dfs2(map, set, nums, node);
            while (set.contains(q)) q++;
            res[node] = q;
            node = parents[node]; // 再往上走
        }
        return res;
    }

    private void dfs1(HashMap<Integer, List<Integer>> map, int node, int[] res) { // mark the node's result as 1
        res[node] = 1;
        if (map.containsKey(node)) {
            for (int child : map.get(node)) dfs1(map, child, res);
        }
    }

    // collect the node's subtree elements into set
    private void dfs2(HashMap<Integer, List<Integer>> map, HashSet<Integer> set, int[] nums, int node) {
        if (set.contains((nums[node]))) return;
        set.add(nums[node]);
        if (map.containsKey(node)) {
            for (int child : map.get(node)) {
                dfs2(map, set, nums, child);
            }
        }
    }

    // S2
    // time = O(n), space = O(n)
    public int[] smallestMissingValueSubtree2(int[] parents, int[] nums) {
        int n = parents.length;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        List<HashSet<Integer>> setList = new ArrayList<>();
        int[] setIdx = new int[100001];
        int[] res = new int[n];

        for (int i = 1; i < n; i++) {
            map.putIfAbsent(parents[i], new ArrayList<>());
            map.get(parents[i]).add(i);
        }
        dfs(nums, map, setList, setIdx,0, res);
        return res;
    }

    private void dfs(int[] nums, HashMap<Integer, List<Integer>> map, List<HashSet<Integer>> setList, int[] setIdx, int node, int[] res) {
        // base case
        if (!map.containsKey(node)) { // leaf node
            setIdx[node] = setList.size();
            setList.add(new HashSet<>(Arrays.asList(nums[node])));
            res[node] = nums[node] == 1 ? 2 : 1;
        } else {

            for (int child : map.get(node)) {
                dfs(nums, map, setList, setIdx, child, res);
            }

            int maxSetSize = 0, maxSetIdx = -1;
            for (int child : map.get(node)) {
                if (setList.get(setIdx[child]).size() > maxSetSize) {
                    maxSetSize = setList.get(setIdx[child]).size();
                    maxSetIdx = setIdx[child];
                }
            }

            setIdx[node] = maxSetIdx;
            for (int child : map.get(node)) {
                if (setIdx[child] == maxSetIdx) continue;
                for (int x : setList.get(setIdx[child])) {
                    setList.get(maxSetIdx).add(x);
                }
            }
            setList.get(maxSetIdx).add(nums[node]);

            int maxMissing = 0;
            for (int child : map.get(node)) {
                maxMissing = Math.max(maxMissing, res[child]);
            }
            int x = maxMissing;
            while (setList.get(maxSetIdx).contains(x)) x++;
            res[node] = x;
        }
    }
}
/**
 * offline queries
 * 可以对queries任意排序，rank这些queries，前面答案对后面有帮助
 * 每个query并不是independent
 * 找val = 1，每个val都是distinct的
 * 下面子节点都不会有1这个元素，就意味着smallest missing value = 1
 * val = 1: node 5 1 2
 *
 * val = 2: node 4
 * val = 3:
 * val = 4: node 3
 * val = 5:
 * val = 6:
 * val = 7: node 0
 * => 单链的时候会有问题 O(n^2) => O(n) 收集和猜测的过程都是O(n)
 * val = 1之后，针对单链进行从下往上遍历即可
 *
 * S2: 启发式合并
 * 树里只给叶子节点开辟集合，其他的都是借用其某个孩子的集合
 * set0: {6,4}
 * set1: 1
 * set2: {3,1,2,6,4,5}
 */
