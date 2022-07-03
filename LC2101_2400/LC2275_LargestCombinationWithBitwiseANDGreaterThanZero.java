package LC2101_2400;

public class LC2275_LargestCombinationWithBitwiseANDGreaterThanZero {
    /**
     * The bitwise AND of an array nums is the bitwise AND of all integers in nums.
     *
     * For example, for nums = [1, 5, 3], the bitwise AND is equal to 1 & 5 & 3 = 1.
     * Also, for nums = [7], the bitwise AND is 7.
     * You are given an array of positive integers candidates. Evaluate the bitwise AND of every combination of numbers
     * of candidates. Each number in candidates may only be used once in each combination.
     *
     * Return the size of the largest combination of candidates with a bitwise AND greater than 0.
     *
     * Input: candidates = [16,17,71,62,12,24,14]
     * Output: 4
     *
     * Input: candidates = [8,8]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= candidates.length <= 10^5
     * 1 <= candidates[i] <= 10^7
     * @param candidates
     * @return
     */
    // time = O(n), space = O(1)
    public int largestCombination(int[] candidates) {
        int n = candidates.length, res = 0;
        for (int i = 0; i < 24; i++) {
            int count = 0;
            for (int x : candidates) {
                if (((x >> i) & 1) == 1) count++;
            }
            res = Math.max(res, count);
        }
        return res;
    }
}
/**
 * 逐个bit位来考虑
 * 将若干个数Bitwise AND之后的结果S如果不为零，说明S至少有一个bit位不为零，
 * 也就是说所有的数在该bit位上不能有0存在。于是我们可以检查每个bit，统计有多少元素在该bit位上非零。
 * 假设有M个元素在某个二进制位上都是1，那么他们的AND结果必然就不是零。
 */