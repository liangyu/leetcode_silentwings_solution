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
    public void flatten(TreeNode root) {
        if (root == null) return;
        if (root.left == null) {
            flatten(root.right);
            return;
        }

        TreeNode h1 = root.left;
        TreeNode h2 = root.right;

        flatten(root.left);
        flatten(root.right);

        root.right = h1;
        root.left = null;

        while (h1.right != null) {
            h1 = h1.right;
        }
        h1.right = h2;
    }

    // S2: dfs
    // time = O(n), space = O(n)
    TreeNode prev = null;
    public void flatten1(TreeNode root) {
        if (root == null) return;

        flatten(root.right);
        flatten(root.left);
        root.right = prev; // 跨越子树的时候，只能通过prev来连接，比如例子中4 -> 5的时候，否则只能得到一半的左子树，右子树就不见了！
        root.left = null;
        prev = root;
    }

    // S2: iteration (最优解！！！）
    // time = O(n), space = O(1)
    public void flatten3(TreeNode root) {
        // corner case
        if (root == null) return;

        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode cur = stack.pop();
            if (cur.right != null) stack.push(cur.right);
            if (cur.left != null) stack.push(cur.left);

            // 等到叶子节点，比如4的时候，上面没有入栈，这时4就在这里指向了栈顶的右子树中的5！
            if (!stack.isEmpty()) cur.right = stack.peek();
            cur.left = null;
        }
    }
}
/**
 * flattern就是拉直的过程，这个命令是可以重复利用的。
 * 注意边界条件，如果左边为空的话，要单独考虑！
 */