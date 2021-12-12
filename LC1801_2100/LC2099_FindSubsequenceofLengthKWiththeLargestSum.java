package LC1801_2100;
import java.util.*;
public class LC2099_FindSubsequenceofLengthKWiththeLargestSum {
    /**
     * You are given an integer array nums and an integer k. You want to find a subsequence of nums of length k that has
     * the largest sum.
     *
     * Return any such subsequence as an integer array of length k.
     *
     * A subsequence is an array that can be derived from another array by deleting some or no elements without changing
     * the order of the remaining elements.
     *
     * Input: nums = [2,1,3,3], k = 2
     * Output: [3,3]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * -105 <= nums[i] <= 10^5
     * 1 <= k <= nums.length
     * @param nums
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int[] maxSubsequence(int[] nums, int k) {
        int n = nums.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o2[0] - o1[0]);
        for (int i = 0; i < n; i++) pq.offer(new int[]{nums[i], i});

        int[] idx = new int[k];
        for (int i = 0; i < k; i++) idx[i] = pq.poll()[1];

        Arrays.sort(idx);
        int[] res = new int[k];
        for (int i = 0; i < k; i++) res[i] = nums[idx[i]];
        return res;
    }
}
