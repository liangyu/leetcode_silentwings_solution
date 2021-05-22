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
}
