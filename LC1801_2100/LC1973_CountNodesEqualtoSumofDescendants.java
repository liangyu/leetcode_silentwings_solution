package LC1801_2100;

public class LC1973_CountNodesEqualtoSumofDescendants {
    /**
     * Given the root of a binary tree, return the number of nodes where the value of the node is equal to the sum of
     * the values of its descendants.
     *
     * A descendant of a node x is any node that is on the path from node x to some leaf node. The sum is considered to
     * be 0 if the node has no descendants.
     *
     * Input: root = [10,3,4,2,1]
     * Output: 2
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^5].
     * 0 <= Node.val <= 10^5
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private int res;
    public int equalToDescendants(TreeNode root) {
        // corner case
        if (root == null) return 0;

        res = 0;

        dfs(root);
        return res;
    }

    private int dfs(TreeNode cur) {
        // base case
        if (cur.left == null && cur.right == null) {
            if (cur.val == 0) res++;
            return cur.val;
        }

        int left = cur.left != null ? dfs(cur.left) : 0;
        int right = cur.right != null ? dfs(cur.right) : 0;
        if (cur.val == left + right) res++;
        return cur.val += left + right;
    }
}
