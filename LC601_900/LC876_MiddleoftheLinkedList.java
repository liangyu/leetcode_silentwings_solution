package LC601_900;

public class LC876_MiddleoftheLinkedList {
    /**
     * Given the head of a singly linked list, return the middle node of the linked list.
     *
     * If there are two middle nodes, return the second middle node.
     *
     * Input: head = [1,2,3,4,5]
     * Output: [3,4,5]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [1, 100].
     * 1 <= Node.val <= 100
     * @param head
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode middleNode(ListNode head) {
        ListNode cur = head;
        int k = 0;
        while (cur != null) {
            cur = cur.next;
            k++;
        }

        k /= 2;
        cur = head;
        while (k-- > 0) cur = cur.next;
        return cur;
    }
}
