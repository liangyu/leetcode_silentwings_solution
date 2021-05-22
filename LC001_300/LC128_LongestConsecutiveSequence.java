package LC001_300;
import java.util.*;
public class LC128_LongestConsecutiveSequence {
    /**
     * Given an unsorted array of integers nums, return the length of the longest consecutive elements sequence.
     *
     * Input: nums = [100,4,200,1,3,2]
     * Output: 4
     *
     * Constraints:
     *
     * 0 <= nums.length <= 10^4
     * -10^9 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int longestConsecutive(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        HashSet<Integer> set = new HashSet<>();
        for (int n : nums) set.add(n);

        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            int down = nums[i] - 1, up = nums[i] + 1;
            while (set.contains(down)) set.remove(down--);
            while (set.contains(up)) set.remove(up++);
            res = Math.max(res, up - down - 1);
        }
        return res;
    }
}
