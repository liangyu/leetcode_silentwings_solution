package LC1501_1800;
import java.util.*;
public class LC1609_EvenOddTree {
    /**
     * A binary tree is named Even-Odd if it meets the following conditions:
     *
     * The root of the binary tree is at level index 0, its children are at level index 1, their children are at level
     * index 2, etc.
     * For every even-indexed level, all nodes at the level have odd integer values in strictly increasing order (from
     * left to right).
     * For every odd-indexed level, all nodes at the level have even integer values in strictly decreasing order (from
     * left to right).
     * Given the root of a binary tree, return true if the binary tree is Even-Odd, otherwise return false.
     *
     * Input: root = [1,10,4,3,null,7,9,12,8,6,null,null,2]
     * Output: true
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 10^5].
     * 1 <= Node.val <= 10^6
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public boolean isEvenOddTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            int prev = 0;
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);

                if (step % 2 == 0 && cur.val % 2 == 0) return false;
                if (step % 2 == 1 && cur.val % 2 == 1) return false;

                if (prev > 0) {
                    if (step % 2 == 0 && cur.val <= prev) return false;
                    if (step % 2 == 1 && cur.val >= prev) return false;
                }
                prev = cur.val;
            }
            step++;
        }
        return true;
    }
}
