package LC1801_2100;
import java.util.*;
public class LC1900_TheEarliestandLatestRoundsWherePlayersCompete {
    /**
     * There is a tournament where n players are participating. The players are standing in a single row and are
     * numbered from 1 to n based on their initial standing position (player 1 is the first player in the row, player
     * 2 is the second player in the row, etc.).
     *
     * The tournament consists of multiple rounds (starting from round number 1). In each round, the ith player from the
     * front of the row competes against the ith player from the end of the row, and the winner advances to the next
     * round. When the number of players is odd for the current round, the player in the middle automatically advances
     * to the next round.
     *
     * For example, if the row consists of players 1, 2, 4, 6, 7
     * Player 1 competes against player 7.
     * Player 2 competes against player 6.
     * Player 4 automatically advances to the next round.
     * After each round is over, the winners are lined back up in the row based on the original ordering assigned to
     * them initially (ascending order).
     *
     * The players numbered firstPlayer and secondPlayer are the best in the tournament. They can win against any other
     * player before they compete against each other. If any two other players compete against each other, either of
     * them might win, and thus you may choose the outcome of this round.
     *
     * Given the integers n, firstPlayer, and secondPlayer, return an integer array containing two values, the earliest
     * possible round number and the latest possible round number in which these two players will compete against each
     * other, respectively.
     *
     * Input: n = 11, firstPlayer = 2, secondPlayer = 4
     * Output: [3,4]
     *
     * Constraints:
     *
     * 2 <= n <= 28
     * 1 <= firstPlayer < secondPlayer <= n
     * @param n
     * @param firstPlayer
     * @param secondPlayer
     * @return
     */
    // time = O(n^4 * logn), space = O(n^4)
    public int[] earliestAndLatest(int n, int firstPlayer, int secondPlayer) {
        int[][][] dp1 = new int[30][30][30];
        int[][][] dp2 = new int[30][30][30];
        return dfs(n, dp1, dp2, firstPlayer, secondPlayer);
    }

    private int[] dfs(int n, int[][][] dp1, int[][][]  dp2, int a ,int b) {
        if (a + b == n + 1) return new int[]{1, 1};

        if (dp1[n][a][b] != 0) return new int[]{dp1[n][a][b], dp2[n][a][b]};

        if (a > b) return dfs(n, dp1, dp2, b, a);
        if ((a + b) / 2.0 > (n + 1) / 2.0) return dfs(n, dp1, dp2, n + 1 - b, n + 1 - a);

        // case 1: 跨过中轴线
        int bb = n + 1 - b, z = (b - bb - 1 + 1) / 2;
        int minVal = Integer.MAX_VALUE / 2;
        int maxVal = Integer.MIN_VALUE / 2;
        if (b > (n + 1) / 2) {
            int range1 = a - 1, range2 = bb - a - 1;
            for (int x = 0; x <= range1; x++) {
                for (int y = 0; y <= range2; y++) {
                    int[] temp = dfs((n + 1) / 2, dp1, dp2, x + 1, x + 1 + y + z + 1);
                    minVal = Math.min(minVal, temp[0] + 1);
                    maxVal = Math.max(maxVal, temp[1] + 1);
                }
            }
        } else {
            int range1 = a - 1, range2 = b - a - 1;
            for (int x = 0; x <= range1; x++) {
                for (int y = 0; y <= range2; y++) {
                    int[] temp = dfs((n + 1) / 2, dp1, dp2, x + 1, x + 1 + y + 1);
                    minVal = Math.min(minVal, temp[0] + 1);
                    maxVal = Math.max(maxVal, temp[1] + 1);
                }
            }
        }
        dp1[n][a][b] = minVal;
        dp2[n][a][b] = maxVal;
        return new int[]{minVal, maxVal};
    }
}
/**
 * 28^4 = O(n^4) = 10^6 -> brute force & dp
 * dp1[n][a][b]
 * dp2[n][a][b]
 * x x x a x x x x b x x x x x x   约定下a比b小，交换下其实是等价的
 * x x x a' x b' x x x x x a x b x
 *
 * 两个约定：
 * 1. when a > b
 *      dp[a][b] -> dp[b][a]
 *
 * 2. when (a+b)/2.0 > (n+1)/2.0 ->
 *      dp[a][b] -> dp[n+1-b][n+1-a]
 *
 * (1) a, b 在中轴线的两侧
 * (L) [xxxx] a [xxx] bb xxx M xxx b [xxx] aa [xxxx] (R)
 *       x       y     z=(b-bb-1+1)/2
 * range1 = a-1;
 * range2 = bb-a-1;
 *
 * for x = 0 to range1
 *  for y = 0 to range2
 *      dp[n][a][b] => dp[(n+1)/2][x+1][x+1+y+z+1]   大规模问题转化为小规模问题
 *      dp1[n][a][b] = min{}
 *      dp1[n][a][b] = max{}
 *
 * (2) a, b 不在中轴线的两侧
 * (L) [xxxx] a [xxx] b xxx M xxx bb [xxx] aa [xxxx] (R)
 *       x       y
 * 只关心左边2段有多少留下来
 *
 * range1 = a-1;
 * range2 = b-a-1;
 *
 * for x = 0 to range1
 *  for y = 0 to range2
 *      dp[n][a][b] => dp[(n+1)/2][x+1][x+1+y+1]   大规模问题转化为小规模问题
 *      dp1[n][a][b] = min{}
 *      dp1[n][a][b] = max{}
 *
 * 边界条件：当a,b已经在中轴对称位置上，返回1
 * when a + b == n + 1
 * dp1[n][a][b] = dp2[n][a][b] = 1
 * 状态稀疏，不需要每个都算也能解出来 -> topdown
 * 如果每个都要算 -> bottom up
 *
 * Greedy -> O(logn) -> O(1)
 * 对于n而言，你要log多少次是可以知道的，比如16 -> 2^4，可以用O(1)解出来
 *
 * earliest round
 * [xxxx] a [xxx] bb xxx M xxx b [xxx] aa [xxxx]
 *   x       y
 * 通过操作让下一轮a与b对称 -> 让a的左边和b的右边相同
 * 只有这种情况无法操作：
 * if x%2==1 && y==0 => return 3;
 * else return 2;
 * 这种情况下只能再多一轮
 *
 * (L) [xxxx] a [xxx] b xxx M xxx bb [xxx] aa [xxxx] (R)
 *       x       y
 * 无脑让上半区的人通赢 => 让中轴以最快速度往左边拉，拉到a, b中间时就跟上面情况一致了
 * first let the first win
 * 最后一轮稍微做下调整，让中轴落在a, b中间
 * 也有个例外：n%2==1 && b=a+1
 * eg. xabxx -> 没有办法找到一个中轴线上a b堆成 => 只能继续无脑让人数减半，直到n为偶数为止，因为无论如何减半，只要是奇数，a与b永远相邻，只能
 * 让n降到偶数为止
 *
 * latest round?
 * 不希望让中轴移到a, b中间 -> 让左边全输，让a, b尽量晚的相遇，同earliest round的想法正好相反
 * (L) [xxxx] a [xxx] b xxx M xxx bb [xxx] aa [xxxx] (R)
 * => dp((n+1)/2,1,1)  让a变第1个，让b变第1个
 *
 * [xxxx] a [xxx] bb (xxx M xxx b) [xxx] aa [xxxx]
 * => dp((n+1)/2,0,1+z+1)  让a变第1个，让b变第1个
 */
