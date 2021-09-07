package LC901_1200;

public class LC1120_MaximumAverageSubtree {
    /**
     * Given the root of a binary tree, return the maximum average value of a subtree of that tree. Answers within 10^-5
     * of the actual answer will be accepted.
     *
     * A subtree of a tree is any node of that tree plus all its descendants.
     *
     * The average value of a tree is the sum of its values, divided by the number of nodes.
     *
     * Input: root = [5,6,1]
     * Output: 6.00000
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * 0 <= Node.val <= 10^5
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public double maximumAverageSubtree(TreeNode root) {
        // corner case
        if (root == null) return 0.0;

        return dfs(root)[0];
    }

    private double[] dfs(TreeNode root) { // [max, #, sum]
        if (root == null) return new double[]{0.0, 0.0, 0.0};

        double[] left = dfs(root.left);
        double[] right = dfs(root.right);

        double avg = (root.val + left[2] + right[2]) / (1 + left[1] + right[1]);
        double maxAvg = Math.max(avg, Math.max(left[0], right[0]));
        return new double[]{maxAvg, 1 + left[1] + right[1], root.val + left[2] + right[2]};
    }
}
