# LC35 Search Insert Position
标签（空格分隔）： LeetCode BinarySearch

---

    /**
     * Given a sorted array of distinct integers and a target value, return the index if the target is found.
     * If not, return the index where it would be if it were inserted in order.
     *
     * Input: nums = [1,3,5,6], target = 5
     * Output: 2
     *
     * @param nums
     * @param target
     * @return
     */


【难点误区】

1. 分析得出实际上就是要求>= target的1st position，所以 A[mid] >= target时，移动的是end = mid，但是由于这里都是distinct元素，所以无论移动start还是end都没有关系，等价的。
2.  post-processing时，由于是求1st position，所以是先check start，再check end。
3. 特别注意最后的edge case，如果走到最后都没找到，就是要插入到array的最后位置，即end + 1。


【解题思路】

通用的二分法模板：

```java
start + 1 < end
start + (end - start）/ 2
A[mid] ==, < , >
a[start] A[end] ? target
```
二分法的要点：
Given an sorted integer array - nums, and an integer - target.

Find the any/first/last position of target in nums

Return -1 if target does not exist.

本题求插入位置，本质上就是找 >= target的1st position，因此按照二分法模板，当A[mid] == target的时候，我们要移动的就是end = mid。最后出while loop后，先对start，再对end做post-processing的check，同时最后边界条件返回end + 1，也就是如果走到最后都没有找到的话，言外之意就是要将target插入到array的最后，即nums.length。

```java     
// time = O(logn), space = O(1)
public int searchInsert(int[] nums, int target) {
    // corner case
    if (nums == null || nums.length == 0) return -1;

    int start = 0, end = nums.length - 1;

    // step 1: binary search -> shrink to 2 numbers, and then do the post-processing
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (nums[mid] == target) { // find the 1st position >= target, so move start in equal condition
            end = mid;
        } else if (nums[mid] < target) {
            start = mid;
        } else {
            end = mid;
        }
    }

    // step 2: post-processing
    if (nums[start] >= target) return start;
    if (nums[end] >= target) return end;
    return end + 1;
}
```
