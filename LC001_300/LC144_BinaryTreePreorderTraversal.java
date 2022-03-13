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
    // S1: iteration
    // time = O(n), space = O(n)
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur == null) {
                cur = stack.pop().right;
            } else {
                res.add(cur.val);
                stack.push(cur);
                cur = cur.left;
            }
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
}
/**
 * 栈的3种用法：
 * 1. 表达式，左右括号匹配
 * 2. 单调栈，O(n) next greater element
 * 3. 遍历一棵树
 * 对于树的问题，理论上都是用递归来做
 * 先序：先遍历根，再遍历左子树和右子树
 * 必须保留根，因为访问完左子树后还要去访问右子树
 * node要存起来，目的是读完左子树后读右子树
 * res: 1,2,3,null,4,null,null,null,5,null,null
 * stack:
 * 1. 先序遍历的时候，我们读什么，栈里就放什么
 * 2. 每次读到null，穷途末路了，我们直接看栈顶元素，找它的右子树
 */