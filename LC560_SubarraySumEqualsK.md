# LC560 Subarray Sum Equals K
标签（空格分隔）： LeetCode Java HashTable PrefixSum

---
    /**
     * Given an array of integers nums and an integer k, return the total number of continuous subarrays
     * whose sum equals to k.
     *
     * Input: nums = [1,1,1], k = 2
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^4
     * -1000 <= nums[i] <= 1000
     * -10^7 <= k <= 10^7
     *
     * @param nums
     * @param k
     * @return
     */

【难点误区】

由于我们解法里，HashMap存的是前缀和以及其对应的count，所以nums[0]的prefixSum = 0一上来也要将其存入HashMap之中，即map.put(0, 1)，否则会造成漏解，这点要非常注意！！！

【解题思路】

本题是prefix sum的经典问题，基本思想就是一路累加，并把到当前为止的所有prefix sum存入HashMap，由于是求个数，所以key = prefix_sum，val = count。每次遍历到一个元素，就去map里查是否有sum - k这个值，类似LC1 Two Sum的思想，一旦存在，则表明长短subarray之间相减，中间夹住的那一段就是加和为k的subarray。这样一来，one pass就能找到所有符合条件的subarray的个数。

这里面唯一要注意的一点是，如果遇到sum - k = 0的情况，那表明从数组开始出发到当前元素的累加刚好和k相等，因此也是一个有效解，但是我们的HashMap存的是一个前缀和极其对应的个数，所以在nums[0]之前，也就是nums[0]的prefixSum = 0，也应该要存入HashMap，否则就会漏掉一个解！！！

```java
// time = O(n), space = O(n)
public int subarraySum(int[] nums, int k) {
    // corner case
    if (nums == null || nums.length == 0) return 0;

    HashMap<Integer, Integer> map = new HashMap<>();
    map.put(0, 1); // 注意，sum - k = 0时也是一个有效答案，表明从nums的头部开始到当前为止的subarray就是1个有效解！！！

    int sum = 0, res = 0;
    for (int i = 0; i < nums.length; i++) {
        sum += nums[i];
        if (map.containsKey(sum - k)) { // 通过prefix sum来做，首尾相减，夹在中间的subarray就是有效解。
            res += map.get(sum - k);
        }
        map.put(sum, map.getOrDefault(sum, 0) + 1);
    }
    return res;
}
```

