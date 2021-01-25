# LC1673 Find the Most Competitive Subsequence
标签（空格分隔）： LeetCode Java Stack 

---
    /**
     * Given an integer array nums and a positive integer k, return the most competitive subsequence of nums of size k.
     *
     * An array's subsequence is a resulting sequence obtained by erasing some (possibly zero) elements from the array.
     *
     * We define that a subsequence a is more competitive than a subsequence b (of the same length) if
     * in the first position where a and b differ, subsequence a has a number less than the corresponding number in b.
     * For example, [1,3,4] is more competitive than [1,3,5] because the first position they differ is
     * at the final number, and 4 is less than 5.
     *
     * Input: nums = [3,5,2,6], k = 2
     * Output: [2,6]
     *
     * Input: nums = [2,4,3,3,5,4,9,6], k = 4
     * Output: [2,3,3,4]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 105
     * 0 <= nums[i] <= 109
     * 1 <= k <= nums.length
     * @param nums
     * @param k
     * @return
     */

【难点误区】

本题的最大难点就是要能看出使用”单调栈“来做。在此基础上，主要的坑点有以下3个：

1. 单调升序时入栈，==的情况也要入栈
2. 最多只能弹栈 len - k个元素，同时对栈顶做任何操作之前一定要记得check 栈不能为空，切记！！！
3. 出for loop之后，一定还要记得check当前栈中元素是否多于k个，多的话要继续弹栈直到只留k个。

【解题思路】

本题与LC402 Remove K Digits根本上是一道题，是单调栈解法的经典问题。基本思路是维护一个单调升序栈，一旦遇到比栈顶元素大的值则压栈，否则就从栈顶弹栈直到遇到比它小的值，再将它入栈。

但是这里又给这个基本规则加入了其他限制，首先前面基本思路要求能和栈顶元素进行比较，所以一旦栈为空，则只能将当前元素入栈。同样的，因为最后输出的数组要有k个元素，所以即使栈顶的元素统统大于当前元素，我们最多也只能弹出一共 len - k个，一旦弹满，则无论如何都只能把当前元素压入栈中。另外，当所有元素都遍历过后，还要记得check当前栈中元素是否多于k个，一旦多于k个，我们还要将多于的元素继续弹栈。最后逆序将栈中元素放入res array之中。

```
/**
 *   1 3 5 7 9 6        单调栈，maintain k-size
 *   1 3 5 (7 9) 6      递增序列 -> 最多扔 n - k 个元素，超过的话只能老老实实放在栈顶上
 */
```

```java
// S1: 单调栈
// time = O(n), space = O(n)
public int[] mostCompetitive(int[] nums, int k) {
    // corner case
    if (nums == null || nums.length == 0 || k <= 0) return new int[0];

    int count = nums.length - k; // 最多能够删除 len - k 个数组元素
    Stack<Integer> stack = new Stack<>();

    for (int n : nums) {
        if (stack.isEmpty() || n >= stack.peek()) { // 单调升序时可压入栈里
            stack.push(n);
        } else {
            while (count > 0 && !stack.isEmpty() && n < stack.peek()) { // 非单调升序且依然可以有配额删除元素时弹栈
                stack.pop();
                count--;
            }
            stack.push(n); // 弹栈完成后切记要将当前元素压栈！！！
        }
    }
    while (count-- > 0) stack.pop(); // 出了for loop之后很有可能栈中元素依然超过k个，那么此刻要把多余的也要弹出直至只留k个
    int[] res = new int[k];
    int i = k - 1;
    while (!stack.isEmpty()) {
        res[i--] = stack.pop(); // 从栈顶开始逆序倒入res array之中
    }
    return res;
}
```

