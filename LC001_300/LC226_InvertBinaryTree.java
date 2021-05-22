package LC001_300;
import java.util.*;
public class LC226_InvertBinaryTree {
    /**
     * Given the root of a binary tree, invert the tree, and return its root.
     *
     * Input: root = [4,2,7,1,3,6,9]
     * Output: [4,7,2,9,6,3,1]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 100].
     * -100 <= Node.val <= 100
     * @param root
     * @return
     */
    // S1: recursion
    // time = O(n), space = O(n)
    public TreeNode invertTree(TreeNode root) {
        // corner case
        if (root == null) return root;

        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;
    }

    // S2: iteration
    // time = O(n), space = O(n)
    public TreeNode invertTree2(TreeNode root) {
        // corner case
        if (root == null) return root;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            TreeNode temp = cur.left;
            cur.left = cur.right;
            cur.right = temp;
            if (cur.left != null) queue.offer(cur.left);
            if (cur.right != null) queue.offer(cur.right);
        }
        return root;
    }
}
