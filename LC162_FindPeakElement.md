# LC162 Find Peak Element
标签（空格分隔）： LeetCode Java BinarySearch

---

    /**
     * A peak element is an element that is strictly greater than its neighbors.
     *
     * Given an integer array nums, find a peak element, and return its index.
     * If the array contains multiple peaks, return the index to any of the peaks.
     *
     * You may imagine that nums[-1] = nums[n] = -∞.
     *
     * Input: nums = [1,2,3,1]
     * Output: 2
     *
     * Input: nums = [1,2,1,3,5,6,4]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * -231 <= nums[i] <= 231 - 1
     * nums[i] != nums[i + 1] for all valid i.
     *
     * @param nums
     * @return
     */


【难点误区】

本题难点在于想到只用去考虑一边的单调性来判断相应波峰的位置处在mid的哪一边，即判断nums[mid]与nums[mid + 1]或者nums[mid]与nums[mid - 1]之间的关系。

【解题思路】

本题难点在于如何去判断这是个波峰，mid落在什么地方，如何去相应的移动start和end。由于该题有个限制条件nums[i] != nums[i + 1] for all valid i，所以我们可以只考虑单调的一边。即如果nums[mid] < nums[mid + 1]，表明波峰在mid右边，所以移动start = mid；反之则波峰在mid的左边，移动end = mid。最后左右相邻之后比较左右start和end值即可找到波峰。

同样，如果考虑nums[mid] < nums[mid - 1]这一边也可以，只不过现在看左边单调一边，如果是 < 则表明波峰在左边，移动end = mid，否则波峰在右边，移动start = mid。



```java
// time = O(logn), space = O(1)
public int findPeakElement(int[] nums) {
    // corner case
    if (nums == null || nums.length == 0) {
        return -1;
    }
    
    if (nums.length == 1) return 0;

    int start = 0, end = nums.length - 1;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (nums[mid] < nums[mid + 1]) start = mid;
        else end = mid;
    }
    return nums[start] > nums[end] ? start : end;
    // if (nums[start] > nums[end]) return start;
    // if (nums[start] < nums[end]) return end;
    // return -1; 
    // nums[i] ！= nums[i + 1] -> 不可能存在平台 -> return nums[start] > nums[end] ? start : end;
}     
```
