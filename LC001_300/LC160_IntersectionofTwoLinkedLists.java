package LC001_300;
import java.util.*;
public class LC160_IntersectionofTwoLinkedLists {
    /**
     * Write a program to find the node at which the intersection of two singly linked lists begins.
     *
     * Input: intersectVal = 8, listA = [4,1,8,4,5], listB = [5,6,1,8,4,5], skipA = 2, skipB = 3
     * Output: Reference of the node with value = 8
     *
     * Input: intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2
     * Output: null
     *
     * Notes:
     *
     * If the two linked lists have no intersection at all, return null.
     * The linked lists must retain their original structure after the function returns.
     * You may assume there are no cycles anywhere in the entire linked structure.
     * Each value on each linked list is in the range [1, 10^9].
     * Your code should preferably run in O(n) time and use only O(1) memory.
     *
     * @param headA
     * @param headB
     * @return
     */
    // time = O(m + n), space = O(1)
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        // corner case
        if (headA == null || headB == null) return null;

        ListNode curA = headA, curB = headB;

        while (curA != curB) {
            curA = curA == null ? headB : curA.next;
            curB = curB == null ? headA : curB.next;
        }
        return curA;
    }
}
