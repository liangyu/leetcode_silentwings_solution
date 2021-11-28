package LC901_1200;
import java.util.*;
public class LC952_LargestComponentSizebyCommonFactor {
    /**
     * You are given an integer array of unique positive integers nums. Consider the following graph:
     *
     * There are nums.length nodes, labeled nums[0] to nums[nums.length - 1],
     * There is an undirected edge between nums[i] and nums[j] if nums[i] and nums[j] share a common factor greater than 1.
     * Return the size of the largest connected component in the graph.
     *
     * Input: nums = [4,6,15,35]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 2 * 10^4
     * 1 <= nums[i] <= 10^5
     * All the values of nums are unique.
     * @param nums
     * @return
     */
    // time = O(n(loglogM + M^(1/2)), space = O(n + M)
    private int L = 100005;
    private int[] parent;
    public int largestComponentSize(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        List<Integer> primes = eratosthenes((int)Math.sqrt(L));
        parent = new int[100005];
        for (int i = 0; i < L; i++) parent[i] = i;

        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            for (int p : primes) {
                if (x < p) break;
                if (x % p == 0) {
                    if (findParent(nums[i]) != findParent(p)) {
                        union(nums[i], p);
                    }
                    while (x % p == 0) x /= p;
                }
            }
            if (x > 1) { // x 本身就是一个 > 10^3的质数 => 这样一个没有除尽的，大的质因数只可能有1个，就是x本身
                if (findParent(nums[i]) != findParent(x)) {
                    union(nums[i], x);
                }
            }
        }

        HashMap<Integer, Integer> map = new HashMap<>(); // parent -> count
        for (int i = 0; i < nums.length; i++) {
            map.put(findParent(nums[i]), map.getOrDefault(findParent(nums[i]), 0) + 1);
        }
        int res = 0;
        for (int key : map.keySet()) {
            res = Math.max(res, map.get(key));
        }
        return res;
    }

    private int findParent(int x) {
        if (x != parent[x]) parent[x] = findParent(parent[x]);
        return parent[x];
    }

    private void union(int x, int y) {
        x = parent[x];
        y = parent[y];
        if (x < y) parent[y] = x;
        else parent[x] = y;
    }

    private List<Integer> eratosthenes(int n) { // 给定一个上限n，求出这个上限以内所有的primes
        int[] q = new int[n + 1];
        for (int i = 2; i <= (int)Math.sqrt(n); i++) {
            if (q[i] == 0) { // q[i] = 0 -> prime; q[i] = 1 -> non-prime
                int j = i * 2;
                while (j < n) {
                    q[j] = 1;
                    j += i;
                }
            }
        }
        List<Integer> primes = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (q[i] == 0) primes.add(i);
        }
        return primes;
    }
}
/**
 * union find
 * 所有可以连通的结点都可以放在一起，连通性可以传递！！！
 * 具体怎么union呢？
 * 按照一对对来。
 * 2 * 10^4 => O(n^2)搞不定
 * 正确的做法也比较经典：prime factors
 * ref: 952, 1627, 1998
 * 我们不去找pair，把所有<= 10^5的质数都搞出来，然后对每个数做因式分解，有公共质因数的数字union到一块
 * 2: [4,6,8,10,...]
 * 3: [3,6,9,...]
 * 6? => 连通的传递性
 * 5:[5,25]
 * 比O(n^2)效率高
 * 因式分解的复杂度 = O(nlogM) M: 数值大小，不停除以2，所以是logM
 * 做配对的话是O(n^2 * logM)
 * primes = [ ]  O(Mloglogn) 非常高效的算法，非常接近于线性时间复杂度
 * 因式分解部分比较慢
 */
