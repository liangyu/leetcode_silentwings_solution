package LC901_1200;
import java.util.*;
public class LC1022_SumofRootToLeafBinaryNumbers {
    /**
     * You are given the root of a binary tree where each node has a value 0 or 1. Each root-to-leaf path represents a
     * binary number starting with the most significant bit.
     *
     * For example, if the path is 0 -> 1 -> 1 -> 0 -> 1, then this could represent 01101 in binary, which is 13.
     * For all leaves in the tree, consider the numbers represented by the path from the root to that leaf. Return the
     * sum of these numbers.
     *
     * The test cases are generated so that the answer fits in a 32-bits integer.
     *
     * Input: root = [1,0,1,0,1,0,1]
     * Output: 22
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 1000].
     * Node.val is 0 or 1.
     * @param root
     * @return
     */
    // S1: dfs
    // time = O(n), space = O(n)
    public int sumRootToLeaf(TreeNode root) {
        // corner case
        if (root == null) return 0;

        return dfs(root, 0);
    }

    private int dfs(TreeNode node, int val) {
        if (node == null) return 0;

        val = val * 2 + node.val;
        return node.left == null && node.right == null ? val : dfs(node.left, val) + dfs(node.right, val);
    }

    // S2: dfs
    // time = O(n), space = O(n)
    public int sumRootToLeaf2(TreeNode root) {
        if (root == null) return 0;

        return helper(root, 0);
    }

    private int helper(TreeNode node, int val) {
        val = val * 2 + node.val;
        if (node.left == null && node.right == null) return val;

        int res = 0;
        if (node.left != null) res += helper(node.left, val);
        if (node.right != null) res += helper(node.right, val);
        return res;
    }
}
