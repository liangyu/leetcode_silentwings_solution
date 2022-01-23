package LC901_1200;
import java.util.*;
public class LC958_CheckCompletenessofaBinaryTree {
    /**
     * Given the root of a binary tree, determine if it is a complete binary tree.
     *
     * In a complete binary tree, every level, except possibly the last, is completely filled, and all nodes in the last
     * level are as far left as possible. It can have between 1 and 2h nodes inclusive at the last level h.
     *
     * Input: root = [1,2,3,4,5,6]
     * Output: true
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 100].
     * 1 <= Node.val <= 1000
     * @param root
     * @return
     */
    // S1: dfs
    // time = O(n), space = O(n)
    public boolean isCompleteTree(TreeNode root) {
        int num = countNodes(root);
        return dfs(root, 0, num);
    }

    private int countNodes(TreeNode node) {
        if (node == null) return 0;
        return countNodes(node.left) + 1 + countNodes(node.right);
    }

    private boolean dfs(TreeNode node, int idx, int num) {
        if (node == null) return true;
        if (idx >= num) return false;
        return dfs(node.left, idx * 2 + 1, num) && dfs(node.right, idx * 2 + 2, num);
    }

    // S2: bfs
    // time = O(n), space = O(n)
    public boolean isCompleteTree2(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur == null) break; // 在遇到最后一个节点之前，所有的节点都是非空的
            queue.offer(cur.left);
            queue.offer(cur.right);
        }

        while (!queue.isEmpty()) { // 队列中剩下的所有的节点必须都是null
            if (queue.poll() != null) return false;
        }
        return true;
    }
}
