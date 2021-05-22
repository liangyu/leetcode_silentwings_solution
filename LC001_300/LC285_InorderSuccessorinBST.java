package LC001_300;
import java.util.*;
public class LC285_InorderSuccessorinBST {
    /**
     *
     * @param root
     * @param p
     * @return
     */
    // S1: recursion
    // time = O(logn), space = O(logn)
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        // corner case
        if (root == null || p == null) return null;

        if (root.val <= p.val) return  inorderSuccessor(root.right, p);
        else {
            TreeNode left = inorderSuccessor(root.left, p);
            return left != null ? left : root;
        }
    }

    // S2: Iteration
    // time = O(n), space = O(1)
    public TreeNode inorderSuccessor2(TreeNode root, TreeNode p) {
        // corner case
        if (root == null || p == null) return null;

        TreeNode cur = root, res = null;
        while (cur != null) {
            if (cur.val <= p.val) cur = cur.right;
            else {
                res = cur;
                cur = cur.left;
            }
        }
        return res;
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}
