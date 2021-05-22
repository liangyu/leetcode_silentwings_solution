package LC001_300;
import java.util.*;
public class LC222_CountCompleteTreeNodes {
    /**
     * Given the root of a complete binary tree, return the number of the nodes in the tree.
     *
     * According to Wikipedia, every level, except possibly the last, is completely filled in a complete binary tree,
     * and all nodes in the last level are as far left as possible. It can have between 1 and 2h nodes inclusive at the
     * last level h.
     *
     * Input: root = [1,2,3,4,5,6]
     * Output: 6
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 5 * 10^4].
     * 0 <= Node.val <= 5 * 10^4
     * The tree is guaranteed to be complete.
     *
     *
     * Follow up: Traversing the tree to count the number of nodes in the tree is an easy solution but with O(n)
     * complexity. Could you find a faster algorithm?
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public int countNodes(TreeNode root) {
        // corner case
        if (root == null) return 0;

        int lh = getHeight(root.left);
        int rh = getHeight(root.right);

        if (lh == rh) {
            return countNodes(root.right) + 1 + (int)Math.pow(2, lh) - 1;
        } else if (lh == rh + 1) {
            return countNodes(root.left) + 1 + (int)Math.pow(2, rh) - 1;
        } else throw new RuntimeException("Not a valid complete tree!");
    }

    private int getHeight(TreeNode cur) {
        int height = 0;
        while (cur != null) {
            cur = cur.left;
            height++;
        }
        return height;
    }
}
/**
 * complete nodes = 2^h - 1   h: height
 * 看究竟左边满树还是右边满树，满树的可以用上述公式，不满的用recursion
 * 别忘了 + 1 (root)
 */
