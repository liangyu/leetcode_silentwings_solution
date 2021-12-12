package LC1801_2100;
import java.util.*;
public class LC2096_StepByStepDirectionsFromaBinaryTreeNodetoAnother {
    /**
     * You are given the root of a binary tree with n nodes. Each node is uniquely assigned a value from 1 to n. You
     * are also given an integer startValue representing the value of the start node s, and a different integer
     * destValue representing the value of the destination node t.
     *
     * Find the shortest path starting from node s and ending at node t. Generate step-by-step directions of such path
     * as a string consisting of only the uppercase letters 'L', 'R', and 'U'. Each letter indicates a specific direction:
     *
     * 'L' means to go from a node to its left child node.
     * 'R' means to go from a node to its right child node.
     * 'U' means to go from a node to its parent node.
     * Return the step-by-step directions of the shortest path from node s to node t.
     *
     * Input: root = [5,1,2,3,null,6,4], startValue = 3, destValue = 6
     * Output: "UURL"
     *
     * Input: root = [2,1], startValue = 2, destValue = 1
     * Output: "L"
     *
     * Constraints:
     *
     * The number of nodes in the tree is n.
     * 2 <= n <= 10^5
     * 1 <= Node.val <= n
     * All the values in the tree are unique.
     * 1 <= startValue, destValue <= n
     * startValue != destValue
     * @param root
     * @param startValue
     * @param destValue
     * @return
     */
    // time = O(n), space = O(n)
    HashMap<TreeNode, TreeNode> map;
    StringBuilder sb1, sb2;
    public String getDirections(TreeNode root, int startValue, int destValue) {
        map = new HashMap<>();
        sb1 = new StringBuilder();
        sb2 = new StringBuilder();

        buildMap(root, root);
        TreeNode ancestor = lca(root, startValue, destValue);
        TreeNode[] nodes = findNodes(root, startValue, destValue);

        TreeNode cur = nodes[0];
        while (cur != ancestor) {
            cur = map.get(cur);
            sb1.append('U');
        }

        cur = nodes[1];
        while (cur != ancestor) {
            TreeNode p = map.get(cur);
            if (p.left == cur) sb2.append('L');
            else sb2.append('R');
            cur = p;
        }

        sb1.append(sb2.reverse());
        return sb1.toString();
    }

    private void buildMap(TreeNode cur, TreeNode parent) {
        if (cur == null) return;
        map.put(cur, parent);
        buildMap(cur.left, cur);
        buildMap(cur.right, cur);
    }

    private TreeNode lca(TreeNode root, int p, int q) {
        if (root == null) return null;
        if (p == root.val || q == root.val) return root;

        TreeNode left = lca(root.left, p, q);
        TreeNode right = lca(root.right, p, q);

        if (left != null && right != null) return root;
        return left == null ? right : left;
    }

    private TreeNode[] findNodes(TreeNode root, int start, int end) {
        TreeNode[] nodes = new TreeNode[2];
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode cur = queue.poll();
            if (cur.val == start) nodes[0] = cur;
            if (cur.val == end) nodes[1] = cur;
            if (nodes[0] != null && nodes[1] != null) break;

            if (cur.left != null) queue.offer(cur.left);
            if (cur.right != null) queue.offer(cur.right);
        }
        return nodes;
    }
}
