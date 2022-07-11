package LC301_600;

public class LC375_GuessNumberHigherorLowerII {
    /**
     * We are playing the Guessing Game. The game will work as follows:
     *
     * I pick a number between 1 and n.
     * You guess a number.
     * If you guess the right number, you win the game.
     * If you guess the wrong number, then I will tell you whether the number I picked is higher or lower, and you will
     * continue guessing.
     * Every time you guess a wrong number x, you will pay x dollars. If you run out of money, you lose the game.
     * Given a particular n, return the minimum amount of money you need to guarantee a win regardless of what number I
     * pick.
     *
     * Input: n = 10
     * Output: 16
     *
     * Constraints:
     *
     * 1 <= n <= 200
     * @param n
     * @return
     */
    // time = O(n^3), space = O(n^2)
    public int getMoneyAmount(int n) {
        int[][] f = new int[n + 2][n + 2];

        for (int len = 2; len <= n; len++) { // 区间长度只有1的话，一次就猜中了，代价为0 => f[i][j] = 0 不需要额外处理！
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                f[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j; k++) {
                    f[i][j] = Math.min(f[i][j], Math.max(f[i][k - 1], f[k + 1][j]) + k);
                }
            }
        }
        return f[1][n];
    }
}
/**
 * 区间dp
 * 1. 状态表示 f[i,j]
 * 集合：所有可能的目标值在[i,j]及所有可能的猜法
 * 属性：最坏情况下的最小值
 * 2. 状态计算：f(i,j): 最坏情况下的最小值
 * guess k
 * 猜对
 * 太大：i ~ k - 1 => f(i,k-1)
 * 太小：k+1 ~ j => f(k+1,j)
 * 取max(f[i,k-1], f[k+1,j]) + k  => 不能决定答案在哪边，就考虑max,加上猜k的代价
 */