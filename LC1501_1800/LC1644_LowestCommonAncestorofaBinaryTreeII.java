package LC1501_1800;

public class LC1644_LowestCommonAncestorofaBinaryTreeII {
    /**
     * Given the root of a binary tree, return the lowest common ancestor (LCA) of two given nodes, p and q. If either
     * node p or q does not exist in the tree, return null. All values of the nodes in the tree are unique.
     *
     * According to the definition of LCA on Wikipedia: "The lowest common ancestor of two nodes p and q in a binary
     * tree T is the lowest node that has both p and q as descendants (where we allow a node to be a descendant of
     * itself)". A descendant of a node x is a node y that is on the path from node x to some leaf node.
     *
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
     * Output: 3
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * -10^9 <= Node.val <= 10^9
     * All Node.val are unique.
     * p != q
     *
     * Follow up: Can you find the LCA traversing the tree, without checking nodes existence?
     * @param root
     * @param p
     * @param q
     * @return
     */
    // time = O(n), space = O(n)
    TreeNode res = null;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return res;
    }

    private int dfs(TreeNode node, TreeNode p, TreeNode q) { // 以root为根的子树里，包含多少个p和q,返回值只有3种：0，1，2
        if (node == null) return 0;

        int left = dfs(node.left, p, q);
        int right = dfs(node.right, p, q);
        int self = (node == p || node == q ? 1 : 0);
        int count = left + right + self;
        if (count == 2 && res == null) res = node;
        return count;
    }
}
/**
 * ref: LC236 一模一样
 */
