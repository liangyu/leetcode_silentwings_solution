package LC601_900;
import java.util.*;
public class LC710_RandomPickwithBlacklist {
    /**
     * You are given an integer n and an array of unique integers blacklist. Design an algorithm to pick a random
     * integer in the range [0, n - 1] that is not in blacklist. Any integer that is in the mentioned range and not in
     * blacklist should be equally likely to be returned.
     *
     * Optimize your algorithm such that it minimizes the number of calls to the built-in random function of your
     * language.
     *
     * Implement the Solution class:
     *
     * Solution(int n, int[] blacklist) Initializes the object with the integer n and the blacklisted integers blacklist.
     * int pick() Returns a random integer in the range [0, n - 1] and not in blacklist.
     *
     * Input
     * ["Solution", "pick", "pick", "pick", "pick", "pick", "pick", "pick"]
     * [[7, [2, 3, 5]], [], [], [], [], [], [], []]
     * Output
     * [null, 0, 4, 1, 6, 1, 0, 4]
     *
     * Constraints:
     *
     * 1 <= n <= 10^9
     * 0 <= blacklist.length <= min(10^5, n - 1)
     * 0 <= blacklist[i] < n
     * All the values of blacklist are unique.
     * At most 2 * 10^4 calls will be made to pick.
     * @param n
     * @param blacklist
     */
    int n, m;
    HashMap<Integer, Integer> map;
    Random random;
    // time = O(m), space = O(m)
    public LC710_RandomPickwithBlacklist(int n, int[] blacklist) {
        random = new Random();
        map = new HashMap<>();
        HashSet<Integer> set = new HashSet<>();
        m = blacklist.length;
        this.n = n;
        for (int i = n - m; i < n; i++) set.add(i);
        for (int x : blacklist) set.remove(x);
        List<Integer> nums = new ArrayList<>(set);
        int idx = 0;
        for (int x : blacklist) {
            if (x < n - m) map.put(x, nums.get(idx++));
        }
    }
    // time = O(1), space = O(m)
    public int pick() {
        int k = random.nextInt(n - m);
        if (map.containsKey(k)) return map.get(k);
        return k;
    }
}
/**
 * n - len
 * 取到黑名单位置，就映射到没有在黑名单的位置
 */
