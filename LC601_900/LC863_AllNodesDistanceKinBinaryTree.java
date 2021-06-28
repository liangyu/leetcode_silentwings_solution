package LC601_900;
import java.util.*;
public class LC863_AllNodesDistanceKinBinaryTree {
    /**
     * We are given a binary tree (with root node root), a target node, and an integer value k.
     *
     * Return a list of the values of all nodes that have a distance k from the target node.  The answer can be returned
     * in any order.
     *
     * Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
     *
     * Output: [7,4,1]
     *
     * Note:
     *
     * The given tree is non-empty.
     * Each node in the tree has unique values 0 <= node.val <= 500.
     * The target node is a node in the tree.
     * 0 <= k <= 1000.
     * @param root
     * @param target
     * @param K
     * @return
     */
    // time = O(n), space = O(n)
    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> res = new ArrayList<>();

        HashMap<TreeNode, TreeNode> map = new HashMap<>();
        buildMap(map, root, null);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(target);

        HashSet<TreeNode> set = new HashSet<>();
        set.add(target);

        int step = 0;
        while (!queue.isEmpty()) {
            if (step == K) break;
            int size = queue.size();
            while (size-- > 0) {
                TreeNode cur = queue.poll();
                if (map.containsKey(cur) && set.add(map.get(cur))) queue.offer(map.get(cur));
                if (cur.left != null && set.add(cur.left)) queue.offer(cur.left);
                if (cur.right != null && set.add(cur.right)) queue.offer(cur.right);
            }
            step++;
        }

        while (!queue.isEmpty()) res.add(queue.poll().val);
        return res;
    }

    private void buildMap(HashMap<TreeNode, TreeNode> map, TreeNode cur, TreeNode parent) {
        if (cur == null) return;
        if (parent != null) map.put(cur, parent);
        if (cur.left != null) buildMap(map, cur.left, cur);
        if (cur.right != null) buildMap(map, cur.right, cur);
    }
}
