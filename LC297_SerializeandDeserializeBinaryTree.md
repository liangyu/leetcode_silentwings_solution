# LC297 Serialize and Deserialize Binary Tree

标签（空格分隔）： LeetCode Java DFS BFS

---
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

【难点误区】

1. 序列化的时候逗号在前，最后出结果前切除开头的一个逗号
2. 反序列化则是要在拿到root.val后立即从list里删除
3. 切记一定要在new出新root前check当前拿到的string是否为"#"

【解题思路】

本题是经典中的经典题，一定要BFS和DFS之间掌握一种解法。这里推荐采用DFS的做法。其中，serialize采用StringBuilder + preOrder遍历的方法，口诀是“逗号在前，结尾加井”。最后只要切去开头的逗号即可。而deserliaze则采用“先分割，后分治”的方法。分治的原理是从list头部拿到root，这里最大的一个坑就是拿出root后一定要立即从list里将其删除，然后别忘了第一步不是把string转化成int去new出一个root，而是看这个拿出来的string是否为"#"！！！不是“#”的话就是root，拿到后将剩下的list继续往其左右子树上拼即可。



```java     
public class Codec {
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
```
