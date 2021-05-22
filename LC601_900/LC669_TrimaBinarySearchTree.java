package LC601_900;
import java.util.*;
public class LC669_TrimaBinarySearchTree {
    /**
     * Given the root of a binary search tree and the lowest and highest boundaries as low and high, trim the tree
     * so that all its elements lies in [low, high]. Trimming the tree should not change the relative structure of
     * the elements that will remain in the tree (i.e., any node's descendant should remain a descendant).
     * It can be proven that there is a unique answer.
     *
     * Return the root of the trimmed binary search tree. Note that the root may change depending on the given bounds.
     *
     * Input: root = [3,0,4,null,2,null,null,1], low = 1, high = 3
     * Output: [3,2,null,1]
     *
     * Constraints:
     *
     * The number of nodes in the tree in the range [1, 104].
     * 0 <= Node.val <= 10^4
     * The value of each node in the tree is unique.
     * root is guaranteed to be a valid binary search tree.
     * 0 <= low <= high <= 10^4
     *
     * @param root
     * @param low
     * @param high
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode trimBST(TreeNode root, int low, int high) {
        // corner case
        if (root == null) return root;

        if (root.val < low) return trimBST(root.right, low, high);
        if (root.val > high) return trimBST(root.left, low ,high);
        root.left = trimBST(root.left, low, root.val);
        root.right = trimBST(root.right, root.val, high);
        return root;
    }

    private class TreeNode {
        private int val;
        private TreeNode left, right;
    }
}
