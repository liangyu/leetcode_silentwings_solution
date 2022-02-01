package LC1501_1800;
import java.util.*;
public class LC1506_FindRootofNAryTree {
    /**
     * You are given all the nodes of an N-ary tree as an array of Node objects, where each node has a unique value.
     *
     * Return the root of the N-ary tree.
     *
     * Constraints:
     *
     * The total number of nodes is between [1, 5 * 104].
     * Each node has a unique value.
     *
     *
     * Follow up:
     * Could you solve this problem in constant space complexity with a linear time algorithm?
     *
     * @param tree
     * @return
     */
    // S1: HashSet
    // time = O(n), space = O(n)
    public Node findRoot(List<Node> tree) {
        // corner case
        if (tree == null || tree.size() == 0) return null;

        HashSet<Node> set = new HashSet<>();

        for (Node node : tree) {
            for (Node next : node.children) {
                set.add(next);
            }
        }

        for (Node node : tree) {
            if (!set.contains(node)) return node;
        }
        return null;
    }

    // S2: YOLO (You Only Look Once) 最优解!
    // time = O(n), space = O(1)
    public Node findRoot2(List<Node> tree) {
        // corner case
        if (tree == null || tree.size() == 0) return null;

        int sum = 0;
        for (Node node : tree) {
            // the value is added as a parent node
            sum += node.val;
            for (Node next : node.children) {
                // the value is deducted as a child node.
                sum -= next.val;
            }
        }

        for (Node node : tree) {
            // the value of the root node is `sum`
            if (node.val == sum) return node;
        }
        return null;
    }

    // Definition for a Node.
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
