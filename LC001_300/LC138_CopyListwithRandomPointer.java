package LC001_300;
import java.util.*;
public class LC138_CopyListwithRandomPointer {
    /**
     * A linked list of length n is given such that each node contains an additional random pointer, which could point
     * to any node in the list, or null.
     *
     * Construct a deep copy of the list.
     *
     * Input: head = [[7,null],[13,0],[11,4],[10,2],[1,0]]
     * Output: [[7,null],[13,0],[11,4],[10,2],[1,0]]
     *
     * Constraints:
     *
     * 0 <= n <= 1000
     * -10000 <= Node.val <= 10000
     * Node.random is null or is pointing to some node in the linked list.
     *
     * @param head
     * @return
     */
    // time = O(n), space = O(n)
    public Node copyRandomList(Node head) {
        // corner case
        if (head == null) return head;

        Node dummy = new Node(0);
        Node cur1 = head, cur2 = dummy; // 核心思想：采用dummy node！

        HashMap<Node, Node> map = new HashMap<>();

        while (cur1 != null) {
            map.putIfAbsent(cur1, new Node(cur1.val)); // copy cur1
            cur2.next = map.get(cur1); // 连接cur2
            if (cur1.random != null) {
                map.putIfAbsent(cur1.random, new Node(cur1.random.val)); // copy cur1.random
                cur2.next.random = map.get(cur1.random); // 连接cur2.random
            }
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return dummy.next;
    }

    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
}
