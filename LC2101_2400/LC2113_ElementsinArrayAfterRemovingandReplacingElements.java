package LC2101_2400;

public class LC2113_ElementsinArrayAfterRemovingandReplacingElements {
    /**
     * You are given a 0-indexed integer array nums. Initially on minute 0, the array is unchanged. Every minute, the
     * leftmost element in nums is removed until no elements remain. Then, every minute, one element is appended to the
     * end of nums, in the order they were removed in, until the original array is restored. This process repeats
     * indefinitely.
     *
     * For example, the array [0,1,2] would change as follows: [0,1,2] → [1,2] → [2] → [] → [0] → [0,1] → [0,1,2] →
     * [1,2] → [2] → [] → [0] → [0,1] → [0,1,2] → ...
     * You are also given a 2D integer array queries of size n where queries[j] = [timej, indexj]. The answer to the
     * jth query is:
     *
     * nums[indexj] if indexj < nums.length at minute timej
     * -1 if indexj >= nums.length at minute timej
     * Return an integer array ans of size n where ans[j] is the answer to the jth query.
     *
     * Input: nums = [0,1,2], queries = [[0,2],[2,0],[3,2],[5,0]]
     * Output: [2,2,-1,0]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 100
     * n == queries.length
     * 1 <= n <= 10^5
     * queries[j].length == 2
     * 0 <= timej <= 10^5
     * 0 <= indexj < nums.length
     * @param nums
     * @param queries
     * @return
     */
    // time = O(m), space = O(1）
    public int[] elementInNums(int[] nums, int[][] queries) {
        int n = nums.length;
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) { // O(m)
            int t = queries[i][0], idx = queries[i][1];
            int remain = t % (2 * n);
            if (remain == 0) res[i] = -1;
            else if (remain < n) {
                if (remain + idx < n) res[i] = nums[remain + idx];
                else res[i] = -1;
            } else {
                if (idx >= 0 && idx < n) res[i] = nums[idx];
                else res[i] = -1;
            }
        }
        return res;
    }
}
