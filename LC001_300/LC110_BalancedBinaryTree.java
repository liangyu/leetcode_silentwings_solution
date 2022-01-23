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

    // S2
    // time = O(n), space = O(n)
    public boolean isBalanced2(TreeNode root) {
        if (root == null) return true;

        if (!isBalanced(root.left) || !isBalanced(root.right)) return false;
        int left = getHeight(root.left);
        int right = getHeight(root.right);
        return Math.abs(left - right) <= 1;
    }

    private int getHeight(TreeNode node) {
        if (node == null) return 0;

        int lh = getHeight(node.left);
        int rh = getHeight(node.right);
        return Math.max(lh, rh) + 1;
    }
}
