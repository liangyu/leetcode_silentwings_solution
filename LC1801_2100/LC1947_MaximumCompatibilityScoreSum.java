package LC1801_2100;
import java.util.*;
public class LC1947_MaximumCompatibilityScoreSum {
    /**
     * There is a survey that consists of n questions where each question's answer is either 0 (no) or 1 (yes).
     *
     * The survey was given to m students numbered from 0 to m - 1 and m mentors numbered from 0 to m - 1. The answers
     * of the students are represented by a 2D integer array students where students[i] is an integer array that
     * contains the answers of the ith student (0-indexed). The answers of the mentors are represented by a 2D integer
     * array mentors where mentors[j] is an integer array that contains the answers of the jth mentor (0-indexed).
     *
     * Each student will be assigned to one mentor, and each mentor will have one student assigned to them. The
     * compatibility score of a student-mentor pair is the number of answers that are the same for both the student
     * and the mentor.
     *
     * For example, if the student's answers were [1, 0, 1] and the mentor's answers were [0, 0, 1], then their
     * compatibility score is 2 because only the second and the third answers are the same.
     * You are tasked with finding the optimal student-mentor pairings to maximize the sum of the compatibility scores.
     *
     * Given students and mentors, return the maximum compatibility score sum that can be achieved.
     *
     * Input: students = [[1,1,0],[1,0,1],[0,0,1]], mentors = [[1,0,0],[0,0,1],[1,1,0]]
     * Output: 8
     *
     * Constraints:
     *
     * m == students.length == mentors.length
     * n == students[i].length == mentors[j].length
     * 1 <= m, n <= 8
     * students[i][k] is either 0 or 1.
     * mentors[j][k] is either 0 or 1.
     * @param students
     * @param mentors
     * @return
     */
    // S1: DFS
    // time = O(n! * n^2), space = O(n)
    private int res = 0;
    public int maxCompatibilitySum(int[][] students, int[][] mentors) {
        HashSet<Integer> visited = new HashSet<>();
        for (int i = 0; i < mentors.length; i++) {
            visited.add(i);
            dfs(students, 0, mentors, i, match(students[0], mentors[i]), visited);
            visited.remove(i);
        }
        return res;
    }

    private void dfs(int[][] students, int s, int[][] mentors, int m, int sum, HashSet<Integer> visited) {
        // base case
        if (s == students.length - 1) {
            res = Math.max(res, sum);
            return;
        }

        for (int i = 0; i < mentors.length; i++) {
            if (!visited.contains(i)) {
                visited.add(i);
                dfs(students, s + 1, mentors, i, sum + match(students[s + 1], mentors[i]), visited);
                visited.remove(i);
            }
        }
    }

    private int match(int[] student, int[] mentor) { // O(n)
        int count = 0;
        for (int i = 0; i < student.length; i++) {
            if (student[i] == mentor[i]) count++;
        }
        return count;
    }

    // S2: DP + State Compression (Gospher's Hack)
    // time = O(m^2 * 2^m), space = O(m^2)
    public int maxCompatibilitySum2(int[][] students, int[][] mentors) {
        int m = students.length, n = students[0].length;
        int[][] match = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                int count = 0;
                for (int k = 0; k < n; k++) {
                    count += students[i][k] == mentors[j][k] ? 1 : 0;
                }
                match[i][j] = count;
            }
        }

        int[] dp = new int[1 << m];

        for (int j = 0; j < m; j++) {
            // 遍历只有一个bit的state => Gospher's hack
            int k = j + 1; // need j + 1 mentors
            int state = (1 << k) - 1;
            while (state < (1 << m)) {
                for (int i = 0; i < m; i++) {
                    if (((state >> i) & 1) == 1) {
                        dp[state] = Math.max(dp[state], dp[state - (1 << i)] + match[i][j]);
                    }
                }

                int c = state & -state;
                int r = state + c;
                state = (((r ^ state) >> 2) / c) | r;
            }
        }
        return dp[(1 << m) - 1];
    }

    // S3: Dijkstra
    // time = O(ElogE) = O(m * 2^m * log(m * 2^m)), space = O(2^m)
    public int maxCompatibilitySum3(int[][] students, int[][] mentors) {
        int m = students.length, n = students[0].length;
        int[][] unmatch = new int[m][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                int count = 0;
                for (int k = 0; k < n; k++) {
                    count += students[i][k] == mentors[j][k] ? 1 : 0;
                }
                unmatch[i][j] = n - count;
            }
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]); // {cost, state}
        pq.offer(new int[]{0, 0});
        int[] dp = new int[1 << m];
        Arrays.fill(dp, -1);

        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int cost = cur[0], state = cur[1];
            if (dp[state] != -1) continue;
            dp[state] = cost;
            if (state == (1 << m) - 1) return m * n - cost;

            int j = Integer.bitCount(state);

            for (int i = 0; i < m; i++) {
                if (((state >> i) & 1) == 1) continue;
                int newState = state | (1 << i);
                if (dp[newState] != -1) continue;
                pq.offer(new int[]{cost + unmatch[i][j], newState});
            }
        }
        return -1;
    }
}
/**
 * 本质是二分图匹配问题
 * 一个导师配一个学生
 * 找出一个分配方案，总分最大
 * 提前算出一个match score
 * 带权的二分图匹配模板题 match[i][j]
 * 无权二分图匹配 => 匈牙利算法 LC1820
 * 带权的二分图匹配 => KM算法
 * ref: LC1879
 * 状态压缩 => 1 <= m, n <= 8
 * 导师是严格按照递增顺序来的，导师可以跳着挑学生，但导师自己是按照顺序来的
 * students: 1 0 1 0 1 0 0 0
 * mentor:   A B C ...     H
 * 学生状态全变成1，表示全挑完了
 * 定义学生被导师挑的状态
 * 我们为什么要用状态压缩，而用纯暴力搜搜？=> 最暴力的dfs ->
 * A-1     3
 * B-3     2
 * C-2     1
 * D-4
 * ..
 * H-7
 * 为什么不这么做？
 * 不剪枝的话，就相当于找8个的全排列。=> 8! = 40320
 * 缺点：前面选的132，这时选321，后面一样的话，如果就已经比前面132得到的值小的话，后面不需要算了
 * 状态压缩最大的好处是，比如这里只看前3个，如果要给前3个导师配备3个学生是最优的话，其他排列不需要考虑了
 * dp[state] = 78
 *    10101000  => 隐去了这3个学生到底是如何与这3个导师去配对的，隐去了究竟哪个学生与哪个导师去配对，
 *    这个东西对其他学生被其他导师挑选是没有任何关系的
 * 这也是为什么状态压缩消除了很多重复的搜索
 * dp[state]: the maximum score we can obtain for the state
 * ans = dp[11111111]
 * 1 0 1 0 1 0 0 0
 * ^
 * dp[state] = max{dp[00101000] + match[0][2]
 *             dp[10001000] + match[2][2]
 *             dp[10100000] + match[4][2]}
 *    j 1s -> 导师是一个个来的，具体哪个导师配哪个学生，对后面没有帮助，不需要记下，因为我们只关心最大值
 * 遍历所有的state，从少一个1的state转化过来
 *
 * Dijkstra: 固定一个起点，有很多路径，求得到任意一点的最短路径
 * Dijkstra只能用在：
 * 1.边权非负
 * 2.求最短路径的算法
 * 乍看似乎无法用Dijkstra
 * 把state作为一个节点，如何定义相邻结点？
 * 根据当前状态找一个mentor，相邻状态是有1个学生被匹配 -> 1号mentor
 * 状态与状态之间只相差一个学生的匹配，那就是相邻结点
 * 最终到达状态就是全1，我们就是要找从起点到终点的最短路径
 * 如果是minimum可以直接用，但现在求最大？
 * 转化一下！=> 换成unmatch score => 求总的不兼容的score越小越好
 * 关系：m * n - min_imcompatibility_score
 * 优先队列里可以有相同的状态，但是一旦哪个第一个冒出来，说明它就是最优解，之后就不用访问这个状态了。
 */
