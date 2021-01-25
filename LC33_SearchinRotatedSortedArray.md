# LC33 Search in Rotated Sorted Array
标签（空格分隔）： LeetCode Java BinarySearch

---
    /**
     * You are given an integer array nums sorted in ascending order (with distinct values), and an integer target.
     *
     * Suppose that nums is rotated at some pivot unknown to you beforehand
     * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
     *
     * If target is found in the array return its index, otherwise, return -1.
     *
     * Input: nums = [4,5,6,7,0,1,2], target = 0
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5000
     * -10^4 <= nums[i] <= 10^4
     * All values of nums are unique.
     * nums is guaranteed to be rotated at some pivot.
     * -10^4 <= target <= 10^4
     *
     * @param nums
     * @param target
     * @return
     */


【难点误区】

本题的难点就在于究竟是根据ums[mid] 与target的大小关系还是nums[start]与nums[mid]的大小关系来进行分段讨论，在这里最好的方案就是沿用LC153和LC154的模板套路，直接还是通过nums[start]与nums[mid]的大小关系来先定出mid的位置，从而根据mid来判断target在mid左边还是右边2种情况来处理，比较简单直观。

【解题思路】

本题是旋转数组经典问题，延续LC153和LC154 Find Minimum in Rotated Sorted Array的思路，我们通过二分法 + 分段讨论来解决。

1. if nums[mid] == target, 没啥好说的直接return
2. 下面的主要难点在于究竟是继续讨论nums[mid] 与target的大小关系还是讨论nums[start]与nums[mid]的大小关系，其主要区别在于是通过前者来判断target落在哪里还是通过后者。由于之前我们正是通过讨论nums[start]与nums[mid]的大小关系来判断mid落在哪个区间，而target又要根据mid的位置来从与其大小关系的比较上来得到target的位置，所以方便起见我们就依然沿用LC153和LC154的解题思路，在探讨mid落在哪个区间的基础上来判断target与之的位置关系。因此，如果mid落在左区间，也就是nums[start] <= nums[mid]的话，那么target可能落在start与mid之间，这样就可以移动end = mid；但也有可能落在start与mid之外，这个时候target可能在左区间mid的右边，也可能落在右区间里，也是mid的右边，同时不管目前是1个还是2个区间，总之target一定在target的右边，那么就可以无脑移动start = mid了。
3. 根据2，我们同样探讨mid落在右区间的话，那么如果target落在右区间mid与end之间，则移动start = mid；否则肯定落在mid之前的左边，无论在左区间还是右区间，异或只有一个区间，都肯定在mid的左边，所以可以无脑移动end = mid。
4. 最后根据模板出loop后check左右start与end，都没有找到target的话返回-1。


```java
// time = O(logn), space = O(1)
public int search(int[] nums, int target) {
    // corner case
    if (nums == null || nums.length == 0) return -1;

    int start = 0, end = nums.length - 1;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        if (nums[mid] == target) return mid;
        if (nums[start] <= nums[mid]) { // 先定出mid的位置，再由此判断target的位置
            if (nums[start] <= target && target <= nums[mid]) end = mid;
            else start = mid;
        } else {
            if (nums[mid] <= target && target <= nums[end]) start = mid;
            else end = mid;
        }
    }
    if (nums[start] == target) return start;
    if (nums[end] == target) return end;
    return -1;
}     
```
