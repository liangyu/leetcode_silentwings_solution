package LC001_300;
import java.util.*;
public class LC206_ReverseLinkedList {
    /**
     * Given the head of a singly linked list, reverse the list, and return the reversed list.
     *
     * Input: head = [1,2,3,4,5]
     * Output: [5,4,3,2,1]
     *
     * Constraints:
     *
     * The number of nodes in the list is the range [0, 5000].
     * -5000 <= Node.val <= 5000
     *
     *
     * Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?
     * @param head
     * @return
     */
    // S1: recursion
    // time = O(n), space = O(n)
    public ListNode reverseList(ListNode head) {
        // corner case
        if (head == null || head.next == null) return head;

        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    // S2: iteration
    // time = O(n), space = O(1)
    public ListNode reverseList2(ListNode head) {
        // corner case
        if (head == null || head.next == null) return head;

        ListNode prev = null, cur = head, next = null;

        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}
