# LC23 Merge k Sorted Lists

标签（空格分隔）： LeetCode Java Divide&Conquer Heap

---
    /**
     * You are given an array of k linked-lists lists, each linked-list is sorted in ascending order.
     *
     * Merge all the linked-lists into one sorted linked-list and return it.
     *
     * Input: lists = [[1,4,5],[1,3,4],[2,6]]
     * Output: [1,1,2,3,4,4,5,6]
     *
     * Constraints:
     *
     * k == lists.length
     * 0 <= k <= 10^4
     * 0 <= lists[i].length <= 500
     * -10^4 <= lists[i][j] <= 10^4
     * lists[i] is sorted in ascending order.
     * The sum of lists[i].length won't exceed 10^4.
     *
     * @param lists
     * @return
     */

【难点误区】

本题最大难点就是想到用分治的方法两两劈开，直到只剩最后一个之后再向上两两merge。如果能想到这点的话，下面重头戏不过是个LC21 merge two而已，不难解决。

【解题思路】

本题的最优解本质上就是个类似merge sort的分治法，先把这些含有Linked List的list不断二分，直到start == end，也就意味着分到只剩一个的时候，然后开始两两向上merge而得到最后的结果。而由于是两两merge，这部分其实就是LC21 Merge Two Sorted Lists。


```java     
// time = O(nlogk), space = O(1)
public ListNode mergeKLists(ListNode[] lists) {
    // corner case
    if (lists == null || lists.length == 0) return null;

    return partition(lists, 0, lists.length - 1);
}

private ListNode partition(ListNode[] lists, int start, int end) {
    if (start == end) return lists[start];
    int mid = start + (end - start) / 2;
    ListNode left = partition(lists, start, mid);
    ListNode right = partition(lists, mid + 1, end);
    return merge(left, right);
}

private ListNode merge(ListNode h1, ListNode h2) {
    // corner case
    if (h1 == null) return h2;
    if (h2 == null) return h1;

    ListNode dummy = new ListNode(0);
    ListNode cur = dummy;

    while (h1 != null && h2 != null) {
        if (h1.val < h2.val) {
            cur.next = h1;
            h1 = h1.next;
        } else {
            cur.next = h2;
            h2 = h2.next;
        }
        cur = cur.next;
    }

    if (h1 != null) cur.next = h1;
    if (h2 != null) cur.next = h2;
    return dummy.next;
}

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
```
