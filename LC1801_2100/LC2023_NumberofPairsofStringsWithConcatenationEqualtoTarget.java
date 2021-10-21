package LC1801_2100;
import java.util.*;
public class LC2023_NumberofPairsofStringsWithConcatenationEqualtoTarget {
    /**
     * Given an array of digit strings nums and a digit string target, return the number of pairs of indices (i, j)
     * (where i != j) such that the concatenation of nums[i] + nums[j] equals target.
     *
     * Input: nums = ["777","7","77","77"], target = "7777"
     * Output: 4
     *
     * Constraints:
     *
     * 2 <= nums.length <= 100
     * 1 <= nums[i].length <= 100
     * 2 <= target.length <= 100
     * nums[i] and target consist of digits.
     * nums[i] and target do not have leading zeros.
     * @param nums
     * @param target
     * @return
     */
    // time = O(n), space = O(n)
    public int numOfPairs(String[] nums, String target) {
        // corner case
        if (nums == null || nums.length == 0 || target == null || target.length() == 0) return 0;

        HashMap<String, Integer> map = new HashMap<>();
        for (String num : nums) map.put(num, map.getOrDefault(num, 0) + 1);

        int n = nums.length, res = 0;
        String diff = "";
        for (int i = 0; i < n; i++) {
            if (target.length() < nums[i].length()) continue;
            int idx = target.indexOf(nums[i]);
            if (idx == 0) {
                diff = target.substring(nums[i].length());
                if (map.containsKey(diff)) res += map.get(diff);
                if (diff.equals(nums[i])) res--;
            }
        }
        return res;
    }
}
