# LC1749 Maximum Absolute Sum of Any Subarray

标签（空格分隔）： LeetCode Java DP Prefix

---
【题目】

    /**
     * Given a string s and a character c that occurs in s, return an array of integers answer where
     * answer.length == s.length and answer[i] is the shortest distance from s[i] to the character c in s.
     *
     * Input: s = "loveleetcode", c = "e"
     * Output: [3,2,1,0,1,0,0,1,2,2,1,0]
     *
     * Input: s = "aaab", c = "b"
     * Output: [3,2,1,0]
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^4
     * s[i] and c are lowercase English letters.
     * c occurs at least once in s.
     *
     * @param s
     * @param c
     * @return
     */

【思路】

```
/**
 * 绝对值 => 可正可负 => 正的找最大值，负的找最小值
 * max |subarray sum| => |max subarray sum|
 *                       |min subarray sum|
 * kadane algorthim
 * dp[i]: the max subarray sum ending at nums[i]
 * X [X X X i] X X
 * dp[i] = max(nums[i], dp[i - 1] + nums[i])
 * dp[i] = min(nums[i], dp[i - 1] + nums[i])
 *
 * S2: Prefix -> 前缀和之差，2种情况取最大化 -> 找一个最大的前缀和 - 最小的前缀和(前面一个最大，后面一个最小)
 * subarray sum of [i + 1 : j] = prefix[j] - prefix[i]
 * abs(subarray sum of [i + 1 : j]) = prefix[j] - prefix[i]
 *                                    prefix[i] - prefix[j]
 */
```

【解答】

```java     
// S1: max + min
// time = O(n), space = O(1)
public int maxAbsoluteSum(int[] nums) {
    // corner case
    if (nums == null || nums.length == 0) return 0;

    int max = 0, min = 0;
    int res = 0;

    for (int n : nums) {
        max = Math.max(n, max + n); // 截止到n为止的max subarray sum
        min = Math.min(n, min + n);
        res = Math.max(res, Math.abs(max));
        res = Math.max(res, Math.abs(min));
    }
    return res;
}
```
```java
// S2: Prefix
// time = O(n), space = O(1)
public int maxAbsoluteSum2(int[] nums) {
    // corner case
    if (nums == null || nums.length == 0) return 0;

    int prefix_max = 0, prefix_min = 0;
    int prefix = 0;
    int res = 0;

    for (int x : nums) {
        prefix += x;
        prefix_max = Math.max(prefix_max, prefix);
        prefix_min = Math.min(prefix_min, prefix);
    }
    return prefix_max - prefix_min;
}
```
