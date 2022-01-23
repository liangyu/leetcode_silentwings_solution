package LC001_300;
import java.util.*;
public class LC145_BinaryTreePostorderTraversal {
    /**
     * Given the root of a binary tree, return the postorder traversal of its nodes' values.
     * @param root
     * @return
     */
    // S1: Stack
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

    // S2: Stack + HashSet
    // time = O(n), space = O(n)
    public List<Integer> postorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        HashSet<TreeNode> set = new HashSet<>();

        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()) {
            if (cur != null) {
                stack.push(cur);
                cur = cur.left;
            } else {
                cur = stack.peek();
                if (set.add(cur)) cur = cur.right;
                else {
                    res.add(cur.val);
                    stack.pop();
                    cur = null;
                }
            }
        }
        return res;
    }

    // S3: DFS
    // time = O(n), space = O(n)
    List<Integer> res;
    public List<Integer> postorderTraversal3(TreeNode root) {
        res = new ArrayList<>();

        postorder(root);
        return res;
    }

    private void postorder(TreeNode node) {
        if (node == null) return;

        postorder(node.left);
        postorder(node.right);
        res.add(node.val);
    }
}
/**
 * postorder区别：根要遍历好几次
 * 左子树都读完，右子树都读完，再读root
 * res: (...)(...) 5
 * stack:
 * set: 2,1,3,5,4
 * res: 1,2,3,4,5
 * 操作完后指针指向null，这样才能吸引其看栈顶元素
 * 右子树遍历完之后，读完栈顶这个值之后才能退栈
 * 第一次见到5，会放到栈里，然后遍历左子树
 * 再看下5，这时候只是用来找它的右子树，遍历完右子树后回来
 * 这个时候才是真正读5的值
 * 为了区分2次对5的操作，可以用一个集合set
 * 第一次在栈顶访问它，看看set里有没有它
 * 到第二次看到它，如果在set里，那么就读它，然后就可以退栈了
 */