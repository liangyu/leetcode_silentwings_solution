# LC141 Linked List Cycle

标签（空格分隔）： LeetCode Java TwoPointers

---
    /**
     * Given head, the head of a linked list, determine if the linked list has a cycle in it.
     *
     * Return true if there is a cycle in the linked list. Otherwise, return false.
     *
     * Input: head = [3,2,0,-4], pos = 1
     * Output: true
     *
     * Input: head = [1], pos = -1
     * Output: false
     *
     * Constraints:
     *
     * The number of the nodes in the list is in the range [0, 10^4].
     * -10^5 <= Node.val <= 10^5
     * pos is -1 or a valid index in the linked-list.
     *
     *
     * Follow up: Can you solve it using O(1) (i.e. constant) memory?
     * @param head
     * @return
     */

【难点误区】

1. fast = 2 * slow
2. fast = head.next, slow = head

【解题思路】

双指针快慢同向而行，其中fast = 2 * slow，并且为了两者才出发前不等，就错开一位，看slow与fast会不会相遇即可来查环。



```java     
// time = O(n), space = O(1)
public boolean hasCycle(ListNode head) {
    // corner case
    if (head == null || head.next == null) return false;

    ListNode slow = head, fast = head.next;
    while (fast != null && fast.next != null) {
        fast = fast.next.next;
        slow = slow.next;
        if (slow == fast) return true;
    }
    return false;
}
```
