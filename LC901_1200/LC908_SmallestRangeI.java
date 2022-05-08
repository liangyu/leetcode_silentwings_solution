package LC901_1200;
import java.util.*;
public class LC908_SmallestRangeI {
    /**
     * You are given an integer array nums and an integer k.
     *
     * In one operation, you can choose any index i where 0 <= i < nums.length and change nums[i] to nums[i] + x where
     * x is an integer from the range [-k, k]. You can apply this operation at most once for each index i.
     *
     * The score of nums is the difference between the maximum and minimum elements in nums.
     *
     * Return the minimum score of nums after applying the mentioned operation at most once for each index in it.
     *
     * Input: nums = [1], k = 0
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^4
     * 0 <= nums[i] <= 10^4
     * 0 <= k <= 104
     * @param A
     * @param K
     * @return
     */
    // time = O(n),s pace = O(1)
    public int smallestRangeI(int[] A, int K) {
        int min = A[0], max = A[0];
        for (int x : A) {
            min = Math.min(min, x);
            max = Math.max(max, x);
        }
        return Math.max(0, max - min - 2 * K);
    }
}
