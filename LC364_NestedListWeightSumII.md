# LC364 Nested List Weight Sum II
标签（空格分隔）： LeetCode Java BFS

---
    /**
     * You are given a nested list of integers nestedList. Each element is either an integer or a list whose elements
     * may also be integers or other lists.
     *
     * The depth of an integer is the number of lists that it is inside of. For example,
     * the nested list [1,[2,2],[[3],2],1] has each integer's value set to its depth.
     *
     * Return the sum of each integer in nestedList multiplied by its depth.
     *
     * Input: nestedList = [1,[4,[6]]]
     * Output: 27
     *
     * Constraints:
     *
     * 1 <= nestedList.length <= 50
     * The values of the integers in the nested list is in the range [-100, 100].
     * The maximum depth of any integer is less than or equal to 50.
     *
     * @param nestedList
     * @return
     */

【难点误区】

corner case，如果list == null || list.size() == 0，即 [ ]的case，则返回0。

【解题思路】

```
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
```

```java
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
```

