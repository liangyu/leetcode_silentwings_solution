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
    // S1
    // time = O(n), space = O(1)
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (nums == null || nums.length == 0) return res;

        int n = nums.length;
        for (int i = 0; i < n; i++) {
            while (nums[i] != i + 1 && nums[i] != nums[nums[i] - 1]) {
                swap(nums, i, nums[i] - 1);
            }
        }

        for (int i = 0; i < n; i++) {
            if (nums[i] != i + 1) res.add(nums[i]);
        }
        return res;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    // S2
    // time = O(n), space = O(1)
    public List<Integer> findDuplicates2(int[] nums) {
        List<Integer> res = new ArrayList<>();

        for (int x : nums) {
            int p = Math.abs(x) - 1; // 0-index
            nums[p] *= -1;
            if (nums[p] > 0) res.add(Math.abs(x));
        }
        return res;
    }
}
/**
 * ref: LC41
 * 满足用index sorting的条件：
 * 1. 值域：1 ~ n;
 * 2. 数组长度: n;
 * Missing, duplicate
 * [4,3,2,7,8,2,3,1]
 * [7,3,2,4,8,2,3,1]
 * [3,3,2,4,8,2,7,1]
 * [2,3,3,4,8,2,7,1]
 * [3,2,3,4,8,2,7,1]
 * [3,2,3,4,1,2,7,8]
 * [1,2,3,4,3,2,7,8] => 3,2
 * 尽量把元素重新排序，尽量让每个index位置上放与index相同的value，如果元素是从0到n-1,我们就用0-index，如果是从1 ~ n,那我们就1-index
 */
