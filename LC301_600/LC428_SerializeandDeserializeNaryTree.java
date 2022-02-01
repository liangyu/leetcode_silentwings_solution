package LC301_600;
import java.util.*;
public class LC428_SerializeandDeserializeNaryTree {
    /**
     * Serialization is the process of converting a data structure or object into a sequence of bits so that it can be
     * stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in
     * the same or another computer environment.
     *
     * Design an algorithm to serialize and deserialize an N-ary tree. An N-ary tree is a rooted tree in which each node
     * has no more than N children. There is no restriction on how your serialization/deserialization algorithm should
     * work. You just need to ensure that an N-ary tree can be serialized to a string and this string can be
     * deserialized to the original tree structure.
     *
     * For example, you may serialize the following 3-ary tree
     *
     * as [1 [3[5 6] 2 4]]. Note that this is just an example, you do not necessarily need to follow this format.
     *
     * Or you can follow LeetCode's level order traversal serialization format, where each group of children is
     * separated by the null value.
     * For example, the above tree may be serialized as [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,
     * 12,null,13,null,null,14].
     *
     * You do not necessarily need to follow the above-suggested formats, there are many more different formats that
     * work so please be creative and come up with different approaches yourself.
     *
     * Input: root = [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
     * Output: [1,null,2,3,4,5,null,null,6,7,null,8,null,9,10,null,null,11,null,12,null,13,null,null,14]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 104].
     * 0 <= Node.val <= 104
     * The height of the n-ary tree is less than or equal to 1000
     * Do not use class member/global/static variables to store states. Your encode and decode algorithms should be
     * stateless.
     * @param root
     * @return
     */
    // S1: bfs
    // Encodes a tree to a single string.
    // time = O(n), space = O(n)
    public String serialize(Node root) {
        if (root == null) return "";

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        StringBuilder sb = new StringBuilder();
        sb.append(root.val).append(',');

        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            sb.append(cur.children.size()).append(',');

            for (Node child : cur.children) {
                sb.append(child.val).append(',');
                queue.offer(child);
            }
        }
        return sb.toString();
    }

    // Decodes your encoded data to tree.
    // time = O(n), space = O(n)
    public Node deserialize(String data) {
        if (data == null || data.length() == 0) return null;

        String[] strs = data.split(",");
        Node root = new Node(Integer.parseInt(strs[0]));

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        int idx = 1;
        while (!queue.isEmpty()) {
            Node cur = queue.poll();
            int size = Integer.parseInt(strs[idx++]);
            cur.children = new ArrayList<>();

            for (int i = 0; i < size; i++) {
                Node child = new Node(Integer.parseInt(strs[idx++]));
                cur.children.add(child);
                queue.offer(child);
            }
        }
        return root;
    }


    // S2: dfs
    // Encodes a tree to a single string.
    // time = O(n), space = O(n)
    public String serialize2(Node root) {
        if (root == null) return "#";
        String res = root.val + ":" + root.children.size() + ",";
        for (Node child : root.children) res += serialize(child) + ",";
//        res = res.substring(0, res.length() - 1); // 这里去掉末尾的','，下面的pos就不可能为-1，也就不用check了！！！
        return res;
    }

    // Decodes your encoded data to tree.
    // time = O(n), space = O(n)
    HashMap<Integer, Integer> map;
    public Node deserialize2(String data) {
        if (data == null || data.length() == 0) return null;
        if (data.equals("#")) return null;

        map = new HashMap<>();

        int n = data.length();
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j < n && data.charAt(j) != ',') j++;
            String s = data.substring(i, j);
            int pos = s.indexOf(":");
            if (pos == -1) continue; // 注意：如果上面res没有在recursion里删除最后一个多余的','，这里必须要check pos是否为-1！！！
            Node node = new Node(Integer.parseInt(s.substring(0, pos)));
            int size = Integer.parseInt(s.substring(pos + 1));
            map.put(nodes.size(), size);
            nodes.add(node);
            i = j;
        }
        return dfs(nodes, 0);
    }

    private Node dfs(List<Node> nodes, int cur) {
        int start = cur + 1; // child node开始的位置
        Node root = nodes.get(cur);
        root.children = new ArrayList<>();

        for (int i = 0; i < map.getOrDefault(cur, 0); i++) {
            root.children.add(dfs(nodes, start));
            // 由于是dfs，每个孩子节点都先都走到了底再返回来进入下一个平级child，所以要先求出每个child最终的size来！
            start += getSize(root.children.get(i));
        }
        return root;
    }

    private int getSize(Node node) {
        if (node == null) return 0;
        int count = 1;
        for (Node child : node.children) {
            count += getSize(child);
        }
        return count;
    }

    class Node {
        public int val;
        public List<Node> children;
        public Node() {}
        public Node(int _val) {
            val = _val;
        }
        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }
}
/**
 * 注意：如果serialize的时候，没有在recursion里对res的最后一位操作，把最后的','抹掉，最后序列化后的data string会变成如下形式：
 * data: "1:3,3:2,5:0,,6:0,,,2:0,,4:0,,"
 * 可以看到有两个逗号隔出的空string
 * 这样在反序列化时，就有可能在这个空string里去check有没有冒号：，那么pos就可能== -1.
 * 这种情况下，直接跳过，continue到下一回合即可！！！
 */
