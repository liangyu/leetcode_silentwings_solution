package LC601_900;

public class LC814_BinaryTreePruning {
    /**
     * Given the root of a binary tree, return the same tree where every subtree (of the given tree) not containing a
     * 1 has been removed.
     *
     * A subtree of a node node is node plus every node that is a descendant of node.
     *
     * Input: root = [1,null,0,0,1]
     * Output: [1,null,0,null,1]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 200].
     * Node.val is either 0 or 1.
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public TreeNode pruneTree(TreeNode root) {
        // corner case
        if (root == null) return root;

        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);

        if (root.left == null && root.right == null && root.val == 0) return null;
        return root;
    }
}
