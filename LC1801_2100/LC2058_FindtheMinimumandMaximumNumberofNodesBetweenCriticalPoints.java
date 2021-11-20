package LC1801_2100;
import java.util.*;
public class LC2058_FindtheMinimumandMaximumNumberofNodesBetweenCriticalPoints {
    /**
     * A critical point in a linked list is defined as either a local maxima or a local minima.
     *
     * A node is a local maxima if the current node has a value strictly greater than the previous node and the next node.
     *
     * A node is a local minima if the current node has a value strictly smaller than the previous node and the next node.
     *
     * Note that a node can only be a local maxima/minima if there exists both a previous node and a next node.
     *
     * Given a linked list head, return an array of length 2 containing [minDistance, maxDistance] where minDistance is
     * the minimum distance between any two distinct critical points and maxDistance is the maximum distance between
     * any two distinct critical points. If there are fewer than two critical points, return [-1, -1].
     *
     * Input: head = [3,1]
     * Output: [-1,-1]
     *
     * Input: head = [5,3,1,2,5,1,2]
     * Output: [1,3]
     *
     * Input: head = [1,3,2,2,3,2,2,2,7]
     * Output: [3,3]
     *
     * Constraints:
     *
     * The number of nodes in the list is in the range [2, 10^5].
     * 1 <= Node.val <= 10^5
     * @param head
     * @return
     */
    public int[] nodesBetweenCriticalPoints(ListNode head) {
        // corner case
        if (head == null || head.next == null) return new int[]{-1, -1};

        ListNode prev = head, cur = head.next;
        List<Integer> list = new ArrayList<>();
        int idx = 1;

        while (cur.next != null) {
            if (cur.val > prev.val && cur.val > cur.next.val) list.add(idx);
            else if (cur.val < prev.val && cur.val < cur.next.val) list.add(idx);
            prev = cur;
            cur = cur.next;
            idx++;
        }
        if (list.size() < 2) return new int[]{-1, -1};

        int n = list.size();
        int min = Integer.MAX_VALUE, max = list.get(n - 1) - list.get(0);
        for (int i = 1; i < list.size(); i++) {
            min = Math.min(min, list.get(i) - list.get(i - 1));
        }
        return new int[]{min, max};
    }
}
