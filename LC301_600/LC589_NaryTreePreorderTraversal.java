package LC301_600;
import java.util.*;
public class LC589_NaryTreePreorderTraversal {
    /**
     * Given the root of an n-ary tree, return the preorder traversal of its nodes' values.
     *
     * Nary-Tree input serialization is represented in their level order traversal. Each group of children is separated
     * by the null value (See examples)
     *
     * Input: root = [1,null,3,2,4,null,5,6]
     * Output: [1,3,5,6,2,4]
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
    // time = O(n), space = O(n)
    public List<Integer> preorder(Node root) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (root == null) return res;

        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            Node cur = stack.pop();
            res.add(cur.val);
            for (int i = cur.children.size() - 1; i >= 0; i--) {
                stack.push(cur.children.get(i));
            }
        }
        return res;
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
