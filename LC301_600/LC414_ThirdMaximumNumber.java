package LC301_600;
import java.util.*;
public class LC414_ThirdMaximumNumber {
    /**
     * Given integer array nums, return the third maximum number in this array. If the third maximum does not exist,
     * return the maximum number.
     *
     * Input: nums = [3,2,1]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^4
     * -2^31 <= nums[i] <= 2^31 - 1
     *
     *
     * Follow up: Can you find an O(n) solution?
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int thirdMax(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return -1;

        int first = nums[0];
        Integer second = null, third = null;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] >= first) {
                if (nums[i] > first) {
                    if (second != null) third = second;
                    second = first;
                }
                first = nums[i];
            } else if (second == null || nums[i] >= second) {
                if (second != null && nums[i] > second) third = second;
                second = nums[i];
            } else if (third == null || nums[i] >= third) {
                third = nums[i];
            }
        }
        return third == null ? first : third;
    }

    // S2: HashSet + Collections.max
    // time = O(n), space = O(1)
    public int thirdMax2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return -1;

        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) set.add(num);

        int max = Collections.max(set); // O(n)
        if (set.size() < 3) return max;

        set.remove(max);
        int secondMax = Collections.max(set);
        set.remove(secondMax);
        return Collections.max(set);
    }
}
