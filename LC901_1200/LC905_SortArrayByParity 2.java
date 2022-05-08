package LC901_1200;
import java.util.*;
public class LC905_SortArrayByParity {
    /**
     * Given an array nums of non-negative integers, return an array consisting of all the even elements of nums,
     * followed by all the odd elements of nums.
     *
     * You may return any answer array that satisfies this condition.
     *
     * Input: nums = [3,1,2,4]
     * Output: [2,4,3,1]
     *
     * Note:
     *
     * 1 <= nums.length <= 5000
     * 0 <= nums[i] <= 5000
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int[] sortArrayByParity(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] res = new int[n];
        List<Integer> list = new ArrayList<>();

        for (int num : nums) {
            if (num % 2 == 0) list.add(0, num);
            else list.add(num);
        }

        for (int i = 0; i < n; i++) res[i] = list.get(i);
        return res;
    }
}
