package LC1201_1500;
import java.util.*;
public class LC1300_SumofMutatedArrayClosesttoTarget {
    /**
     * Given an integer array arr and a target value target, return the integer value such that when we change all
     * the integers larger than value in the given array to be equal to value, the sum of the array gets as close as
     * possible (in absolute difference) to target.
     *
     * In case of a tie, return the minimum such integer.
     *
     * Notice that the answer is not neccesarilly a number from arr.
     *
     * Input: arr = [4,9,3], target = 10
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^4
     * 1 <= arr[i], target <= 10^5
     * @param arr
     * @param target
     * @return
     */
    // S1: BS
    // time = O(n), space = O(1)
    public int findBestValue(int[] arr, int target) {
        // corner case
        if (arr == null || arr.length == 0) return 0;

        int n = arr.length, sum = 0, max = 0;
        for (int x : arr) { // O(n)
            sum += x;
            max = Math.max(max, x);
        }
        if (sum <= target) return max; // 数字元素之和都比target小，那么阈值就只能取数组里的最大值，原来数组和就是最接近target的值

        int left = 0, right = (int) 1e5;
        while (left < right) { // O(log(1e5))
            int mid = left + (right - left) / 2;
            if (helper(arr, mid) < target) left = mid + 1;
            else right = mid;
        }

        int sum1 = helper(arr, left); // O(n)
        int sum2 = helper(arr, left - 1);
        if (Math.abs(sum1 - target) < Math.abs(sum2 - target)) return left;
        return left - 1;
    }

    private int helper(int[] arr, int target) { // O(n)
        int sum = 0;
        for (int x : arr) {
            if (x > target) sum += target;
            else sum += x;
        }
        return sum;
    }

    // S2: BS + presum
    // time = O(nlogn), space = O(n)
    public int findBestValue2(int[] arr, int target) {
        // corner case
        if (arr == null || arr.length == 0) return 0;

        int n = arr.length;
        Arrays.sort(arr); // O(nlogn)

        int[] presum = new int[n];
        presum[0] = arr[0];
        for (int i = 1; i < n; i++) presum[i] = presum[i - 1] + arr[i]; // O(n)
        if (presum[n - 1] <= target) return arr[n - 1];

        int left = 0, right = (int) 1e5;
        while (left < right) { // mid: the smallest threshold that makes sum > target
            int mid = left + (right - left) / 2;
            if (checkSum(arr, presum, mid) >= target) right = mid; // O(logn)
            else left = mid + 1; // mid不满足条件
        }
        int sum1 = checkSum(arr, presum, left); // O(logn)
        int sum2 = checkSum(arr, presum, left - 1);
        if (Math.abs(sum1 - target) < Math.abs(sum2 - target)) return left;
        return left - 1;
    }

    private int checkSum(int[] arr, int[] presum, int threshold) {
        int idx = upperBound(arr, threshold); // O(logn)
        int num = arr.length - idx;
        if (idx - 1 >= 0) return presum[idx - 1] + num * threshold;
        else return num * threshold; // 第一个元素 > threshold => 所有元素都比threshold大
    }

    private int upperBound(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] <= target) left = mid + 1;
            else right = mid;
        }
        return arr[left] > target ? left : left + 1;
    }
}
/**
 * 猜val
 * 排过序之后，求新sum，找个分界点，之后的所有元素都是threshold，前面的只要看presum
 */
