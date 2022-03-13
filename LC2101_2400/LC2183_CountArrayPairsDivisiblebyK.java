package LC2101_2400;
import java.util.*;
public class LC2183_CountArrayPairsDivisiblebyK {
    /**
     * Given a 0-indexed integer array nums of length n and an integer k, return the number of pairs (i, j) such that:
     *
     * 0 <= i < j <= n - 1 and
     * nums[i] * nums[j] is divisible by k.
     *
     * Input: nums = [1,2,3,4,5], k = 2
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i], k <= 10^5
     * @param nums
     * @param k
     * @return
     */
    // S1
    // time = O(nlogn), space = O(n)
    public long countPairs(int[] nums, int k) {
        HashSet<Integer> set = new HashSet<>();
        for (int i = 1; i * i <= k; i++) {
            if (k % i == 0) {
                set.add(i);
                set.add(k / i);
            }
        }

        int n = nums.length;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int x : set) {
                if (nums[i] % x == 0) {
                    map.putIfAbsent(x, new ArrayList<>());
                    map.get(x).add(i);
                }
            }
        }

        long res = 0;
        for (int i = 0; i < n; i++) {
            int a = gcd(nums[i], k);
            int b = k / a;
            if (!map.containsKey(b)) continue;
            int idx = upperBound(map.get(b), i);
            res += map.get(b).size() - idx;
        }
        return res;
    }

    private int upperBound(List<Integer> nums, int t) {
        int left = 0, right = nums.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums.get(mid) <= t) left = mid + 1;
            else right = mid;
        }
        return nums.get(left) > t ? left : left + 1;
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // S2:
    // time = O(nlogn), space = O(n)
    public long countPairs2(int[] nums, int k) {
        int n = nums.length;
        int[] g = new int[n];
        for (int i = 0; i < n; i++) g[i] = gcd(nums[i], k);

        HashMap<Integer, List<Integer>> map = new HashMap();
        for (int i = 0; i < n; i++) {
            map.putIfAbsent(g[i], new ArrayList<>());
            map.get(g[i]).add(i);
        }

        long res = 0;
        for (int i = 0; i < n; i++) {
            int find = k / g[i];
            if (find == 1) res += n - i - 1;
            else res += helper(map, find, i);
        }
        return res;
    }

    private long helper(HashMap<Integer, List<Integer>> map, int k, int i) {
        long count = 0;
        for (int key : map.keySet()) {
            if (key % k == 0) {
                List<Integer> list = map.get(key);
                int idx = upperBound(list, i);
                count += list.size() - idx;
            }
        }
        return count;
    }
}
/**
 * (i, j) O(N) -> log(N)
 * (i,j,k)  O(N) -> log(N)
 * nums[i] * nums[j] % k
 * a = gcd(nums[i], k)
 * nums[j] => b = k / a
 * i [x x x x x x]
 * for (int i = 0; i < nums.size(); i++)
 *      for (int b : restriction of k)
 *          if (nums[i] % b == 0)  => O(100n)
 *              map[b].push_back(i);
 */