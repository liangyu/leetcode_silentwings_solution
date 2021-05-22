package LC001_300;
import java.util.*;
public class LC101_SymmetricTree {
    /**
     * Given the root of a binary tree, check whether it is a mirror of itself (i.e., symmetric around its center).
     *
     * Input: root = [1,2,2,3,4,4,3]
     * Output: true
     *
     *
     Constraints:

     The number of nodes in the tree is in the range [1, 1000].
     -100 <= Node.val <= 100

     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public boolean isSymmetric(TreeNode root) {
        // corner case
        if (root == null) return true;

        return helper(root.left, root.right);
    }

    private boolean helper(TreeNode left, TreeNode right) {
        // corner case
        if (left == null && right == null) return true;
        if (left == null || right == null) return false;

        if (left.val != right.val) return false;
        return helper(left.left, right.right) && helper(left.right, right.left);
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}
