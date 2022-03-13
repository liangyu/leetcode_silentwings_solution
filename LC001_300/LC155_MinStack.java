package LC001_300;
import java.util.*;
public class LC155_MinStack {
    /**
     * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
     *
     * Implement the MinStack class:
     *
     * MinStack() initializes the stack object.
     * void push(val) pushes the element val onto the stack.
     * void pop() removes the element on the top of the stack.
     * int top() gets the top element of the stack.
     * int getMin() retrieves the minimum element in the stack.
     *
     * Input
     * ["MinStack","push","push","push","getMin","pop","top","getMin"]
     * [[],[-2],[0],[-3],[],[],[],[]]
     *
     * Output
     * [null,null,null,null,-3,null,0,-2]
     *
     * Constraints:
     *
     * -2^31 <= val <= 2^31 - 1
     * Methods pop, top and getMin operations will always be called on non-empty stacks.
     * At most 3 * 10^4 calls will be made to push, pop, top, and getMin.
     */
    /** initialize your data structure here. */
    // S1: One Stack
    // time = O(1), space = O(n)
    private Stack<Integer> stack;
    private int min;
    public LC155_MinStack() {
        stack = new Stack<>();
        min = Integer.MAX_VALUE;
    }

    public void push(int val) {
        if (val <= min) { // trick: 在min变更前，把当前的min压入栈顶留下记录，方便之后O(1)获取
            stack.push(min);
            min = val;
        }
        stack.push(val);
    }

    public void pop() {
        if (stack.pop() == min) min = stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }

    // S2: One stack
    // time = O(1), space = O(n)
    class MinStack {
        Stack<Long> stack;
        long min;
        public MinStack() {
            stack = new Stack<>();
            min = Integer.MAX_VALUE;
        }

        public void push(int val) {
            if (stack.isEmpty()) {
                stack.push(0L);
                min = val;
            } else {
                stack.push(val - min);
                min = val >= min ? min : val;
            }
        }

        public void pop() {
            min = stack.peek() > 0 ? min : min - stack.peek();
            stack.pop();
        }

        public int top() {
            return stack.peek() > 0 ? (int)(min + stack.peek()) : (int)min;
        }

        public int getMin() {
            return (int)min;
        }
    }
}
/**
 * x1  x2  x3 .... xk
 * m1  m2  m3 .... mk
 * 省空间：我存的不是元素本身，而是x - min
 * 单独保留一个变量min，记录最小值
 * if (x > min) => stack.push(delta = x - min)  (if delta > 0 => no change for min)
 * if (x < min) => stack.push(x - min)  min = x;
 * pop的时候，if delta >= 0, min_old = min_new, x = delta + min_new
 *           if delta < 0, x = min_new, min_old = min_new - delta
 */