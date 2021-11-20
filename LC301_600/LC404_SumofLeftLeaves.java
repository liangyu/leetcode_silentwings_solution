package LC301_600;

public class LC404_SumofLeftLeaves {
    /**
     * Given the root of a binary tree, return the sum of all left leaves.
     *
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 24
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 1000].
     * -1000 <= Node.val <= 1000
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private int sum = 0;
    public int sumOfLeftLeaves(TreeNode root) {
        // corner case
        if (root == null) return 0;

        dfs(root, false);
        return sum;
    }

    private void dfs(TreeNode node, boolean isLeft) {
        if (node.left == null && node.right == null) {
            if (isLeft) {
                sum += node.val;
            }
            return;
        }

        if (node.left != null) dfs(node.left, true);
        if (node.right != null) dfs(node.right, false);
    }
}
