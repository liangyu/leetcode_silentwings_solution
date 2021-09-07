package LC1501_1800;
import java.util.*;
public class LC1775_EqualSumArraysWithMinimumNumberofOperations {
    /**
     * You are given two arrays of integers nums1 and nums2, possibly of different lengths. The values in the arrays
     * are between 1 and 6, inclusive.
     *
     * In one operation, you can change any integer's value in any of the arrays to any value between 1 and 6, inclusive.
     *
     * Return the minimum number of operations required to make the sum of values in nums1 equal to the sum of values
     * in nums2. Return -1 if it is not possible to make the sum of the two arrays equal.
     *
     * Input: nums1 = [1,2,3,4,5,6], nums2 = [1,1,2,2,2,2]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums1.length, nums2.length <= 10^5
     * 1 <= nums1[i], nums2[i] <= 6
     * @param nums1
     * @param nums2
     * @return
     */
    // time = O(mlogm + nlogn), space = O(1)
    public int minOperations(int[] nums1, int[] nums2) {
        int sum1 = 0, sum2 = 0;
        for (int num : nums1) sum1 += num;
        for (int num : nums2) sum2 += num;
        if (sum1 < sum2) return minOperations(nums2, nums1);

        int diff = sum1 - sum2;
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int i = nums1.length - 1, j = 0;
        int count = 0;

        while (diff > 0) {
            if (i < 0 && j == nums2.length) return -1; // 已经改无可改了，还是diff > 0，那就是不行了！
            if (i < 0) diff -= (6 - nums2[j++]);
            else if (j == nums2.length) diff -= (nums1[i--] - 1);
            else if (nums1[i] - 1 > 6 - nums2[j]) diff -= (nums1[i--] - 1);
            else diff -= (6 - nums2[j++]);
            count++;
        }
        return count;
    }
}
/**
 * 假设nums1比num2的sum要大，那么我们要将这两个sum更靠近，无非就是两种思路：要么将nums1里面的元素改小，要么将num2里面的元素改大。
 * 为了减少操作次数，我们必须最大化改动的效率。也就是说，如果选择将nums1里面的元素改小，那么我们一定会将最大的元素改成1；
 * 反之，我们也可以将nums2里面最小的元素改成6。
 * 至于这两种方案里面怎么选，自然是查看它们的"变动幅度"，看哪个更大一些。
 * 一旦改动幅度能够cover当前的diff，就说明可以将这两个sum变成一致的。
 * 所以我们将两个数组都排个序。对于nums1，我们从后往前改动；对于nums2，我们从前往后改动。
 * 于是本题本质就是一个双指针，每改动一次，就移动相应的一个指针。
 * 直至diff小于等于零（实现目标），或者两个指针都到了尽头（无法实现目标）。
 * 有点像贪心法 + 双指针
 */
