package LC001_300;
import java.util.*;
public class LC99_RecoverBinarySearchTree {
    /**
     * You are given the root of a binary search tree (BST), where exactly two nodes of the tree were swapped by
     * mistake. Recover the tree without changing its structure.
     *
     * Follow up: A solution using O(n) space is pretty straight forward. Could you devise a constant space solution?
     *
     * Input: root = [1,3,null,null,2]
     * Output: [3,1,null,null,2]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [2, 1000].
     * -2^31 <= Node.val <= 2^31 - 1
     * @param root
     */
    // time = O(n), space = O(n)
    private TreeNode prev = null;
    public void recoverTree(TreeNode root) {
        // corner case
        if (root == null) return;

        TreeNode[] mistakes = new TreeNode[2];
        dfs(root, mistakes);
        swap(mistakes);
    }

    private void dfs(TreeNode cur, TreeNode[] mistakes) {
        // base case
        if (cur == null) return;

        dfs(cur.left, mistakes);
        if (prev != null && prev.val > cur.val) {
            mistakes[1] = cur;
            if (mistakes[0] == null) mistakes[0] = prev;
        }
        prev = cur;
        dfs(cur.right, mistakes);
    }

    private void swap(TreeNode[] mistakes) {
        int temp = mistakes[0].val;
        mistakes[0].val = mistakes[1].val;
        mistakes[1].val = temp;
    }

    private class TreeNode {
        private int val;
        private TreeNode left, right;
    }
}
