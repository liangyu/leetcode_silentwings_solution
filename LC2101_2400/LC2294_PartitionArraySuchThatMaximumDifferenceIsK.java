package LC2101_2400;
import java.util.*;
public class LC2294_PartitionArraySuchThatMaximumDifferenceIsK {
    /**
     * You are given an integer array nums and an integer k. You may partition nums into one or more subsequences such
     * that each element in nums appears in exactly one of the subsequences.
     *
     * Return the minimum number of subsequences needed such that the difference between the maximum and minimum values
     * in each subsequence is at most k.
     *
     * A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without
     * changing the order of the remaining elements.
     *
     * Input: nums = [3,6,1,2,5], k = 2
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^5
     * 0 <= k <= 10^5
     * @param nums
     * @param k
     * @return
     */
    // S1: sort
    // time = O(nlogn), space = O(1)
    public int partitionArray(int[] nums, int k) {
        Arrays.sort(nums);

        int n = nums.length, count = 1;
        for (int i = 0; i < n; i++) {
            int j = i;
            while (j < n && nums[j] - nums[i] <= k) j++;
            if (j == n) break;
            count++;
            i = j - 1;
        }
        return count;
    }

    // S2: Enumeration
    // time = O(N), space = O(N)
    final int N = 100010;
    public int partitionArray2(int[] nums, int k) {
        boolean[] st = new boolean[N];
        int n = nums.length;
        for (int x : nums) st[x] = true;

        int prev = -1, count = 1, total = 0;
        for (int i = 0; i < N; i++) {
            if (st[i]) {
                total++;
                if (prev == -1) prev = i;
                else if (i - prev > k) {
                    count++;
                    prev = i;
                }
            }
            if (total == n) break;
        }
        return count;
    }
}
