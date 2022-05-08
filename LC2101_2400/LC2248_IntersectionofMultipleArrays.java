package LC2101_2400;
import java.util.*;
public class LC2248_IntersectionofMultipleArrays {
    /**
     * Given a 2D integer array nums where nums[i] is a non-empty array of distinct positive integers, return the list
     * of integers that are present in each array of nums sorted in ascending order.
     *
     * Input: nums = [[3,1,2,4,5],[1,2,3,4],[3,4,5,6]]
     * Output: [3,4]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 1 <= sum(nums[i].length) <= 1000
     * 1 <= nums[i][j] <= 1000
     * All the values of nums[i] are unique.
     * @param nums
     * @return
     */
    // time = O(m * n), space = O(1)
    public List<Integer> intersection(int[][] nums) {
        List<Integer> res = new ArrayList<>();

        int n = nums.length;
        int[] count = new int[1001];
        for (int[] x : nums) {
            for (int y : x) {
                count[y]++;
            }
        }
        for (int i = 1; i <= 1000; i++) {
            if (count[i] == n) res.add(i);
        }
        return res;
    }
}
