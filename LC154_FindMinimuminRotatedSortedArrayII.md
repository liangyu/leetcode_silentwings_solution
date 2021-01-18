# LC154 Find Minimum in Rotated Sorted Array II

标签（空格分隔）： LeetCode Java BinarySearch

---

    /**
     * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
     *
     * (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
     *
     * Find the minimum element.
     *
     * The array may contain duplicates.
     *
     * Input: [2,2,2,0,1]
     * Output: 0
     *
     * Note:
     *
     * This is a follow up problem to Find Minimum in Rotated Sorted Array.
     * Would allow duplicates affect the run-time complexity? How and why?
     *
     * @param nums
     * @return
     */


【难点误区】

一旦有duplicate，主要的难点和错误点在：

1. 一旦nums[start] == nums[mid] == nums[end]，则需要通过start++或者end--来打破平衡
2. 讨论落在左区间时，要用 <= cover住整个区间的情况，同时在下面判断单调一致性时，也要用 <= 来代替原来的 < 来cover住单调上升的case。

【解题思路】

沿用LC153 Find Minimum in Rotated Sorted Array的思路，不同的地方有以下3点：

1. 一旦出现nums[start] == nums[mid] == nums[end]，那么二分法的应用前提就被打破，因为你无法判断当前mid落在哪个单调区间之中，所以你就没办法舍弃一半而在另一半之中查找。解决办法就是打破平衡，要么start++，要么end--，无论哪种方式只要换来平衡的打破即可。
2. 由于出现duplicate，在判断落在哪个单调区间时，这时候就要加上==的情况，于是原来的nums[start] < nums[mid]就要变成nums[start] <= nums[mid]了，这样才能cover住整个左区间，else的情况就能cover住mid一定落在了右区间之中。
3. 同样下面如果判断是否只有一个单调区间时，也必须cover住mid == end的情况，否则如果出现 [1, 3, 3] 的情况，就会跑到下面else分支去实现start = mid，从而得出min = 3的错误结果，所以要保证单调一致性，在有duplicate的情况下，<= 一定是首尾都要兼顾和cover住的。


```java
// time = O(logn), space = O(1)
public int findMin(int[] nums) {
    // corner case
    if (nums == null || nums.length == 0) {
        throw new IllegalArgumentException("Invalid input!");
    }

    int start = 0, end = nums.length - 1;
    while (start + 1 < end) {
        int mid = start + (end - start) / 2;
        // case 3: what if nums[start] == nums[mid] == nums[end]
        // you can't tell which range mid is falling into
        // move either side will break the balance
        if (nums[start] == nums[mid] && nums[mid] == nums[end]) start++; // or end--;
        // case 1: mid falls in the left range
        else if (nums[start] <= nums[mid]) { // 注意这里与上面试互斥条件，一定要用else if而不是if !!!
            if (nums[mid] <= nums[end]) return nums[start]; // 这里变成<=，而不仅仅是 < 
            else start = mid;
        } else { // case 2: mid falls in the right range
            end = mid;
        }
    }
    if (nums[start] < nums[end]) return nums[start];
    return nums[end];
}     
```
