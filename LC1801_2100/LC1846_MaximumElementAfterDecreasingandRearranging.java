package LC1801_2100;
import java.util.*;
public class LC1846_MaximumElementAfterDecreasingandRearranging {
    /**
     * You are given an array of positive integers arr. Perform some operations (possibly none) on arr so that it
     * satisfies these conditions:
     *
     * The value of the first element in arr must be 1.
     * The absolute difference between any 2 adjacent elements must be less than or equal to 1. In other words,
     * abs(arr[i] - arr[i - 1]) <= 1 for each i where 1 <= i < arr.length (0-indexed). abs(x) is the absolute value of x.
     * There are 2 types of operations that you can perform any number of times:
     *
     * Decrease the value of any element of arr to a smaller positive integer.
     * Rearrange the elements of arr to be in any order.
     * Return the maximum possible value of an element in arr after performing the operations to satisfy the conditions.
     *
     * Input: arr = [2,2,1,2,1]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^5
     * 1 <= arr[i] <= 10^9
     * @param arr
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int maximumElementAfterDecrementingAndRearranging(int[] arr) {
        // corner case
        if (arr == null || arr.length == 0) return -1;

        Arrays.sort(arr); // O(nlogn)

        // arr[0] must be 1, so only look from arr[1]
        arr[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > arr[i - 1] + 1) arr[i] = arr[i - 1] + 1;
        }
        return arr[arr.length - 1];
    }
}
/**
 * x x x x x x
 * 1 2
 * if (arr[i] > arr[i - 1] + 1) arr[i] = arr[i - 1] + 1
 */