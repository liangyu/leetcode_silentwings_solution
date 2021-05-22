package LC1201_1500;
import java.util.*;
public class LC1302_DeepestLeavesSum {
    /**
     * Given the root of a binary tree, return the sum of values of its deepest leaves.
     *
     * Input: root = [1,2,3,4,5,null,6,7,null,null,null,null,8]
     * Output: 15
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 104].
     * 1 <= Node.val <= 100
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public int deepestLeavesSum(TreeNode root) {
        // corner case
        if (root == null) throw new IllegalArgumentException();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int height = getHeight(root);
        int sum = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                height--;
                TreeNode cur = queue.poll();
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
                if (height == 0) sum += cur.val;
            }
        }
        return sum;
    }

    private int getHeight(TreeNode cur) {
        if (cur == null) return 0;
        int left = getHeight(cur.left);
        int right = getHeight(cur.right);
        return Math.max(left, right) + 1;
    }

    private class TreeNode {
        private int val;
        private TreeNode left, right;
    }
}
