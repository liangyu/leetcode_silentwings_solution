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
        int n = nestedList.size();
        for (int i = n - 1; i >= 0; i--) stack.push(nestedList.get(i));
    }

    @Override
    public Integer next() {
        return stack.pop().getInteger(); // 访问完就要弹出，不能再留在栈内！
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty() && !stack.peek().isInteger()) {
            List<NestedInteger> list = stack.pop().getList(); // 注意这里是用pop()而不是peek(),把栈顶元素破开！
            int n = list.size();
            for (int i = n - 1; i >= 0; i--) stack.push(list.get(i));
        }
        return !stack.isEmpty();
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
/**
 * 与遍历二叉树有点像
 * hasNext(), next()不能一下子都遍历完
 * stack:[1,1] -> [1],[1]
 * 用一个stack,破拆后倒着放
 */