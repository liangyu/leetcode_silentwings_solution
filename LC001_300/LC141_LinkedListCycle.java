package LC001_300;
import java.util.*;
public class LC141_LinkedListCycle {
    /**
     * Given head, the head of a linked list, determine if the linked list has a cycle in it.
     *
     * Return true if there is a cycle in the linked list. Otherwise, return false.
     *
     * Input: head = [3,2,0,-4], pos = 1
     * Output: true
     *
     * Input: head = [1], pos = -1
     * Output: false
     *
     * Constraints:
     *
     * The number of the nodes in the list is in the range [0, 10^4].
     * -10^5 <= Node.val <= 10^5
     * pos is -1 or a valid index in the linked-list.
     *
     *
     * Follow up: Can you solve it using O(1) (i.e. constant) memory?
     * @param head
     * @return
     */
    // time = O(n), space = O(1)
    public boolean hasCycle(ListNode head) {
        // corner case
        if (head == null || head.next == null) return false;

        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (slow == fast) return true;
        }
        return false;
    }
}
