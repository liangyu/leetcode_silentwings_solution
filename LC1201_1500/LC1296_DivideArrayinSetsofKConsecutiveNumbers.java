package LC1201_1500;
import java.util.*;
public class LC1296_DivideArrayinSetsofKConsecutiveNumbers {
    /**
     * Given an array of integers nums and a positive integer k, find whether it is possible to divide this array into
     * sets of k consecutive numbers.
     *
     * Return true if it is possible. Otherwise, return false.
     *
     * Input: nums = [1,2,3,3,4,4,5,6], k = 4
     * Output: true
     *
     * Constraints:
     *
     * 1 <= k <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param nums
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(n)
    public boolean isPossibleDivide(int[] nums, int k) {
        int n = nums.length;
        if (n % k != 0) return false;

        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int x : nums) map.put(x, map.getOrDefault(x, 0) + 1);

        while (map.size() > 0) {
            int m = map.firstKey();
            for (int i = m; i < m + k; i++) {
                if (map.containsKey(i)) {
                    map.put(i, map.get(i) - 1);
                    if (map.get(i) == 0) map.remove(i);
                } else return false;
            }
        }
        return true;
    }
}
/**
 * same as LC846
 */