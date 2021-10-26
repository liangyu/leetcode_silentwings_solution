package LC601_900;

public class LC687_LongestUnivaluePath {
    /**
     * Given the root of a binary tree, return the length of the longest path, where each node in the path has the same
     * value. This path may or may not pass through the root.
     *
     * The length of the path between two nodes is represented by the number of edges between them.
     *
     * Input: root = [5,4,5,1,1,5]
     * Output: 2
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 10^4].
     * -1000 <= Node.val <= 1000
     * The depth of the tree will not exceed 1000.
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private int res = 0;
    public int longestUnivaluePath(TreeNode root) {
        // corner case
        if (root == null) return 0;

        dfs(root);
        return res - 1;
    }

    private int dfs(TreeNode node) { // longest path towards leaf with same value as node
        if (node == null) return 0;

        int len1 = dfs(node.left);
        int len2 = dfs(node.right);

        int L = 0, R = 0;
        if (node.left != null && node.left.val == node.val) L = len1;
        if (node.right != null && node.right.val == node.val) R = len2;
        res = Math.max(res, L + R + 1);
        return Math.max(L, R) + 1;
    }
}
/**
 * 5的左右两边都不可能有其他拐点出来了
 * 只要看下左边是否有个一路向下的路径
 * 递归 -> node往下走能走多远得到一个最长路径，路径上的值都相等
 * 都是往下递归，定义的是单向往下走的path
 * 真正全局的longest univalue path一定是有个拐点，往左右和往右走，需要左右拼起来一起看
 */
