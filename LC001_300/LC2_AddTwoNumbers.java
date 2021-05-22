package LC001_300;
import java.util.*;
public class LC2_AddTwoNumbers {
    /**
     * You are given two non-empty linked lists representing two non-negative integers. The digits are stored in
     * reverse order, and each of their nodes contains a single digit. Add the two numbers and return the sum as
     * a linked list.
     *
     * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
     *
     * Input: l1 = [2,4,3], l2 = [5,6,4]
     * Output: [7,0,8]
     * Explanation: 342 + 465 = 807.
     *
     * Input: l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]
     * Output: [8,9,9,9,0,0,0,1]
     *
     * Constraints:
     *
     * The number of nodes in each linked list is in the range [1, 100].
     * 0 <= Node.val <= 9
     * It is guaranteed that the list represents a number that does not have leading zeros.
     *
     * @param l1
     * @param l2
     * @return
     */
    // time = O(max(m, n)), space = O(max(m, n))
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        int carry = 0, val = 0;

        while (l1 != null || l2 != null) {
            if (l1 != null && l2 != null) {
                val = l1.val + l2.val + carry;
                l1 = l1.next;
                l2 = l2.next;
            } else if (l1 != null) {
                val = l1.val + carry;
                l1 = l1.next;
            } else {
                val = l2.val + carry;
                l2 = l2.next;
            }

            cur.next = new ListNode(val % 10);
            carry = val / 10;
            cur = cur.next;
        }
        if (carry > 0) cur.next = new ListNode(carry);
        return dummy.next;
    }
}
