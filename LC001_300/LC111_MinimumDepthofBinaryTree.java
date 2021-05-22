package LC001_300;
import java.util.*;
public class LC111_MinimumDepthofBinaryTree {
    /**
     * Given a binary tree, find its minimum depth.
     *
     * The minimum depth is the number of nodes along the shortest path from the root node down to the nearest leaf node.
     *
     * Note: A leaf is a node with no children.
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 105].
     * -1000 <= Node.val <= 1000
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public int minDepth(TreeNode root) {
        // corner case
        if (root == null) return 0;

        if (root.left == null && root.right == null) return 1;

        int min = Integer.MAX_VALUE;
        // 注意： 这里不能用left = minDepth(root.left),因为如果一边为null的话 => minDepth = 0，显然不对，应该是不为null的一边的高度
        if (root.left != null) min = Math.min(min, minDepth(root.left));
        if (root.right != null) min = Math.min(min, minDepth(root.right));
        return min + 1;
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}