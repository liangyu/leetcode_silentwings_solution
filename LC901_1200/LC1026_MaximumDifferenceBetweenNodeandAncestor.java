package LC901_1200;

public class LC1026_MaximumDifferenceBetweenNodeandAncestor {
    /**
     * Given the root of a binary tree, find the maximum value v for which there exist different nodes a and b where
     * v = |a.val - b.val| and a is an ancestor of b.
     *
     * A node a is an ancestor of b if either: any child of a is equal to b or any child of a is an ancestor of b.
     *
     * Input: root = [8,3,10,1,6,null,14,null,null,4,7,13]
     * Output: 7
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [2, 5000].
     * 0 <= Node.val <= 10^5
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public int maxAncestorDiff(TreeNode root) {
        if (root == null) return 0;

        int left = helper(root.left, root.val, root.val);
        int right = helper(root.right, root.val, root.val);
        return Math.max(left, right);
    }

    private int helper(TreeNode node, int min, int max) {
        if (node == null) return 0;

        int ans1 = Math.abs(node.val - min);
        int ans2 = Math.abs(node.val - max);
        int ans = Math.max(ans1, ans2);
        min = Math.min(node.val, min);
        max = Math.max(node.val, max);
        return Math.max(ans, Math.max(helper(node.left, min, max), helper(node.right, min, max)));
    }
}
