package LC001_300;
import java.util.*;
public class LC98_ValidateBinarySearchTree {
    /**
     * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
     *
     * A valid BST is defined as follows:
     *
     * The left subtree of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     *
     * @param root
     * @return
     */
    // S1: ResultType
    // time = O(n), space = O(n)
    public boolean isValidBST(TreeNode root) {
        ResultType r = helper(root);
        return r.isbst;
    }

    private ResultType helper(TreeNode root) {
        // corner case
        if (root == null) return new ResultType(true, Integer.MAX_VALUE, Integer.MIN_VALUE);

        ResultType left = helper(root.left);
        ResultType right = helper(root.right);

        if (!left.isbst || !right.isbst) return new ResultType(false, 0, 0);

        if (root.left != null && left.max >= root.val || root.right != null && right.min <= root.val) {
            return new ResultType(false, 0, 0);
        }

        return new ResultType(true, Math.min(root.val, left.min), Math.max(root.val, right.max));
    }

    private class ResultType {
        private boolean isbst;
        private int min;
        private int max;
        public ResultType(boolean isbst, int min, int max) {
            this.isbst = isbst;
            this.min = min;
            this.max = max;
        }
    }

    // S2: Recursion
    // time = O(n), space = O(n)
    private TreeNode prev = null;
    public boolean isValidBST2(TreeNode root) {
        // corner case
        if (root == null) return true;

        if (!isValidBST(root.left)) return false;

        if (prev != null && prev.val >= root.val) return false;
        prev = root;
        return isValidBST(root.right);
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}
