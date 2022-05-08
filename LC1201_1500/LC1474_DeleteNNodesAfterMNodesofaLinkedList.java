package LC1201_1500;

public class LC1474_DeleteNNodesAfterMNodesofaLinkedList {
    /**
     * You are given the head of a linked list and two integers m and n.
     *
     * Traverse the linked list and remove some nodes in the following way:
     *
     * Start with the head as the current node.
     * Keep the first m nodes starting with the current node.
     * Remove the next n nodes
     * Keep repeating steps 2 and 3 until you reach the end of the list.
     * Return the head of the modified list after removing the mentioned nodes.
     *
     * Input: head = [1,2,3,4,5,6,7,8,9,10,11,12,13], m = 2, n = 3
     * Output: [1,2,6,7,11,12]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [1, 10^4].
     * 1 <= Node.val <= 10^6
     * 1 <= m, n <= 1000
     *
     *
     * Follow up: Could you solve this problem by modifying the list in-place?
     * @param head
     * @param m
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode deleteNodes(ListNode head, int m, int n) {
        ListNode cur = head;
        while (cur != null) {
            for (int i = 0; i < m - 1; i++) {
                cur = cur.next;
                if (cur == null) break;
            }
            if (cur == null) break;
            ListNode anchor = cur;
            cur = cur.next;
            if (cur == null) break;
            for (int i = 0; i < n; i++) {
                cur = cur.next;
                if (cur == null) break;
            }
            anchor.next = cur;
        }
        return head;
    }
}
