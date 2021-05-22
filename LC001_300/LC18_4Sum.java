package LC001_300;
import java.util.*;
public class LC18_4Sum {
    /**
     * Given an array nums of n integers and an integer target, are there elements a, b, c, and d in nums such that
     * a + b + c + d = target? Find all unique quadruplets in the array which gives the sum of target.
     *
     * Notice that the solution set must not contain duplicate quadruplets
     *
     * Input: nums = [1,0,-1,0,-2,2], target = 0
     * Output: [[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
     *
     * Constraints:
     *
     * 0 <= nums.length <= 200
     * -10^9 <= nums[i] <= 10^9
     * -10^9 <= target <= 10^9
     * @param nums
     * @param target
     * @return
     */
    // time = O(n^3), space = O(1)
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (nums == null || nums.length == 0) return res;

        Arrays.sort(nums); // O(nlogn)

        for (int i = 0; i < nums.length - 3; i++) { // O(n)
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            for (int j = i + 1; j < nums.length - 2; j++) { // O(n)
                if (j > i + 1 && nums[j] == nums[j - 1]) continue;
                int left = j + 1, right = nums.length - 1;
                while (left < right) { // O(n)
                    int sum = nums[i] + nums[j] + nums[left] + nums[right];
                    if (sum == target) {
                        res.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) left++;
                        while (left < right && nums[right] == nums[right - 1]) right--;
                        left++;
                        right--;
                    } else if (sum < target) left++;
                    else right--;
                }
            }
        }
        return res;
    }
}
