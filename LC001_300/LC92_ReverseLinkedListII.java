package LC001_300;
import java.util.*;
public class LC92_ReverseLinkedListII {
    /**
     * Given the head of a singly linked list and two integers left and right where left <= right, reverse the nodes
     * of the list from position left to position right, and return the reversed list.
     *
     * Input: head = [1,2,3,4,5], left = 2, right = 4
     * Output: [1,4,3,2,5]
     *
     * Constraints:
     *
     * The number of nodes in the list is n.
     * 1 <= n <= 500
     * -500 <= Node.val <= 500
     * 1 <= left <= right <= n
     * @param head
     * @param left
     * @param right
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode reverseBetween(ListNode head, int left, int right) {
        // corner case
        if (head == null || head.next == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode prev = dummy, cur = head;

        for (int i = 1; i < left; i++) {
            cur = cur.next;
            prev = prev.next;
        }
        // cur -> left, prev.next -> cur / start
        for (int i = 0; i < right - left; i++) {
            // pair reverse
            ListNode next = cur.next; // next = 3
            cur.next = next.next; // 2 -> 4
            next.next = prev.next; // 3 -> 2
            prev.next = next; // 1 -> 3
        }
        return dummy.next;
    }

    // S2
    // time = O(n), space = O(1)
    public ListNode reverseBetween2(ListNode head, int left, int right) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode cur = dummy;
        for (int i = 1; i <= left - 1; i++) {
            cur = cur.next;
        }

        ListNode endOfFirst = cur;
        cur = cur.next;
        ListNode startOfSecond = cur;

        // reverse
        ListNode prev = null, next = null;
        for (int i = left; i <= right; i++) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        endOfFirst.next = prev;
        startOfSecond.next = cur;
        return dummy.next;
    }
}
/**
 * one-pass
 * 记录两个节点
 * 处理逆序区也要有左右节点指针
 * 同时维护三个指针
 */
