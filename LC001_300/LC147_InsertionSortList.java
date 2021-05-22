package LC001_300;
import java.util.*;
public class LC147_InsertionSortList {
    /**
     * Given the head of a singly linked list, sort the list using insertion sort, and return the sorted list's head.
     *
     * The steps of the insertion sort algorithm:
     *
     * Insertion sort iterates, consuming one input element each repetition and growing a sorted output list.
     * At each iteration, insertion sort removes one element from the input data, finds the location it belongs within
     * the sorted list and inserts it there.
     * It repeats until no input elements remain.
     *
     * Input: head = [4,2,1,3]
     * Output: [1,2,3,4]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [1, 5000].
     * -5000 <= Node.val <= 5000
     * @param head
     * @return
     */
    // time = O(n^2), space = O(1)
    public ListNode insertionSortList(ListNode head) {
        // corner case
        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = head;
        ListNode temp = null, prev = null;

        while (cur != null && cur.next != null) {
            if (cur.val <= cur.next.val) cur = cur.next;
            else {
                temp = cur.next;
                cur.next = temp.next;
                prev = dummy;
                while (prev.next.val <= temp.val) prev = prev.next;
                temp.next = prev.next;
                prev.next = temp;
            }
        }
        return dummy.next;
    }
}
