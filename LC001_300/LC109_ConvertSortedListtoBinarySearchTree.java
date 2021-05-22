package LC001_300;
import java.util.*;
public class LC109_ConvertSortedListtoBinarySearchTree {
    /**
     * Given the head of a singly linked list where elements are sorted in ascending order, convert it to a height
     * balanced BST.
     *
     * For this problem, a height-balanced binary tree is defined as a binary tree in which the depth of the two
     * subtrees of every node never differ by more than 1.
     *
     * Input: head = [-10,-3,0,5,9]
     * Output: [0,-3,9,-10,null,5]
     *
     * Constraints:
     *
     * The number of nodes in head is in the range [0, 2 * 10^4].
     * -10^5 <= Node.val <= 10^5
     * @param head
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public TreeNode sortedListToBST(ListNode head) {
        // corner case
        if (head == null) return null;

        ListNode cur = head;
        int len = 0;
        while (cur != null) {
            cur = cur.next;
            len++;
        }
        return helper(head, 0, len - 1);
    }

    private TreeNode helper(ListNode head, int start, int end) {
        if (start > end) return null;

        int mid = start + (end - start) / 2;
        ListNode cur = head;
        for (int i = 0; i < mid; i++) cur = cur.next;
        TreeNode root = new TreeNode(cur.val);
        root.left = helper(head, start, mid - 1);
        root.right = helper(head, mid + 1, end); // 注意：这里依然是用head, 而不是cur.next！
        return root;
    }

    // S2: Two pointers (最优解！)
    // time = O(n), space = O(n)
    public TreeNode sortedListToBST2(ListNode head) {
        // corner case
        if (head == null) return null;

        return helper(head, null);
    }

    private TreeNode helper(ListNode head, ListNode tail) {
        if (head == tail) return null;

        ListNode slow = head, fast = head;
        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }
        TreeNode root = new TreeNode(slow.val);
        root.left = helper(head, slow);
        root.right = helper(slow.next, tail);
        return root;
    }
}
