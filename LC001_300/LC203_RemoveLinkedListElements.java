package LC001_300;
import java.util.*;
public class LC203_RemoveLinkedListElements {
    /**
     * Given the head of a linked list and an integer val, remove all the nodes of the linked list that has
     * Node.val == val, and return the new head.
     *
     * Input: head = [1,2,6,3,4,5,6], val = 6
     * Output: [1,2,3,4,5]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [0, 10^4].
     * 1 <= Node.val <= 50
     * 0 <= k <= 50
     * @param head
     * @param val
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode removeElements(ListNode head, int val) {
        // corner case
        if (head == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;

        while (cur.next != null) {
            if (cur.next.val != val) cur = cur.next;
            else cur.next = cur.next.next;
        }
        return dummy.next;
    }
}
