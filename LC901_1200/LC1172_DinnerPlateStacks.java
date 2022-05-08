package LC901_1200;
import java.util.*;
public class LC1172_DinnerPlateStacks {
    /**
     * You have an infinite number of stacks arranged in a row and numbered (left to right) from 0, each of the stacks
     * has the same maximum capacity.
     *
     * Implement the DinnerPlates class:
     *
     * DinnerPlates(int capacity) Initializes the object with the maximum capacity of the stacks capacity.
     * void push(int val) Pushes the given integer val into the leftmost stack with a size less than capacity.
     * int pop() Returns the value at the top of the rightmost non-empty stack and removes it from that stack, and
     * returns -1 if all the stacks are empty.
     * int popAtStack(int index) Returns the value at the top of the stack with the given index index and removes it
     * from that stack or returns -1 if the stack with that given index is empty.
     *
     * Input
     * ["DinnerPlates", "push", "push", "push", "push", "push", "popAtStack", "push", "push", "popAtStack", "popAtStack",
     * "pop", "pop", "pop", "pop", "pop"]
     * [[2], [1], [2], [3], [4], [5], [0], [20], [21], [0], [2], [], [], [], [], []]
     * Output
     * [null, null, null, null, null, null, 2, null, null, 20, 21, 5, 4, 3, 1, -1]
     *
     * Constraints:
     *
     * 1 <= capacity <= 2 * 10^4
     * 1 <= val <= 2 * 10^4
     * 0 <= index <= 10^5
     * At most 2 * 10^5 calls will be made to push, pop, and popAtStack.
     * @param capacity
     */
    List<Stack<Integer>> stacks;
    TreeSet<Integer> set;
    int capacity;
    public LC1172_DinnerPlateStacks(int capacity) {
        this.stacks = new ArrayList<>();
        this.set = new TreeSet<>();
        this.capacity = capacity;
    }

    // time = O(logn), space = O(n)
    public void push(int val) {
        if (set.isEmpty()) { // all previous stacks are full
            stacks.add(new Stack<>());
            set.add(stacks.size() - 1);
        }

        Integer fk = set.first();
        stacks.get(fk).push(val);
        if (stacks.get(fk).size() == capacity) set.remove(fk);
    }

    // time = O(logn), space = O(n)
    public int pop() {
        return helper(stacks.size() - 1);
    }

    // time = O(logn), space = O(n)
    public int popAtStack(int index) {
        return helper(index);
    }

    private int helper(int i) {
        if (i < 0 || i >= stacks.size() || stacks.get(i).isEmpty()) return -1;

        int res = stacks.get(i).pop();
        set.add(i); // idx-th stack must not full after pop()

        // the last element in our list of stacks MUST be nonempty.
        while (stacks.size() > 0 && stacks.get(stacks.size() - 1).isEmpty()) {
            int lk = set.last();
            set.remove(lk); // if empty, remove from both set and stacks
            stacks.remove(lk);
        }
        return res;
    }
}
/**
 *
 */