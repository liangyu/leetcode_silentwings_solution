package LC1801_2100;
import java.util.*;
public class LC1998_GCDSortofanArray {
    /**
     * You are given an integer array nums, and you can perform the following operation any number of times on nums:
     *
     * Swap the positions of two elements nums[i] and nums[j] if gcd(nums[i], nums[j]) > 1 where gcd(nums[i], nums[j])
     * is the greatest common divisor of nums[i] and nums[j].
     * Return true if it is possible to sort nums in non-decreasing order using the above swap method, or false otherwise.
     *
     * Input: nums = [7,21,3]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= nums.length <= 3 * 10^4
     * 2 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n(loglogM + M^(1/2)), space = O(n + M)
    private int[] parent;
    public boolean gcdSort(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return false;

        parent = new int[100005]; // 拿数值进行union，而不是index
        for (int i = 0; i < parent.length; i++) parent[i] = i;

        int[] primes = eratosthenes((int)Math.sqrt(100000));

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
            if (x > 1) {
                if (findParent(nums[i]) != findParent(x)) {
                    union(nums[i], x);
                }
            }
        }

        int[] copy = nums.clone();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            if (findParent(nums[i]) != findParent(copy[i])) return false;
        }
        return true;
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

    private int[] eratosthenes(int n) { // 给定一个上限n，求出这个上限以内所有的primes
        int[] q = new int[n + 1];
        for (int i = 2; i <= (int)Math.sqrt(n); i++) {
            if (q[i] == 0) {
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
        int[] res = new int[primes.size()];
        for (int i = 0; i < primes.size(); i++) res[i] = primes.get(i);
        return res;
    }
}
/**
 * a <-> b, b <-> c 具有传递性
 * (a,b,c,d,e)
 * 我们希望交换之后呈现一个递增关系
 * 把原来的数组和期望的数组比较下，比较相同位置上这两个数是否在同一个group里
 * nums0: 4 3 2 6
 *        -   - -
 * nums1: 2 3 4 6
 * 看4与2是否在同一个group里，表示4，2，6可以互换位置
 * 逐位检查，每个num pair是否在同一个group
 * O(n^2)不行
 * ref: 952, 1627
 * 不直接比较两个数的质因数，而是列举出所有的质因数
 * primes = [...]   O(nloglogM)，近似O(NlogM) 不停除以2
 * 对每个num而言，每个尝试一遍，这样就非常高效了
 * 把所有相同质因数的都group起来
 * 2: [....6.....]
 * 3: [....6......]
 * 5: [..........]
 * num = 6 = 2 * 3;  => 连通起2和3这2个group
 */