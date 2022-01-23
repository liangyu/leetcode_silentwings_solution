package LC901_1200;
import java.util.*;
public class LC938_RangeSumofBST {
    /**
     * Given the root node of a binary search tree, return the sum of values of all nodes with a value
     * in the range [low, high].
     *
     * Input: root = [10,5,15,3,7,null,18], low = 7, high = 15
     * Output: 32
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 2 * 104].
     * 1 <= Node.val <= 10^5
     * 1 <= low <= high <= 10^5
     * All Node.val are unique.
     *
     * @param root
     * @param low
     * @param high
     * @return
     */
    // S1：DFS
    // time = O(n), space = O(n)  n: number of nodes in the tree
    public int rangeSumBST(TreeNode root, int low, int high) {
        // corner case
        if (root == null) return 0;

        if (root.val < low) {
            return rangeSumBST(root.right, low, high);
        }
        if (root.val > high) {
            return rangeSumBST(root.left, low ,high);
        }
        return root.val + rangeSumBST(root.left, low, high) + rangeSumBST(root.right, low ,high);
    }

    // S2: iteration
    // time = O(n), space = O(n)  n: number of nodes in the tree
    // the stack will contain no more than two levels of the nodes, the maximal number of nodes in a binary tree is n/2
    // the maximal space needed for the stack would be O(n)
    public int rangeSumBST2(TreeNode root, int low, int high) {
        // corner case
        if (root == null) return 0;

        int res = 0;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            if (cur != null) {
                if (low <= cur.val && cur.val <= high) {
                    res += cur.val;
                }
                if (low < cur.val) stack.push(cur.left);
                if (cur.val < high) stack.push(cur.right);
            }
        }
        return res;
    }

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     */
}
