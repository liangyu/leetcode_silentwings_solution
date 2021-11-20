package LC1801_2100;
import java.util.*;
public class LC2074_ReverseNodesinEvenLengthGroups {
    /**
     * You are given the head of a linked list.
     *
     * The nodes in the linked list are sequentially assigned to non-empty groups whose lengths form the sequence of
     * the natural numbers (1, 2, 3, 4, ...). The length of a group is the number of nodes assigned to it. In other words,
     *
     * The 1st node is assigned to the first group.
     * The 2nd and the 3rd nodes are assigned to the second group.
     * The 4th, 5th, and 6th nodes are assigned to the third group, and so on.
     * Note that the length of the last group may be less than or equal to 1 + the length of the second to last group.
     *
     * Reverse the nodes in each group with an even length, and return the head of the modified linked list.
     *
     * Input: head = [5,2,6,3,9,1,7,3,8,4]
     * Output: [5,6,2,3,9,1,4,8,3,7]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [1, 10^5].
     * 0 <= Node.val <= 10^5
     * @param head
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public ListNode reverseEvenLengthGroups(ListNode head) {
        // corner case
        if (head == null || head.next == null) return head;

        ListNode cur = head;
        int idx = 2;
        while (cur != null) {
            ListNode prev = cur;
            int count = 0;
            while (count < idx && cur != null) {
                cur = cur.next;
                count++;
            }
            if (cur == null) {
                if (count <= idx) count--;
                if (count % 2 == 0) prev.next = reverse(prev.next);
            } else {
                ListNode next = cur.next;
                cur.next = null;
                if (count % 2 == 0) prev.next = reverse(prev.next);
                for (int i = 0; i < idx; i++) prev = prev.next;
                prev.next = next;
                cur = prev;
            }
            idx++;
        }
        return head;
    }

    private ListNode reverse(ListNode head) {
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

    // S2
    // time = O(n), space = O(n)
    public ListNode reverseEvenLengthGroups2(ListNode head) {
        List<ListNode> heads = new ArrayList<>();
        List<Integer> lens = new ArrayList<>();

        int len = 1;
        ListNode cur = head;
        while (true) {
            heads.add(cur);
            int count = 1;
            for (int i = 0; i < len - 1; i++) {
                if (cur.next == null) break; // reach the end
                cur = cur.next;
                count++;
            }
            lens.add(count);

            // break
            if (cur.next == null) break; // reach the end
            ListNode next = cur.next;
            cur.next = null;
            cur = next;

            len++;
        }

        // reverse if needed
        for (int i = 0; i < heads.size(); i++) {
            if (lens.get(i) % 2 == 0) {
                heads.set(i, reverseLinkedList(heads.get(i)));
            }
        }

        // re-attach
        for (int i = 0; i < heads.size() - 1; i++) { // the former list tail -> the next list head
            cur = heads.get(i);
            while (cur.next != null) cur = cur.next; // find the tail
            cur.next = heads.get(i + 1);
        }
        return heads.get(0);
    }

    private ListNode reverseLinkedList(ListNode head) {
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
 * (1 + x) * x / 2 >= sum
 * 1. 拆分
 * heads数组，装每一个sub LinkedList的头指针
 * 2. lens -> 翻转
 * 3. 拼接
 */