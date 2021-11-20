package LC901_1200;
import java.util.*;
public class LC987_VerticalOrderTraversalofaBinaryTree {
    /**
     * Given the root of a binary tree, calculate the vertical order traversal of the binary tree.
     *
     * For each node at position (row, col), its left and right children will be at positions (row + 1, col - 1) and
     * (row + 1, col + 1) respectively. The root of the tree is at (0, 0).
     *
     * The vertical order traversal of a binary tree is a list of top-to-bottom orderings for each column index
     * starting from the leftmost column and ending on the rightmost column. There may be multiple nodes in the same
     * row and same column. In such a case, sort these nodes by their values.
     *
     * Return the vertical order traversal of the binary tree.
     *
     * Input: root = [3,9,20,null,null,15,7]
     * Output: [[9],[3,15],[20],[7]]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 1000].
     * 0 <= Node.val <= 1000
     * @param root
     * @return
     */
    // time = O(nlogn), space = O(n)
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        TreeMap<Integer, List<Integer>> map = new TreeMap<>();

        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 0));

        while (!queue.isEmpty()) {
            int size = queue.size();
            HashMap<Integer, List<Integer>> temp = new HashMap<>();
            while (size-- > 0) {
                Pair cur = queue.poll();
                TreeNode node = cur.node;
                int idx = cur.idx;

                temp.putIfAbsent(idx, new ArrayList<>());
                temp.get(idx).add(node.val);

                if (node.left != null) queue.offer(new Pair(node.left, idx - 1));
                if (node.right != null) queue.offer(new Pair(node.right, idx + 1));
            }
            for (int key : temp.keySet()) {
                List<Integer> list = temp.get(key);
                Collections.sort(list);
                map.putIfAbsent(key, new ArrayList<>());
                map.get(key).addAll(list);
            }
        }

        for (int key : map.keySet()) {
            List<Integer> list = map.get(key);
            res.add(list);
        }
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
