package LC001_300;
import java.util.*;
public class LC110_BalancedBinaryTree {
    /**
     * Given a binary tree, determine if it is height-balanced.
     *
     * For this problem, a height-balanced binary tree is defined as:
     *
     * a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 5000].
     * -10^4 <= Node.val <= 10^4
     *
     * @param root
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public boolean isBalanced(TreeNode root) {
        return maxDepth(root) != -1;
    }

    private int maxDepth(TreeNode root) {
        // corner case
        if (root == null) return 0;

        // 既要知道是否平衡，又要知道高度差为多少 => 非平衡的话，高度用-1来表示
        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) return -1;
        return Math.max(left, right) + 1;
    }

    // S2: Use ResultType
    // time = O(n), space = O(n)
    public boolean isBalanced2(TreeNode root) {
        return helper(root).isBalanced;
    }

    private ResultType helper(TreeNode root) {
        // corner case
        if (root == null) return new ResultType(true, 0);

        ResultType left = helper(root.left);
        ResultType right = helper(root.right);

        // subtree not balanced
        if (!left.isBalanced || !right.isBalanced) return new ResultType(false, -1);

        // root not balanced
        if (Math.abs(left.maxDepth - right.maxDepth) > 1) return new ResultType(false, -1);

        return new ResultType(true, Math.max(left.maxDepth, right.maxDepth) + 1);
    }

    private class ResultType {
        private boolean isBalanced;
        private int maxDepth;
        public ResultType(boolean isBalanced, int maxDepth) {
            this.isBalanced = isBalanced;
            this.maxDepth = maxDepth;
        }
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}
