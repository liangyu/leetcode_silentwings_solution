package LC301_600;

public class LC565_ArrayNesting {
    /**
     * You are given an integer array nums of length n where nums is a permutation of the numbers in the range [0, n - 1].
     *
     * You should build a set s[k] = {nums[k], nums[nums[k]], nums[nums[nums[k]]], ... } subjected to the following rule:
     *
     * The first element in s[k] starts with the selection of the element nums[k] of index = k.
     * The next element in s[k] should be nums[nums[k]], and then nums[nums[nums[k]]], and so on.
     * We stop adding right before a duplicate element occurs in s[k].
     * Return the longest length of a set s[k].
     *
     * Input: nums = [5,4,0,3,1,6,2]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] < nums.length
     * All the values of nums are unique.
     * @param nums
     * @return
     */
    // time = O(n), space = O(1)
    public int arrayNesting(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, res = 0;
        for (int i = 0; i < n; i++) {
            int next = i, count = 0;
            while (nums[next] != -1) {
                int temp = next;
                count++;
                next = nums[next];
                nums[temp] = -1; // 把走过的node都设置成-1
            }
            res = Math.max(res, count);
        }
        return res;
    }
}
