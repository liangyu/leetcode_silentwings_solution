package LC301_600;
import java.util.*;
public class LC442_FindAllDuplicatesinanArray {
    /**
     * Given an integer array nums of length n where all the integers of nums are in the range [1, n] and each integer
     * appears once or twice, return an array of all the integers that appears twice.
     *
     * You must write an algorithm that runs in O(n) time and uses only constant extra space.
     *
     * Input: nums = [4,3,2,7,8,2,3,1]
     * Output: [2,3]
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^5
     * 1 <= nums[i] <= n
     * Each element in nums appears once or twice.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (nums == null || nums.length == 0) return res;

        int n = nums.length;
        for (int i = 0; i < n; i++) nums[i]--;
        for (int i = 0; i < n; i++) {
            while (nums[i] != i  && nums[i] < n && nums[i] != nums[nums[i]]) {
                swap(nums, i, nums[i]);
            }
        }
        for (int i = 0; i < n; i++) {
            if (nums[i] != i) res.add(nums[i] + 1);
        }
        return res;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
/**
 * 满足用index sorting的条件：
 * 1. 值域：1 ~ n;
 * 2. 数组长度: n;
 * Missing, duplicate
 * 尽量把元素重新排序，尽量让每个index位置上放与index相同的value，如果元素是从0到n-1,我们就用0-index，如果是从1 ~ n,那我们就1-index
 */
