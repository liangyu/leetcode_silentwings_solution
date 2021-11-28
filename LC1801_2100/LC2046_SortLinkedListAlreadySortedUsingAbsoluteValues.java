package LC1801_2100;

public class LC2046_SortLinkedListAlreadySortedUsingAbsoluteValues {
    /**
     * Given the head of a singly linked list that is sorted in non-decreasing order using the absolute values of its
     * nodes, return the list sorted in non-decreasing order using the actual values of its nodes.
     *
     * Input: head = [0,2,-5,5,10,-10]
     * Output: [-10,-5,0,2,5,10]
     *
     * Constraints:
     *
     * The number of nodes in the list is the range [1, 10^5].
     * -5000 <= Node.val <= 5000
     * head is sorted in non-decreasing order using the absolute value of its nodes.
     *
     *
     * Follow up:
     * Can you think of a solution with O(n) time complexity?
     * @param head
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public ListNode sortLinkedList(ListNode head) {
        // corner case
        if (head == null || head.next == null) return head;

        ListNode prev = head, cur = head.next;
        while (cur != null) {
            if (cur.val < 0) {
                prev.next = cur.next;
                cur.next = head;
                head = cur;
                cur = prev.next;
            } else {
                prev = cur;
                cur = cur.next;
            }
        }
        return head;
    }

    // S2
    // time = O(n), space = O(n)
    public ListNode sortLinkedList2(ListNode head) {
        ListNode pos = new ListNode(0);
        ListNode neg = new ListNode(0);
        ListNode p1 = pos;
        ListNode p2 = neg;
        ListNode h = head;

        while (h != null) {
            if (h.val >= 0) {
                p1.next = h;
                p1 = p1.next;
                h = h.next;
            } else {
                p2.next = h;
                p2 = p2.next;
                h = h.next;
            }
        }

        p1.next = null;
        p2.next = null;

        pos = pos.next;
        neg = neg.next;
        neg = reverseLinkedList(neg);

        if (neg != null) {
            h = neg;
            while (h.next != null) h = h.next;
            h.next = pos;
            return neg;
        }
        return pos;

    }

    private ListNode reverseLinkedList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode cur = head, prev = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
}
/**
 * 一旦用到排序，就是O(nlogn),但没用到题目按照绝对值已经排好序的条件
 * 最好做些in-place的操作
 * 拆成2部分，一部分都是负数，一部分都是正数
 * 负数部分reverse即可
 * [0,2,5,10,-10]
 * O(n) => check if there are two-sum that equals target
 * => 分情况讨论
 */