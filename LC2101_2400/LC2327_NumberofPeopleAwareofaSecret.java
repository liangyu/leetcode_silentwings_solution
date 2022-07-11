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
    // time = O(n * k), space = O(n)
    public int peopleAwareOfSecret2(int n, int delay, int forget) {
        long[] f = new long[n + 1];
        long M = (long)(1e9 + 7);
        f[1] = 1;

        for (int i = 1; i <= n; i++) {
            for (int j = i + delay; j < i + forget; j++) {
                if (j > n) break;
                f[j] += f[i];
                f[j] %= M;
            }
        }

        long res = 0;
        for (int i = 1; i <= n; i++) {
            if (i + forget > n) {
                res += f[i];
                res %= M;
            }
        }
        return (int) res;
    }

    // S3: dp + diff
    // time = O(n), space = O(n)
    public int peopleAwareOfSecret3(int n, int delay, int forget) {
        long[] f = new long[n + 1];
        long[] diff = new long[n + 1];
        long M = (long)(1e9 + 7);
        diff[1]++;
        diff[2]--;
        f[1] = 1;

        for (int i = 1; i <= n; i++) {
            f[i] = (f[i - 1] + diff[i] + M) % M;
            if (i + delay <= n) diff[i + delay] += f[i];
            if (i + forget <= n) diff[i + forget] -= f[i];
        }

        long res = 0;
        for (int i = 1; i <= n; i++) {
            if (i + forget > n) res = (res + f[i]) % M;
        }
        return (int) res;
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
 *
 * i-th day => [i+delay, i+forget]
 * 差分 -> 线性，只要考虑前后端点的做法
 * dp[i]: how many people affected in the i-th day
 *        NewlyAffectly(i),  AlreadyAffected   -> 区分开来 [i+delay, i+forget-1]
 * 确定新感染人数很重要 -> 增加一维
 * 刚被感染 or 前面几天被感染 (老病患 / 新病患) 没办法区分
 * 有多少人痊愈
 * dp[i] + ... - NewlyAffected(i-forget)
 * dp[i]: how many new people get affected in the i-th day
 * dp[1] = 1
 * for (int j = i + delay; j < i + forget; j++) {
 *     dp[j] += dp[i]; // 每天都会新增加感染的人数
 * }
 * ret？ dp 不能问什么设什么
 * 不关心是新感染的还是已经感染的
 * 这一天感染人数依然在第n天处于感染状态
 * for (int i = 1; i <= n; i++) {
 *     if (i + delay > n) ret += dp[i];
 * }
 * dp[i] = ... f(existing dp[i-delay])
 * dp[i] => update futrue dp[i+delay]
 *
 * diff[i] = dp[i] - dp[i - 1]
 */