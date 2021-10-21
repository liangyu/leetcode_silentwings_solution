package LC601_900;

public class LC725_SplitLinkedListinParts {
    /**
     * Given the head of a singly linked list and an integer k, split the linked list into k consecutive linked list parts.
     *
     * The length of each part should be as equal as possible: no two parts should have a size differing by more than
     * one. This may lead to some parts being null.
     *
     * The parts should be in the order of occurrence in the input list, and parts occurring earlier should always have
     * a size greater than or equal to parts occurring later.
     *
     * Return an array of the k parts.
     *
     * Input: head = [1,2,3], k = 5
     * Output: [[1],[2],[3],[],[]]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [0, 1000].
     * 0 <= Node.val <= 1000
     * 1 <= k <= 50
     * @param head
     * @param k
     * @return
     */
    // time = O(n + min(n, k)), space = O(1)
    public ListNode[] splitListToParts(ListNode head, int k) {
        ListNode[] res = new ListNode[k];

        int len = 0;
        ListNode cur = head;
        while (cur != null) { // O(n)
            cur = cur.next;
            len++;
        }

        cur = head;
        if (len / k == 0) {
            for (int i = 0; i < len; i++) { // O(n)
                res[i] = cur;
                cur = cur.next;
                res[i].next = null;
            }
        } else {
            int r = len % k;
            int n = len / k;
            for (int i = 0; i < k; i++) { // O(k)
                res[i] = cur;
                int count = r > 0 ? n + 1 : n;
                while (cur != null && count-- > 1) cur = cur.next;
                if (cur != null) {
                    ListNode next = cur.next;
                    cur.next = null;
                    cur = next;
                }
                r--;
            }
        }
        return res;
    }
}
