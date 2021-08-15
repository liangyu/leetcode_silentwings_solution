package LC1801_2100;

public class LC1953_MaximumNumberofWeeksforWhichYouCanWork {
    /**
     * There are n projects numbered from 0 to n - 1. You are given an integer array milestones where each milestones[i]
     * denotes the number of milestones the ith project has.
     *
     * You can work on the projects following these two rules:
     *
     * Every week, you will finish exactly one milestone of one project. You must work every week.
     * You cannot work on two milestones from the same project for two consecutive weeks.
     * Once all the milestones of all the projects are finished, or if the only milestones that you can work on will
     * cause you to violate the above rules, you will stop working. Note that you may not be able to finish every
     * project's milestones due to these constraints.
     *
     * Return the maximum number of weeks you would be able to work on the projects without violating the rules
     * mentioned above.
     *
     * Input: milestones = [1,2,3]
     * Output: 6
     *
     * Constraints:
     *
     * n == milestones.length
     * 1 <= n <= 10^5
     * 1 <= milestones[i] <= 10^9
     * @param milestones
     * @return
     */
    // time = O(n), space = O(1)
    public long numberOfWeeks(int[] milestones) {
        // corner case
        if (milestones == null || milestones.length == 0) return 0;

        long sum = 0, max = 0;
        for (int m : milestones) {
            sum += m;
            max = Math.max(max, m);
        }
        if (max <= sum / 2) return sum;
        return (sum - max) * 2 + 1;
    }
}
/**
 * 尽量让不同种类的task间隔排列
 * 某一种任务特别多
 * xxxxxxxxxxxooooooo
 * xx
 * 非x任务尽量往里面插
 * 就看milestones最多的任务有几个，有没有占到全体的半数之上
 * 保证没有任何一个是超过半数以上的
 * 反之，x特别多，必然会有xx在一起
 */
