package LC301_600;
import java.util.*;
public class LC426_ConvertBinarySearchTreetoSortedDoublyLinkedList {
    /**
     * Convert a Binary Search Tree to a sorted Circular Doubly-Linked List in place.
     *
     * Input: root = [4,2,5,1,3]
     * Output: [1,2,3,4,5]
     *
     * Constraints:
     *
     * -1000 <= Node.val <= 1000
     * Node.left.val < Node.val < Node.right.val
     * All values of Node.val are unique.
     * 0 <= Number of Nodes <= 2000
     *
     * @param root
     * @return
     */
    // time = O(n), space = O(n)
    private Node prev = null, head = null;
    public Node treeToDoublyList(Node root) {
        // corner case
        if (root == null) return root;

        inorder(root);
        prev.right = head;
        head.left = prev;
        return head;
    }

    private void inorder(Node cur) {
        if (cur == null) return;

        inorder(cur.left);
        if (prev != null) {
            prev.right = cur;
            cur.left = prev;
        } else head = cur;

        prev = cur;
        inorder(cur.right);
    }

    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    };
}
