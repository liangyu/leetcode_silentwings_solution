package LC1501_1800;

public class LC1740_FindDistanceinaBinaryTree {
    /**
     * Given the root of a binary tree and two integers p and q, return the distance between the nodes of value p and
     * value q in the tree.
     *
     * The distance between two nodes is the number of edges on the path from one to the other.
     *
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 0
     * Output: 3
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^4].
     * 0 <= Node.val <= 10^9
     * All Node.val are unique.
     * p and q are values in the tree.
     * @param root
     * @param p
     * @param q
     * @return
     */
    // time = O(n), space = O(n)
    int res = -1;
    public int findDistance(TreeNode root, int p, int q) {
        dfs(root, p, q);
        return res;
    }

    private int[] dfs(TreeNode node, int p, int q) {
        // base case
        if (node == null) return new int[]{-1, -1};
        if (res != -1) return new int[]{-1, -1};

        int[] ans1 = dfs(node.left, p, q);
        int[] ans2 = dfs(node.right, p, q);

        int dp = -1, dq = -1;
        if (ans1[0] != -1) dp = ans1[0] + 1;
        else if (ans2[0] != -1) dp = ans2[0] + 1;
        else if (node.val == p) dp = 0;

        if (ans1[1] != -1) dq = ans1[1] + 1;
        else if (ans2[1] != -1) dq = ans2[1] + 1;
        else if (node.val == q) dq = 0;

        if (dp != -1 && dq != -1 && res == -1) res = dp + dq; // 注意千万别忘了判断res == -1!!!
        return new int[]{dp, dq};
    }
}
/**
 * ref: LC236
 * 所谓的拐点就是lca
 * lowest common ancestor
 * 本质上就是求两者分别到lca的距离之和
 * 更简洁的做法就是纯递归，不需要使用额外的空间
 * int dfs(node): whether node is the LCA of p & q
 *            how many p & q are contained in the subtree of node
 * 还要知道lca到p和q的距离
 * pair<int,int> dfs(node): {the distance between p & node, the distance between q & node}
 * 当这2个分量都不为-1时，表示找到了lca
 */
