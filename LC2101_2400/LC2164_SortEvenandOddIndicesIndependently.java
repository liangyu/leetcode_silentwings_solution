package LC2101_2400;
import java.util.*;
public class LC2164_SortEvenandOddIndicesIndependently {
    /**
     * You are given a 0-indexed integer array nums. Rearrange the values of nums according to the following rules:
     *
     * Sort the values at odd indices of nums in non-increasing order.
     * For example, if nums = [4,1,2,3] before this step, it becomes [4,3,2,1] after. The values at odd indices 1 and
     * 3 are sorted in non-increasing order.
     * Sort the values at even indices of nums in non-decreasing order.
     * For example, if nums = [4,1,2,3] before this step, it becomes [2,1,4,3] after. The values at even indices 0 and 2
     * are sorted in non-decreasing order.
     * Return the array formed after rearranging the values of nums.
     *
     * Input: nums = [4,1,2,3]
     * Output: [2,3,4,1]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 1 <= nums[i] <= 100
     * @param nums
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int[] sortEvenOdd(int[] nums) {
        int n = nums.length;
        List<Integer> odd = new ArrayList<>();
        List<Integer> even = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if (i % 2 == 1) odd.add(nums[i]);
            else even.add(nums[i]);
        }

        Collections.sort(odd, ((o1, o2) -> o2 - o1));
        Collections.sort(even);

        int[] res = new int[n];
        int idx1 = 0, idx2 = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) res[i] = even.get(idx1++);
            else res[i] = odd.get(idx2++);
        }
        return res;
    }
}
