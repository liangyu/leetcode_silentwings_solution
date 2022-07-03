package LC2101_2400;

public class LC2327_NumberofPeopleAwareofaSecret {
    /**
     * On day 1, one person discovers a secret.
     *
     * You are given an integer delay, which means that each person will share the secret with a new person every day,
     * starting from delay days after discovering the secret. You are also given an integer forget, which means that
     * each person will forget the secret forget days after discovering it. A person cannot share the secret on the same
     * day they forgot it, or on any day afterwards.
     *
     * Given an integer n, return the number of people who know the secret at the end of day n. Since the answer may be
     * very large, return it modulo 10^9 + 7.
     *
     * Input: n = 6, delay = 2, forget = 4
     * Output: 5
     *
     * Input: n = 4, delay = 1, forget = 3
     * Output: 6
     *
     * Constraints:
     *
     * 2 <= n <= 1000
     * 1 <= delay < forget <= n
     * @param n
     * @param delay
     * @param forget
     * @return
     */
    // S1: dp
    // time = O(n), space = O(n)
    public int peopleAwareOfSecret(int n, int delay, int forget) {
        long[] f = new long[n + 1];
        f[1] = 1;
        long M = (long)(1e9 + 7), sum = 0;

        for (int i = 2; i <= n; i++) {
            if (i > delay) sum = (sum + f[i - delay]) % M;
            if (i > forget) sum = (sum - f[i - forget] + M) % M;
            f[i] = sum;
        }
        long res = 0;
        for (int i = n; i > n - forget; i--) res = (res + f[i]) % M;
        return (int) res;
    }

    // S2: dp
    // time = O(n * k), space = O(n^2)
    public int peopleAwareOfSecret2(int n, int delay, int forget) {
        long M = (long)(1e9 + 7);
        long[][] f = new long[n + 1][n + 1];
        for (int i = 1; i <= forget; i++) f[1][i] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= forget; j++) {
                if (j == 1) f[i][j] = (f[i - 1][forget - 1] - f[i - 1][delay - 1]) % M;
                else f[i][j] = (f[i - 1][j - 1] - f[i - 1][j - 2]) % M;
                f[i][j] = (f[i][j] + f[i][j - 1]) % M;
            }
        }
        return (int)((f[n][forget] + M) % M);
    }
}
/**
 * S1
 * f[i] means the number of people who found the secret on ith day.
 *
 * S2:
 * 每天最多只有forget类，每一类看做一个来处理
 * dp
 * 状态表示 f[i,j]:
 * 1.集合：第i天后，保守秘密j天的所有人的集合
 * 2.属性：数量
 * 状态计算 f[i,j]
 * j > 1: f[i,j] = f[i-1,j-1]
 * j = 1: delay <= j < forget => f[i,1] = f[i-1,delay] + f[i-1,delay+1] + ...+f[i-1,forget-1]
 * f[n,1]+...+f[n,forget]
 * => O(n^3)
 * 一段连续的数字和 => 前缀和
 * f'[i,j] = f[i,1] + ... + f[i,j]
 * =>
 * j > 1: f[i,j] = f[i-1,j-1] - f[i-1,j-2]
 * j = 1: f[i,1] = f[i-1,b-1] - f[i-1,a-1]
 */