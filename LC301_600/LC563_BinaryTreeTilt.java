package LC301_600;

public class LC563_BinaryTreeTilt {
    /**
     * Given the root of a binary tree, return the sum of every tree node's tilt.
     *
     * The tilt of a tree node is the absolute difference between the sum of all left subtree node values and all right
     * subtree node values. If a node does not have a left child, then the sum of the left subtree node values is
     * treated as 0. The rule is similar if there the node does not have a right child.
     *
     * Input: root = [1,2,3]
     * Output: 1
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 104].
     * -1000 <= Node.val <= 1000
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private int res = 0;
    public int findTilt(TreeNode root) {
        // corner case
        if (root == null) return 0;

        helper(root);
        return res;
    }

    private int helper(TreeNode root) {
        if (root == null) return 0;

        int ls = helper(root.left);
        int rs = helper(root.right);

        res += Math.abs(ls - rs);
        return ls + rs + root.val;
    }
}
