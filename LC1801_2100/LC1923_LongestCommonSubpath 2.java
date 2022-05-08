package LC1801_2100;
import java.util.*;
public class LC1923_LongestCommonSubpath {
    /**
     * There is a country of n cities numbered from 0 to n - 1. In this country, there is a road connecting every pair
     * of cities.
     *
     * There are m friends numbered from 0 to m - 1 who are traveling through the country. Each one of them will take a
     * path consisting of some cities. Each path is represented by an integer array that contains the visited cities in
     * order. The path may contain a city more than once, but the same city will not be listed consecutively.
     *
     * Given an integer n and a 2D integer array paths where paths[i] is an integer array representing the path of the
     * ith friend, return the length of the longest common subpath that is shared by every friend's path, or 0 if there
     * is no common subpath at all.
     *
     * A subpath of a path is a contiguous sequence of cities within that path.
     *
     * Input: n = 5, paths = [[0,1,2,3,4],
     *                        [2,3,4],
     *                        [4,0,1,2,3]]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * m == paths.length
     * 2 <= m <= 10^5
     * sum(paths[i].length) <= 10^5
     * 0 <= paths[i][j] < n
     * The same city is not listed multiple times consecutively in paths[i].
     * @param n
     * @param paths
     * @return
     */
    // time = O(m * k * logC), space = O(m * k)   C: 10^5
    final long base = (long)(1e5 + 7);
    public int longestCommonSubpath(int n, int[][] paths) {
        // corner case
        if (paths == null || paths.length <= 1) return 0;

        int left = 0, right = 100000;
        while (left < right) {
            int k = left + (right - left) / 2 + 1;
            if (checkOK(paths, k)) { // if there is a common subarray of length k
                left = k;
            } else {
                right = k - 1;
            }
        }
        return left; // 一定有收敛解
    }

    private boolean checkOK(int[][] paths, int len) {
        HashMap<Long, Integer> map = new HashMap<>();
        long head = 1;
        for (int i = 0; i < len - 1; i++) head = head * base;

        for (int k = 0; k < paths.length; k++) {  // O(m)
            HashSet<Long> set = new HashSet<>();
            long hash = 0;

            for (int i = 0; i < paths[k].length; i++) { // O(k)
                if (i >= len) hash -= paths[k][i - len] * head;
                hash = hash * base + paths[k][i];

                if (i >= len - 1 && !set.contains(hash)) { // 只有当 i >= len - 1时才是个有效的hash，才能加入set去查重
                    set.add(hash);
                    map.put(hash, map.getOrDefault(hash, 0) + 1);
                }
            }
        }

        for (int val : map.values()) {
            if (val == paths.length) return true; // 有一个就ok
        }
        return false;
    }
}
/**
 * find the longest common substring => suffix
 * rolling hash
 * 最长长度：二分搜索来搜索这个长度k
 * 如果我们找到一个substring,出现的次数恰好等于m，并且某一个长度为k的出现了m次
 * 如果找到k，那么可能可以继续往更长的方向去猜
 * 如何去找？用一个sliding window  path ~ 10^5
 * count[subarray],每一行只能数一次 => 设置一个set
 * 扫完之后看count，如果count == m，那就ok，继续决定往大还是往小猜
 * => rolling hash: subarray -> int LC1044, 1062
 * "xyz" => (23)(24)(25) => 23*26^2 + 24*26^1 + 25*26^0
 * 把一个字符串变成了一个数，而且这个字符串与这个数肯定是一一对应的
 * 因为每一位上只有26种可能，所以不会有任何冲突
 * => 某个进制上的数，每个元素都可能达到10^5 => non-overlap -> 10^5进制
 * "1 2 3" => (1)(2)(3) => 1*base^2 + 2*base^1 + 3*base^0
 * base = 1e5+7
 *
 * [456]7
 * nums[0:2] -> hash1
 * nums[1:3] -> hash2 = (hash1 - nums[0]*pow(base,2))*base+nums[3]
 * 这就叫rolling hash,下一个hash值由上一个得来，O(1)时间得到 => 效率非常高
 * rolling hash最难的部分是对付overflow，搞出来的hash值非常大
 * 为了避免溢出，所有操作的时候，不停的对一个大数取模 % mod = 1e9+7 => int32里最大的prime number
 * 但是一旦取了模，就会有hash collision
 * 如何判断冲突的概率大不大呢？
 * mod     n = 1e5
 * 生日悖论：一年365天，只要有23个人，2个人在同一天生日的概率就会超过50%
 * 23   365   > 50%
 * 经验公式： M > n^2
 * 调参：base and mod -> 调啊调跑过所有test case，需要一定的运气
 * 双hash，设计好2套hash的参数,分别是hash1和hash2
 * 两个hash值的组合出现过，对应同一个编码的这2个subarray是相同的 (乘法原理) 0.5 * 0.5 = 0.25
 * => 只有2个hash编码都吻合的时候，我们才能判断是同一个subarray
 *
 * mod的极限是2^64,最高位是符号位 -> mod = 2^64-1
 * 把所有数据结构都变成无符号的64位int，unit64_t,而且在所有计算中都取消取模的操作。
 * 骚操作：对于无符号数来说，一旦溢出之后，自动会有一个wrap up的操作，
 * 正无穷大+1=0，就相当于取了2^64的模的操作，2^64后有19个0，这样冲突概率就很小了，这样就不需要自己手动取模了，亲测非常好用！
 */
