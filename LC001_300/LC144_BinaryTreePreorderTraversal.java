package LC001_300;
import java.util.*;
public class LC144_BinaryTreePreorderTraversal {
    /**
     * Given the root of a binary tree, return the preorder traversal of its nodes' values.
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 100].
     * -100 <= Node.val <= 100
     * @param root
     * @return
     */
    // S!: iteration
    // time = O(n), space = O(n)
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            res.add(cur.val);
            if (cur.right != null) stack.push(cur.right);
            if (cur.left != null) stack.push(cur.left);
        }
        return res;
    }

    // S2: recursion
    // time = O(n), space = O(n)
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        preOrder(root, res);
        return res;
    }

    private void preOrder(TreeNode root, List<Integer> res) {
        if (root == null) return;

        res.add(root.val);
        preOrder(root.left, res);
        preOrder(root.right, res);
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}
