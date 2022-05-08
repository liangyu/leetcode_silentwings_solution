package LC1501_1800;
import java.util.*;
public class LC1670_DesignFrontMiddleBackQueue {
    /**
     * Design a queue that supports push and pop operations in the front, middle, and back.
     *
     * Implement the FrontMiddleBack class:
     *
     * FrontMiddleBack() Initializes the queue.
     * void pushFront(int val) Adds val to the front of the queue.
     * void pushMiddle(int val) Adds val to the middle of the queue.
     * void pushBack(int val) Adds val to the back of the queue.
     * int popFront() Removes the front element of the queue and returns it. If the queue is empty, return -1.
     * int popMiddle() Removes the middle element of the queue and returns it. If the queue is empty, return -1.
     * int popBack() Removes the back element of the queue and returns it. If the queue is empty, return -1.
     * Notice that when there are two middle position choices, the operation is performed on the frontmost middle
     * position choice. For example:
     *
     * Pushing 6 into the middle of [1, 2, 3, 4, 5] results in [1, 2, 6, 3, 4, 5].
     * Popping the middle from [1, 2, 3, 4, 5, 6] returns 3 and results in [1, 2, 4, 5, 6].
     *
     * Input:
     * ["FrontMiddleBackQueue", "pushFront", "pushBack", "pushMiddle", "pushMiddle", "popFront", "popMiddle", "popMiddle",
     * "popBack", "popFront"]
     * [[], [1], [2], [3], [4], [], [], [], [], []]
     * Output:
     * [null, null, null, null, null, 1, 3, 4, 2, -1]
     *
     * Constraints:
     *
     * 1 <= val <= 10^9
     * At most 1000 calls will be made to pushFront, pushMiddle, pushBack, popFront, popMiddle, and popBack.
     */
    // S1: brute-force
    List<Integer> list;
    public LC1670_DesignFrontMiddleBackQueue() {
        list = new ArrayList<>();
    }

    // time = O(n), space = O(n)
    public void pushFront(int val) {
        list.add(0, val);
    }

    // time = O(n), space = O(n)
    public void pushMiddle(int val) {
        int mid = list.size() / 2;
        list.add(mid, val);
    }

    // time = O(1), space = O(n)
    public void pushBack(int val) {
        list.add(val);
    }

    // time = O(n), space = O(n)
    public int popFront() {
        if (list.size() == 0) return -1;
        return list.remove(0);
    }

    // time = O(n), space = O(n)
    public int popMiddle() {
        if (list.size() == 0) return -1;
        return list.remove((list.size() - 1) / 2);
    }

    // time = O(1), space = O(n)
    public int popBack() {
        if (list.size() == 0) return -1;
        return list.remove(list.size() - 1);
    }

    // S2: double deque
    class FrontMiddleBackQueue {
        Deque<Integer> front;
        Deque<Integer> back;
        public FrontMiddleBackQueue() {
            front = new LinkedList<>();
            back = new LinkedList<>();
        }

        // time = O(1), space = O(n)
        public void pushFront(int val) {
            front.offerFirst(val);
            rebalance();
        }

        // time = O(1), space = O(n)
        public void pushMiddle(int val) {
            back.offerFirst(val);
            rebalance();
        }

        // time = O(1), space = O(n)
        public void pushBack(int val) {
            back.offerLast(val);
            rebalance();
        }

        // time = O(1), space = O(n)
        public int popFront() {
            Integer val = front.isEmpty() ? back.pollFirst() : front.pollFirst();
            rebalance();
            return val == null ? -1 : val;
        }

        // time = O(1), space = O(n)
        public int popMiddle() {
            Integer val = front.size() == back.size() ? front.pollLast() : back.pollFirst();
            rebalance();
            return val == null ? -1 : val;
        }

        // time = O(1), space = O(n)
        public int popBack() {
            Integer val = back.isEmpty() ? front.pollLast() : back.pollLast();
            rebalance();
            return val == null ? -1 : val;
        }

        // time = O(1), space = O(n)
        private void rebalance() {
            while (front.size() < back.size()) front.offerLast(back.pollFirst());
            while (front.size() > back.size()) back.offerFirst(front.pollLast());
        }
    }

/**
 * Your FrontMiddleBackQueue object will be instantiated and called as such:
 * FrontMiddleBackQueue obj = new FrontMiddleBackQueue();
 * obj.pushFront(val);
 * obj.pushMiddle(val);
 * obj.pushBack(val);
 * int param_4 = obj.popFront();
 * int param_5 = obj.popMiddle();
 * int param_6 = obj.popBack();
 */
}
/**
 * o o o o o o
 * o o x o o o
 * O(1) -> double linked list
 */
