package LC1801_2100;
import java.util.*;
public class LC1884_EggDropWith2EggsandNFloors {
    /**
     * You are given two identical eggs and you have access to a building with n floors labeled from 1 to n.
     *
     * You know that there exists a floor f where 0 <= f <= n such that any egg dropped at a floor higher than f will
     * break, and any egg dropped at or below floor f will not break.
     *
     * In each move, you may take an unbroken egg and drop it from any floor x (where 1 <= x <= n). If the egg breaks,
     * you can no longer use it. However, if the egg does not break, you may reuse it in future moves.
     *
     * Return the minimum number of moves that you need to determine with certainty what the value of f is.
     *
     * Input: n = 2
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 1000
     * @param n
     * @return
     */
    // time = O(n^2), space = O(n)
    public int twoEggDrop(int n) {
        int[][] dp = new int[2][n + 1];
        for (int i = 0; i < 2; i++) Arrays.fill(dp[i], Integer.MAX_VALUE);
        dp[0][0] = dp[1][0] = 0;
        for (int j = 1; j <= n; j++) dp[0][j] = j;

        for (int j = 1; j <= n; j++) {
            for (int k = 1; k <= j; k++) {
                dp[1][j] = Math.min(Math.max(dp[0][k - 1], dp[1][j - k]) + 1, dp[1][j]);
            }
        }
        return dp[1][n];
    }
}
/**
 * ref: LC887
 * 我们用dp[i][j]表示有(i+1)枚鸡蛋(i从0开始),需要检测j层楼时最少的移动步数
 * 首先我们考虑只有一枚鸡蛋的情况,由于只要一枚鸡蛋,因此我们只能逐层尝试,即从1楼到N楼,那么极限情况最多需要N次
 * 接下来我们考虑两枚鸡蛋去检测j层楼的情况，假设我们在第k(k>0)层楼丢鸡蛋：
 *
 * 如果鸡蛋碎了,那么就转化为一枚鸡蛋去检测K-1层楼的问题
 * 如果鸡蛋没有碎,那么就转化为了两枚鸡蛋去检测j-k层楼的问题
 * 由于我们需要确保能检测出结果,因此需要取上述两个子问题中的较大值
 * 因此dp方程的转化为：
 * dp[0][j] = j
 * dp[1][j] = min(max(dp[0][k-1],dp[1][j-k])+1) 注意:这里加1是因为当前检测需要一次,1<=k<=j
 */