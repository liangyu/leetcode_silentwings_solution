package LC1801_2100;
import java.util.*;
public class LC1894_FindtheStudentthatWillReplacetheChalk {
    /**
     * There are n students in a class numbered from 0 to n - 1. The teacher will give each student a problem starting
     * with the student number 0, then the student number 1, and so on until the teacher reaches the student number
     * n - 1. After that, the teacher will restart the process, starting with the student number 0 again.
     *
     * You are given a 0-indexed integer array chalk and an integer k. There are initially k pieces of chalk. When the
     * student number i is given a problem to solve, they will use chalk[i] pieces of chalk to solve that problem.
     * However, if the current number of chalk pieces is strictly less than chalk[i], then the student number i will
     * be asked to replace the chalk.
     *
     * Return the index of the student that will replace the chalk.
     *
     * Input: chalk = [5,1,5], k = 22
     * Output: 0
     *
     * Constraints:
     *
     * chalk.length == n
     * 1 <= n <= 10^5
     * 1 <= chalk[i] <= 10^5
     * 1 <= k <= 10^9
     * @param chalk
     * @param k
     * @return
     */
    // time = O(n), space = O(1)
    public int chalkReplacer(int[] chalk, int k) {
        // corner case
        if (chalk == null || chalk.length == 0 || k < 0) return 0;

        long sum = 0; // 注意：n -> 10^5, chalk[i] -> 10^5 => sum -> 10^10 => long!!!
        for (int c : chalk) sum += c;
        k %= sum;

        for (int i = 0; i < chalk.length; i++) {
            if (k < chalk[i]) return i;
            k -= chalk[i];
        }
        return -1;
    }
}
