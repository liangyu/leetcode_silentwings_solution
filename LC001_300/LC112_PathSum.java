package LC001_300;
import java.util.*;
public class LC112_PathSum {
    /**
     * Given the root of a binary tree and an integer targetSum, return true if the tree has a root-to-leaf path such
     * that adding up all the values along the path equals targetSum.
     *
     * A leaf is a node with no children.
     *
     * Input: root = [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
     * Output: true
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 5000].
     * -1000 <= Node.val <= 1000
     * -1000 <= targetSum <= 1000
     *
     * @param root
     * @param targetSum
     * @return
     */
    // S1: Recursion
    // time = O(n), space = O(n)
    public boolean hasPathSum(TreeNode root, int targetSum) {
        // corner case
        if (root == null) return false;

        if (root.left == null && root.right == null && targetSum == root.val) return true;
        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    // S2: iteration
    // time = O(n), space = O(n)
    public boolean hasPathSum2(TreeNode root, int targetSum) {
        // corner case
        if (root == null) return false;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            if (cur.left == null && cur.right == null) {
                if (cur.val == targetSum) return true; // 到达终点时，终点node上的val即为从root出发所有路径上的总和
            }

            if (cur.left != null) {
                stack.push(cur.left);
                cur.left.val += cur.val; // 不断累加前面路径上的和到下一个node上
            }

            if (cur.right != null) {
                stack.push(cur.right);
                cur.right.val += cur.val;
            }
        }
        return false;
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}
