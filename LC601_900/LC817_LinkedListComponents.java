package LC601_900;
import java.util.*;
public class LC817_LinkedListComponents {
    /**
     * You are given the head of a linked list containing unique integer values and an integer array nums that is a
     * subset of the linked list values.
     *
     * Return the number of connected components in nums where two values are connected if they appear consecutively in
     * the linked list.
     *
     * Input: head = [0,1,2,3], nums = [0,1,3]
     * Output: 2
     *
     * Constraints:
     *
     * The number of nodes in the linked list is n.
     * 1 <= n <= 10^4
     * 0 <= Node.val < n
     * All the values Node.val are unique.
     * 1 <= nums.length <= n
     * 0 <= nums[i] < n
     * All the values of nums are unique.
     * @param head
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int numComponents(ListNode head, int[] nums) {
        if (head == null) return 0;

        HashSet<Integer> set = new HashSet<>();
        for (int x : nums) set.add(x);

        int res = 0, s = 0;
        for (ListNode p = head; p != null; p = p.next) {
            if (set.contains(p.val)) s++;
            else {
                if (s > 0) {
                    s = 0;
                    res++;
                }
            }
        }
        if (s > 0) res++;
        return res;
    }
}
