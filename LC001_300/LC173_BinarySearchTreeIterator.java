package LC001_300;
import java.util.*;
public class LC173_BinarySearchTreeIterator {
    /**
     * Implement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree
     * (BST):
     *
     * BSTIterator(TreeNode root) Initializes an object of the BSTIterator class. The root of the BST is given as part
     * of the constructor. The pointer should be initialized to a non-existent number smaller than any element in the
     * BST.
     * boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer, otherwise
     * returns false.
     * int next() Moves the pointer to the right, then returns the number at the pointer.
     * Notice that by initializing the pointer to a non-existent smallest number, the first call to next() will return
     * the smallest element in the BST.
     *
     * You may assume that next() calls will always be valid. That is, there will be at least a next number in the
     * in-order traversal when next() is called.
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 105].
     * 0 <= Node.val <= 106
     * At most 105 calls will be made to hasNext, and next.
     *
     *
     * Follow up:
     *
     * Could you implement next() and hasNext() to run in average O(1) time and use O(h) memory, where h is the height
     * of the tree?
     *
     * @param root
     */
    Stack<TreeNode> stack;
    public LC173_BinarySearchTreeIterator(TreeNode root) {
        stack = new Stack<>();
        TreeNode cur = root;

        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
    }

    // time = O(n), space = O(n)
    public int next() {
        TreeNode top = stack.pop();
        TreeNode cur = top.right;
        while (cur != null) {
            stack.push(cur);
            cur = cur.left;
        }
        return top.val;
    }

    // time = O(1), space = O(n)
    public boolean hasNext() {
        return !stack.isEmpty();
    }
}
/**
 * BST: 中序遍历，依次递归下去
 * 对于树的遍历，递归比较好写
 * 这里用迭代的方法，一个接一个的遍历
 * 永远往左走，找到最小值
 * 接下来会遍历root => use stack
 */