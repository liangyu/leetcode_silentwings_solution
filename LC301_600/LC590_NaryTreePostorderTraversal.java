package LC301_600;
import java.util.*;
public class LC590_NaryTreePostorderTraversal {
    /**
     * Given the root of an n-ary tree, return the postorder traversal of its nodes' values.
     *
     * Nary-Tree input serialization is represented in their level order traversal. Each group of children is separated
     * by the null value (See examples)
     *
     * Input: root = [1,null,3,2,4,null,5,6]
     * Output: [5,6,3,2,4,1]
     *
     * Constraints:
     *
     * The number of nodes in the tree is in the range [0, 104].
     * 0 <= Node.val <= 10^4
     * The height of the n-ary tree is less than or equal to 1000.
     *
     *
     * Follow up: Recursive solution is trivial, could you do it iteratively?
     * @param root
     * @return
     */
    // S1: Iteration
    // time = O(n), space = O(n)
    public List<Integer> postorder(Node root) {
        List<Integer> res = new LinkedList<>();
        if (root == null) return res;

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            res.add(0, cur.val);

            int n = cur.children.size();
            for (int i = 0; i < n; i++) {
                stack.push(cur.children.get(i));
            }
        }
        return res;
    }

    // S2: dfs
    // time = O(n), space = O(n)
    List<Integer> res;
    public List<Integer> postorder2(Node root) {
        res = new ArrayList<>();
        if (root == null) return res;

        dfs(root);
        return res;
    }

    private void dfs(Node node) {
        if (node == null) return;

        int n = node.children.size();
        for (int i = 0; i < n; i++) {
            dfs(node.children.get(i));
        }
        res.add(node.val);
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
