package LC001_300;
import java.util.*;
public class LC217_ContainsDuplicate {
    /**
     * Given an integer array nums, return true if any value appears at least twice in the array, and return false if
     * every element is distinct.
     *
     * Input: nums = [1,2,3,1]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -10^9 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // S1: HashSet
    // time = O(n), space = O(n)
    public boolean containsDuplicate(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        HashSet<Integer> set = new HashSet<>();
        for (int n : nums) {
            if (!set.add(n)) return true;
        }
        return false;
    }

    // S2: sort
    // time = O(nlogn), space = O(1)
    public boolean containsDuplicate2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        Arrays.sort(nums);

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) return true;
        }
        return false;
    }
}
