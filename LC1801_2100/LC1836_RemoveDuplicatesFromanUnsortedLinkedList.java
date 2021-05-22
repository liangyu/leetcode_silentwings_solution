package LC1801_2100;
import java.util.*;
public class LC1836_RemoveDuplicatesFromanUnsortedLinkedList {
    /**
     * Given the head of a linked list, find all the values that appear more than once in the list and delete the nodes
     * that have any of those values.
     *
     * Return the linked list after the deletions.
     *
     * Input: head = [1,2,3,2]
     * Output: [1,3]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [1, 10^5]
     * 1 <= Node.val <= 10^5
     * @param head
     * @return
     */
    public ListNode deleteDuplicatesUnsorted(ListNode head) {
        // corner case
        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;

        HashMap<Integer, Integer> map = new HashMap<>();
        ListNode cur = head;
        while (cur != null) {
            map.put(cur.val, map.getOrDefault(cur.val, 0) + 1);
            cur = cur.next;
        }
        cur = dummy;
        while (cur.next != null) {
            int val = cur.next.val;
            if (map.get(val) > 1) {
                cur.next = cur.next.next;
            } else cur = cur.next;
        }
        return dummy.next;
    }
}
