package LC1501_1800;
import java.util.*;
public class LC1539_KthMissingPositiveNumber {
    /**
     * Given an array arr of positive integers sorted in a strictly increasing order, and an integer k.
     *
     * Find the kth positive integer that is missing from this array.
     *
     * Input: arr = [2,3,4,7,11], k = 5
     * Output: 9
     *
     * Constraints:
     *
     * 1 <= arr.length <= 1000
     * 1 <= arr[i] <= 1000
     * 1 <= k <= 1000
     * arr[i] < arr[j] for 1 <= i < j <= arr.length
     * @param arr
     * @param k
     * @return
     */
    // S1: HashSet
    // time = O(n), space = O(n)
    public int findKthPositive(int[] arr, int k) {
        // corner case
        if (arr == null || arr.length == 0 || k < 0) return 0;

        HashSet<Integer> set = new HashSet<>();
        for (int num : arr) set.add(num);

        int i = 1;
        Integer res = null;
        while (k > 0) {
            if (!set.contains(i)) k--;
            i++;
        }
        return i - 1;
    }

    // S2: BS
    // time = O(logn), space = O(1)
    public int findKthPositive2(int[] arr, int k) {
        // corner case
        if (arr == null || arr.length == 0 || k < 0) return 0;

        int n = arr.length;
        int left = 1, right = arr[n - 1] + k;
        while (left < right) {
            int mid = right - (right - left) / 2;
            int M = mid - 1;
            int T = lowerBound(arr, mid) + 1;
            if (M - T < k) left = mid;
            else right = mid - 1;
        }
        return left;
    }

    private int lowerBound(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (nums[mid] < target) left = mid;
            else right = mid - 1;
        }
        return nums[left] < target ? left : left - 1;
    }
}
