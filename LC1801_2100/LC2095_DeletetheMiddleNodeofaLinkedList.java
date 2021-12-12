package LC1801_2100;

public class LC2095_DeletetheMiddleNodeofaLinkedList {
    /**
     * You are given the head of a linked list. Delete the middle node, and return the head of the modified linked list.
     *
     * The middle node of a linked list of size n is the ⌊n / 2⌋th node from the start using 0-based indexing, where
     * ⌊x⌋ denotes the largest integer less than or equal to x.
     *
     * For n = 1, 2, 3, 4, and 5, the middle nodes are 0, 1, 1, 2, and 2, respectively.
     *
     * Input: head = [1,3,4,7,1,2,6]
     * Output: [1,3,4,1,2,6]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [1, 10^5].
     * 1 <= Node.val <= 10^5
     * @param head
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode deleteMiddle(ListNode head) {
        // corner case
        if (head == null || head.next == null) return null;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy;

        int count = 0;
        while (cur.next != null) {
            cur = cur.next;
            count++;
        }

        int k = count / 2;
        cur = dummy;
        while (k-- > 0) cur = cur.next;
        if (cur.next != null) cur.next = cur.next.next;
        else cur.next = null;
        return dummy.next;
    }
}
