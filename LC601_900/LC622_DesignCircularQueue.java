package LC601_900;
import java.util.*;
public class LC622_DesignCircularQueue {
    /**
     * Design your implementation of the circular queue. The circular queue is a linear data structure in which the
     * operations are performed based on FIFO (First In First Out) principle and the last position is connected back to
     * the first position to make a circle. It is also called "Ring Buffer".
     *
     * One of the benefits of the circular queue is that we can make use of the spaces in front of the queue. In a
     * normal queue, once the queue becomes full, we cannot insert the next element even if there is a space in front of
     * the queue. But using the circular queue, we can use the space to store new values.
     *
     * Implementation the MyCircularQueue class:
     *
     * MyCircularQueue(k) Initializes the object with the size of the queue to be k.
     * int Front() Gets the front item from the queue. If the queue is empty, return -1.
     * int Rear() Gets the last item from the queue. If the queue is empty, return -1.
     * boolean enQueue(int value) Inserts an element into the circular queue. Return true if the operation is successful.
     * boolean deQueue() Deletes an element from the circular queue. Return true if the operation is successful.
     * boolean isEmpty() Checks whether the circular queue is empty or not.
     * boolean isFull() Checks whether the circular queue is full or not.
     *
     * Input
     * ["MyCircularQueue", "enQueue", "enQueue", "enQueue", "enQueue", "Rear", "isFull", "deQueue", "enQueue", "Rear"]
     * [[3], [1], [2], [3], [4], [], [], [], [4], []]
     * Output
     * [null, true, true, true, false, 3, true, true, true, 4]
     *
     * Constraints:
     *
     * 1 <= k <= 1000
     * 0 <= value <= 1000
     * At most 3000 calls will be made to enQueue, deQueue, Front, Rear, isEmpty, and isFull.
     *
     *
     * Follow up: Could you solve the problem without using the built-in queue?
     * @param k
     */
    // time = O(1), space = O(1)
    private ListNode head, tail;
    private int capacity, count;
    public LC622_DesignCircularQueue(int k) {
        capacity = k;
    }

    public boolean enQueue(int value) {
        if (count == capacity) return false;

        ListNode node = new ListNode(value);
        // case 1: list is empty and the new node will become the head
        if (count == 0) head = tail = node;
        else { // case 2: list is not empty and new node will become the new tail
            tail.next = node;
            node.next = head;
            tail = node;
        }
        count++;
        return true;
    }

    public boolean deQueue() {
        if (count == 0) return false;

        head = head.next;
        tail.next = head;
        count--;
        return true;
    }

    public int Front() {
        if (count == 0) return -1;

        return head.val;
    }

    public int Rear() {
        if (count == 0) return -1;

        return tail.val;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public boolean isFull() {
        return count == capacity;
    }

    private class ListNode {
        private int val;
        private ListNode next;
        public ListNode(int val) {
            this.val = val;
        }
    }
}
