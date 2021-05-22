package LC001_300;
import java.util.*;
public class LC47_PermutationsII {
    /**
     * Given a collection of numbers, nums, that might contain duplicates, return all possible unique permutations in
     * any order.
     *
     * Input: nums = [1,1,2]
     * Output:
     * [[1,1,2],
     *  [1,2,1],
     *  [2,1,1]]
     *
     *  Constraints:
     *
     * 1 <= nums.length <= 8
     * -10 <= nums[i] <= 10
     * @param nums
     * @return
     */
    // time = O(n * n!), space = O(n)
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (nums == null || nums.length == 0) return res;

        dfs(nums, 0, res);
        return res;
    }

    private void dfs(int[] nums, int idx, List<List<Integer>> res) {
        // base case
        if (idx == nums.length) {
            List<Integer> list = new ArrayList<>();
            for (int n : nums) list.add(n);
            res.add(list);
            return;
        }

        HashSet<Integer> set = new HashSet<>();
        for (int i = idx; i < nums.length; i++) {
            if (set.add(nums[i])) {
                swap(nums, idx, i);
                dfs(nums, idx + 1, res);
                swap(nums, idx, i);
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
