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
    // S1
    // time = O(n), space = O(n)
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // corner case
        if (root == null || p == null || q == null) return null;
        if (p == root || q == root) return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // left与right都不为null -> p, q分别是root.left和root.right (根据p == root || q == root) return root;而来！
        if (left != null && right != null) return root; // p, q分别是root.left和root.right
        return left == null ? right : left;
    }

    // S2
    // time = O(n), space = O(n)
    TreeNode res = null;
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return res;
    }

    private int dfs(TreeNode node, TreeNode p, TreeNode q) {
        if (node == null) return 0;

        int left = dfs(node.left, p, q);
        int right = dfs(node.right, p, q);
        int self = (node == p || node == q ? 1 : 0);
        int count = self + left + right;
        if (count == 2 && res == null) res = node;
        return count;
    }
}
/**
 * dfs(node) 是否是公共祖先
 * node所在子树是否包含p和q
 * 只要找某些结点同时包含p和q
 * dfs(node): how many nodes of p and q is included in the subtree of node
 * 3,5,2 -> 如何找到2
 * 从上往下走的一系列common ancestor里面，最下面的就是最小的
 * 递归返回从下往上返回，返回的时候2是确认common ancestor，之前没有，那就是lowest，因为其他的都在2的上面
 * 本质上找第一个node，用一个全局变量就可以
 */
