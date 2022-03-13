package LC2101_2400;
import java.util.*;
public class LC2190_MostFrequentNumberFollowingKeyInanArray {
    /**
     * You are given a 0-indexed integer array nums. You are also given an integer key, which is present in nums.
     *
     * For every unique integer target in nums, count the number of times target immediately follows an occurrence of
     * key in nums. In other words, count the number of indices i such that:
     *
     * 0 <= i <= n - 2,
     * nums[i] == key and,
     * nums[i + 1] == target.
     * Return the target with the maximum count. The test cases will be generated such that the target with maximum
     * count is unique.
     *
     * Input: nums = [1,100,200,1,100], key = 1
     * Output: 100
     *
     * Constraints:
     *
     * 2 <= nums.length <= 1000
     * 1 <= nums[i] <= 1000
     * The test cases will be generated such that the answer is unique.
     * @param nums
     * @param key
     * @return
     */
    // time = O(n), space = O(n)
    public int mostFrequent(int[] nums, int key) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = nums.length, max = 0, res = -1;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] == key) {
                map.put(nums[i + 1], map.getOrDefault(nums[i + 1], 0) + 1);
                if (max < map.get(nums[i + 1])) {
                    max = map.get(nums[i + 1]);
                    res = nums[i + 1];
                }
            }
        }
        return res;
    }
}
