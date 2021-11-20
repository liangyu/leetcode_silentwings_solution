package LC301_600;
import java.util.*;
public class LC430_FlattenaMultilevelDoublyLinkedList {
    /**
     * You are given a doubly linked list which in addition to the next and previous pointers, it could have a child
     * pointer, which may or may not point to a separate doubly linked list. These child lists may have one or more
     * children of their own, and so on, to produce a multilevel data structure, as shown in the example below.
     *
     * Flatten the list so that all the nodes appear in a single-level, doubly linked list. You are given the head of
     * the first level of the list.
     *
     * Input: head = [1,2,3,4,5,6,null,null,null,7,8,9,10,null,null,11,12]
     * Output: [1,2,3,7,8,11,12,9,10,4,5,6]
     *
     * Constraints:
     *
     * The number of Nodes will not exceed 1000.
     * 1 <= Node.val <= 10^5
     * @param head
     * @return
     */
    // time = O(n), space = O(n)
    public Node flatten(Node head) {
        // corner case
        if (head == null) return head;

        dfs(head);
        return head;
    }

    private Node dfs(Node h) {
        if (h == null) return null;

        Node child = h.child, next = h.next;
        Node childEnd = dfs(child);
        Node nextEnd = dfs(next);
        h.child = null; // 一定要记得先断开h.child，因为faltten后是没有child这个结点了！！！

        if (child != null && next != null) {
            h.next = child;
            child.prev = h;
            childEnd.next = next;
            next.prev = childEnd;
            return nextEnd;
        } else if (child == null && next != null) return nextEnd;
        else if (child != null && next == null) {
            h.next = child;
            child.prev = h;
            return childEnd;
        } else return h;
    }

    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }
}
/**
 * 设计一种遍历的方法，通过prev和next关系把它联系起来
 * 有点像中序遍历 => 递归
 * h + dfs(h -> child) + dfs(h -> next)
 * 拼起来
 * dfs返回链的最后一个元素 => 子链表的结尾
 */