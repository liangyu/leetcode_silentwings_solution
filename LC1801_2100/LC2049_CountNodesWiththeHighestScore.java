package LC1801_2100;
import java.util.*;
public class LC2049_CountNodesWiththeHighestScore {
    /**
     * There is a binary tree rooted at 0 consisting of n nodes. The nodes are labeled from 0 to n - 1. You are given
     * a 0-indexed integer array parents representing the tree, where parents[i] is the parent of node i. Since node 0
     * is the root, parents[0] == -1.
     *
     * Each node has a score. To find the score of a node, consider if the node and the edges connected to it were
     * removed. The tree would become one or more non-empty subtrees. The size of a subtree is the number of the nodes
     * in it. The score of the node is the product of the sizes of all those subtrees.
     *
     * Return the number of nodes that have the highest score.
     *
     * Input: parents = [-1,2,0,2,0]
     * Output: 3
     *
     * Constraints:
     *
     * n == parents.length
     * 2 <= n <= 10^5
     * parents[0] == -1
     * 0 <= parents[i] <= n - 1 for i != 0
     * parents represents a valid binary tree.
     * @param parents
     * @return
     */
    // S1: dfs
    // time = O(n), space = O(n)
    private List<Integer>[] children;
    TreeMap<Long, Integer> map;
    public int countHighestScoreNodes(int[] parents) {
        int n = parents.length;
        children = new List[n];
        for (int i = 0; i < n; i++) children[i] = new ArrayList<>();
        map = new TreeMap<>();
        for (int i = 1; i < n; i++) children[parents[i]].add(i);

        dfs(0, n);
        return map.get(map.lastKey());
    }

    private int dfs(int node, int n) { // the num of nodes in the subtree rooted at node
        int sub_total = 0;
        List<Integer> sub = new ArrayList<>();

        for (int child : children[node]) {
            sub.add(dfs(child, n));
            sub_total += sub.get(sub.size() - 1);
        }

        long score = 1;
        if (n - 1 - sub_total > 0) score *= n - 1 - sub_total;
        for (int x : sub) {
            if (x > 0) score *= x;
        }
        map.put(score, map.getOrDefault(score, 0) + 1);
        return sub_total + 1;
    }

    // S2: DFS
    // time = O(n), space = O(n)
    private long max = 0, res = 0;
    public int countHighestScoreNodes2(int[] parents) {
        int n = parents.length;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.putIfAbsent(parents[i], new ArrayList<>());
            map.get(parents[i]).add(i);
        }

        dfs(0, n, map);
        return (int) res;
    }

    private int dfs(int node, int n, HashMap<Integer, List<Integer>> map) {
        int sum = 1;
        long score = 1;

        for (int child : map.getOrDefault(node, new ArrayList<>())) {
            int count = dfs(child, n, map);
            sum += count;
            score *= count;
        }
        if (n - sum > 0) score *= n - sum;
        if (score > max) {
            max = score;
            res = 1;
        } else if (score == max) res++;
        return sum;
    }

    // S3: dfs + memo
    // time = O(n), space = O(n)
    HashMap<TreeNode, long[]> memo;
    public int countHighestScoreNodes3(int[] parents) {
        int n = parents.length;
        memo = new HashMap<>();
        // step 1: build tree
        TreeNode[] nodes = new TreeNode[n];
        for (int i = 0; i < n; i++) {
            nodes[i] = new TreeNode(i);
        }
        TreeNode root = nodes[0];

        for (int i = 1; i < n; i++) {
            int p = parents[i];
            if (nodes[p].left == null) nodes[p].left = nodes[i];
            else nodes[p].right = nodes[i];
        }

        long max = 0, count = 0;
        for (int i = 0; i < n; i++) {
            long[] x = helper(nodes[i]);

            long val = (x[0] == 0 ? 1 : x[0]) * (x[1] == 0 ? 1 : x[1]) * (n - 1 - x[0] - x[1] == 0 ? 1 : n - 1 - x[0] - x[1]);
            if (val >= max) {
                if (val == max) count++;
                else {
                    max = val;
                    count = 1;
                }
            }
        }
        return (int) count;
    }

    private long[] helper(TreeNode root) {
        // base case
        if (root.left == null && root.right == null) {
            memo.put(root, new long[]{0, 0, 1});
            return new long[]{0, 0, 1};
        }

        long[] l = new long[3], r = new long[3];
        if (root.left != null) {
            if (memo.containsKey(root.left)) l = memo.get(root.left);
            else l = helper(root.left);
        }
        if (root.right != null) {
            if (memo.containsKey(root.right)) r = memo.get(root.right);
            else r = helper(root.right);
        }
        memo.put(root, new long[]{l[0] + l[1] + l[2], r[0] + r[1] + r[2], 1});
        return new long[]{l[0] + l[1] + l[2], r[0] + r[1] + r[2], 1};
    }

    private class TreeNode {
        private int val;
        private TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
        }
    }
}
/**
 * 关于树的问题，无脑想如何设计一个递归函数即可
 * 三者非零元素个数的乘积
 * LC543， 687:枚举拐点
 * 这题枚举的就是每棵子树有多少个结点
 */
