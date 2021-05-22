package LC1501_1800;
import java.util.*;
public class LC1721_SwappingNodesinaLinkedList {
    /**
     * You are given the head of a linked list, and an integer k.
     *
     * Return the head of the linked list after swapping the values of the kth node from the beginning and the kth
     * node from the end (the list is 1-indexed).
     *
     * Input: head = [1,2,3,4,5], k = 2
     * Output: [1,4,3,2,5]
     *
     * Input: head = [1,2], k = 1
     * Output: [2,1]
     *
     * Constraints:
     *
     * The number of nodes in the list is n.
     * 1 <= k <= n <= 105
     * 0 <= Node.val <= 100
     *
     * @param head
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode swapNodes(ListNode head, int k) {
        // corner case
        if (head == null || k <= 0) return null;

        int len = 0;
        ListNode front = null, end = null, cur = head;

        while (cur != null) {
            len++;
            if (end != null) end = end.next; // end回到head后，开始向后走，最后停留在倒数第k个位置上
            if (len == k) {
                front = cur; // front停留在正数第k个位置上
                end = head;
            }
            cur = cur.next;
        }

        int temp = front.val;
        front.val = end.val;
        end.val = temp;
        return head;
    }

    private class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
}





