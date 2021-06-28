package LC301_600;

public class LC360_SortTransformedArray {
    /**
     * Given a sorted integer array nums and three integers a, b and c, apply a quadratic function of the form
     * f(x) = ax2 + bx + c to each element nums[i] in the array, and return the array in a sorted order.
     *
     * Input: nums = [-4,-2,2,4], a = 1, b = 3, c = 5
     * Output: [3,9,15,33]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 200
     * -100 <= nums[i], a, b, c <= 100
     * nums is sorted in ascending order.
     *
     *
     * Follow up: Could you solve it in O(n) time?
     * @param nums
     * @param a
     * @param b
     * @param c
     * @return
     */
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        // corner case
        if (nums == null || nums.length == 0) return new int[0];

        int n = nums.length;
        int[] res = new int[n];

        if (a == 0) {
            for (int i = 0; i < n; i++) res[i] = getValue(nums[i], a, b, c);
            if (b < 0) reverse(res);
        } else {
            double mid = -b / (2.0 * a);
            int left = 0, right = nums.length - 1, idx = 0;
            while (left <= right) {
                if (Math.abs(mid - nums[left]) > Math.abs(mid - nums[right])) {
                    res[idx++] = getValue(left, a, b, c);
                    left++;
                } else {
                    res[idx++] = getValue(right, a, b, c);
                    right--;
                }
            }
            if (a > 0) reverse(res);
        }
        return res;
    }

    private int getValue(int x, int a, int b, int c) {
        return a * x * x + b * x + c;
    }

    private void reverse(int[] res) {
        int i = 0, j = 0;
        while (i < j) {
            int temp = res[i];
            res[i++] = res[j];
            res[j--] = temp;
        }
    }
}
