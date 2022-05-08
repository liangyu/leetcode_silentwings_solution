package LC1501_1800;
import java.util.*;
public class LC1522_DiameterofNAryTree {
    /**
     * Given a root of an N-ary tree, you need to compute the length of the diameter of the tree.
     *
     * The diameter of an N-ary tree is the length of the longest path between any two nodes in the tree. This path may
     * or may not pass through the root.
     *
     * (Nary-Tree input serialization is represented in their level order traversal, each group of children is separated
     * by the null value.)
     *
     * Input: root = [1,null,3,2,4,null,5,6]
     * Output: 3
     *
     * Constraints:
     *
     * The depth of the n-ary tree is less than or equal to 1000.
     * The total number of nodes is between [1, 10^4].
     * @param root
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    private int res = 0;
    public int diameter(Node root) {
        // corner case
        if (root == null) return 0;

        dfs(root);
        return res;
    }

    private int dfs(Node cur) {
        // base case
        if (cur == null) return 0;

        int max1 = 0, max2 = 0;
        for (Node child : cur.children) {
            int height = dfs(child);
            if (height > max1) {
                max2 = max1;
                max1 = height;
            } else if (height > max2) {
                max2 = height;
            }
        }
        res = Math.max(res, max1 + max2);
        return max1 + 1;
    }

    // S2: dfs
    int ans = 0;
    public int diameter2(Node root) {
        helper(root);
        return ans - 1;
    }

    private int helper(Node node) {
        if (node == null) return 0;

        List<Integer> list = new ArrayList<>();
        for (Node child : node.children) {
            list.add(helper(child));
        }
        Collections.sort(list, (o1, o2) -> o2 - o1);
        if (list.size() >= 2) ans = Math.max(ans, list.get(0) + list.get(1) + 1);
        else if (list.size() == 1) ans = Math.max(ans, list.get(0) + 1);
        else ans = Math.max(ans, 1);
        return list.size() == 0 ? 1 : list.get(0) + 1;
    }

    class Node {
        public int val;
        public List<Node> children;


        public Node() {
            children = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            children = new ArrayList<Node>();
        }

        public Node(int _val,ArrayList<Node> _children) {
            val = _val;
            children = _children;
        }
    }
}
/**
 * => 求任意两棵子树的最大深度之和
 * 定义了根
 * 路径都有个拐点
 * 按照拐点来数路径，可以不重不漏
 * 所有孩子里面找2条最长单链即可。
 * h(node) = max{h(node - child)} + 1
 * 本质上求不带拐点的最长路径
 */
