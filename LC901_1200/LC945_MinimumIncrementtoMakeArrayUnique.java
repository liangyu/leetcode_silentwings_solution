package LC901_1200;
import java.util.*;
public class LC945_MinimumIncrementtoMakeArrayUnique {
    /**
     * You are given an integer array nums. In one move, you can pick an index i where 0 <= i < nums.length and
     * increment nums[i] by 1.
     *
     * Return the minimum number of moves to make every value in nums unique.
     *
     * The test cases are generated so that the answer fits in a 32-bit integer.
     *
     * Input: nums = [1,2,2]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // S1: sort
    // time = O(nlogn), space = O(logn)
    public int minIncrementForUnique(int[] nums) {
        Arrays.sort(nums);
        int res = 0, last = -1;
        for (int x : nums) {
            int y = Math.max(last + 1, x);
            res += y - x;
            last = y;
        }
        return res;
    }

    // S2: bucket sort
    // time = O(n), space = O(n)
    public int minIncrementForUnique2(int[] nums) {
        int min = nums[0], max = nums[0];
        for (int x : nums) {
            min = Math.min(min, x);
            max = Math.max(max, x);
        }

        int[] bucket = new int[max - min + 1];
        for (int x : nums) {
            bucket[x - min]++;
        }

        int n = bucket.length, res = 0, last = -1;
        for (int i = 0; i < n; i++) {
            if (bucket[i] == 0) continue;
            for (int j = 0; j < bucket[i]; j++) {
                int y = Math.max(last + 1, min + i);
                res += y - (i + min);
                last = y;
            }
        }
        return res;
    }
}
