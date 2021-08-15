package LC1501_1800;

public class LC1537_GettheMaximumScore {
    /**
     * You are given two sorted arrays of distinct integers nums1 and nums2.
     *
     * A valid path is defined as follows:
     *
     * Choose array nums1 or nums2 to traverse (from index-0).
     * Traverse the current array from left to right.
     * If you are reading any value that is present in nums1 and nums2 you are allowed to change your path to the other
     * array. (Only one repeated value is considered in the valid path).
     * Score is defined as the sum of uniques values in a valid path.
     *
     * Return the maximum score you can obtain of all possible valid paths.
     *
     * Since the answer may be too large, return it modulo 10^9 + 7.
     *
     * Input: nums1 = [2,4,5,8,10], nums2 = [4,6,8,9]
     * Output: 30
     *
     * Constraints:
     *
     * 1 <= nums1.length <= 10^5
     * 1 <= nums2.length <= 10^5
     * 1 <= nums1[i], nums2[i] <= 10^7
     * nums1 and nums2 are strictly increasing.
     * @param nums1
     * @param nums2
     * @return
     */
    // time = O(m + n), space = O(1)
    public int maxSum(int[] nums1, int[] nums2) {
        int i = 0, j = 0;
        long x = 0, y = 0;
        int m = nums1.length, n = nums2.length;
        long M = (long)(1e9 + 7);

        while (i < m || j < n) {
            if (i == m) {
                y += nums2[j];
                j++;
            } else if (j == n) {
                x += nums1[i];
                i++;
            } else if (nums1[i] < nums2[j]) {
                x += nums1[i];
                i++;
            } else if (nums1[i] > nums2[j]){
                y += nums2[j];
                j++;
            } else if (nums1[i] == nums2[j]) { // 到达传送门位置
                x = Math.max(x, y) + nums1[i];
                y = x;
                i++;
                j++;
            }
        }

        return (int)(Math.max(x, y) % M);
    }
}
/**
 * 跳转的时候，重复的数字只+1次
 * x x x x o w w w o
 *         |       |
 * y y y   o z z z o
 * 2个相同的数字构成传送门
 * 如果你在没有走到传送门的时候，你在上路的路径只能往右，直到碰到传送门，这时就多一个选择
 * 同样，对于下路来说也是一样
 * 如果同时到达传送门，你会做什么决策？
 * 在score1与score2之间取一个最大值，往回看 => 确定了到达前的最优路径
 * 前面的分数 + 最后一段
 * 阶段性的一个贪心，每对传送门把问题砍成了好几段
 * 双指针问题，各个指针走到传送门停下来
 * 如何在传送门停下呢？一个比较好的办法就是看指针大小，最后一定会让它们停在相等的位置上
 * 总结：本质上就是个双指针，目的就是移动双指针，使得它们可以同时停在传送门的位置上
 * 上下unify一下取个最大值，继往开来，以此为新起点继续兵分两路向右前进
 */
