package LC901_1200;

import java.util.HashMap;

public class LC1123_LowestCommonAncestorofDeepestLeaves {
    /**
     * Given the root of a binary tree, return the lowest common ancestor of its deepest leaves.
     *
     * Recall that:
     *
     * The node of a binary tree is a leaf if and only if it has no children
     * The depth of the root of the tree is 0. if the depth of a node is d, the depth of each of its children is d + 1.
     * The lowest common ancestor of a set S of nodes, is the node A with the largest depth such that every node in S
     * is in the subtree with root A.
     *
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4]
     * Output: [2,7,4]
     *
     * Constraints:
     *
     * The number of nodes in the tree will be in the range [1, 1000].
     * 0 <= Node.val <= 1000
     * The values of the nodes in the tree are unique.
     *
     * Note: This question is the same as 865: https://leetcode.com/problems/smallest-subtree-with-all-the-deepest-nodes/
     * @param root
     * @return
     */
    // S1: recursion
    // time = O(n), space = O(n)
    int maxDepth = 0, maxDepthCount = 0;
    HashMap<Integer, Integer> map;
    TreeNode res = null;
    public TreeNode lcaDeepestLeaves(TreeNode root) {
        map = new HashMap<>();
        dfs(root, 1);
        for (int x : map.keySet()) {
            if (map.get(x) == maxDepth) maxDepthCount++;
        }

        dfs2(root);
        return res;
    }

    private void dfs(TreeNode node, int depth) {
        if (node == null) return;

        map.put(node.val, depth);
        maxDepth = Math.max(maxDepth, depth);
        dfs(node.left, depth + 1);
        dfs(node.right, depth + 1);
    }

    private int dfs2(TreeNode node) {
        if (node == null) return 0;

        int self = (map.get(node.val) == maxDepth ? 1 : 0);
        int left = dfs2(node.left);
        int right = dfs2(node.right);
        if (self + left + right == maxDepthCount && res == null) res = node;
        return self + left + right;
    }

    // S2: recursion (最优解!）
    // time = O(n), space = O(n)
    int deepest = 0;
    TreeNode lca;
    public TreeNode lcaDeepestLeaves2(TreeNode root) {
        helper(root, 1);
        return lca;
    }

    private int helper(TreeNode node, int depth) {
        deepest = Math.max(deepest, depth);
        if (node == null) return depth;

        int left = helper(node.left, depth + 1);
        int right = helper(node.right, depth + 1);
        if (left == deepest && right == deepest) lca = node;
        return Math.max(left, right);
    }
}
/**
 * tree是递归定义的题目
 * 用递归定义来check if a node whose subtree contains all deepest leaves
 * 考察一个结点有什么性质无脑用递归 -> 孩子的性质和它本身的性质有什么相关性
 * 必须依赖下面的结点有什么性质，肯定有什么相关性
 * 真正解决问题是从下往上的过程中
 * 在递归的时候设置个全局变量
 */