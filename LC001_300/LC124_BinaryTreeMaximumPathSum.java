package LC001_300;
import java.util.*;
public class LC124_BinaryTreeMaximumPathSum {
    /**
     * A path in a binary tree is a sequence of nodes where each pair of adjacent nodes in the sequence has an edge
     * connecting them. A node can only appear in the sequence at most once. Note that the path does not need to pass
     * through the root.
     *
     * The path sum of a path is the sum of the node's values in the path.
     *
     * Given the root of a binary tree, return the maximum path sum of any path.
     *
     * Input: root = [1,2,3]
     * Output: 6
     *
     * Input: root = [-10,9,20,null,null,15,7]
     * Output: 42
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 3 * 10^4].
     * -1000 <= Node.val <= 1000
     * @param root
     * @return
     */
    // S1: DFS
    // time = O(n), space = O(n)
    private int res = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        // corner case
        if (root == null) return 0;

        dfs(root);
        return res;
    }

    private int dfs(TreeNode node) {
        if (node == null) return 0;

        int leftSum = dfs(node.left);
        int rightSum = dfs(node.right);

        res = Math.max(res, node.val + Math.max(0, leftSum) + Math.max(0, rightSum));
        return node.val + Math.max(0, Math.max(leftSum, rightSum));
    }

    // S2: ResultType
    // time = O(n), space = O(n)
    public int maxPathSum2(TreeNode root) {
        ResultType res = helper2(root);
        return res.any2any;
    }

    private ResultType helper2(TreeNode root) {
        // corner case
        if (root == null) return new ResultType(Integer.MIN_VALUE, Integer.MIN_VALUE);

        ResultType left = helper2(root.left);
        ResultType right = helper2(root.right);

        int root2any = root.val + Math.max(Math.max(0, left.root2any), Math.max(0, right.root2any));
        int any2any = Math.max(left.any2any, right.any2any);
        any2any = Math.max(any2any, Math.max(0, left.root2any) + Math.max(0, right.root2any) + root.val);
        return new ResultType(root2any, any2any);
    }

    private class ResultType {
        private int root2any;
        private int any2any;
        public ResultType(int root2any, int any2any) {
            this.root2any = root2any;
            this.any2any = any2any;
        }
    }
}
/**
 * break down 2个问题
 * 这个结点一直往下走，路径最大和，不带拐弯的
 * MaxPath(node, AsTurn) -> node.val + max(0, MaxPath(leftNode, noTurn)) + Math.max(0, MaxPath(rightNode, noTurn))
 * dfs写个不带拐弯的从当前结点往下走，最多能走多少
 */
