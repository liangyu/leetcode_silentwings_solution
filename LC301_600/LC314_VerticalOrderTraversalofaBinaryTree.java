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
        map.putIfAbsent(0, new ArrayList<>());
        map.get(0).add(root.val);

        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(root, 0));

        int minCol = 0, maxCol = 0;
        while (!queue.isEmpty()) {
            Cell cur = queue.poll();
            minCol = Math.min(minCol, cur.idx);
            maxCol = Math.max(maxCol, cur.idx);

            if (cur.node.left != null) {
                map.putIfAbsent(cur.idx - 1, new ArrayList<>());
                map.get(cur.idx - 1).add(cur.node.left.val);
                queue.offer(new Cell(cur.node.left, cur.idx - 1));
            }
            if (cur.node.right != null) {
                map.putIfAbsent(cur.idx + 1, new ArrayList<>());
                map.get(cur.idx + 1).add(cur.node.right.val);
                queue.offer(new Cell(cur.node.right, cur.idx + 1));
            }
        }

        for (int i = minCol; i <= maxCol; i++) {
            res.add(map.get(i));
        }
        return res;
    }

    private class Cell {
        private TreeNode node;
        private int idx;
        public Cell(TreeNode node, int idx) {
            this.node = node;
            this.idx = idx;
        }
    }

    private class TreeNode {
        private int val;
        private TreeNode left, right;
    }

    /**
     * Definition for a binary tree node.
     * public class TreeNode {
     *     int val;
     *     TreeNode left;
     *     TreeNode right;
     *     TreeNode() {}
     *     TreeNode(int val) { this.val = val; }
     *     TreeNode(int val, TreeNode left, TreeNode right) {
     *         this.val = val;
     *         this.left = left;
     *         this.right = right;
     *     }
     * }
     */
}
