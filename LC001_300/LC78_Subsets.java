package LC001_300;
import java.util.*;
public class LC78_Subsets {
    /**
     * Given an integer array nums, return all possible subsets (the power set).
     *
     * The solution set must not contain duplicate subsets.
     *
     * Input: nums = [1,2,3]
     * Output: [[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10
     * -10 <= nums[i] <= 10
     * All the numbers of nums are unique.
     *
     * @param nums
     * @return
     */
    // time = O(n * 2^n), space = O(n)
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // corner case
        if (nums == null || nums.length == 0) return res;

        dfs(nums, 0, new ArrayList<>(), res);
        return res;
    }

    private void dfs(int[] nums, int idx, List<Integer> list, List<List<Integer>> res) {
        res.add(new ArrayList<>(list)); // O(n)

        for (int i = idx; i < nums.length; i++) { // O(n)
            list.add(nums[i]);
            dfs(nums, i + 1, list, res);
            list.remove(list.size() - 1);
        }
    }
}

/**
 * 时间复杂度： O(n * 2^n)
 *                     { }
 *                    / |  \
 *                 {1} {2} {3}     --> O(n)
 *                /  \
 *           2取/不取  3取/不取      --> O(2^(n - 1))
 *      {1, 2, 3}, {1, 3}, {1, 2}, {1}  ==> O(2^n)
 *      but we also need to do deep copy for each possible subset and add it to the res list,
 *      and in worst case the cost of deep copy is O(n)
 *      ==> time = O(n * 2^n)
 *
 * 空间复杂度：O(n) 递归时的栈空间消耗，也就是压栈的深度，如果考虑output的space，那么space = O(n * 2^n)
 */
