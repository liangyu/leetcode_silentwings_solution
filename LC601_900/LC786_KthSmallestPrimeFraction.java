package LC601_900;

public class LC786_KthSmallestPrimeFraction {
    /**
     * You are given a sorted integer array arr containing 1 and prime numbers, where all the integers of arr are unique.
     * You are also given an integer k.
     *
     * For every i and j where 0 <= i < j < arr.length, we consider the fraction arr[i] / arr[j].
     *
     * Return the kth smallest fraction considered. Return your answer as an array of integers of size 2,
     * where answer[0] == arr[i] and answer[1] == arr[j].
     *
     * Input: arr = [1,2,3,5], k = 3
     * Output: [2,5]
     *
     * Constraints:
     *
     * 2 <= arr.length <= 1000
     * 1 <= arr[i] <= 3 * 10^4
     * arr[0] == 1
     * arr[i] is a prime number for i > 0.
     * All the numbers of arr are unique and sorted in strictly increasing order.
     * 1 <= k <= arr.length * (arr.length - 1) / 2
     * @param arr
     * @param k
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        // corner case
        if (arr == null || arr.length == 0 || k < 0) return new int[0];

        double left = 0, right = 1, mid = 0;
        while (left < right) {
            mid = left + (right - left) / 2;
            int count = 0;
            for (int i = 0; i < arr.length; i++) {
                int idx = lowerBound(arr, arr[i] * 1.0 / mid);
                count += arr.length - idx;
            }

            if (count < k) left = mid;
            else if (count > k) right = mid;
            else break;
        }

        int[] res = new int[2];
        double ans = 0;
        for (int i = 0; i < arr.length; i++) {
            int idx = lowerBound(arr, arr[i] * 1.0 / mid);
            if (idx != arr.length && arr[i] * 1.0 / arr[idx] > ans) { // arr[i]/arr[j] <= mid => 取arr[i]/arr[j]最大的那个！
                ans = arr[i] * 1.0 / arr[idx];
                res[0] = arr[i];
                res[1] = arr[idx];
            }
        }
        return res;
    }

    private int lowerBound(int[] nums, double target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) left = mid + 1;
            else right = mid;
        }
        return nums[left] >= target ? left : left + 1;
    }
}
/**
 * kth smallest -> search by value
 * 用bs来猜这个数，然后来判断是猜大还是猜小，试错！逆向思维非常典型的做法！
 * nums[i] / nums[j] <= mid =>
 * nums[j] >= nums[i] / mid
 * 找出来的mid未必在这个arr里
 * 3/5 = 0.6 => mid = 0.625
 * 如果是double,永远是开区间，得通过手动设置条件跳出loop
 * 分母越大，整个fraction就越小，所以找的j是后半段 => n - idx
 */
