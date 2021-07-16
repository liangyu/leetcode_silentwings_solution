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
    public String serialize(TreeNode root) {
        // corner case
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);

        String res = sb.toString();
        return res.substring(1);
    }

    private void preOrder(TreeNode root, StringBuilder sb) {
        if (root == null) return; // 这里由于是bst，所以不需要通过增加"#"来判断是否到了leaf下的null

        sb.append("," + root.val);
        preOrder(root.left, sb);
        preOrder(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        // corner case
        if (data == null || data.length() == 0) return null;

        String[] strs = data.split(",");
        Queue<Integer> queue = new LinkedList<>();
        for (String s : strs) queue.offer(Integer.parseInt(s));
        return buildTree(queue);
    }

    private TreeNode buildTree(Queue<Integer> queue) {
        if (queue.isEmpty()) return null;

        TreeNode root = new TreeNode(queue.poll());
        Queue<Integer> smallQ = new LinkedList<>();
        while (!queue.isEmpty() && queue.peek() < root.val) {
            smallQ.offer(queue.poll());
        }

        root.left = buildTree(smallQ);
        root.right = buildTree(queue);
        return root;
    }
}
