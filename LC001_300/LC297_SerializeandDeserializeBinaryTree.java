package LC001_300;
import java.util.*;
public class LC297_SerializeandDeserializeBinaryTree {
    /**
     * Design an algorithm to serialize and deserialize a binary tree.
     *
     * Input: root = [1,2,3,null,null,4,5]
     * Output: [1,2,3,null,null,4,5]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 104].
     * -1000 <= Node.val <= 1000
     * @param root
     * @return
     */
    // Encodes a tree to a single string.
    // time = O(n), space = O(n)
    public String serialize(TreeNode root) {
        // corner case
        if (root == null) return "";

        StringBuilder sb = new StringBuilder();
        preOrder(root, sb);
        String res = sb.toString();
        return res.substring(1);
    }

    private void preOrder(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(",#");
            return;
        }

        sb.append("," + root.val); // 用","在前面隔开
        preOrder(root.left, sb);
        preOrder(root.right, sb);
    }

    // Decodes your encoded data to tree.
    // time = O(n), space = O(n)
    public TreeNode deserialize(String data) {
        // corner case
        if (data == null || data.length() == 0) return null;

        String[] strs = data.split(",");
        List<String> list = new LinkedList<>();
        for (String s : strs) list.add(s);

        TreeNode root = buildTree(list);
        return root;
    }

    private TreeNode buildTree(List<String> list) {
        // corner case
        if (list == null || list.size() == 0) return null;

        String s = list.get(0);
        list.remove(0); // 拿到root值后一定要马上从List里remove掉root.val!!!
        if (s.equals("#")) return null;
        TreeNode root = new TreeNode(Integer.valueOf(s));

        root.left = buildTree(list);
        root.right = buildTree(list);
        return root;
    }
}
