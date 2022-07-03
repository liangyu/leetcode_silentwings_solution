package LC301_600;
import java.util.*;
public class LC515_FindLargestValueinEachTreeRow {
    /**
     * Given the root of a binary tree, return an array of the largest value in each row of the tree (0-indexed).
     *
     * Input: root = [1,3,2,5,3,null,9]
     * Output: [1,3,9]
     *
     * Constraints:
     *
     * The number of nodes in the tree will be in the range [0, 104].
     * -2^31 <= Node.val <= 2^31 - 1
     * @param root
     * @return
     */
    // S1: bfs
    // time = O(n), space = O(n)
    public List<Integer> largestValues(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            int max = Integer.MIN_VALUE;
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                max = Math.max(max, cur.val);
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
            }
            res.add(max);
        }
        return res;
    }

    // S2: dfs
    // time = O(n), space = O(n)
    HashMap<Integer, Integer> map;
    public List<Integer> largestValues2(TreeNode root) {
        map = new HashMap<>();

        dfs(root, 1);

        List<Integer> res = new ArrayList<>();
        for (int i = 1; i <= map.size(); i++) {
            res.add(map.get(i));
        }
        return res;
    }

    private void dfs(TreeNode node, int d) {
        if (node == null) return;

        if (!map.containsKey(d)) map.put(d, node.val);
        else map.put(d, Math.max(map.get(d), node.val));

        dfs(node.left, d + 1);
        dfs(node.right, d + 1);
    }
}
