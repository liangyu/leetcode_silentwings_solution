package LC301_600;
import java.util.*;
public class LC429_NaryTreeLevelOrderTraversal {
    /**
     * Given an n-ary tree, return the level order traversal of its nodes' values.
     *
     * Nary-Tree input serialization is represented in their level order traversal, each group of children is
     * separated by the null value (See examples).
     *
     * Input: root = [1,null,3,2,4,null,5,6]
     * Output: [[1],[3,2,4],[5,6]]
     *
     * Constraints:
     *
     * The height of the n-ary tree is less than or equal to 1000
     * The total number of nodes is between [0, 10^4]
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    public List<List<Integer>> levelOrder(Node root) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> list = new ArrayList<>();
            while (size-- > 0) {
                Node cur = queue.poll();
                list.add(cur.val);
                for (Node child : cur.children) {
                    if (child != null) queue.offer(child);
                }
            }
            res.add(new ArrayList<>(list));
        }
        return res;
    }

    // Definition for a Node.
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
