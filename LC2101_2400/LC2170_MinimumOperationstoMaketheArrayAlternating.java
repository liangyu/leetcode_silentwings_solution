package LC2101_2400;
import java.util.*;
public class LC2170_MinimumOperationstoMaketheArrayAlternating {
    /**
     * You are given a 0-indexed array nums consisting of n positive integers.
     *
     * The array nums is called alternating if:
     *
     * nums[i - 2] == nums[i], where 2 <= i <= n - 1.
     * nums[i - 1] != nums[i], where 1 <= i <= n - 1.
     * In one operation, you can choose an index i and change nums[i] into any positive integer.
     *
     * Return the minimum number of operations required to make the array alternating.
     *
     * Input: nums = [3,1,3,2,4,3]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int minimumOperations(int[] nums) {
        int n = nums.length;
        if (n == 1) return 0;

        HashMap<Integer, Integer> odd = new HashMap<>();
        HashMap<Integer, Integer> even = new HashMap<>();

        int oddMax = 0, oddSecMax = 0, oddMaxId = -1, oddSecId = -1;
        int evenMax = 0, evenSecMax = 0, evenMaxId = -1, evenSecId = -1;

        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) even.put(nums[i], even.getOrDefault(nums[i], 0) + 1);
            else odd.put(nums[i], odd.getOrDefault(nums[i], 0) + 1);
        }

        for (int key : odd.keySet()) {
            if (odd.get(key) > oddMax) {
                oddSecMax = oddMax;
                oddSecId = oddMaxId;
                oddMax = odd.get(key);
                oddMaxId = key;
            } else {
                oddSecMax = odd.get(key);
                oddSecId = key;
            }
        }

        for (int key : even.keySet()) {
            if (even.get(key) > evenMax) {
                evenSecMax = evenMax;
                evenSecId = evenMaxId;
                evenMax = even.get(key);
                evenMaxId = key;
            } else {
                evenSecMax = even.get(key);
                evenSecId = key;
            }
        }

        int res = 0;
        if (oddMaxId == evenMaxId) res = n - Math.max(oddMax + evenSecMax, oddSecMax + evenMax);
        else res = n - (oddMax + evenMax);
        return res;
    }
}
