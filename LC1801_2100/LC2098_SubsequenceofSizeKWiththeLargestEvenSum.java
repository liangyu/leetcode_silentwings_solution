package LC1801_2100;
import java.util.*;
public class LC2098_SubsequenceofSizeKWiththeLargestEvenSum {
    /**
     * You are given an integer array nums and an integer k. Find the largest even sum of any subsequence of nums that
     * has a length of k.
     *
     * Return this sum, or -1 if such a sum does not exist.
     *
     * A subsequence is an array that can be derived from another array by deleting some or no elements without changing
     * the order of the remaining elements.
     *
     * Input: nums = [4,1,5,3,1], k = 3
     * Output: 12
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^5
     * 1 <= k <= nums.length
     * @param nums
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(n)
    public long largestEvenSum(int[] nums, int k) {
        Arrays.sort(nums); // O(nlogn)

        List<Integer> oddList = new ArrayList<>();
        List<Integer> evenList = new ArrayList<>();

        for (int x : nums) { // O(n)
            if (x % 2 == 0) evenList.add(x);
            else oddList.add(x);
        }

        int m = evenList.size(), n = oddList.size();
        int i = m - 1, j = n - 1;
        long sum = 0;
        while (i >= 0 || j >= 0) { // O(k)
            if (i >= 0 && j >= 0) {
                if (evenList.get(i) > oddList.get(j)) {
                    sum += evenList.get(i);
                    i--;
                } else {
                    sum += oddList.get(j);
                    j--;
                }
            } else if (i >= 0) {
                sum += evenList.get(i);
                i--;
            } else if (j >= 0) {
                sum += oddList.get(j);
                j--;
            }
            k--;
            if (k == 0) break;
        }

        if (sum % 2 == 0) return sum;
        long res = Integer.MIN_VALUE;
        // odd -> odd + even
        // plan A: remove the last odd and replace with the largest even from the rest
        if (i >= 0 && j + 1 < n) res = sum - oddList.get(j + 1) + evenList.get(i);
        // plan B: remove the last even and replace with the largest odd from the rest
        if (j >= 0 && i + 1 < m) res = Math.max(res, sum - evenList.get(i + 1) + oddList.get(j));
        return res == Integer.MIN_VALUE ? -1 : res;
    }
}
