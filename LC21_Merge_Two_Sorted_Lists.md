# LC21 Merge Two Sorted Lists
标签（空格分隔）： LeetCode LinkedList

---
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


【难点误区】

本题基本难点在于如何找出新List的队头，可以通过dummy node或者先比较 l1 和 l2的队头得到。另外一个特别注意事项就是在跳出while loop后别忘了做post-processing，把可能没走完的另一个list中的剩余node给当前的cur接上。


【解题思路】

本题可以通过recursion和iteration两种方法去做，但最优解是通过直接将两个List从队头开始两两比较，谁小移谁的iteration方法，一步步完成对两个list的遍历最后merge完成。最简单直接的方法就是通过建立一个dummy node直接从队头开始往后拼接，如果不允许使用dummy node的话，可以先将 l1 和 l2两个list的head进行比较，找到较小的那个作为newHead，然后用cur指向newHead，再向后谁小移谁的进行遍历merge即可。剩下唯一要注意的就是当其中一个List走到头跳出loop后，别忘了做post-processing，将剩余的一个可能尚未走完的list后面的node接上当前的cur。

本题的解法可以作为一个helper function用在其他LinkedList类题目之中，比如著名的高频题LC23 Merge k Sorted Lists，因此非常基础和重要！！！

* 时间复杂度：O(n)，n表示 l1 和 l2 中所有的node总数
* 空间复杂度：O(1)

```java     
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
```
