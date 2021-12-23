package LC301_600;
import java.util.*;
public class LC324_WiggleSortII {
    /**
     * Given an integer array nums, reorder it such that nums[0] < nums[1] > nums[2] < nums[3]....
     *
     * You may assume the input array always has a valid answer.
     *
     * Input: nums = [1,5,1,1,6,4]
     * Output: [1,6,1,5,1,4]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 5 * 10^4
     * 0 <= nums[i] <= 5000
     * It is guaranteed that there will be an answer for the given input nums.
     *
     * Follow Up: Can you do it in O(n) time and/or in-place with O(1) extra space?
     * @param nums
     */
    // S1: Sort
    // time = O(nlogn), space = O(n)
    public void wiggleSort(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        int i = (n - 1) / 2, j = n - 1;

        int flag = 0, idx = 0;
        int[] res = new int[n];
        for (int k = 0; k < n ; k++){
            if (flag == 0) res[idx++] = nums[i--];
            else res[idx++] = nums[j--];
            flag = 1 - flag;
        }
        for (i = 0; i < n; i++) nums[i] = res[i];
    }
}
/**
 * 我们会先排序，利用已知的大小关系来排列数组的元素。
 * 一个简单的方法就是用双指针，一个指向末尾（最大的数），另一个指向中间（中位数）。
 * 每次取数的时候大小交错，取完一个分别将各自的指针往前移动一次。这样一定能保证是大小交错的。
 */
