package LC901_1200;

public class LC982_TripleswithBitwiseANDEqualToZero {
    /**
     * Given an integer array nums, return the number of AND triples.
     *
     * An AND triple is a triple of indices (i, j, k) such that:
     *
     * 0 <= i < nums.length
     * 0 <= j < nums.length
     * 0 <= k < nums.length
     * nums[i] & nums[j] & nums[k] == 0, where & represents the bitwise-AND operator.
     *
     * Input: nums = [2,1,3]
     * Output: 12
     * Explanation: We could choose the following i, j, k triples:
     * (i=0, j=0, k=1) : 2 & 2 & 1
     * (i=0, j=1, k=0) : 2 & 1 & 2
     * (i=0, j=1, k=1) : 2 & 1 & 1
     * (i=0, j=1, k=2) : 2 & 1 & 3
     * (i=0, j=2, k=1) : 2 & 3 & 1
     * (i=1, j=0, k=0) : 1 & 2 & 2
     * (i=1, j=0, k=1) : 1 & 2 & 1
     * (i=1, j=0, k=2) : 1 & 2 & 3
     * (i=1, j=1, k=0) : 1 & 1 & 2
     * (i=1, j=2, k=0) : 1 & 3 & 2
     * (i=2, j=0, k=1) : 3 & 2 & 1
     * (i=2, j=1, k=0) : 3 & 1 & 2
     *
     * Input: nums = [0,0,0]
     * Output: 27
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] < 2^16
     * @param nums
     * @return
     */
    // time = O(n * k), space = O(k)  k: max value in the array
    public int countTriplets(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int max = 0;
        for (int x : nums) max = Math.max(max, x);

        int[] count = new int[max + 1];
        for (int a : nums) {
            for (int b : nums) {
                count[a & b]++;
            }
        }

        int res = 0;
        for (int x : nums) {
            for (int i = 0; i <= max; i++) {
                if ((x & i) == 0) res += count[i];
                // i -> i + (x & i); 把i里原先与x相同为1的位置都变成0,而i里原来为0的位置变成1不影响结果，因为x对应的位置是0
                else i += (x & i) - 1;
            }
        }
        return res;
    }
}
