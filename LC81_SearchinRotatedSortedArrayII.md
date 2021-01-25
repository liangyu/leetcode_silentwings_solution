# LC81 Search in Rotated Sorted Array II
标签（空格分隔）： LeetCode Java BinarySearch

---
    /**
     * You are given an integer array nums sorted in ascending order (not necessarily distinct values),
     * and an integer target.
     *
     * Suppose that nums is rotated at some pivot unknown to you beforehand (i.e., [0,1,2,4,4,4,5,6,6,7] might
     * become [4,5,6,6,7,0,1,2,4,4]).
     *
     * If target is found in the array return its index, otherwise, return -1.
     *
     * Input: nums = [2,5,6,0,0,1,2], target = 0
     * Output: true
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5000
     * -10^4 <= nums[i] <= 10^4
     * nums is guaranteed to be rotated at some pivot.
     * -10^4 <= target <= 10^4
     *
     * Follow up: This problem is the same as Search in Rotated Sorted Array, where nums may contain duplicates.
     * Would this affect the run-time complexity? How and why?
     *
     * @param nums
     * @param target
     * @return
     */


【难点误区】

同LC153和LC154一样，对于duplicate题目的处理在于break balance，只要明白移动首尾任何一头即可打破平衡，继续使用二分法。

【解题思路】

本题延续LC33 Search in Rotated Sorted Array的思路，参照LC153和LC154的套路，这里唯一的区别就在于出现nums[start] == nums[mid] == nums[end] 的时候，如果break balance，最简单方法就是start++或者end--。其余部分跟LC33基本毫无区别，直接搬来即可。


```java
// time = O(logn) (worst : O(n)), space = O(1)
public boolean search(int[] nums, int target) {
    // corner case
    if (nums == null || nums.length == 0) return false;

    int start = 0, end = nums.length - 1;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (nums[mid] == target) return true;
        if (nums[start] == nums[mid] && nums[mid] == nums[end]) start++; // break the balance
        else if (nums[start] <= nums[mid]) {
            if (nums[start] <= target && target <= nums[mid]) end = mid;
            else start = mid;
        } else {
            if (nums[mid] <= target && target <= nums[end]) start = mid;
            else end = mid;
        }
    }
    if (nums[start] == target || nums[end] == target) return true;
    return false;
}   
```
