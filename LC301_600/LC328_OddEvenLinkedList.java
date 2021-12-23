package LC301_600;

public class LC328_OddEvenLinkedList {
    /**
     * Given the head of a singly linked list, group all the nodes with odd indices together followed by the nodes with
     * even indices, and return the reordered list.
     *
     * The first node is considered odd, and the second node is even, and so on.
     *
     * Note that the relative order inside both the even and odd groups should remain as it was in the input.
     *
     * You must solve the problem in O(1) extra space complexity and O(n) time complexity.
     *
     * Input: head = [1,2,3,4,5]
     * Output: [1,3,5,2,4]
     *
     * Constraints:
     *
     * n == number of nodes in the linked list
     * 0 <= n <= 10^4
     * -10^6 <= Node.val <= 10^6
     * @param head
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode oddEvenList(ListNode head) {
        // corner case
        if (head == null || head.next == null) return head;

        ListNode h1 = head, h2 = head.next;
        ListNode cur1 = h1, cur2 = h2;

        while (cur1 != null && cur2 != null) {
            if (cur2.next == null) break;
            cur1.next = cur2.next;
            cur1 = cur1.next;
            if (cur1 != null) {
                cur2.next = cur1.next;
                cur2 = cur2.next;
            }
        }

        cur1.next = h2;
        return h1;
    }
}
