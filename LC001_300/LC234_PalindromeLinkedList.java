package LC001_300;
import java.util.*;
public class LC234_PalindromeLinkedList {
    /**
     * Given the head of a singly linked list, return true if it is a palindrome.
     *
     * Input: head = [1,2,2,1]
     * Output: true
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [1, 10^5].
     * 0 <= Node.val <= 9
     *
     *
     * Follow up: Could you do it in O(n) time and O(1) space?
     * @param head
     * @return
     */
    // time = O(n), space = O(1)
    public boolean isPalindrome(ListNode head) {
        // corner case
        if (head == null) return true;

        ListNode mid = findMid(head);
        mid.next = reverse(mid.next);

        ListNode cur1 = head, cur2 = mid.next;
        while (cur1 != null && cur2 !=null) {
            if (cur1.val != cur2.val) return false;
            cur1 = cur1.next;
            cur2 = cur2.next;
        }
        return true;
    }

    private ListNode findMid(ListNode head) {
        ListNode slow = head, fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }

    private ListNode reverse(ListNode head) {
        // corner case
        if (head == null || head.next == null) return head;

        ListNode cur = head, prev = null, next = null;
        while (cur != null) { // 1 2 2 1
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}
