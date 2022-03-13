package LC001_300;
import java.util.*;
public class LC232_ImplementQueueusingStacks {
    /**
     * Implement a first in first out (FIFO) queue using only two stacks. The implemented queue should support all the
     * functions of a normal queue (push, peek, pop, and empty).
     *
     * Implement the MyQueue class:
     *
     * void push(int x) Pushes element x to the back of the queue.
     * int pop() Removes the element from the front of the queue and returns it.
     * int peek() Returns the element at the front of the queue.
     * boolean empty() Returns true if the queue is empty, false otherwise.
     * Notes:
     *
     * You must use only standard operations of a stack, which means only push to top, peek/pop from top, size, and is
     * empty operations are valid.
     * Depending on your language, the stack may not be supported natively. You may simulate a stack using a list or
     * deque (double-ended queue) as long as you use only a stack's standard operations.
     * Follow-up: Can you implement the queue such that each operation is amortized O(1) time complexity? In other
     * words, performing n operations will take overall O(n) time even if one of those operations may take longer.
     *
     * Input
     * ["MyQueue", "push", "push", "peek", "pop", "empty"]
     * [[], [1], [2], [], [], []]
     * Output
     * [null, null, null, 1, 1, false]
     *
     * Constraints:
     *
     * 1 <= x <= 9
     * At most 100 calls will be made to push, pop, peek, and empty.
     * All the calls to pop and peek are valid.
     */
    /** Initialize your data structure here. */
    // time = O(1), space = O(n)
    private Stack<Integer> stack1;
    private Stack<Integer> stack2;
    public LC232_ImplementQueueusingStacks() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    /** Push element x to the back of queue. */
    public void push(int x) {
        while (!stack1.isEmpty()) stack2.push(stack1.pop());
        stack2.push(x);
        while (!stack2.isEmpty()) stack1.push(stack2.pop());
    }

    /** Removes the element from in front of queue and returns that element. */
    public int pop() {
        return stack1.pop();
    }

    /** Get the front element. */
    public int peek() {
        return stack1.peek();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return stack1.isEmpty();
    }
}

// S2
// time = O(1), space = O(n)
class MyQueue {
    Stack<Integer> s1; // push
    Stack<Integer> s2; // pop
    public MyQueue() {
        s1 = new Stack<>();
        s2 = new Stack<>();
    }

    public void push(int x) {
        s1.push(x);
    }

    public int pop() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) s2.push(s1.pop());
        }
        return s2.pop();
    }

    public int peek() {
        if (s2.isEmpty()) {
            while (!s1.isEmpty()) s2.push(s1.pop());
        }
        return s2.peek();
    }

    public boolean empty() {
        return s1.isEmpty() && s2.isEmpty();
    }
}
/**
 * x1  x2x3x4x5x6
 * stack: x10, x11, ... -> 永远做push
 * stack:               -> 永远做pop
 */