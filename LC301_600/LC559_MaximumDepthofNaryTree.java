package LC301_600;
import java.util.*;
public class LC559_MaximumDepthofNaryTree {
    /**
     * Given a n-ary tree, find its maximum depth.
     *
     * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     *
     * Input: root = [1,null,3,2,4,null,5,6]
     * Output: 3
     *
     * Constraints:
     *
     * The depth of the n-ary tree is less than or equal to 1000.
     * The total number of nodes is between [0, 10^4].
     *
     * @param root
     * @return
     */
    // S1: DFS
    // time = O(n), space = O(n)
    public int maxDepth(Node root) {
        // corner case
        if (root == null) return 0;

        int res = 0;
        for (Node child : root.children) {
            res = Math.max(res, maxDepth(child));
        }
        return res + 1;
    }

    // S2: iteration -> BFS
    // time = O(n), space = O(n)
    public int maxDepth2(Node root) {
        // corner case
        if (root == null) return 0;

        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(root, 1));

        int res = 0;
        while (!queue.isEmpty()) {
            Pair cur = queue.poll();
            Node node = cur.node;
            if (node != null) {
                res = Math.max(res, cur.depth);
                for (Node next : node.children) {
                    queue.offer(new Pair(next, cur.depth + 1));
                }
            }
        }
        return res;
    }

    private class Pair {
        private Node node;
        private int depth;
        public Pair(Node node, int val) {
            this.node = node;
            this.depth = val;
        }
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
    };
}
