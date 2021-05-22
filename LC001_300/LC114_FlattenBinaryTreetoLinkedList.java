package LC001_300;
import java.util.*;
public class LC114_FlattenBinaryTreetoLinkedList {
    /**
     * Given the root of a binary tree, flatten the tree into a "linked list":
     *
     * The "linked list" should use the same TreeNode class where the right child pointer points to the next node in
     * the list and the left child pointer is always null.
     * The "linked list" should be in the same order as a pre-order traversal of the binary tree.
     *
     * Input: root = [1,2,5,3,4,null,6]
     * Output: [1,null,2,null,3,null,4,null,5,null,6]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 2000].
     * -100 <= Node.val <= 100
     *
     *
     * Follow up: Can you flatten the tree in-place (with O(1) extra space)?
     * @param root
     */
    // S1: recursion
    // time = O(n), space = O(n)
    private TreeNode prev = null;
    public void flatten(TreeNode root) {
        // corner case
        if (root == null) return;

        flatten(root.left);
        flatten(root.right);
        root.right = prev;
        root.left = null;
        prev = root;
    }

    // S2: iteration (最优解！！！）
    // time = O(n), space = O(1)
    public void flatten2(TreeNode root) {
        // corner case
        if (root == null) return;

        TreeNode cur = root;
        while (cur != null) {
            if (cur.left == null) cur = cur.right;
            else {
                TreeNode prev = cur.left;
                while (prev.right != null) prev = prev.right;
                prev.right = cur.right;
                cur.right = cur.left;
                cur.left = null;
                cur = cur.right;
            }
        }
    }
}
