package LC001_300;
import java.util.*;
public class LC298_BinaryTreeLongestConsecutiveSequence {
    /**
     * Given the root of a binary tree, return the length of the longest consecutive sequence path.
     *
     * The path refers to any sequence of nodes from some starting node to any node in the tree along the parent-child
     * connections. The longest consecutive path needs to be from parent to child (cannot be the reverse).
     *
     * Input: root = [1,null,3,2,4,null,null,null,5]
     * Output: 3
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 3 * 10^4].
     * -3 * 10^4 <= Node.val <= 3 * 10^4
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private int res = 0;
    public int longestConsecutive(TreeNode root) {
        // corner case
        if (root == null) return 0;

        helper(root, 0, root.val);
        return res;
    }

    private void helper(TreeNode root, int max, int target) {
        if (root == null) return;
        if (root.val == target) max++;
        else max = 1;

        res = Math.max(res, max);
        helper(root.left, max, root.val + 1);
        helper(root.right, max, root.val + 1);
    }
}
