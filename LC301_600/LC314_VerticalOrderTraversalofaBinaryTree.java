package LC301_600;
import java.util.*;
public class LC314_VerticalOrderTraversalofaBinaryTree {
    /**
     * Given the root of a binary tree, return the vertical order traversal of its nodes' values.
     * (i.e., from top to bottom, column by column).
     *
     * If two nodes are in the same row and column, the order should be from left to right.
     *
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[9],[3,15],[20],[7]]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 100].
     * -100 <= Node.val <= 100
     *
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public List<List<Integer>> verticalOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        HashMap<Integer, List<Integer>> map = new HashMap<>();
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 0));
        int minIdx = 0, maxIdx = 0;

        while (!queue.isEmpty()) {
            Pair cur = queue.poll();
            TreeNode node = cur.node;
            int idx = cur.idx;
            map.putIfAbsent(idx, new ArrayList<>());
            map.get(idx).add(node.val);

            if (node.left != null) {
                queue.offer(new Pair(node.left, idx - 1));
                minIdx = Math.min(minIdx, idx - 1);
            }
            if (node.right != null) {
                queue.offer(new Pair(node.right, idx + 1));
                maxIdx = Math.max(maxIdx, idx + 1);
            }
        }

        for (int i = minIdx; i <= maxIdx; i++) res.add(map.get(i));
        return res;
    }

    private class Pair {
        private TreeNode node;
        private int idx;
        public Pair(TreeNode node, int idx) {
            this.node = node;
            this.idx = idx;
        }
    }
}