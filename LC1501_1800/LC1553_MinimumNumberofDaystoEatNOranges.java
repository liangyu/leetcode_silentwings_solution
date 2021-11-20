package LC1501_1800;
import java.util.*;
public class LC1553_MinimumNumberofDaystoEatNOranges {
    /**
     * There are n oranges in the kitchen and you decided to eat some of these oranges every day as follows:
     *
     * Eat one orange.
     * If the number of remaining oranges (n) is divisible by 2 then you can eat  n/2 oranges.
     * If the number of remaining oranges (n) is divisible by 3 then you can eat  2*(n/3) oranges.
     * You can only choose one of the actions per day.
     *
     * Return the minimum number of days to eat n oranges.
     *
     * Input: n = 10
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= n <= 2*10^9
     * @param n
     * @return
     */
    // S1: dfs
    // time = O((logn)^2), space = O((logn)^2)
    HashMap<Integer, Integer> map = new HashMap<>(); // init HashMap outside as the main function is used in dfs loop!!!
    public int minDays(int n) {
        // base case
        if (n == 1) return 1;
        if (n == 2 || n == 3) return 2;

        if (map.containsKey(n)) return map.get(n);

        int res = Math.min(n % 2 + 1 + minDays(n / 2), n % 3 + 1 + minDays(n / 3));
        map.put(n, res);
        return res;
    }

    // S2: bfs
    // time = O(logn), space = O(n)
    public int minDays2(int n) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n);
        HashSet<Integer> visited = new HashSet<>();
        visited.add(n);

        int step = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int cur = queue.poll();
                if (cur == 0) return step;

                if (visited.add(cur - 1)) queue.offer(cur - 1);
                if (cur % 2 == 0 && visited.add(cur / 2)) queue.offer(cur / 2);
                if (cur % 3 == 0 && visited.add(cur / 3)) queue.offer(cur / 3);
            }
            step++;
        }
        return -1;
    }
}
/**
 * 理论上没有明显的贪心解法
 * dp, 从小到大
 * for i = 1 : n
 * dp[i] = min{1 + dp[i - 1], 1 + dp[i/2], 1 + dp[1/3]}
 * dfs: 3条岔路，但i - 1这条岔路效率会非常慢，其实这条岔路不是十分明智
 * 想要递归速度更快，那就尽量实现/2或者/3，而不会优先选择去选择减去若干个1.
 * 比如11，尽量要用到2后者3，先减去1变成10，10能被2整除，就马上除以2
 * 或者11-1=10 -> 9 - 1 = 8 -> 4
 * 同样到4，早用2永远要比晚用2要好，没必要减去3个1
 * -1永远是为/2或者/3去服务的
 * 优化的表达式：dp(i) = min{i % 2 + 1 + dp(i/2), i % 3 + 1 + dp(i/3)}
 * 并没有证据表明/3要比/2要好
 * 17 -> 16 -> 8 -> 4 -> 2 -> 1 -> 0  => /2更好
 * 所以两者都得尝试下
 * 本质就是递归处理，配合记忆化的使用，减掉一些不必要的重复运算
 * 每一层2个分支,大概logn层 => 每一层2分支，所以 2^(logn) = O(n),为什么没有超时？？？
 * 因为用了记忆化后，就变成time = O(logn)^2
 * 对于第k层，意味着我们做了k次除法，这k次除法中/2的个数可能有0次，1次，2次...，直至有k次，这对应了k+1种不同状态。
 * 举个例子，我们从n开始做了5次除法，假设有2次是/2，另外3次是/3，考虑到除法的顺序不影响递归的结果。
 * 只要n经过了两次/2和三次/3，剩下来的n'肯定都是一样。
 * 经过第k层递归后，得到的只会是k+1种不同的n'。
 * 所以这是一个公差为1的等差数列。所以递归完所有的状态，需要记录总的状态就是 1+2+...+logN ~ O(logN^2)
 * n = 20,    10,   3
 *     20,     6,   3
 *       f(20)
 *   f(10)   f(6)
 * f(5)   f(3)   f(2)
 */
