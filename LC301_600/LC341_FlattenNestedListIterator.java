package LC301_600;
import practice.NestedInteger;

import java.util.*;
public class LC341_FlattenNestedListIterator {
    /**
     * You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements
     * may also be integers or other lists. Implement an iterator to flatten it.
     *
     * Implement the NestedIterator class:
     *
     * NestedIterator(List<NestedInteger> nestedList) Initializes the iterator with the nested list nestedList.
     * int next() Returns the next integer in the nested list.
     * boolean hasNext() Returns true if there are still some integers in the nested list and false otherwise.
     * @param nestedList
     */
    // S1: Single Stack
    // time = O(n), space = O(n)
    Stack<NestedInteger> stack;
    public LC341_FlattenNestedListIterator(List<NestedInteger> nestedList) {
        stack = new Stack<>();
        for (int i = nestedList.size() - 1; i >= 0; i--) {
            stack.push(nestedList.get(i));
        }
    }

    @Override
    public Integer next() {
        return stack.pop().getInteger();
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty()) {
            NestedInteger ni = stack.peek();
            if (ni.isInteger()) return true;

            stack.pop();
            for (int i = ni.getList().size() - 1; i >= 0; i--) {
                stack.push(ni.getList().get(i));
            }
        }
        return false;
    }

    // S2: Two Stacks
    // time = O(D), space = O(n)  D: the maximum nesting depth
    Stack<Integer> stack1;
    Stack<Integer> stack2;
    public LC341_FlattenNestedListIterator(List<NestedInteger> nestedList) {
        stack1 = new Stack<>();
        stack2 = new Stack<>();

        helper(nestedList);
        while (!stack1.isEmpty()) stack2.push(stack1.pop());
    }

    @Override
    public Integer next() {
        return stack2.pop();
    }

    @Override
    public boolean hasNext() {
        return !stack2.isEmpty();
    }

    private void helper(List<NestedInteger> nestedList) {
        for (NestedInteger ni : nestedList) {
            if (ni.isInteger()) stack1.push(ni.getInteger());
            else helper(ni.getList());
        }
    }

    /**
     * // This is the interface that allows for creating nested lists.
     * // You should not implement it, or speculate about its implementation
     * public interface NestedInteger {
     *
     *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
     *     public boolean isInteger();
     *
     *     // @return the single integer that this NestedInteger holds, if it holds a single integer
     *     // Return null if this NestedInteger holds a nested list
     *     public Integer getInteger();
     *
     *     // @return the nested list that this NestedInteger holds, if it holds a nested list
     *     // Return empty list if this NestedInteger holds a single integer
     *     public List<NestedInteger> getList();
     * }
     */
}
