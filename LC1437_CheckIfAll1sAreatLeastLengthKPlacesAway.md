# LC1437 Check If All 1's Are at Least Length K Places Away

标签（空格分隔）： LeetCode Java 

---
    /**
     * Given an array nums of 0s and 1s and an integer k, return True if all 1's are at least k places away
     * from each other, otherwise return False.
     *
     * Input: nums = [1,0,0,0,1,0,0,1], k = 2
     * Output: true
     *
     * Input: nums = [1,0,0,1,0,1], k = 2
     * Output: false
     *
     * Constraints:
     *
     * 1 <= nums.length <= 105
     * 0 <= k <= nums.length
     * nums[i] is 0 or 1
     *
     * @param nums
     * @param k
     * @return
     */

【难点误区】

1. 想到用统计相邻2个1之间的0的个数来与k比较
2. 每次遇到下一个1的时候，count要记得设回0
3. count得初始化为0，以应对遇到第一个1直接返回false的局面

【解题思路】

本题最优解用到的一个巧妙思路就是要求任意2个1之间的距离是否至少为k，其实就是求相邻的2个1之间是否至少有k个0，于是我们可以着眼于统计1之后在遇到下个1之前0的个数，用1个count去统计。这样当遇到下个1时，我们就check下当前count的值与k的关系即可，同时再从当前遇到的这第二个1开始继续遍历下去，这个时候count记得一定要重新设为0。



```java     
// time = O(n), space = O(1)
public boolean kLengthApart(int[] nums, int k) {
    // corner case
    if (nums == null || nums.length == 0 || k < 0) return false;

    int count = k; // 为了在遇到初始的第一个1时不返回false,在这里设置为k
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] == 1) {
            if (count < k) return false;
            count = 0; // 记得遇到新的一个1后，则要开始重新计数了
        } else count++; // 如果是0则++ => 统计各个1之间0的个数是否 >= k
    }
    return true;
}
```
