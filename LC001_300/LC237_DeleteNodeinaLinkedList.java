package LC001_300;
import java.util.*;
public class LC237_DeleteNodeinaLinkedList {
    /**
     * Write a function to delete a node in a singly-linked list. You will not be given access to the head of the list,
     * instead you will be given access to the node to be deleted directly.
     *
     * It is guaranteed that the node to be deleted is not a tail node in the list.
     *
     * Input: head = [4,5,1,9], node = 5
     * Output: [4,1,9]
     *
     * Constraints:
     *
     * The number of the nodes in the given list is in the range [2, 1000].
     * -1000 <= Node.val <= 1000
     * The value of each node in the list is unique.
     * The node to be deleted is in the list and is not a tail node
     * @param node
     */
    // time = O(1), space = O(1)
    public void deleteNode(ListNode node) {
        // corner case
        if (node == null) return;

        node.val = node.next.val;
        node.next = node.next.next;
    }
}
