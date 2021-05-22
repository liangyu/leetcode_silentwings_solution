package LC001_300;
import java.util.*;
public class LC250_CountUnivalueSubtrees {
    /**
     * Given the root of a binary tree, return the number of uni-value subtrees.
     *
     * A uni-value subtree means all nodes of the subtree have the same value.
     *
     * Input: root = [5,1,5,5,5,null,5]
     * Output: 4
     *
     * Constraints:
     *
     * The numbrt of the node in the tree will be in the range [0, 1000].
     * -1000 <= Node.val <= 1000
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private int count = 0;
    public int countUnivalSubtrees(TreeNode root) {
        // corner case
        if (root == null) return 0;

        dfs(root, root.val);
        return count;
    }

    private boolean dfs(TreeNode root, int val) {
        // base case
        if (root == null) return true;

        boolean l = dfs(root.left, root.val);
        boolean r = dfs(root.right, root.val);

        if (l && r) {
            count++;
            return root.val == val;
        } else return false;
    }
}
