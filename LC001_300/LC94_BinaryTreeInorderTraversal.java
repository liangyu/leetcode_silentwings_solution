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
     * Follow up:
     *
     * Recursive solution is trivial, could you do it iteratively?
     *
     * @param root
     * @return
     */
    // S1: Stack
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
                cur = stack.pop();
                res.add(cur.val);
                cur = cur.right;
            }
        }
        return res;
    }

    // S2: DFS
    List<Integer> res;
    public List<Integer> inorderTraversal2(TreeNode root) {
        res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        inorder(root);
        return res;
    }

    private void inorder(TreeNode node) {
        if (node == null) return;

        inorder(node.left);
        res.add(node.val);
        inorder(node.right);
    }
}
/**
 * root不能立即记录val，因为要先读左子树
 * 那么一旦往下走了，怎么回来？
 * 这个时候就要用一个栈,这样左子树读完后，可以通过栈顶元素读到根节点，然后去到右子树
 * res: 1,2,3,4,5
 * stack:
 * 一路都往左下角走，每碰到一个新节点，不停留，存到栈里，直到走不动了
 * 取栈顶元素，读取值，再去右边
 * 1. node != null => stack.push(node), node = node.left
 * 2. node == null => node = stack.peek(), node = node.right, stack.pop()
 */
