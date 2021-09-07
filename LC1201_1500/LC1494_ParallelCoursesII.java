package LC1201_1500;
import java.util.*;
public class LC1494_ParallelCoursesII {
    /**
     * You are given an integer n, which indicates that there are n courses labeled from 1 to n. You are also given an
     * array relations where relations[i] = [prevCoursei, nextCoursei], representing a prerequisite relationship
     * between course prevCoursei and course nextCoursei: course prevCoursei has to be taken before course nextCoursei.
     * Also, you are given the integer k.
     *
     * In one semester, you can take at most k courses as long as you have taken all the prerequisites in the previous
     * semester for the courses you are taking.
     *
     * Return the minimum number of semesters needed to take all courses. The testcases will be generated such that
     * it is possible to take every course.
     *
     * Input: n = 4, dependencies = [[2,1],[3,1],[1,4]], k = 2
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= n <= 15
     * 1 <= k <= n
     * 0 <= relations.length <= n * (n-1) / 2
     * relations[i].length == 2
     * 1 <= prevCoursei, nextCoursei <= n
     * prevCoursei != nextCoursei
     * All the pairs [prevCoursei, nextCoursei] are unique.
     * The given graph is a directed acyclic graph.
     * @param n
     * @param relations
     * @param k
     * @return
     */
    // time = O(n * 3^n), space = O(2^n)
    public int minNumberOfSemesters(int n, int[][] relations, int k) {
        int[] dp = new int[1 << n];
        Arrays.fill(dp, Integer.MAX_VALUE / 2);

        int[] prevCourse = new int[n];
        for (int[] x : relations) {
            prevCourse[x[1] - 1] += 1 << (x[0] - 1); // 1-index -> 0-index
        }

        int[] prereq = new int[1 << n];
        for (int state = 0; state < (1 << n); state++) {
            prereq[state] = 0;
            for (int i = 0; i < n; i++) {
                if (((state >> i) & 1) == 1) {
                    prereq[state] |= prevCourse[i];
                }
            }
        }

        dp[0] = 0;
        for (int state = 0; state < (1 << n); state++) {
            if (getOneBit(state) <= k && prereq[state] == 0) dp[state] = 1; // init
            for (int subset = state; subset > 0; subset = (subset - 1) & state) {
                if (getOneBit(state) - getOneBit(subset) <= k && (subset & prereq[state]) == prereq[state]) {
                    dp[state] = Math.min(dp[state], dp[subset] + 1);
                }
            }
        }
        return dp[(1 << n) - 1];
    }

    private int getOneBit(int state) {
        int count = 0;
        while (state > 0) {
            count++;
            state = state & (state - 1);
        }
        return count;
    }
}
/**
 * 拓扑排序里，在不同深度的课程是不能放在同一个阶段里学的，在这里规定就是semester
 * 在同一个学期里，不能既修先修课程，又把后续课程修完
 * 贪心法拓扑排序
 * 1 -> 2
 * 3 -> 4
 * 5 -> 6
 * 1,3,5入度为0，可以先修
 * 事实上如何选择k是不存在任何贪心策略
 * 1 <= n <= 15  提示你用状态压缩，是个巧妙的暴力解，把所有可能解都encode在一个整型的bit位上面
 * dp[state] 2^n = 2^15 = 32768 可以接受
 * dp[1111111]
 * dp[0000...001]
 * dp[0000...101]
 * 可以遍历所有状态来求它的dp值
 * for (int state = 0; state < (1 << n); state++)
 *      dp[state] = min(dp[state], dp[prevState] + 1)
 *
 * prevState -> state
 * 1. prevState is a subset of state
 * 2. countOne(state) - countOne(prevState) <= k
 * 3. prevState must contain prerequisites of state
 * subset > prereq[state]
 * 10011 | 10010 = 10010
 */
