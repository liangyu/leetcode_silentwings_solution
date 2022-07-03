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
    // time = O(logn * logn), space = O(1)
    public int findKthPositive2(int[] arr, int k) {
        // corner case
        if (arr == null || arr.length == 0 || k < 0) return 0;

        int n = arr.length;
        int left = 1, right = arr[n - 1] + k;
        while (left < right) {
            int mid = right - (right - left) / 2;
            int M = mid - 1;
            int T = lowerBound(arr, mid) + 1;
            if (M - T <= k - 1) left = mid;
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
/**
 * 二分搜索。和1060.Missing-Element-in-Sorted-Array一样的思路。
 * 假设我们猜测mid是否是答案。
 * 考察[1,mid-1]这段连续自然数区间，可知道这段区间的自然数有M个，并且有T个存在于数组中(即查找数组里有多少个小于mid的元素个数，利用lower_bound)
 * 所以，在[1,mid-1]这段连续自然数区间内有missing number = M-T个。
 * 理论上我们希望这段区间应该有missing number共k-1个，于是就可以帮助判定mid是否偏大和偏小。
 * if (missing <= k-1)
 *      left = mid;
 * else
 *      right = mid-1;
 * 特别注意，当missing==k-1的时候，mid可能并不是最终答案，因为mid可能也存在于数组中，所以mid可以再往大猜(即left=mid)。
 * 因此这个分支在上面的代码里与missing<k-1合并。
 */
