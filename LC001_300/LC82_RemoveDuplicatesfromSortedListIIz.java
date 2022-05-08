package LC001_300;
import java.util.*;
public class LC82_RemoveDuplicatesfromSortedListIIz {
    /**
     * Given the head of a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct
     * numbers from the original list. Return the linked list sorted as well.
     *
     * Input: head = [1,2,3,3,4,4,5]
     * Output: [1,2,5]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [0, 300].
     * -100 <= Node.val <= 100
     * The list is guaranteed to be sorted in ascending order.
     * @param head
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public ListNode deleteDuplicates(ListNode head) {
        // corner case
        if (head == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;

        while (cur.next != null && cur.next.next != null) {
            if (cur.next.val == cur.next.next.val) {
                int temp = cur.next.val;
                while (cur.next != null && cur.next.val == temp) cur.next = cur.next.next;
            } else cur = cur.next;
        }
        return dummy.next;
    }

    // S2:
    // time = O(n), space = O(1)
    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;

        while (cur != null && cur.next != null) {
            ListNode p = cur.next;
            int count = 1;
            while (p != null && p.next != null && p.val == p.next.val) {
                count++;
                p = p.next;
            }
            if (count > 1) cur.next = p.next;
            else cur = cur.next;
        }
        return dummy.next;
    }
}
/**
 * 注意实现上的小技巧。对于... dummy->B1->B2->C1->C2-> D....，我们做法是立足dummy，看到有若干个相同数值的B1、B2，就跳过他们，
 * 令dummy->next = C1。同样地，继续立足dummy，看到有若干个相同数值的C1、C2，就跳过他们，令dummy->next = D。
 */