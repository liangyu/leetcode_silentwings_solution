package LC001_300;
import java.util.*;
public class LC297_SerializeandDeserializeBinaryTree {
    /**
     * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be
     * stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in
     * the same or another computer environment.
     *
     * Design an algorithm to serialize and deserialize a binary tree. There is no restriction on how your
     * serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized
     * to a string and this string can be deserialized to the original tree structure.
     *
     * Input: root = [1,2,3,null,null,4,5]
     * Output: [1,2,3,null,null,4,5]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 10^4].
     * -1000 <= Node.val <= 1000
     * @param root
     * @return
     */
    // S1: dfs
    // Encodes a tree to a single string.
    // time = O(n), space = O(n)
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) return "#";
        return root.val + "," + serialize(root.left) + "," + serialize(root.right); // 注意：root.right序列化后不要加','!!!
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        Queue<String> queue = new LinkedList<>(); // queue里只要按照"根左右"的顺序读出string，交给dfs去做反序列化递归即可！！！
        int n = data.length();
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j < n && data.charAt(j) != ',') j++;
            queue.offer(data.substring(i, j));
            i = j;
        }
        return dfs(queue);
    }

    private TreeNode dfs(Queue<String> queue) {
        String cur = queue.poll();
        if (cur.equals("#")) return null;

        TreeNode root = new TreeNode(Integer.valueOf(cur));
        root.left = dfs(queue);
        root.right = dfs(queue);
        return root;
    }

    // S2: bfs
    public class Codec {
        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            Queue<TreeNode> queue = new LinkedList<>();
            queue.offer(root);

            StringBuilder sb = new StringBuilder();
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node == null) sb.append("#,");
                else {
                    sb.append(node.val + ",");
                    queue.offer(node.left);
                    queue.offer(node.right);
                }
            }
            return sb.toString();
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            int n = data.length();
            List<TreeNode> nodes = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                int j = i;
                while (j < n && data.charAt(j) != ',') j++;
                String str = data.substring(i, j);
                if (str.equals("#")) nodes.add(null);
                else nodes.add(new TreeNode(Integer.valueOf(str)));
                i = j; // next time i++ -> j + 1
            }

            int i = 0, j = 1; // i: parent, j: child
            while (j < nodes.size()) {
                if (nodes.get(i) != null) {
                    nodes.get(i).left = nodes.get(j);
                    nodes.get(i).right = nodes.get(j + 1);
                    j += 2;
                }
                i++;
            }
            return nodes.get(0);
        }
    }
}
/**
 * 先序遍历有个著名的性质：按照先序遍历能够唯一确定一棵树！
 * 1，2，#，#，3，4，#，#，5，#，#
 * 一下子就把根找到了，
 * 唯一要找的就是左子树和右子树的分界点
 * 你只要看左子树，dfs递归处理
 * 1,(2,#,#,)(3,[4,#,#,], [5,#,#])
 * 1. the first element must be root
 * 2. recursively do the sub problem by calling the second element
 */