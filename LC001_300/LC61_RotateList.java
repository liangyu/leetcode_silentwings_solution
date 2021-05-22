package LC001_300;
import java.util.*;
public class LC61_RotateList {
    /**
     * Given the head of a linked list, rotate the list to the right by k places.
     *
     * Input: head = [1,2,3,4,5], k = 2
     * Output: [4,5,1,2,3]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [0, 500].
     * -100 <= Node.val <= 100
     * 0 <= k <= 2 * 10^9
     * @param head
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode rotateRight(ListNode head, int k) {
        // corner case
        if (head == null || head.next == null || k <= 0) return head;

        ListNode cur = head;
        int count = 1;
        while (cur.next != null) {
            cur = cur.next;
            count++;
        }
        cur.next = head;
        cur = head;

        for (int i = 1; i < count - k % count; i++) cur = cur.next;
        ListNode newHead = cur.next;
        cur.next = null;
        return newHead;
    }
}
