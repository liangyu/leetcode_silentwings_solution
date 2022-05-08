package LC2101_2400;

public class LC2265_CountNodesEqualtoAverageofSubtree {
    /**
     * Given the root of a binary tree, return the number of nodes where the value of the node is equal to the average
     * of the values in its subtree.
     *
     * Note:
     *
     * The average of n elements is the sum of the n elements divided by n and rounded down to the nearest integer.
     * A subtree of root is a tree consisting of root and all of its descendants.
     *
     * Input: root = [4,8,5,0,1,null,6]
     * Output: 5
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 1000].
     * 0 <= Node.val <= 1000
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    int res = 0;
    public int averageOfSubtree(TreeNode root) {
        if (root == null) return 0;

        dfs(root);
        return res;
    }

    private int[] dfs(TreeNode node) {
        if (node == null) return new int[]{0, 0};

        int[] l = dfs(node.left);
        int[] r = dfs(node.right);

        int sum = l[0] + r[0] + node.val;
        int amount = l[1] + r[1] + 1;
        int avg = sum / amount;
        if (avg == node.val) res++;
        return new int[]{sum, amount};
    }
}
