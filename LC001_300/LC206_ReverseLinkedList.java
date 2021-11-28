package LC001_300;
import java.util.*;
public class LC206_ReverseLinkedList {
    /**
     * Given the head of a singly linked list, reverse the list, and return the reversed list.
     *
     * Input: head = [1,2,3,4,5]
     * Output: [5,4,3,2,1]
     *
     * Constraints:
     *
     * The number of nodes in the list is the range [0, 5000].
     * -5000 <= Node.val <= 5000
     *
     *
     * Follow up: A linked list can be reversed either iteratively or recursively. Could you implement both?
     * @param head
     * @return
     */
    // S1: recursion
    // time = O(n), space = O(n)
    public ListNode reverseList(ListNode head) {
        // corner case
        if (head == null || head.next == null) return head;

        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    // S2: iteration
    // time = O(n), space = O(1)
    public ListNode reverseList2(ListNode head) {
        // corner case
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
 * 我们维护一个滑窗，last,cur,nxt表示三个连续的node。
 * 初始的时候，last=NULL, cur=head, nxt=cur->next.
 * 对于每一个滑窗，我们要做的仅仅就是把cur从指向nxt改为指向last。
 * 然后移动滑窗，更新last,cur,nxt所对应的节点.
 * 最终当cur==NULL是结束循环，此时反转链表之后的头指针就是last。
 */
