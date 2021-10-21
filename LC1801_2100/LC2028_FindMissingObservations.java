package LC1801_2100;
import java.util.*;
public class LC2028_FindMissingObservations {
    /**
     * You have observations of n + m 6-sided dice rolls with each face numbered from 1 to 6. n of the observations went
     * missing, and you only have the observations of m rolls. Fortunately, you have also calculated the average value
     * of the n + m rolls.
     *
     * You are given an integer array rolls of length m where rolls[i] is the value of the ith observation. You are
     * also given the two integers mean and n.
     *
     * Return an array of length n containing the missing observations such that the average value of the n + m rolls
     * is exactly mean. If there are multiple valid answers, return any of them. If no such array exists, return an
     * empty array.
     *
     * The average value of a set of k numbers is the sum of the numbers divided by k.
     *
     * Note that mean is an integer, so the sum of the n + m rolls should be divisible by n + m.
     *
     * Input: rolls = [3,2,4,3], mean = 4, n = 2
     * Output: [6,6]
     *
     * Constraints:
     *
     * m == rolls.length
     * 1 <= n, m <= 10^5
     * 1 <= rolls[i], mean <= 6
     * @param rolls
     * @param mean
     * @param n
     * @return
     */
    // time = O(m + n), space = O(1)
    public int[] missingRolls(int[] rolls, int mean, int n) {
        // corner case
        if (rolls == null || rolls.length == 0) return new int[0];

        int m = rolls.length, sum = 0;
        for (int x : rolls) sum += x;
        int diff = mean * (m + n) - sum;
        int avg = diff / n, remain = diff % n;
        if (avg < 1 || avg > 6 || avg == 6 && remain > 0) return new int[0];

        int[] res = new int[n];
        Arrays.fill(res, avg);
        int idx = 0;
        while (remain > 0) {
            res[idx++]++;
            remain--;
        }
        return res;
    }
}
