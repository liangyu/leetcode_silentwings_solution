package LC601_900;
import java.util.*;
public class LC662_MaximumWidthofBinaryTree {
    /**
     * Given the root of a binary tree, return the maximum width of the given tree.
     *
     * The maximum width of a tree is the maximum width among all levels.
     *
     * The width of one level is defined as the length between the end-nodes (the leftmost and rightmost non-null nodes),
     * where the null nodes between the end-nodes are also counted into the length calculation.
     *
     * It is guaranteed that the answer will in the range of 32-bit signed integer.
     *
     * Input: root = [1,3,2,5,3,null,9]
     * Output: 4
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 3000].
     * -100 <= Node.val <= 100
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) return 0;

        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        root.val = 0;

        int res = 1;
        while (!deque.isEmpty()) {
            int size = deque.size();
            res = Math.max(res, deque.peekLast().val - deque.peekFirst().val + 1);
            while (size-- > 0) {
                TreeNode cur = deque.pollFirst();
                if (cur.left != null) {
                    cur.left.val = cur.val * 2;
                    deque.offer(cur.left);
                }
                if (cur.right != null) {
                    cur.right.val = cur.val * 2 + 1;
                    deque.offer(cur.right);
                }
            }
        }
        return res;
    }
}
/**
 * bfs
 * 属于同一层级的结点，看它们的编号之差
 * node.val
 * left.val = node.val * 2
 * right.val = node.val * 2 + 1
 * 优化的方法是：如果某一层只有一个节点的话，我们可以把那个节点看做成根节点，并将其序号reset成为0.
 * 这样它之后层级的节点序号就又变小了，同时也不影响最终结果。
 */