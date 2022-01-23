package LC001_300;
import java.util.*;
public class LC285_InorderSuccessorinBST {
    /**
     * Given the root of a binary search tree and a node p in it, return the in-order successor of that node in the BST.
     * If the given node has no in-order successor in the tree, return null.
     *
     * The successor of a node p is the node with the smallest key greater than p.val.
     *
     * Input: root = [2,1,3], p = 1
     * Output: 2
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 104].
     * -10^5 <= Node.val <= 10^5
     * All Nodes will have unique values.
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
}
