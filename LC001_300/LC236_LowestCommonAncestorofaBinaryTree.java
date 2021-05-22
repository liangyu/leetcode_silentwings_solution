package LC001_300;
import java.util.*;
public class LC236_LowestCommonAncestorofaBinaryTree {
    /**
     * Given a binary tree, find the lowest common ancestor (LCA) of two given nodes in the tree.
     *
     * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and
     * q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of
     * itself).”
     *
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * Output: 3
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [2, 10^5].
     * -10^9 <= Node.val <= 10^9
     * All Node.val are unique.
     * p != q
     * p and q will exist in the tree.
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // corner case
        if (root == null || p == null || q == null) return null;
        if (p == root || q == root) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) return root;
        return left == null ? right : left;
    }
}
