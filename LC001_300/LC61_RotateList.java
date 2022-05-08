package LC001_300;
import java.util.*;
public class LC61_RotateList {
    /**
     * Given the head of a linked list, rotate the list to the right by k places.
     *
     * Input: head = [1,2,3,4,5], k = 2
     * Output: [4,5,1,2,3]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [0, 500].
     * -100 <= Node.val <= 100
     * 0 <= k <= 2 * 10^9
     * @param head
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode rotateRight(ListNode head, int k) {
        // corner case
        if (head == null || head.next == null || k <= 0) return head;

        ListNode cur = head;
        int count = 1;
        while (cur.next != null) {
            cur = cur.next;
            count++;
        }
        cur.next = head;
        cur = head;

        for (int i = 1; i < count - k % count; i++) cur = cur.next;
        ListNode newHead = cur.next;
        cur.next = null;
        return newHead;
    }
}
/**
 * 此题对于链表的操作包括：
 *
 * 确定链表的总长度Len，注意如果Len==0时的处理（此时可以顺便记录末尾节点end）
 * 确定实际需要旋转的次数 k=k%Len
 * 令p从头指针前进 Len-k+1 步，就到了断开链表的位置。
 * 将p->next作为新的head，原本的end之后指向原本的head，再把p的next指向NULL，
 */