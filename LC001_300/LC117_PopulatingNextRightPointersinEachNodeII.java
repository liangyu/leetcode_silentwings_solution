package LC001_300;
import java.util.*;
public class LC117_PopulatingNextRightPointersinEachNodeII {
    /**
     * Given a binary tree
     *
     * struct Node {
     *   int val;
     *   Node *left;
     *   Node *right;
     *   Node *next;
     * }
     * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer
     * should be set to NULL.
     *
     * Initially, all next pointers are set to NULL.
     *
     *
     *
     * Follow up:
     *
     * You may only use constant extra space.
     * Recursive approach is fine, you may assume implicit stack space does not count as extra space for this problem.
     *
     * Input: root = [1,2,3,4,5,null,7]
     * Output: [1,#,2,3,#,4,5,7,#]
     *
     * Constraints:
     *
     * The number of nodes in the given tree is less than 6000.
     * -100 <= node.val <= 100
     * @param root
     * @return
     */
    // S1: BFS
    // time = O(n), space = O(n)
    public Node connect(Node root) {
        // corner case
        if (root == null) return root;

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                Node cur = queue.poll();
                if (cur.left != null) queue.offer(cur.left);
                if (cur.right != null) queue.offer(cur.right);
                if (size > 0) cur.next = queue.peek();
            }
        }
        return root;
    }

    // S2: iteration
    // time = O(n), space = O(1)
    public Node connect2(Node root) {
        // corner case
        if (root == null) return root;

        Node cur = root, prev = null, head = null;
        while (cur != null) {
            if (cur.left != null) {
                if (head == null) {
                    head = cur.left;
                    prev = head;
                } else {
                    prev.next = cur.left;
                    prev = prev.next;
                }
            }
            if (cur.right != null) {
                if (head == null) {
                    head = cur.right;
                    prev = head;
                } else {
                    prev.next = cur.right;
                    prev = prev.next;
                }
            }
            cur = cur.next;
            if (cur == null) {
                cur = head;
                prev = null;
                head = null;
            }
        }
        return root;
    }

    // Definition for a Node.
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
}
