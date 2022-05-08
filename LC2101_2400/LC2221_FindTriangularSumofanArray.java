package LC2101_2400;

public class LC2221_FindTriangularSumofanArray {
    /**
     * You are given a 0-indexed integer array nums, where nums[i] is a digit between 0 and 9 (inclusive).
     *
     * The triangular sum of nums is the value of the only element present in nums after the following process terminates:
     *
     * Let nums comprise of n elements. If n == 1, end the process. Otherwise, create a new 0-indexed integer array
     * newNums of length n - 1.
     * For each index i, where 0 <= i < n - 1, assign the value of newNums[i] as (nums[i] + nums[i+1]) % 10,
     * where % denotes modulo operator.
     * Replace the array nums with newNums.
     * Repeat the entire process starting from step 1.
     * Return the triangular sum of nums.
     *
     * Input: nums = [1,2,3,4,5]
     * Output: 8
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] <= 9
     * @param nums
     * @return
     */
    // S1
    // time = O(n^2), space = O(1)
    public int triangularSum(int[] nums) {
        for (int n = nums.length - 1; n > 0; n--) {
            for (int i = 0; i < n; i++) {
                nums[i] = (nums[i] + nums[i + 1]) % 10;
            }
        }
        return nums[0];
    }

    // S2: Math - Combinatorics
    // time = O(n^2), space = O(1)
    public int triangularSum2(int[] nums) {
        int n = nums.length - 1;
        long[][] comb = new long[1000][1000];
        for (int i = 0; i <= n; i++) {
            comb[i][i] = comb[i][0] = 1;
            if (i == 0) continue;
            for (int j = 1; j < i; j++) {
                comb[i][j] = comb[i - 1][j - 1] + comb[i - 1][j];
                comb[i][j] %= 10;
            }
        }
        long res = 0;
        for (int i = 0; i <= n; i++) {
            res += nums[i] * comb[n][i] % 10;
        }
        return (int) res % 10;
    }
}
/**
 * 杨辉三角形
 * 多次二项式的系数
 * 1 4 6 4 1
 * (a+b)^4 = a^4+4ab^3+6ab^2+4ab^3+b^4  -> 1 4 6 4 1
 * 1 5 10 10 5 1  -> (a+b)^5
 * 组合数，二项式的系数
 * 1 = C(4,0), 4 = C(4,1), 6 = C(4,2), 4 = C(4,3), 1 = C(4,4)
 * n次二项式系数不需要画出来 => (a+b)^n，总共n + 1项， i-th coefficient = C(n,i)
 * 本题是反过来，越加越小
 * 本质上是加权平均
 */