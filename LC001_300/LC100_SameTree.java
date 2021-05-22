package LC001_300;
import java.util.*;
public class LC100_SameTree {
    /**
     * Given the roots of two binary trees p and q, write a function to check if they are the same or not.
     *
     * Two binary trees are considered the same if they are structurally identical, and the nodes have the same value.
     *
     * Input: p = [1,2,3], q = [1,2,3]
     * Output: true
     *
     * Constraints:
     *
     * The number of nodes in both trees is in the range [0, 100].
     * -10^4 <= Node.val <= 10^4
     *
     * @param p
     * @param q
     * @return
     */
    // time = O(n), space = O(n)
    public boolean isSameTree(TreeNode p, TreeNode q) {
        // corner case
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;

        if (p.val != q.val) return false;
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}
