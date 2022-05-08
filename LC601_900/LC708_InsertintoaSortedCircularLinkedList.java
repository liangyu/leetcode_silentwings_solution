package LC601_900;
import java.util.*;
public class LC708_InsertintoaSortedCircularLinkedList {
    /**
     * Given a node from a Circular Linked List which is sorted in ascending order, write a function to insert a value
     * insertVal into the list such that it remains a sorted circular list. The given node can be a reference to any
     * single node in the list, and may not be necessarily the smallest value in the circular list.
     *
     * If there are multiple suitable places for insertion, you may choose any place to insert the new value.
     * After the insertion, the circular list should remain sorted.
     *
     * If the list is empty (i.e., given node is null), you should create a new single circular list and return the
     * reference to that single node. Otherwise, you should return the original given node.
     *
     * Input: head = [3,4,1], insertVal = 2
     * Output: [3,4,1,2]
     *
     * Constraints:
     *
     * 0 <= Number of Nodes <= 5 * 10^4
     * -10^6 <= Node.val <= 10^6
     * -10^6 <= insertVal <= 10^6
     *
     * @param head
     * @param insertVal
     * @return
     */
    // time = O(n), space = O(1)
    public Node insert(Node head, int insertVal) {
        // corner case
        if (head == null) {
            Node node = new Node(insertVal);
            node.next = node;
            return node;
        }

        Node cur = head.next, prev = head;
        while (cur != head) {
            if (cur.val >= prev.val) {
                cur = cur.next;
                prev = prev.next;
            } else break;
        }

        Node newHead = cur;
        while (cur.next != newHead && !(cur.val <= insertVal && insertVal <= cur.next.val)) cur = cur.next;

        Node temp = cur.next;
        cur.next = new Node(insertVal);
        cur = cur.next;
        cur.next = temp;
        return head;
    }

    class Node {
        public int val;
        public Node next;
        public Node() {}
        public Node(int _val) {
            val = _val;
        }
        public Node(int _val, Node _next) {
            val = _val;
            next = _next;
        }
    }
}

