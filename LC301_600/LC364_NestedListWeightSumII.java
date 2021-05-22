package LC301_600;
import practice.NestedInteger;

import java.util.*;
public class LC364_NestedListWeightSumII {
    /**
     * Given a nested list of integers, return the sum of all integers in the list weighted by their depth.
     *
     * Each element is either an integer, or a list -- whose elements may also be integers or other lists.
     *
     * Different from the previous question where weight is increasing from root to leaf, now the weight is defined
     * from bottom up. i.e., the leaf level integers have weight 1, and the root level integers have the largest weight.
     *
     * Input: [[1,1],2,[1,1]]
     * Output: 8
     *
     * Input: [1,[4,[6]]]
     * Output: 17
     *
     * @param nestedList
     * @return
     */
    // time = O(n), space = O(n)
    // n: the total number of nested elements in the input list
    public int depthSumInverse(List<NestedInteger> nestedList) {
        // corner case
        if (nestedList == null || nestedList.size() == 0) {
            throw new IllegalArgumentException("Invalid input");
        }

        Queue<NestedInteger> queue = new LinkedList<>();

        // init
        for (NestedInteger ni : nestedList) {
            queue.offer(ni);
        }

        // bfs
        int res = 0, nodeSum = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                NestedInteger cur = queue.poll();
                if (cur.isInteger()) nodeSum += cur.getInteger();
                else {
                    for (NestedInteger ni : cur.getList()) {
                        queue.offer(ni);
                    }
                }
            }
            res += nodeSum;
        }
        return res;
    }
    /**
     * // This is the interface that allows for creating nested lists.
     * // You should not implement it, or speculate about its implementation
     * public interface NestedInteger {
     *     // Constructor initializes an empty nested list.
     *     public NestedInteger();
     *
     *     // Constructor initializes a single integer.
     *     public NestedInteger(int value);
     *
     *     // @return true if this NestedInteger holds a single integer, rather than a nested list.
     *     public boolean isInteger();
     *
     *     // @return the single integer that this NestedInteger holds, if it holds a single integer
     *     // Return null if this NestedInteger holds a nested list
     *     public Integer getInteger();
     *
     *     // Set this NestedInteger to hold a single integer.
     *     public void setInteger(int value);
     *
     *     // Set this NestedInteger to hold a nested list and adds a nested integer to it.
     *     public void add(NestedInteger ni);
     *
     *     // @return the nested list that this NestedInteger holds, if it holds a nested list
     *     // Return empty list if this NestedInteger holds a single integer
     *     public List<NestedInteger> getList();
     * }
     */
}

/**
 * [[1,1],2,[1,1]]
 *          2                  2  --> nodeSum / res
 *    1  1     1  1    1 + 1 + 1 + 1 + 2 * 2 = (1 + 1 + 1 + 1 + 2) + (2) = 8
 *                                       cur.getInteger() + nodeSum + res  res
 *                                       => nodeSum = 6, res = 8
 *
 *    diff = 1 + 1 + 1 + 1 + 2 = sum(level 2) + sum(level 1)
 *    => needs to keep the sum of previous level(nodeSum), and add the sum of current level to get the diff
 *    => diff + nodeSum = res + nodeSum = res
 *
 *    [1,[4,[6]]]
 *         1    nodeSum / res = 1
 *       4     nodeSum = 1 + 4 = 5, res = 5 + 1 = 6
 *      6      nodeSum = 5 + 6 = 11, res = 6 + 11 = 17
 *      => res = 17
 *
 */
