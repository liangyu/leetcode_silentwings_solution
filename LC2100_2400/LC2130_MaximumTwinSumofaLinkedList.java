package LC2100_2400;
import java.util.*;
public class LC2130_MaximumTwinSumofaLinkedList {
    /**
     * In a linked list of size n, where n is even, the ith node (0-indexed) of the linked list is known as the twin of
     * the (n-1-i)th node, if 0 <= i <= (n / 2) - 1.
     *
     * For example, if n = 4, then node 0 is the twin of node 3, and node 1 is the twin of node 2. These are the only
     * nodes with twins for n = 4.
     * The twin sum is defined as the sum of a node and its twin.
     *
     * Given the head of a linked list with even length, return the maximum twin sum of the linked list.
     *
     * Input: head = [5,4,2,1]
     * Output: 6
     *
     * Constraints:
     *
     * The number of nodes in the list is an even integer in the range [2, 105].
     * 1 <= Node.val <= 10^5
     * @param head
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public int pairSum(ListNode head) {
        ListNode slow = head, fast = head;
        int k = 0, res = 0;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next.next;
            k++;
        }

        ListNode revHead = reverse(slow);
        ListNode left = head, right = revHead;
        while (k-- > 0) {
            res = Math.max(res, left.val + right.val);
            left = left.next;
            right = right.next;
        }
        return res;
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
    // time = O(n), space = O(1)
    public int pairSum2(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode cur = head;

        while (cur != null) {
            list.add(cur.val);
            cur = cur.next;
        }

        int i = 0, j = list.size() - 1, max = 0;
        while (i < j) {
            max = Math.max(list.get(i) + list.get(j), max);
            i++;
            j--;
        }
        return max;
    }
}
