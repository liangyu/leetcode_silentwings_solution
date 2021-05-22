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
    // time = O(n), space = O(n)
    private int max = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        // corner case
        if (root == null) return 0;

        helper(root);
        return max;
    }

    private int helper(TreeNode cur) {
        if (cur == null) return 0;

        int leftMax = helper(cur.left); // 左边最大路径和
        int rightMax = helper(cur.right); // 右边最大路径和

        int curSum = cur.val + Math.max(0, leftMax) + Math.max(0, rightMax); // 当前最大路径
        int curMax = cur.val + Math.max(0, Math.max(leftMax, rightMax)); // 当前单边的最大值，helper函数的返回值！！！
        max = Math.max(curSum, max); // 更新max
        return curMax; // 返回单边最大路径和
    }

    private class TreeNode {
        private int val;
        private TreeNode left, right;
    }

    // S2: ResultType
    // time = O(n), space = O(n)
//    public int maxPathSum(TreeNode root) {
//        ResultType res = helper(root);
//        return res.any2any;
//    }
//
//    private ResultType helper(TreeNode root) {
//        // corner case
//        if (root == null) return new ResultType(Integer.MIN_VALUE, Integer.MIN_VALUE);
//
//        ResultType left = helper(root.left);
//        ResultType right = helper(root.right);
//
//        int root2any = root.val + Math.max(Math.max(0, left.root2any), Math.max(0, right.root2any));
//        int any2any = Math.max(left.any2any, right.any2any);
//        any2any = Math.max(any2any, Math.max(0, left.root2any) + Math.max(0, right.root2any) + root.val);
//        return new ResultType(root2any, any2any);
//    }
//
//    private class ResultType {
//        private int root2any;
//        private int any2any;
//        public ResultType(int root2any, int any2any) {
//            this.root2any = root2any;
//            this.any2any = any2any;
//        }
//    }

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode(int x) { val = x; }
     * }
     */
}
