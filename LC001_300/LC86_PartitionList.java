package LC001_300;
import java.util.*;
public class LC86_PartitionList {
    /**
     * Given the head of a linked list and a value x, partition it such that all nodes less than x come before nodes
     * greater than or equal to x.
     *
     * You should preserve the original relative order of the nodes in each of the two partitions.
     *
     * Input: head = [1,4,3,2,5,2], x = 3
     * Output: [1,2,2,4,3,5]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [0, 200].
     * -100 <= Node.val <= 100
     * -200 <= x <= 200
     * @param head
     * @param x
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode partition(ListNode head, int x) {
        // corner case
        if (head == null || head.next == null) return head;

        ListNode smallHead = new ListNode(0);
        ListNode bigHead = new ListNode(0);
        ListNode small = smallHead;
        ListNode big = bigHead;

        ListNode cur = head;
        while (cur != null) {
            if (cur.val < x) {
                small.next = cur;
                small = small.next;
            } else {
                big.next = cur;
                big = big.next;
            }
            cur = cur.next;
        }
        small.next = bigHead.next;
        big.next = null;
        return smallHead.next;
    }
}
