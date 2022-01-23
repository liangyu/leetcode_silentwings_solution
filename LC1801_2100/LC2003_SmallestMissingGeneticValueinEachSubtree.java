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
    List<Integer>[] children;
    HashSet<Integer> set;
    int q = 1;
    public int[] smallestMissingValueSubtree(int[] parents, int[] nums) {
        int n = parents.length;
        children = new List[n];
        for (int i = 0; i < n; i++) children[i] = new ArrayList<>();
        set = new HashSet<>();
        int node1 = -1;
        for (int i = 0; i < n; i++) {
            if (parents[i] != -1) children[parents[i]].add(i);
            if (nums[i] == 1) node1 = i;
        }
        if (node1 == -1) { // 整棵树都没有1
            int[] res = new int[n];
            Arrays.fill(res, 1);
            return res;
        }

        int[] res = new int[n];
        // case 1: node值为1的所有子树的结果都是1
        for (int child : children[node1]) {
            dfs1(child, res);
        }

        // case 2: 从node开始不停向上的父亲节点的其他分支肯定没有1，所以其smallest missing一定也为1
        int node = node1;
        while (node != 0) { // 注意: 这里最多只能走到node.parent = 0，而node本身不需要走到0！
            int p = parents[node];
            for (int child : children[p]) {
                if (child != node) {
                    dfs1(child, res);
                }
            }
            node = p; // node往上走一级
        }

        // case 3: 走剩下唯一从root到node1的单链,把所有子树的node值全部放入set里，然后从小到大遍历找第一个不在set里的值
        node = node1;
        while (node != -1) { // 可以走到0
            dfs2(node, nums); // find all of the children nodes of node
            // find smallest missing num in the set
            while (set.contains(q)) q++;
            res[node] = q;
            node = parents[node]; // 再往上走
        }
        return res;
    }

    private void dfs1(int node, int[] res) { // mark the node's result as 1
        res[node] = 1;
        for (int child : children[node]) {
            dfs1(child, res);
        }
    }

    private void dfs2(int node, int[] nums) { // collect the node's subtree elements into set
        if (set.contains(nums[node])) return;
        set.add(nums[node]);
        for (int child : children[node]) {
            dfs2(child, nums);
        }
    }

    // S2
    // time = O(n), space = O(n)
    // List<Integer>[] children;
    List<HashSet<Integer>> setList;
    int[] setIdx, nums, res;
    public int[] smallestMissingValueSubtree2(int[] parents, int[] nums) {
        int n = parents.length;
        children = new List[n];
        setIdx = new int[n];
        setList = new ArrayList<>();
        this.nums = nums;
        res = new int[n];

        for (int i = 0; i < n; i++) children[i] = new ArrayList<>();
        for (int i = 1; i < n; i++) {
            children[parents[i]].add(i);
        }

        dfs(0);
        return res;
    }

    private void dfs(int node) {
        // base case
        if (children[node].size() == 0) {
            setIdx[node] = setList.size();
            setList.add(new HashSet<>(Arrays.asList(nums[node])));
            res[node] = nums[node] == 1 ? 2 : 1;
        } else {
            for (int child : children[node]) {
                dfs(child);
            }

            // find biggest set among node's children
            int maxSetSize = 0;
            int maxSetIdx = -1;
            for (int child : children[node]) {
                if (setList.get(setIdx[child]).size() > maxSetSize) {
                    maxSetSize = setList.get(setIdx[child]).size();
                    maxSetIdx = setIdx[child];
                }
            }

            // merge all sets into the max set of node's children, and assign it to node
            setIdx[node] = maxSetIdx;
            for (int child : children[node]) {
                if (setIdx[child] == maxSetIdx) continue;
                setList.get(maxSetIdx).addAll(setList.get(setIdx[child]));
            }
            setList.get(maxSetIdx).add(nums[node]);

            // find smallest missing value
            int maxMissing = 0;
            for (int child : children[node]) {
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
