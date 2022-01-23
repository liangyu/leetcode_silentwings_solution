package LC001_300;
import java.util.*;
public class LC94_BinaryTreeInorderTraversal {
    /**
     * Given the root of a binary tree, return the inorder traversal of its nodes' values.
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 100].
     * -100 <= Node.val <= 100
     *
     *
     * Follow up:
     *
     * Recursive solution is trivial, could you do it iteratively?
     *
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;

        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                TreeNode top = stack.pop();
                res.add(top.val);
                cur = top.right;
            }
        }
        return res;
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}
