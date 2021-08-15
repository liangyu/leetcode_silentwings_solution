package LC301_600;

public class LC549_BinaryTreeLongestConsecutiveSequenceII {
    /**
     * Given the root of a binary tree, return the length of the longest consecutive path in the tree.
     *
     * This path can be either increasing or decreasing.
     *
     * For example, [1,2,3,4] and [4,3,2,1] are both considered valid, but the path [1,2,4,3] is not valid.
     * On the other hand, the path can be in the child-Parent-child order, where not necessarily be parent-child order.
     *
     * Input: root = [1,2,3]
     * Output: 2
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 3 * 10^4].
     * -3 * 10^4 <= Node.val <= 3 * 10^4
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private int res;
    public int longestConsecutive(TreeNode root) {
        // corner case
        if (root == null) return 0;

        res = 0;
        helper(root);
        return res;
    }

    private int[] helper(TreeNode root) {
        // corner case
        if (root == null) return new int[]{0};

        int[] left = helper(root.left);
        int[] right = helper(root.right);
        int dcr = 1, icr = 1;

        if (root.left != null) {
            if (root.left.val == root.val + 1) icr = left[1] + 1;
            if (root.left.val == root.val - 1) dcr = left[0] + 1;
        }

        if (root.right != null) {
            if (root.right.val == root.val + 1) icr = Math.max(icr, right[1] + 1);
            if (root.right.val == root.val - 1) dcr = Math.max(dcr, right[0] + 1);
        }

        res = Math.max(res, dcr + icr - 1);
        return new int[]{dcr, icr};
    }
}
