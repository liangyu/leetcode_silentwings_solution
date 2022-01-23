package LC2100_2400;

public class LC2145_CounttheHiddenSequences {
    /**
     * You are given a 0-indexed array of n integers differences, which describes the differences between each pair of
     * consecutive integers of a hidden sequence of length (n + 1). More formally, call the hidden sequence hidden, then
     * we have that differences[i] = hidden[i + 1] - hidden[i].
     *
     * You are further given two integers lower and upper that describe the inclusive range of values [lower, upper]
     * that the hidden sequence can contain.
     *
     * For example, given differences = [1, -3, 4], lower = 1, upper = 6, the hidden sequence is a sequence of length 4
     * whose elements are in between 1 and 6 (inclusive).
     * [3, 4, 1, 5] and [4, 5, 2, 6] are possible hidden sequences.
     * [5, 6, 3, 7] is not possible since it contains an element greater than 6.
     * [1, 2, 3, 4] is not possible since the differences are not correct.
     * Return the number of possible hidden sequences there are. If there are no possible sequences, return 0.
     *
     * Input: differences = [1,-3,4], lower = 1, upper = 6
     * Output: 2
     *
     * Constraints:
     *
     * n == differences.length
     * 1 <= n <= 10^5
     * -10^5 <= differences[i] <= 10^5
     * -10^5 <= lower <= upper <= 10^5
     * @param differences
     * @param lower
     * @param upper
     * @return
     */
    // time = O(n), space = O(1)
    public int numberOfArrays(int[] differences, int lower, int upper) {
        int n = differences.length;
        long sum = 0, min = 0, max = 0; // 假设起始位置为0,hidden[0] = 0,从而min = max = hidden[0]
        for (int x : differences) {
            sum += x;
            min = Math.min(min, sum);
            max = Math.max(max, sum);
        }
        return (int) Math.max(0, (upper - lower) - (max - min) + 1); // 平移window，看[min:max]在[lower,upper]里能移动多少次!
    }
}
/**
 * Assume we start with a = 0,
 * continuously calculate the next value by difference.
 * We only need to record the current value a, the max and the min value in this sequence.
 * Now we need to put the sequence with range [min, max] into a range of [lower, upper].
 * If upper - lower < max - min, no possible hidden sequences.
 * If upper - lower == max - min, we have only 1 possible hidden sequences.
 * If upper - lower == max - min + 1, we have 2 possible hidden sequences.
 * If upper - lower == max - min + k, we have k + 1 possible hidden sequences.
 */
