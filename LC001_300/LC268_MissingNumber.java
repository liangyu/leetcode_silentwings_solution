package LC001_300;
import java.util.*;
public class LC268_MissingNumber {
    /**
     * Given an array nums containing n distinct numbers in the range [0, n], return the only number in the range that
     * is missing from the array.
     *
     * Follow up: Could you implement a solution using only O(1) extra space complexity and O(n) runtime complexity?
     *
     * Input: nums = [3,0,1]
     * Output: 2
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^4
     * 0 <= nums[i] <= n
     * All the numbers of nums are unique.
     * @param nums
     * @return
     */
    // S1: XOR
    // time = O(n), space = O(1)
    public int missingNumber(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return -1;

        int n = nums.length, res = 0;
        for (int i = 0; i <= n; i++) {
            res ^= i;
        }

        for (int x : nums) res ^= x;
        return res;
    }

    // S2: Math
    // time = O(n), space = O(1)
    public int missingNumber2(int[] nums) {
        int n = nums.length;
        int res = n * (n + 1) / 2;
        for (int x : nums) res -= x;
        return res;
    }

    // S3: indexing sort
    // time = O(n), space = O(n)
    public int missingNumber3(int[] nums) {
        int n = nums.length;
        int[] arr = new int[n + 1];
        arr[n] = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) arr[i] = nums[i];
        for (int i = 0; i <= n; i++) {
            while (arr[i] != i && arr[i] <= n && arr[i] != arr[arr[i]]) { // 基本满足这3个条件来套用indexing sort模板
                swap(arr, i, arr[i]);
            }
        }

        for (int i = 0; i <= n; i++) {
            if (arr[i] != i) return i;
        }
        return -1;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
/**
 * 有一个更优雅的做法。nums[i]包含了0~N的所有数（除了一个missing number，假设是x）。我们将其亦或起来。
 * 同时将这个结果再与0~N都亦或一遍。
 * 这样，除了x，其他的数字都被xor了两遍而被消除。
 * 剩下的结果就是x。
 * missing =4∧(0∧0)∧(1∧1)∧(2∧3)∧(3∧4)
 * =(4∧4)∧(0∧0)∧(1∧1)∧(3∧3)∧2
 * =0∧0∧0∧0∧2
 * =2
 */
