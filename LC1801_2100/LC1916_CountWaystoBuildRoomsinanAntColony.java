package LC1801_2100;
import java.util.*;
public class LC1916_CountWaystoBuildRoomsinanAntColony {
    /**
     * You are an ant tasked with adding n new rooms numbered 0 to n-1 to your colony. You are given the expansion plan
     * as a 0-indexed integer array of length n, prevRoom, where prevRoom[i] indicates that you must build room
     * prevRoom[i] before building room i, and these two rooms must be connected directly. Room 0 is already built, so
     * prevRoom[0] = -1. The expansion plan is given such that once all the rooms are built, every room will be
     * reachable from room 0.
     *
     * You can only build one room at a time, and you can travel freely between rooms you have already built only if
     * they are connected. You can choose to build any room as long as its previous room is already built.
     *
     * Return the number of different orders you can build all the rooms in. Since the answer may be large, return it
     * modulo 10^9 + 7.
     *
     * Input: prevRoom = [-1,0,1]
     * Output: 1
     *
     * Constraints:
     *
     * n == prevRoom.length
     * 2 <= n <= 10^5
     * prevRoom[0] == -1
     * 0 <= prevRoom[i] < n for all 1 <= i < n
     * Every room is reachable from room 0 once all the rooms are built.
     * @param prevRoom
     * @return
     */
    long M = (long)(1e9 + 7);
    public int waysToBuildRooms(int[] prevRoom) {
        // corner case
        if (prevRoom == null || prevRoom.length == 0) return 0;

        int n = prevRoom.length;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        long[] count = new long[n]; // total # of children in the subtree of cur node, eg. 4!
        long[] dp = new long[n]; // final res
        long[] f = new long[n + 1]; // permutation function

        // calculate n!
        f[0] = 1;
        for (int i = 1; i <= n; i++) {
            f[i] = f[i - 1] * i % M;
        }

        // build map
        for (int i = 0; i < n; i++) {
            map.putIfAbsent(prevRoom[i], new ArrayList<>());
            map.get(prevRoom[i]).add(i);
        }

        dfs(map, count, dp, f, 0);
        return (int)dp[0];
    }

    private void dfs(HashMap<Integer, List<Integer>> map, long[] count, long[] dp, long[] f, int node) {
        // base case
        if (!map.containsKey(node)) {
            dp[node] = 1;
            count[node] = 1;
            return;
        }

        for (int next : map.get(node)) {
            dfs(map, count, dp, f, next);
        }

        int sum = 0;
        for (int next : map.get(node)) {
            sum += count[next];
        }

        long res = f[sum];
        for (int next : map.get(node)) {
            res = res * inv(f[(int)count[next]]) % M; // 不同组之间的全排列
        }

        for (int next : map.get(node)) {
            res = res * dp[next] % M; // * 同组内部排列数
        }

        dp[node] = res;
        count[node] = sum + 1;
    }

    private long inv(long x) {
        long s = 1;
        for (; x > 1; x = M % x) {
            s = s * (M - M / x) % M;
        }
        return s;
    }
}
/**
 * 0#### => 2x 2y 6种全排列  把同组内的不同元素都看成对等不加区分的元素，比如1-3 => x, 2-4 => y
 * xxyy
 * xyxy
 * xyyx
 * yyxx
 * yxyx
 * yxxy
 * => 2个x对等，可轮换
 * 4! = 24
 * ...x1...x2...
 * ...x2...x1...
 * 对y也是一样
 * 消除内部全排列 => dp(root) = 4! / 2! / 2! = 6 * dp(x) * dp(y)
 * 之所以这里要摒弃剔除掉组内的全排列，是因为组内的不同元素排列必须遵循题目给点的先后关系，我们把它单独拿出来考虑，用dp(x)和dp(y)来标记。
 * dp(x),dp(y)是要考虑约束关系的，不是2!
 * 递归调用
 * 1-3
 * 2-4
 * 按照组的全排列
 *
 * 本题主要考察的是组合数的分解，或者说关于递归的分解
 * dp[node] -> dp[x] dp[y]
 * 把一棵子树的node都想象成同一个类别，另一棵子树也想象成同一个类别
 * 类别与类别之间是完全自由的全排列
 * 我们令dp[node]表示以node为节点的子树的排列数，num[node]表示以node为节点的子树的节点数目，那么就有递推公式：
 * dp[node] = num[node] ! / (num[child1]! * num[child2]! * ... * num[childk]!) * dp[child1] * dp[child2] * ... * dp[childk]
 * 注意到上述的式子中含有除法，对大数取模的过程遇到除法需要用到逆元运算，即
 * (a / b) % M = a * inv(b, M)
 */