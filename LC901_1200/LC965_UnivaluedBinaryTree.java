package LC901_1200;

public class LC965_UnivaluedBinaryTree {
    /**
     * A binary tree is uni-valued if every node in the tree has the same value.
     *
     * Given the root of a binary tree, return true if the given tree is uni-valued, or false otherwise.
     *
     * Input: root = [1,1,1,1,1,null,1]
     * Output: true
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 100].
     * 0 <= Node.val < 100
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public boolean isUnivalTree(TreeNode root) {
        return dfs(root, root.val);
    }

    private boolean dfs(TreeNode node, int val) {
        if (node == null) return true;
        if (node.val != val) return false;
        return dfs(node.left, val) && dfs(node.right, val);
    }
}
