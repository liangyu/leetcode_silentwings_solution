package LC2101_2400;
import java.util.*;
public class LC2140_SolvingQuestionsWithBrainpower {
    /**
     * You are given a 0-indexed 2D integer array questions where questions[i] = [pointsi, brainpoweri].
     *
     * The array describes the questions of an exam, where you have to process the questions in order (i.e., starting
     * from question 0) and make a decision whether to solve or skip each question. Solving question i will earn you
     * pointsi points but you will be unable to solve each of the next brainpoweri questions. If you skip question i,
     * you get to make the decision on the next question.
     *
     * For example, given questions = [[3, 2], [4, 3], [4, 4], [2, 5]]:
     * If question 0 is solved, you will earn 3 points but you will be unable to solve questions 1 and 2.
     * If instead, question 0 is skipped and question 1 is solved, you will earn 4 points but you will be unable to
     * solve questions 2 and 3.
     * Return the maximum points you can earn for the exam.
     *
     * Input: questions = [[3,2],[4,3],[4,4],[2,5]]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= questions.length <= 10^5
     * questions[i].length == 2
     * 1 <= pointsi, brainpoweri <= 10^5
     * @param questions
     * @return
     */
    // S1: 填表法
    // time = O(n), space = O(n)
    public long mostPoints(int[][] questions) {
        int n = questions.length;
        long[][] dp = new long[n][2];
        dp[0][0] = 0;
        dp[0][1] = questions[0][0];
        int[][] endTimes = new int[n][2];
        for (int i = 0; i < n; i++) endTimes[i] = new int[]{questions[i][1] + i, i};

        Arrays.sort(endTimes, (o1, o2) -> o1[0] - o2[0]);

        int j = 0;
        long rollingMax = 0;
        for (int i = 1; i < n; i++) {
            while (j < n && endTimes[j][0] < i) {
                rollingMax = Math.max(rollingMax, dp[endTimes[j][1]][1]);
                j++;
            }
            dp[i][0] = Math.max(dp[i - 1][0], dp[i - 1][1]);
            dp[i][1] = rollingMax + questions[i][0];
        }
        return Math.max(dp[n - 1][0], dp[n - 1][1]);
    }

    // S2: 刷表法
    // time = O(n), space = O(n)
    public long mostPoints2(int[][] questions) {
        int n = questions.length;
        long[][] dp = new long[n][2];
        dp[0][0] = 0;
        dp[0][1] = questions[0][0];

        long[] base = new long[n];
        long curBase = 0;
        for (int i = 0; i < n; i++) {
            curBase = Math.max(curBase, base[i]);
            dp[i][1] = Math.max(dp[i][1], curBase + questions[i][0]);
            if (i + 1 < n) dp[i + 1][0] = Math.max(dp[i + 1][0], Math.max(dp[i][0], dp[i][1]));
            int j = i + questions[i][1] + 1;
            if (j < n) base[j] = Math.max(base[j], dp[i][1]);
        }
        return Math.max(dp[n - 1][0], dp[n - 1][1]);
    }

    // S3: 从后往前做dp
    // time = O(n), space = O(n)
    public long mostPoints3(int[][] questions) {
        int n = questions.length;
        long[] dp = new long[n + 1];
        for (int i = n - 1; i >= 0; i--) {
            int j = i + questions[i][1] + 1;
            dp[i] = Math.max(dp[i + 1], (j < n ? dp[j] : 0) + questions[i][0]);
        }
        return dp[0];
    }

    // S4: HashMap
    // time = O(n), space = O(n)
    public long mostPoints4(int[][] questions) {
        int n = questions.length;
        HashMap<Integer, Long> map = new HashMap<>();

        long res = 0;
        for (int i = 0; i < n; i++) {
            int val = questions[i][0], next = questions[i][1];
            res = Math.max(res, map.getOrDefault(i, 0L) + val);
            if (i + next + 1 < n) {
                map.put(i + next + 1, Math.max(map.getOrDefault(i + next + 1, 0L), map.getOrDefault(i, 0L) + val));
            }
            if (i + 1 < n) {
                map.put(i + 1, Math.max(map.getOrDefault(i + 1, 0L), map.getOrDefault(i, 0L)));
            }
        }
        return res;
    }
}
/**
 * 看上去像House Robber
 * 这道题每个地方能够skip的地方是不一样的
 * dp[i]: the maximum gain after addressing [0:i]
 * 2种策略：
 * 1. 根据以前dp来决定现在dp: compute dp[i] based on the previous dps -> 填表法
 * 2. 根据现在dp来更新未来dp: compute future dps based on the current dp[i] -> 刷表法
 * 填表法：dp[i] = f(dp[j1], dp[j2],...) j < i
 * 刷表法：with dp[i] known, dp[j1] <= f{dp[i]}  max{}
 * eg. dp[j] = max{dp[j], f(dp[i])},
 *     dp[j] += f(dp[i])
 *     刷新dp[j]，可能有若干个不同的途径来更新dp[j]
 *     for loop走到j的话，表示j已经更新完毕
 * dp[i] must be monotonically increasing
 * 1. not pick i-th problem, dp[i] = dp[i-1] 截止到dp[i-1]的最大收益
 * 2. do pick i-th problem, dp[i] = dp[j] + val[i], where j + skip[j] < i, find the nearest j to i
 * dp[j-1]      dp[j]  ---->  dp[i]
 *  yes          no           yes
 *
 *  刷表法：
 *  dp[i]
 *  dp[i+1] = max{dp[i+1],dp[i]}  do not pick i+1-th problem
 *  dp[j] = max(dp[j], dp[i]+val[j]) where j = i+skip[i]+1, (pick j-th problem)
 *  dp[i-1]      dp[i]  ---->  dp[j]
 *   yes          no           yes
 * 都会遇到这个问题，怎么解决呢？
 * 如果非要这么想，如何解决？
 * 问题在于并不知道dp[i]取还是不取，冷冻期只能用在2个取的状态之间 => 修改状态定义,+1维状态, to be or not to be
 * dp[i][0]: the maximum gain after addressing [0:i] and we do not pick i-th problem
 * dp[i][1]: the maximum gain after addressing [0:i] and we do pick i-th problem
 * 填表法:
 * dp[i][0] = max{dp[i-1][0], dp[i-1][1]}
 * dp[i][1] = max{dp[j][1]} + val[i] where for all j+skip[j] < i  => O(n^2) n ~ 10^5  TLE
 * 挑那些冷冻期在i之前结束的j，随着i的推移，j的候选会越来越多，始终找一个dp[j][1]的rolling max来实时更新即可。
 * sort{j+skip[j]} 随着i的增大，每加入一个新的j，就算一个dp[j][1]
 * 刷表法：
 * dp[i][0], dp[i][1]
 * dp[i+1][0] = max(dp[i][0], dp[i][1]);
 * dp[j][1] = max(dp[j][1], dp[i][1]+val[j]); for all i+skip[i] < j  j的位置有很多种选择 => O(n^2) 优化
 * 要更新的j都是一直到底 => 类似差分数组的想法
 * 我只要标记下从它开始之后，所有的dp[j]都要提升dp[1][1]即可。
 * S3: 从后往前考虑
 * dp[i]: the maximum gain after addressing problems [i:n-1]
 * do not pick: dp[i] = dp[i+1]
 * do pick: dp[i] = dp[i+skip[i]+1] + val[i]
 */