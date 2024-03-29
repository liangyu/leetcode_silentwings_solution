package LC301_600;
import java.util.*;
public class LC384_ShuffleanArray {
    /**
     * Given an integer array nums, design an algorithm to randomly shuffle the array. All permutations of the array
     * should be equally likely as a result of the shuffling.
     *
     * Implement the Solution class:
     *
     * Solution(int[] nums) Initializes the object with the integer array nums.
     * int[] reset() Resets the array to its original configuration and returns it.
     * int[] shuffle() Returns a random shuffling of the array.
     *
     * Input
     * ["Solution", "shuffle", "reset", "shuffle"]
     * [[[1, 2, 3]], [], [], []]
     * Output
     * [null, [3, 1, 2], [1, 2, 3], [1, 3, 2]]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 200
     * -10^6 <= nums[i] <= 10^6
     * All the elements of nums are unique.
     * At most 5 * 10^4 calls in total will be made to reset and shuffle.
     * @param nums
     */
    // time = O(n), space = O(n)
    int[] arr;
    int[] nums;
    public LC384_ShuffleanArray(int[] nums) {
        this.nums = nums;
        arr = nums.clone();
    }

    public int[] reset() {
        arr = nums.clone();
        return arr;
    }

    public int[] shuffle() {
        Random random = new Random();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            int idx = random.nextInt(n);
            swap(arr, i, idx);
        }
        return arr;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
