# LC339 Nested List Weight Sum
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

记得累积时必须要乘上当前层的深度minLen。

【解题思路】

LinkedIn经典面试题，BFS经典题，直接通过level order traversal的思路，如果是integer直接读值并乘上当前层的深度(通过minLen)来获得；如果是list，那就继续将其中的元素加入queue中，遍历到下一层，同时minLen在进入下一层时++即可。

```java
// time = O(n), space = O(n)
// n: the total number of nested elements in the input list
public int depthSum(List<NestedInteger> nestedList) {
    // corner case
    if (nestedList == null || nestedList.size() == 0) {
        return 0;
    }

    Queue<NestedInteger> queue = new LinkedList<>();

    // init
    for (NestedInteger ni : nestedList) {
        queue.offer(ni);
    }

    int res = 0, minLen = 1;
    while (!queue.isEmpty()) {
        int size = queue.size();
        while (size-- > 0) {
            NestedInteger cur = queue.poll();
            if (cur.isInteger()) res += cur.getInteger() * minLen;
            else {
                for (NestedInteger ni : cur.getList()) {
                    queue.offer(ni);
                }
            }
        }
        minLen++;
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

