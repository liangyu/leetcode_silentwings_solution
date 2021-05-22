package LC001_300;
import java.util.*;
public class LC235_LowestCommonAncestorofaBinarySearchTree {
    /**
     * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the BST.
     *
     * Input: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
     * Output: 6
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [2, 10^5].
     * -10^9 <= Node.val <= 10^9
     * All Node.val are unique.
     * p != q
     * p and q will exist in the BST.
     * @param root
     * @param p
     * @param q
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // corner case
        if (root == null || p == null || q == null) return null;

        if (p.val < root.val && q.val < root.val) return lowestCommonAncestor(root.left, p, q);
        if (p.val > root.val && q.val > root.val) return lowestCommonAncestor(root.right, p, q);
        return root;
    }
}
