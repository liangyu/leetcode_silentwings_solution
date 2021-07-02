package LC301_600;
import java.util.*;
public class LC366_FindLeavesofBinaryTree {
    /**
     * Given the root of a binary tree, collect a tree's nodes as if you were doing this:
     *
     * Collect all the leaf nodes.
     * Remove all the leaf nodes.
     * Repeat until the tree is empty.
     *
     * Input: root = [1,2,3,4,5]
     * Output: [[4,5,3],[2],[1]]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 100].
     * -100 <= Node.val <= 100
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        helper(root, res);
        return res;
    }

    private int helper(TreeNode root, List<List<Integer>> res) {
        if (root == null) return -1;

        int left = helper(root.left, res);
        int right = helper(root.right, res);

        int level = Math.max(left, right) + 1;
        if (res.size() == level) res.add(new ArrayList<>());
        res.get(level).add(root.val);
        root.left = null; // remove leaves
        root.right = null;
        return level;
    }
}
