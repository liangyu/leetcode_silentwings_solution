package LC001_300;
import java.util.*;
public class LC142_LinkedListCycleII {
    /**
     * Given a linked list, return the node where the cycle begins. If there is no cycle, return null.
     *
     * There is a cycle in a linked list if there is some node in the list that can be reached again by continuously
     * following the next pointer. Internally, pos is used to denote the index of the node that tail's next pointer is
     * connected to. Note that pos is not passed as a parameter.
     *
     * Notice that you should not modify the linked list.
     *
     * Input: head = [3,2,0,-4], pos = 1
     * Output: tail connects to node index 1
     *
     * Constraints:
     *
     * The number of the nodes in the list is in the range [0, 104].
     * -10^5 <= Node.val <= 10^5
     * pos is -1 or a valid index in the linked-list.
     *
     *
     * Follow up: Can you solve it using O(1) (i.e. constant) memory?
     * @param head
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode detectCycle(ListNode head) {
        // corner case
        if (head == null || head.next == null) return null;

        ListNode slow = head, fast = head, cur = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) break;
        }
        if (fast == null || fast.next == null) return null;
        while (slow != cur) {
            slow = slow.next;
            cur = cur.next;
        }
        return cur;
    }
}
