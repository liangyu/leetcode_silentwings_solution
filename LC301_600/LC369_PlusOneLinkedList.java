package LC301_600;

public class LC369_PlusOneLinkedList {
    /**
     * Given a non-negative integer represented as a linked list of digits, plus one to the integer.
     *
     * The digits are stored such that the most significant digit is at the head of the list.
     *
     * Input: head = [1,2,3]
     * Output: [1,2,4]
     *
     * Input: head = [0]
     * Output: [1]
     *
     * Constraints:
     *
     * The number of nodes in the linked list is in the range [1, 100].
     * 0 <= Node.val <= 9
     * The number represented by the linked list does not contain leading zeros except for the zero itself.
     *
     *
     * @param head
     * @return
     */
    // S1: iteration (optimal Solution!)
    // time = O(n), space = O(1)
    public ListNode plusOne(ListNode head) {
        if (head == null) return head;

        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode cur = dummy, notNine = dummy; // notNine is the rightMost ListNode whose val is not 9!

        while (cur != null) {
            if (cur.val != 9) notNine = cur;
            cur = cur.next;
        }

        notNine.val++;
        notNine = notNine.next;
        while (notNine != null) {
            notNine.val = 0;
            notNine = notNine.next;
        }
        return dummy.val != 0 ? dummy : dummy.next;
    }

    // S2: iteration 2
    // time = O(n), space = O(1)
    public ListNode plusOne2(ListNode head) {
        if (head == null) return head;

        ListNode rev = reverse(head);

        ListNode cur = rev;
        int carry = 0;
        while (cur != null) {
            carry = (cur.val + 1) / 10;
            cur.val = (cur.val + 1) % 10;
            if (carry > 0) {
                if (cur.next != null) cur = cur.next;
                else {
                    cur.next = new ListNode(carry);
                    break;
                }
            } else break;
        }
        head = reverse(rev);
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

    // S3: dfs
    // time = O(n), space = O(n)
    public ListNode plusOne3(ListNode head) {
        if (head == null) return head;

        if (dfs(head) == 0) return head;
        else {
            ListNode node = new ListNode(1);
            node.next = head;
            return node;
        }
    }

    private int dfs(ListNode node) {
        if (node == null) return 1;

        int carry = dfs(node.next);
        if (carry == 0) return 0;

        int val = node.val + carry;
        node.val = val % 10;
        return val / 10;
    }
}
