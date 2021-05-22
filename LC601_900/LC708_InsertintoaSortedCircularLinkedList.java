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
        Node node = new Node(insertVal);
        // corner case
        if (head == null) { // 当链表为null时，插入node即为head，自我首尾相接即可
            node.next = node;
            return node;
        }

        Node cur = head; // 从头部出发

        while (cur.next != head) { // 走到底，即到达最后一个node
            // 3种case:
            // 1. 由于是升序，那么第一种就是在升序的2个node之间插入
            // 2. 到达了rotate的分界线，即出现cur.val > cur.next.val，那么这时2种情况，1是insertVal就是最大值，2就是最小值
            // 无论是最大还是最小值，都是直接插入到cur之后，cur.next之前
            // 由于可能出现insertVal与cur.val或者cur.next.val相同的情况，所以要在判断条件里加上=
            if (cur.val <= insertVal && cur.next.val >= insertVal || cur.val > cur.next.val && (insertVal <= cur.next.val || insertVal >= cur.val)) {
                node.next = cur.next;
                cur.next = node;
                return head;
            }
            cur = cur.next;
        }
        // 走到头了，还没实现插入，那就只能放到尾部，连上头部即可
        cur.next = node;
        node.next = head;
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

