package LC601_900;
import java.util.*;
public class LC701_InsertintoaBinarySearchTree {
    /**
     * You are given the root node of a binary search tree (BST) and a value to insert into the tree. Return the root
     * node of the BST after the insertion. It is guaranteed that the new value does not exist in the original BST.
     *
     * Notice that there may exist multiple valid ways for the insertion, as long as the tree remains a BST after
     * insertion. You can return any of them.
     *
     * Input: root = [4,2,7,1,3], val = 5
     * Output: [4,2,7,1,3,5]
     *
     * Constraints:
     *
     * The number of nodes in the tree will be in the range [0, 10^4].
     * -10^8 <= Node.val <= 10^8
     * All the values Node.val are unique.
     * -10^8 <= val <= 10^8
     * It's guaranteed that val does not exist in the original BST.
     *
     * @param root
     * @param val
     * @return
     */
    // S1: Recursion
    // time = O(n), space = O(n)
    public TreeNode insertIntoBST(TreeNode root, int val) {
        // corner case
        if (root == null) return new TreeNode(val);

        if (root.val < val) root.right = insertIntoBST(root.right, val);
        else root.left = insertIntoBST(root.left, val);
        return root;
    }

    // S2: Iteration
    // time = O(n), space = O(n)
    public TreeNode insertIntoBST2(TreeNode root, int val) {
        // corner case
        if (root == null) return new TreeNode(val);

        TreeNode cur = root;

        while (cur != null) {
            if (cur.val < val) {
                if (cur.right == null) {
                    cur.right = new TreeNode(val);
                    return root;
                } else cur = cur.right;
            } else {
                if (cur.left == null) {
                    cur.left = new TreeNode(val);
                    return root;
                } else cur = cur.left;
            }
        }
        return new TreeNode(val);
    }

    class TreeNode {
        int val;
        TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
        }
    }
}
