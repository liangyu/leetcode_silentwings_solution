package LC001_300;
import java.util.*;
public class LC107_BinaryTreeLevelOrderTraversalII {
    /**
     * Given the root of a binary tree, return the bottom-up level order traversal of its nodes' values. (i.e., from
     * left to right, level by level from leaf to root).
     *
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[15,7],[9,20],[3]]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 2000].
     * -1000 <= Node.val <= 1000
     *
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        // corner case
        List<List<Integer>> res = new LinkedList<>();
        // corner case
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new LinkedList<>();
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                list.add(cur.val);
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
            res.add(0, list);
        }
        return res;
    }

    class TreeNode {
        int val;
        TreeNode left, right;
    }
}
