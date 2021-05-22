package LC001_300;
import java.util.*;
public class LC104_MaximumDepthofBinaryTree {
    /**
     * Given the root of a binary tree, return its maximum depth.
     *
     * A binary tree's maximum depth is the number of nodes along the longest path from the root node down to the
     * farthest leaf node
     *
     * Input: root = [3,9,20,null,null,15,7]
     * Output: 3
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 10^4].
     * -100 <= Node.val <= 100
     *
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public int maxDepth(TreeNode root) {
        // corner case
        if (root == null) return 0;

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);
        return Math.max(left, right) + 1;
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}
