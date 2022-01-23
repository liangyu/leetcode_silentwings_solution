package LC2101_2400;
import java.util.*;
public class LC2150_FindAllLonelyNumbersintheArray {
    /**
     * You are given an integer array nums. A number x is lonely when it appears only once, and no adjacent numbers
     * (i.e. x + 1 and x - 1) appear in the array.
     *
     * Return all lonely numbers in nums. You may return the answer in any order.
     *
     * Input: nums = [10,6,5,8]
     * Output: [10,8]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^6
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public List<Integer> findLonely(int[] nums) {
        List<Integer> res = new ArrayList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : nums) map.put(x, map.getOrDefault(x, 0) + 1);

        for (int x : nums) {
            if (map.get(x) > 1) continue;
            if (map.containsKey(x + 1) || map.containsKey(x - 1)) continue;
            res.add(x);
        }
        return res;
    }
}
