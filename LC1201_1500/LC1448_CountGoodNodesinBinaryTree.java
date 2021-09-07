package LC1201_1500;
import java.util.*;
public class LC1448_CountGoodNodesinBinaryTree {
    /**
     * Given a binary tree root, a node X in the tree is named good if in the path from root to X there are no nodes
     * with a value greater than X.
     *
     * Return the number of good nodes in the binary tree.
     *
     * Input: root = [3,1,4,3,null,1,5]
     * Output: 4
     *
     * Constraints:
     *
     * The number of nodes in the binary tree is in the range [1, 10^5].
     * Each node's value is between [-10^4, 10^4].
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private int res;
    public int goodNodes(TreeNode root) {
        // corner case
        if (root == null) return 0;

        res = 1;
        int max = root.val;
        dfs(root, max);
        return res;
    }

    private void dfs(TreeNode cur, int max) {
        // base case
        if (cur.left == null && cur.right == null) return;

        int temp = max;
        if (cur.left != null) {
            if (cur.left.val >= max) {
                max = cur.left.val;
                res++;
            }
            dfs(cur.left, max);
            max = temp; // remember to backtracking, eg. [3,3,null,4,3]
        }
        if (cur.right != null) {
            if (cur.right.val >= max) {
                max = cur.right.val;
                res++;
            }
            dfs(cur.right, max);
            max = temp;
        }
    }
}
