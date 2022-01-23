package LC001_300;
import java.util.*;
public class LC116_PopulatingNextRightPointersinEachNode {
    /**
     * You are given a perfect binary tree where all leaves are on the same level, and every parent has two children.
     * The binary tree has the following definition:
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
     * Input: root = [1,2,3,4,5,6,7]
     * Output: [1,#,2,3,#,4,5,6,7,#]
     *
     * Constraints:
     *
     * The number of nodes in the given tree is less than 4096.
     * -1000 <= node.val <= 1000
     * @param root
     * @return
     */
    // S1: recursion
    // time = O(n), space = O(n)
    public Node connect(Node root) {
        // corner case
        if (root == null) return root;

        if (root.left != null) root.left.next = root.right;
        if (root.right != null && root.next != null) root.right.next = root.next.left;

        connect(root.left);
        connect(root.right);
        return root;
    }

    // S2: iteration
    // time = O(n), space = O(1)
    public Node connect2(Node root) {
        // corner case
        if (root == null) return root;

        Node parent = root; // level start node
        while (parent != null) {
            Node cur = parent;
            while (cur != null) {
                if (cur.left != null) cur.left.next = cur.right;
                if (cur.right != null && cur.next != null) cur.right.next = cur.next.left;
                cur = cur.next;
            }
            parent = parent.left;
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
