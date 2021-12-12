package LC1501_1800;

public class LC1524_NumberofSubarraysWithOddSum {
    /**
     * Given an array of integers arr, return the number of subarrays with an odd sum.
     *
     * Since the answer can be very large, return it modulo 10^9 + 7.
     *
     * Input: arr = [1,3,5]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^5
     * 1 <= arr[i] <= 100
     * @param arr
     * @return
     */
    // time = O(1), space = O(1)
    public int numOfSubarrays(int[] arr) {
        int presum = 0;
        long odd = 0, even = 1, res = 0; // 注意，刚开始sum = 0, even = 1!
        long M = (long)(1e9 + 7);

        for (int x : arr) {
            presum += x;
            if (presum % 2 == 0) res = (res + odd) % M;
            else res = (res + even) % M;
            if (presum % 2 == 0) even++;
            else odd++;
        }
        return (int) res;
    }
}
