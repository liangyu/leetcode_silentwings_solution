# LC153 Find Minimum in Rotated Sorted Array

标签（空格分隔）： LeetCode Java BinarySearch

---

    /**
     * Suppose an array of length n sorted in ascending order is rotated between 1 and n times.
     * For example, the array nums = [0,1,2,4,5,6,7] might become:
     *
     * [4,5,6,7,0,1,2] if it was rotated 4 times.
     * [0,1,2,4,5,6,7] if it was rotated 7 times.
     * Notice that rotating an array [a[0], a[1], a[2], ..., a[n-1]] 1 time results in the array
     * [a[n-1], a[0], a[1], a[2], ..., a[n-2]].
     *
     * Given the sorted rotated array nums, return the minimum element of this array.
     *
     * Input: nums = [3,4,5,1,2]
     * Output: 1
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 5000
     * -5000 <= nums[i] <= 5000
     * All the integers of nums are unique.
     * nums is sorted and rotated between 1 and n times.
     *
     * @param nums
     * @return
     */

【难点误区】

难点主要在于旋转分成2区间下，如何根据mid落在哪个区进行讨论，同时需要考虑到转回一个单调区间的case。具体解决方法为二分查找，如果mid落在左区，则看当前是一个单调区间还是2个区；如果是右区的话，那么由于答案一定在右区，所以[mid, end]这段可以直接舍弃，向mid左半边去继续查找。


【解题思路】

对付sorted rotated array的题目，大方向是一个单调sorted array当前被拆分成了2段单调的区间，分别是左区和右区。解决方法是用二分模板，然后看mid落在左区还是右区。由于是求最小值，所以按照下图所示，两种情况：

1. 如果是转回原来一个单调区间的话，那么无疑最小值就是数组的左端点start
2. 如果是左右区割裂的话，那么最小值无疑就是右区的左端点
		
		             /                           /
		           /                           /
		         /                            ----------
		       /                                       /
		     /                                       /   
		  case 1                                 case 2

然后就可以根据这两种情况，看mid落在哪个区间里然后舍弃掉不存在min的一半，转而在剩下的另一半中查找。

```java
// time = o(logn), space = O(1)
public int findMin(int[] nums) {
    // corner case
    if (nums == null || nums.length == 0) {
        throw new IllegalArgumentException("Invalid input!");
    }

    int start = 0, end = nums.length - 1;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (nums[start] < nums[mid]) { // case 1: 如果mid落在左边上升区间
            if (nums[mid] < nums[end]) return nums[start]; // 1.1: 单调递增，则start就是最小
            else start = mid; // 1.2: 2个单调区间，则最小值应该落在第2区间的左端点 -> move start = mid
        } else { // case 2: 如果落在右边上升区间
            end = mid; // 只有一种情况，就是min落在右边上升区间的左端点 -> move end = mid
        }
    }
    if (nums[start] < nums[end]) return nums[start];
    return nums[end];
}     
```
