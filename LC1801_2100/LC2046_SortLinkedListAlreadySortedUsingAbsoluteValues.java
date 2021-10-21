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
}
