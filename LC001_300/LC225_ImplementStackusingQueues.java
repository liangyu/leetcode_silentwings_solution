package LC001_300;
import java.util.*;
public class LC225_ImplementStackusingQueues {
    /**
     * Implement a last in first out (LIFO) stack using only two queues. The implemented stack should support all the
     * functions of a normal queue (push, top, pop, and empty).
     *
     * Implement the MyStack class:
     *
     * void push(int x) Pushes element x to the top of the stack.
     * int pop() Removes the element on the top of the stack and returns it.
     * int top() Returns the element on the top of the stack.
     * boolean empty() Returns true if the stack is empty, false otherwise.
     * Notes:
     *
     * You must use only standard operations of a queue, which means only push to back, peek/pop from front, size,
     * and is empty operations are valid.
     * Depending on your language, the queue may not be supported natively. You may simulate a queue using a list or
     * deque (double-ended queue), as long as you use only a queue's standard operations.
     *
     * Input
     * ["MyStack", "push", "push", "top", "pop", "empty"]
     * [[], [1], [2], [], [], []]
     * Output
     * [null, null, null, 2, 2, false]
     *
     * Constraints:
     *
     * 1 <= x <= 9
     * At most 100 calls will be made to push, pop, top, and empty.
     * All the calls to pop and top are valid.
     *
     *
     * Follow-up: Can you implement the stack such that each operation is amortized O(1) time complexity? In other
     * words, performing n operations will take overall O(n) time even if one of those operations may take longer.
     * You can use more than two queues.
     */
    /** Initialize your data structure here. */
    Queue<Integer> queue;
    public LC225_ImplementStackusingQueues() {
        queue = new LinkedList<>();
    }

    /** Push element x onto stack. */
    // time = O(n), space = O(1)
    public void push(int x) {
        queue.offer(x);
        for (int i = 0; i < queue.size() - 1; i++) {
            queue.offer(queue.poll());
        }
    }

    /** Removes the element on top of the stack and returns that element. */
    // time = O(1), space = O(1)
    public int pop() {
        return queue.poll();
    }

    /** Get the top element. */
    // time = O(1), space = O(1)
    public int top() {
        return queue.peek();
    }

    /** Returns whether the stack is empty. */
    // time = O(1), space = O(1)
    public boolean empty() {
        return queue.isEmpty();
    }
}
