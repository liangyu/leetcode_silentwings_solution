package LC001_300;
import java.util.*;
public class LC145_BinaryTreePostorderTraversal {
    /**
     * Given the root of a binary tree, return the postorder traversal of its nodes' values.
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode prev = null;
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode cur = stack.peek();
            if (prev == null || prev.left == cur || prev.right == cur) {
                if (cur.left != null) stack.push(cur.left);
                else if (cur.right != null) stack.push(cur.right);
                else res.add(stack.pop().val);
            } else if (prev == cur.left) {
                if (cur.right != null) stack.push(cur.right);
                else res.add(stack.pop().val);
            } else {
                res.add(stack.pop().val);
            }
            prev = cur;
        }
        return res;
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}
