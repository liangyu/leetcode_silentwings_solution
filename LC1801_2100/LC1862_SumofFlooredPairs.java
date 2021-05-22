package LC1801_2100;
import java.util.*;
public class LC1862_SumofFlooredPairs {
    /**
     * Given an integer array nums, return the sum of floor(nums[i] / nums[j]) for all pairs of indices 0 <= i, j < nums.length in the array. Since the answer may be too large, return it modulo 109 + 7.
     *
     * The floor() function returns the integer part of the division.
     *
     * Input: nums = [2,5,9]
     * Output: 10
     *
     * Input: nums = [7,7,7,7,7,7,7]
     * Output: 49
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // S1: B.S.
    // time = O(nlogn), space = O(1)
    public int sumOfFlooredPairs(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        Arrays.sort(nums); // O(nlogn)

        int M = (int)1e9 + 7;
        int res = 0, i = 0, n = nums.length;
        while (i < n) { // O(n)
            int temp = nums[i];
            int cur = nums[i];
            while (cur <= nums[n - 1]) { // O(logn)
                int left = 0, right = n - 1;
                while (left <= right) {
                    int mid = left + (right - left) / 2;
                    if (nums[mid] < cur) left = mid + 1;
                    else right = mid - 1;
                }
                res = (res + n - left) % M;
                cur += temp;
            }
            i++;
        }
        return res;
    }

    // S2：bucket sort
    // time = O(nlogn), space = O(1)
    public int sumOfFlooredPairs2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int maxN = 100000, M = (int)1e9 + 7;
        int[] count = new int[maxN + 1];

        for (int x : nums) count[x]++;

        int[] presum = new int[maxN + 1];
        for (int i = 1; i <= maxN; i++) presum[i] = presum[i - 1] + count[i];

        long res = 0;
        HashSet<Integer> set = new HashSet<>();
        for (int x : nums) {
            if (set.contains(x)) continue; // 用一个HashSet来优化出现大量相同元素的情况
            long ans = 0;
            int k = 1;
            for (k = 1; x * (k + 1) - 1 <= maxN; k++) {
                ans += k * (presum[x * (k + 1) - 1] - presum[x * k - 1]) % M;
                ans %= M;
            }
            if (x * (k + 1) - 1 > maxN && x * k - 1 <= maxN) { // 注意处理最后一个零头区间，注意别漏了！！！
                ans += k * (presum[maxN] - presum[x * k - 1]) % M;
                ans %= M;
            }
            res = (res + ans * count[x]) % M;
            set.add(x);
        }
        return (int)res;
    }
}
/**
 * 任取 -> 没有index上的制约
 * {x,y}
 * y/x -> 只考虑比x大的元素
 * x = 5,
 * y = 6, 7, 8, 9 => y/x = 1
 * y = 10, 11, 12, 13 .. => y/x = 2
 * y = [5,9], [10,14],...[10^5-5,10^5-1]   => 10^5 / x => O(logn)
 *     m1*1    m2*2       mk*(10^4-1)
 * 分段分组，记下每组的频次 => 桶排序
 * count[y]: how many times y appears in nums
 * count[5]+count[6]+...+count[9] => presum[9] - presum[4]
 * y/x = k
 * for k = 1,2,3...until x*(k+1)-1 <= 1e5
 * ret += k * freq[x*k, x*(k+1)-1] => presum[x*(k+1)-1] - presum[x*k-1]
 * k++;
 * ret += k * presum[1e5] - presum[x*k-1]
 * time = O(nlogn)
 */