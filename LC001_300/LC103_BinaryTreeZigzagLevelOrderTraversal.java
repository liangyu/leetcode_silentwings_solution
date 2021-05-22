package LC001_300;
import java.util.*;
public class LC103_BinaryTreeZigzagLevelOrderTraversal {
    /**
     * Given the root of a binary tree, return the zigzag level order traversal of its nodes' values. (i.e., from left
     * to right, then right to left for the next level and alternate between).
     *
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[3],[20,9],[15,7]]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 2000].
     * -100 <= Node.val <= 100
     *
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        boolean flag = false;

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                list.add(cur.val);
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
            if (flag) Collections.reverse(list);
            res.add(list);
            flag = !flag;
        }
        return res;
    }

    private class TreeNode {
        private int val;
        TreeNode left, right;
        public TreeNode(int val) {
            this.val = val;
        }
    }
}
