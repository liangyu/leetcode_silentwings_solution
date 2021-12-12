package LC1201_1500;

public class LC1290_ConvertBinaryNumberinaLinkedListtoInteger {
    /**
     * Given head which is a reference node to a singly-linked list. The value of each node in the linked list is
     * either 0 or 1. The linked list holds the binary representation of a number.
     *
     * Return the decimal value of the number in the linked list.
     *
     * Input: head = [1,0,1]
     * Output: 5
     *
     * Constraints:
     *
     * The Linked List is not empty.
     * Number of nodes will not exceed 30.
     * Each node's value is either 0 or 1.
     * @param head
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public int getDecimalValue(ListNode head) {
        ListNode cur = head;
        int num = cur.val;
        while (cur.next != null) {
            num = num * 2 + cur.next.val;
            cur = cur.next;
        }
        return num;
    }

    // S2: reverse LinkedList
    // time = O(n), space = O(1)
    public int getDecimalValue2(ListNode head) {
        int res = 0, count = 0;
        ListNode cur = reverse(head);

        while (cur != null) {
            if (cur.val == 1) res += (int) Math.pow(2, count);
            cur = cur.next;
            count++;
        }
        return res;
    }

    private ListNode reverse(ListNode head) {
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
