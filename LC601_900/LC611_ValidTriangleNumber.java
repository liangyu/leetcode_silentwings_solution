package LC601_900;
import java.util.*;
public class LC611_ValidTriangleNumber {
    /**
     * Given an integer array nums, return the number of triplets chosen from the array that can make triangles if we
     * take them as side lengths of a triangle.
     *
     * Input: nums = [2,2,3,4]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] <= 1000
     * @param nums
     * @return
     */
    // S1: brute-force
    // time = O(n^3), space = O(1)
    public int triangleNumber(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, res = 0;
        Arrays.sort(nums);

        for (int i = 0; i < n - 2; i++) {
            for (int j = i + 1; j < n - 1; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i] + nums[j] > nums[k]) res++;
                    else break;
                }
            }
        }
        return res;
    }

    // S2: linear scan
    // time = O(n^2), space = O(logn)
    public int triangleNumber2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int count = 0, n = nums.length;
        Arrays.sort(nums);

        for (int i = 0; i < n - 2; i++) {
            int k = i + 2;
            for (int j = i + 1; j < n - 1 && nums[i] != 0; j++) {
                while (k < n && nums[i] + nums[j] > nums[k]) k++;
                count += k - j - 1;
            }
        }
        return count;
    }
}
/**
 * all runs through this while loop during one iteration of the outer for loop will together cover all elements.
 * For instance, with i = 0, j = 1, k will run from 2 to a1; with i = 0, j = 2, k will run from a1 to a2;
 * with i = 0, j = 3, k will run from a2 to a3, ...
 * In total, with i = 0, j will run from 1 to n once, and k will run from 2 to n once,
 * making for a runtime of O(2n) = O(n) for each iteration of the outer loop. Since there are n such iterations,
 * the entire process has a runtime of O(n²)
 */
