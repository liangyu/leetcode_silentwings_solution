package LC1201_1500;
import java.util.*;
public class LC1381_DesignaStackWithIncrementOperation {
    /**
     * Design a stack which supports the following operations.
     *
     * Implement the CustomStack class:
     *
     * CustomStack(int maxSize) Initializes the object with maxSize which is the maximum number of elements in the stack
     * or do nothing if the stack reached the maxSize.
     * void push(int x) Adds x to the top of the stack if the stack hasn't reached the maxSize.
     * int pop() Pops and returns the top of stack or -1 if the stack is empty.
     * void inc(int k, int val) Increments the bottom k elements of the stack by val. If there are less than k elements
     * in the stack, just increment all the elements in the stack.
     *
     * Input
     * ["CustomStack","push","push","pop","push","push","push","increment","increment","pop","pop","pop","pop"]
     * [[3],[1],[2],[],[2],[3],[4],[5,100],[2,100],[],[],[],[]]
     * Output
     * [null,null,null,2,null,null,null,null,null,103,202,201,-1]
     *
     * Constraints:
     *
     * 1 <= maxSize <= 1000
     * 1 <= x <= 1000
     * 1 <= k <= 1000
     * 0 <= val <= 100
     * At most 1000 calls will be made to each method of increment, push and pop each separately.
     * @param maxSize
     */
    // S1: Deque
    Deque<Integer> deque;
    Stack<Integer> stack;
    int maxSize;
    public LC1381_DesignaStackWithIncrementOperation(int maxSize) {
        this.deque = new LinkedList<>();
        this.stack = new Stack<>();
        this.maxSize = maxSize;
    }

    // time = O(1), space = O(n)
    public void push(int x) {
        if (deque.size() < maxSize) deque.offerLast(x);
    }

    // time = O(1), space = O(n)
    public int pop() {
        return deque.isEmpty() ? -1 : deque.pollLast();
    }

    // time = O(n), space = O(n)
    public void increment(int k, int val) {
        int n = deque.size();
        for (int i = 0; i < Math.min(k, n); i++) {
            stack.push(deque.pollFirst() + val);
        }
        while (!stack.isEmpty()) deque.offerFirst(stack.pop());
    }

    // S2: Diff Array (optimal solution)
    class CustomStack {
        int[] nums, offset;
        int maxSize, count, diff;
        public CustomStack(int maxSize) {
            this.maxSize = maxSize;
            this.count = 0;
            this.nums = new int[maxSize];
            this.offset = new int[maxSize];
        }

        // time = O(1), space = O(n)
        public void push(int x) {
            if (count == maxSize) return;

            if (count >= 1) offset[count - 1] += diff;
            diff = 0;
            nums[count] = x;
            offset[count] = 0;
            count++;
        }

        // time = O(1), space = O(n)
        public int pop() {
            if (count == 0) return -1;
            diff += offset[count - 1];
            int res = nums[count - 1] + diff;
            count--;
            return res;
        }

        // time = O(1), space = O(n)
        public void increment(int k, int val) {
            if (count == 0) return;
            offset[Math.min(k - 1, count - 1)] += val;
        }
    }
}
/**
 * 差分数组
 * 假设当我们遇到increment(k,val)操作时，我们设置offset[k]=value。
 * 这样当我们退栈的过程中遇到第k个元素的时候，就知道从此往下继续退栈的话，所有的元素都要加上一个diff = value。
 * 如果往下退栈的过程中再遇到另一个offset[k2] = value2，就知道从k2往下继续退栈出的元素都要加上一个diff = value+value2.
 * 特别注意的是，当如果你需要入栈第k个元素的时候，你需要记录offset[k-1] = diff，同时将手头的diff清空。
 *              x x x x x x  y  ...
 *                        ^
 * offset                 5
 * diff         3
 */