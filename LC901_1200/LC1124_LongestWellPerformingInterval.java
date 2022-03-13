package LC901_1200;
import java.util.*;
public class LC1124_LongestWellPerformingInterval {
    /**
     * We are given hours, a list of the number of hours worked per day for a given employee.
     *
     * A day is considered to be a tiring day if and only if the number of hours worked is (strictly) greater than 8.
     *
     * A well-performing interval is an interval of days for which the number of tiring days is strictly larger than the
     * number of non-tiring days.
     *
     * Return the length of the longest well-performing interval.
     *
     * Input: hours = [9,9,6,0,6,6,9]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= hours.length <= 10^4
     * 0 <= hours[i] <= 16
     * @param hours
     * @return
     */
    // time = O(n), space = O(n)
    public int longestWPI(int[] hours) {
        int n = hours.length, sum = 0;
        int[] presum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            if (hours[i] > 8) sum++;
            else sum--;
            presum[i + 1] = sum;
        }

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i <= n; i++) {
            if (stack.isEmpty() || presum[stack.peek()] > presum[i]) stack.push(i);
        }

        // 从后往前扫一遍presum
        int res = 0;
        for (int j = n; j >= 0; j--) {
            while (!stack.isEmpty() && presum[stack.peek()] < presum[j]) {
                res = Math.max(res, j - stack.pop());
            }
        }
        return res;
    }
}
/**
 * 1 1 0 0 0 0 1
 * 1 2 2 2 2 2 3  presum[i]  0~i num of tiring days in total
 * [i,j] presum[j] - presum[i-1]   i~j num of tiring days in total
 *  s.t. presum[j] - presum[i-1] > (j-i+1)-(presum[j]-presum[i-1])
 *  => 2 * (presum[j]-presum[i-1]) > j - i + 1
 *  tiring day: 1, non-tiring day: -1
 *  0 1 1 -1 -1 -1 -1 1
 *  0 1 2 1  0  -1 -2 -1   presum[i]  0~i: diff of num of tiring days vs non-tiring day
 * [i,j] s.t. presum[j] > presum[i-1]  找一个最长的i,j区间
 * => LC962 一模一样
 * 固定一个j，在前面找一个i，i要尽可能靠前 => O(n) 单调栈
 * if 单调增   1 2 3 4 5 x => 只会尽量找第一个元素，并没有太大意义
 * if 单调减   6 5 4 3 2 1 4   1和4比起来，既数值小又位置靠前，肯定选1，不会选4，4永远不会被考虑 => 4不用入栈
 * 只有满足从头开始依次单调减的元素才入栈，任何大于栈顶元素的都不用入栈，因为永远不会作为i的候选
 * 0 [1 2 1 0] -1 -2 [-1]
 * 从后往前看
 */