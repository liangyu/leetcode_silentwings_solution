package LC1501_1800;
import java.util.*;
public class LC1586_BinarySearchTreeIteratorII {
    /**
     * Implement the BSTIterator class that represents an iterator over the in-order traversal of a binary search tree
     * (BST):
     *
     * BSTIterator(TreeNode root) Initializes an object of the BSTIterator class. The root of the BST is given as part
     * of the constructor. The pointer should be initialized to a non-existent number smaller than any element in the BST.
     * boolean hasNext() Returns true if there exists a number in the traversal to the right of the pointer, otherwise
     * returns false.
     * int next() Moves the pointer to the right, then returns the number at the pointer.
     * boolean hasPrev() Returns true if there exists a number in the traversal to the left of the pointer, otherwise
     * returns false.
     * int prev() Moves the pointer to the left, then returns the number at the pointer.
     * Notice that by initializing the pointer to a non-existent smallest number, the first call to next() will return
     * the smallest element in the BST.
     *
     * You may assume that next() and prev() calls will always be valid. That is, there will be at least a next/previous
     * number in the in-order traversal when next()/prev() is called.
     *
     * Input
     * ["BSTIterator", "next", "next", "prev", "next", "hasNext", "next", "next", "next", "hasNext", "hasPrev", "prev",
     * "prev"]
     * [[[7, 3, 15, null, null, 9, 20]], [null], [null], [null], [null], [null], [null], [null], [null], [null], [null],
     * [null], [null]]
     * Output
     * [null, 3, 7, 3, 7, true, 9, 15, 20, false, true, 15, 9]
     *
     * Follow up: Could you solve the problem without precalculating the values of the tree?
     * @param root
     */
    // time = O(n), space = O(n)
    Stack<Pair> next;
    Stack<TreeNode> visited;
    public LC1586_BinarySearchTreeIteratorII(TreeNode root) {
        next = new Stack<>();
        visited = new Stack<>();

        TreeNode cur = root;
        while (cur != null) {
            next.push(new Pair(cur, true));
            cur = cur.left;
        }
    }

    public boolean hasNext() {
        return !next.isEmpty();
    }

    public int next() {
        Pair p = next.pop();
        TreeNode node = p.node;
        boolean flag = p.flag;

        if (flag) {  // if flag is true -> the right subtree has not been visited yet, push it into stack to prepare
            TreeNode cur = node.right;
            while (cur != null) {
                next.push(new Pair(cur, true));
                cur = cur.left;
            }
        }
        visited.push(node);
        return node.val;
    }

    public boolean hasPrev() {
        // visited的栈顶是刚访问过的，prev在离栈顶的第二位置上！
        return visited.size() > 1;
    }

    public int prev() {
        next.push(new Pair(visited.pop(), false)); // pop已经访问过了的纠正过顺序的元素，所以标记为false，next后不需要重新访问右子树了。
        return visited.peek().val;
    }

    private class Pair {
        private TreeNode node;
        private boolean flag;
        public Pair(TreeNode node, boolean flag) {
            this.node = node;
            this.flag = flag;
        }
    }
}
