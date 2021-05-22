package LC001_300;
import java.util.*;
public class LC21_MergeTwoSortedLists {
    /**
     * Merge two sorted linked lists and return it as a sorted list. The list should be made by splicing together
     * the nodes of the first two lists.
     *
     * Input: list1 = null, list2 = 0->3->3->null
     * Output: 0->3->3->null
     *
     * Input:  list1 =  1->3->8->11->15->null, list2 = 2->null
     * Output: 1->2->3->8->11->15->null
     *
     * Constraints:
     *
     * The number of nodes in both lists is in the range [0, 50].
     * -100 <= Node.val <= 100
     * Both l1 and l2 are sorted in non-decreasing order.
     *
     * @param l1
     * @param l2
     * @return
     */
    // time = O(n), space = O(1)
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // corner case
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        // step 1: init
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;

        // step 2: iterate through both lists
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }

        // step 3: post-processing
        if (l1 != null) cur.next = l1;
        if (l2 != null) cur.next = l2;
        return dummy.next;
    }
}
