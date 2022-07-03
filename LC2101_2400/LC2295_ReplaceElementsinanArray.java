package LC2101_2400;
import java.util.*;
public class LC2295_ReplaceElementsinanArray {
    /**
     * You are given a 0-indexed array nums that consists of n distinct positive integers. Apply m operations to this
     * array, where in the ith operation you replace the number operations[i][0] with operations[i][1].
     *
     * It is guaranteed that in the ith operation:
     *
     * operations[i][0] exists in nums.
     * operations[i][1] does not exist in nums.
     * Return the array obtained after applying all the operations.
     *
     * Input: nums = [1,2,4,6], operations = [[1,3],[4,7],[6,1]]
     * Output: [3,2,7,1]
     *
     * Constraints:
     *
     * n == nums.length
     * m == operations.length
     * 1 <= n, m <= 10^5
     * All the values of nums are distinct.
     * operations[i].length == 2
     * 1 <= nums[i], operations[i][0], operations[i][1] <= 10^6
     * operations[i][0] will exist in nums when applying the ith operation.
     * operations[i][1] will not exist in nums when applying the ith operation.
     * @param nums
     * @param operations
     * @return
     */
    // time = O(n), space = O(n)
    public int[] arrayChange(int[] nums, int[][] operations) {
        int n = nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) map.put(nums[i], i);

        for (int[] x : operations) {
            int a = x[0], b = x[1];
            int idx = map.get(a);
            map.remove(a);
            map.put(b, idx);
        }

        for (int x : map.keySet()) nums[map.get(x)] = x;
        return nums;
    }
}
