package LC901_1200;

public class LC978_LongestTurbulentSubarray {
    /**
     * Given an integer array arr, return the length of a maximum size turbulent subarray of arr.
     *
     * A subarray is turbulent if the comparison sign flips between each adjacent pair of elements in the subarray.
     *
     * More formally, a subarray [arr[i], arr[i + 1], ..., arr[j]] of arr is said to be turbulent if and only if:
     *
     * For i <= k < j:
     * arr[k] > arr[k + 1] when k is odd, and
     * arr[k] < arr[k + 1] when k is even.
     * Or, for i <= k < j:
     * arr[k] > arr[k + 1] when k is even, and
     * arr[k] < arr[k + 1] when k is odd.
     *
     * Input: arr = [9,4,2,10,7,8,8,1,9]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= arr.length <= 4 * 10^4
     * 0 <= arr[i] <= 10^9
     * @param arr
     * @return
     */
    // time = O(n), space = O(n)
    public int maxTurbulenceSize(int[] arr) {
        // corner case
        if (arr == null || arr.length == 0) return 0;

        int n = arr.length;
        int[] q = new int[n];
        for (int i = 0; i < n - 1; i++) {
            if (arr[i] > arr[i + 1]) q[i] = -1;
            else if (arr[i] < arr[i + 1]) q[i] = 1;
        }

        if (n == 1) return 1;
        if (n == 2) return q[0] == 0 ? 1 : 2;

        int count = 1, start = 0;
        for (int i = 0; i + 1 < n - 1; i++) {
            if (q[i] != 0) {
                count = Math.max(count, 2);
            }
            start = i;
            while (i + 1 < n - 1 && q[i] * q[i + 1] == -1) {
                i++;
                count = Math.max(count, i - start + 2);
            }
        }
        return count;
    }
}
