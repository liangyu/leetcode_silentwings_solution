# LC238 Product of Array Except Self
标签（空格分隔）： LeetCode Java Divide&Conquer

---
    /**
     * Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to
     * the product of all the elements of nums except nums[i].
     *
     * Example:
     *
     * Input:  [1,2,3,4]
     * Output: [24,12,8,6]
     * Constraint: It's guaranteed that the product of the elements of any prefix or suffix of the array
     * (including the whole array) fits in a 32 bit integer.
     *
     * Note: Please solve it without division and in O(n).
     *
     * Follow up:
     * Could you solve it with constant space complexity? (The output array does not count as extra space for
     * the purpose of space complexity analysis.)
     *
     * @param nums
     * @return
     */

【难点误区】

本题最大难点就是想到用分治法，two pass来解决，一个pass算该元素左边所有元素的乘积，另一个pass算出该元素右边所有元素的乘积，最后两个一乘即可。由于是乘积，两端开始的初始化都要为1才行。

【解题思路】

本题的最优解法是two pass，基本思想是由于要求的是除去该元素之外所有其他元素的乘积，因此我们可以这么去想，首先第一个pass算出所有在该元素左边的元素的累积，然后在第二个pass里，从右向左算出所有在该元素右边的元素的累积，两者一乘即可得到答案。

由于涉及到乘积，所以初始化起点为1，不能为0，这样第一次pass从左到右做下来，除了nums[0]左边没有元素，而初始化为1之外，其他元素的左边累积都已经算出。这样想来，最后一个元素已经得到了除自己以外所有其他元素的乘积，因为其右边不存在任何其他元素了，所以就是最终答案。

然后，第二个pass开始，为了求出所有该元素右边的元素累积，我们可以从右到左进行遍历，从最右边出发，最后的元素右边没有元素，那么结果就是自己，相当于自己 x 1，所以我们可以使用一个变量right，初始化为1，然后每次向左走一位，就同步累积遍历过的元素，这样每到达一个元素，该元素右边的所有元素肯定都已经访问过并累积到了right中，这样把第一个pass里得到的该元素左边所有元素的累积去乘上这个right的值，即可得到除该元素外所有其他元素的累积。

P.S.第一个pass求左边所有元素的累积，其思想有点类似于dp，即 res[i] = res[i - 1] * nums[i - 1]，由于涉及到i - 1，那我们的i就要从1开始，而把res[i - 1] = res[0] 要初始化为1。


```java
// time = O(n), space = O(1)
public int[] productExceptSelf(int[] nums) {
    // corner case
    if (nums == null || nums.length == 0) return new int[0];

    int len = nums.length;
    int[] res = new int[len];
    res[0] = 1; // 左端点初始化为1
    for (int i = 1; i < len; i++) { // 注意由于是从当前访问元素向前看，所以i要从1开始
        res[i] = nums[i - 1] * res[i - 1]; // res[i - 1]：不包括nums[i - 1]的前面其他数的累积
    }
    // res = [1, 1, 2, 6]

    // 逆向遍历，补上第一遍遍历时每个元素向后的所有元素的累积
    int right = 1; // 将右端点设为1
    for (int i = len - 1; i >= 0; i--) {
        res[i] = res[i] * right; // 最右点已经是除自己之外的前面所有元素的累积，所以right从1开始
        right *= nums[i]; // right代表当前元素之后的所有元素的累积，所以必须乘以当前元素的值，以便loop到前一个元素时它能表示之后所有元素的累积
    }
    return res;
}
```

