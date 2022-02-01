package LC301_600;
import java.util.*;
public class LC449_SerializeandDeserializeBST {
    /**
     * Serialization is converting a data structure or object into a sequence of bits so that it can be stored in a
     * file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or
     * another computer environment.
     *
     * Design an algorithm to serialize and deserialize a binary search tree. There is no restriction on how your
     * serialization/deserialization algorithm should work. You need to ensure that a binary search tree can be
     * serialized to a string, and this string can be deserialized to the original tree structure.
     *
     * The encoded string should be as compact as possible.
     *
     * Input: root = [2,1,3]
     * Output: [2,1,3]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 10^4].
     * 0 <= Node.val <= 10^4
     * The input tree is guaranteed to be a binary search tree.
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    // Encodes a tree to a single string.
    StringBuilder sb;
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        sb = new StringBuilder();
        dfs(root);
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.length() == 0) return null;

        String[] strs = data.split(",");
        Queue<Integer> queue = new LinkedList<>();
        for (String s : strs) queue.offer(Integer.valueOf(s));
        return buildTree(queue);
    }

    private TreeNode buildTree(Queue<Integer> queue) {
        if (queue.isEmpty()) return null;

        Queue<Integer> sq = new LinkedList<>();

        TreeNode root = new TreeNode(queue.poll());
        while (!queue.isEmpty()) {
            if (queue.peek() < root.val) sq.offer(queue.poll());
            else break;
        }

        root.left = buildTree(sq);
        root.right = buildTree(queue);
        return root;
    }

    private void dfs(TreeNode node) {
        if (node == null) return;

        sb.append(node.val).append(',');
        dfs(node.left);
        dfs(node.right);
    }

    // S2: dfs
    // Encodes a tree to a single string.
    public String serialize2(TreeNode root) {
        if (root == null) return "";
        String res = root.val + "," + serialize(root.left) + "," + serialize(root.right);
        return res;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize2(String data) {
        String[] strs = data.split(",");
        Queue<Integer> queue = new LinkedList<>();
        for (String s : strs) {
            if (s.length() != 0) queue.offer(Integer.parseInt(s));
        }
        return dfs(queue);
    }

    private TreeNode dfs(Queue<Integer> queue) {
        if (queue.isEmpty()) return null;

        Queue<Integer> sq = new LinkedList<>();
        TreeNode root = new TreeNode(queue.poll());
        while (!queue.isEmpty()) {
            if (queue.peek() < root.val) sq.offer(queue.poll());
            else break;
        }
        root.left = dfs(sq);
        root.right = dfs(queue);
        return root;
    }
}
