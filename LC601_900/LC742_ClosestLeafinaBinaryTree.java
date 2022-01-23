package LC601_900;
import java.util.*;
public class LC742_ClosestLeafinaBinaryTree {
    /**
     * Given the root of a binary tree where every node has a unique value and a target integer k, return the value of
     * the nearest leaf node to the target k in the tree.
     *
     * Nearest to a leaf means the least number of edges traveled on the binary tree to reach any leaf of the tree.
     * Also, a node is called a leaf if it has no children.
     *
     * Input: root = [1,3,2], k = 1
     * Output: 2
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [1, 1000].
     * 1 <= Node.val <= 1000
     * All the values of the tree are unique.
     * There exist some node in the tree where Node.val == k.
     * @param root
     * @param k
     * @return
     */
    // S1: BFS
    // time = O(n), space = O(n)
    HashMap<TreeNode, TreeNode> map;
    public int findClosestLeaf(TreeNode root, int k) {
        map = new HashMap<>();
        buildMap(root, root);

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        TreeNode t = null;

        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.val == k) {
                t = cur;
                break;
            }

            if (cur.left != null) queue.offer(cur.left);
            if (cur.right != null) queue.offer(cur.right);
        }

        queue.clear();
        queue.offer(t);
        HashSet<TreeNode> set = new HashSet<>();
        set.add(t);

        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.left == null && cur.right == null) return cur.val;

            if (cur.left != null && set.add(cur.left)) queue.offer(cur.left);
            if (cur.right != null && set.add(cur.right)) queue.offer(cur.right);
            if (set.add(map.get(cur))) queue.offer(map.get(cur));
        }
        return -1;
    }

    private void buildMap(TreeNode cur, TreeNode parent) {
        if (cur == null) return;
        map.put(cur, parent);
        buildMap(cur.left, cur);
        buildMap(cur.right, cur);
    }

    // S2: dfs
    HashMap<TreeNode, Integer> dist2Leaf; // {node -> dist to closest leaf}
    HashMap<TreeNode, Integer> node2Leaf; // {node -> closestLeaf}
    int dist = Integer.MAX_VALUE, res = 0;
    public int findClosestLeaf2(TreeNode root, int k) {
        dist2Leaf = new HashMap<>();
        node2Leaf = new HashMap<>();

        findDist2Leaf(root);
        findDist2K(root, k);
        return res;
    }

    private int findDist2Leaf(TreeNode node) {
        if (node == null) return Integer.MAX_VALUE;
        if (node.left == null && node.right == null) {
            dist2Leaf.put(node, 0);
            node2Leaf.put(node, node.val);
            return 0;
        }

        int left = findDist2Leaf(node.left);
        int right = findDist2Leaf(node.right);

        if (left < right) {
            dist2Leaf.put(node, left + 1);
            node2Leaf.put(node, node2Leaf.get(node.left));
            return left + 1;
        } else {
            dist2Leaf.put(node, right + 1);
            node2Leaf.put(node, node2Leaf.get(node.right));
            return right + 1;
        }
    }

    private int findDist2K(TreeNode node, int k) {
        if (node == null) return -1;

        if (node.val == k) {
            if (dist > dist2Leaf.get(node)) {
                dist = dist2Leaf.get(node);
                res = node2Leaf.get(node);
            }
            return 0;
        }

        int left = findDist2K(node.left, k);
        if (left != -1) {
            if (node.right != null && dist > left + 2 + dist2Leaf.get(node.right)) {
                dist = left + 2 + dist2Leaf.get(node.right);
                res = node2Leaf.get(node.right);
            }
            return left + 1;
        }

        int right = findDist2K(node.right, k);
        if (right != -1) {
            if (node.left != null && dist > right + 2 + dist2Leaf.get(node.left)) {
                dist = right + 2 + dist2Leaf.get(node.left);
                res = node2Leaf.get(node.left);
            }
            return right + 1;
        }
        return -1;
    }
}
