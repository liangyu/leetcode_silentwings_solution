package LC001_300;
import java.util.*;
public class LC19_RemoveNthNodeFromEndofList {
    /**
     * Given the head of a linked list, remove the nth node from the end of the list and return its head.
     *
     * Follow up: Could you do this in one pass?
     *
     * Input: head = [1,2,3,4,5], n = 2
     * Output: [1,2,3,5]
     *
     * Input: head = [1], n = 1
     * Output: []
     *
     * Constraints:
     *
     * The number of nodes in the list is sz.
     * 1 <= sz <= 30
     * 0 <= Node.val <= 100
     * 1 <= n <= sz
     * @param head
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // corner case
        if (head == null) return head;

        ListNode dummy = new ListNode(0); // 可能会出现删除头部元素的情况，所以一定要用dummy来做！
        dummy.next = head;
        ListNode slow = dummy, fast = dummy;
        for (int i = 0; i < n; i++) fast = fast.next; // 1 <= n <= sz  -> fast不会出界！

        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return dummy.next;
    }
}
