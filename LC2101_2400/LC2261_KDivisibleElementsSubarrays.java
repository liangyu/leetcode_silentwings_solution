package LC2101_2400;
import java.util.*;
public class LC2261_KDivisibleElementsSubarrays {
    /**
     * Given an integer array nums and two integers k and p, return the number of distinct subarrays which have at most
     * k elements divisible by p.
     *
     * Two arrays nums1 and nums2 are said to be distinct if:
     *
     * They are of different lengths, or
     * There exists at least one index i where nums1[i] != nums2[i].
     * A subarray is defined as a non-empty contiguous sequence of elements in an array.
     *
     * Input: nums = [2,3,3,2,2], k = 2, p = 2
     * Output: 11
     *
     * Constraints:
     *
     * 1 <= nums.length <= 200
     * 1 <= nums[i], p <= 200
     * 1 <= k <= nums.length
     * @param nums
     * @param k
     * @param p
     * @return
     */
    // S1: hash values
    // time = O(n^3), space = O(n)
    public int countDistinct(int[] nums, int k, int p) {
        int n = nums.length;
        int[] presum = new int[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + (nums[i - 1] % p == 0 ? 1 : 0);

        int count = 0;
        HashSet<String> set = new HashSet<>();
        for (int i = 1; i <= n; i++) {
            for (int j = i; j <= n; j++) {
                int diff = presum[j] - presum[i - 1];
                if (diff <= k && set.add(helper(nums, i - 1, j - 1))) count++;
            }
        }
        return count;
    }

    private String helper(int[] nums, int start, int end) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i <= end; i++) {
            sb.append(nums[i]).append('#');
        }
        return sb.toString();
    }

    // S2: rolling hash
    // time = O(n^2), space = O(n)
    public int countDistinct2(int[] nums, int k, int p) {
        int base = 201;
        int n = nums.length;
        long[] power = new long[base];
        power[0] = 1;
        for (int i = 1; i < base; i++) {
            power[i] = power[i - 1] * base;
        }

        int res = 0;
        for (int len = 1; len <= n; len++) {
            HashSet<Long> set = new HashSet<>();
            long hash = 0;
            int count = 0;
            for (int i = 0; i < n; i++) {
                if (i >= len) {
                    hash = hash - nums[i - len] * power[len - 1];
                    count -= nums[i - len] % p == 0 ? 1 : 0;
                }
                hash = hash * base + nums[i];
                count += nums[i] % p == 0 ? 1 : 0;
                if (i >= len - 1) {
                    if (!set.add(hash)) continue;
                    if (count <= k) res++;
                }
            }
        }
        return res;
    }
}
/**
 * 编码：hash下变成数字 => O(1) => O(n^2)
 * Rolling Hash
 * 后缀数组 O(nlogn)
 * 1 <= nums[i], p <= 200  摆明让你用rolling hash变成201进制的数成为独一无二的编码
 * [2,3,3,2,2] -> 2*p^4 + 3*p^3 + 3*p^2 + 2*p^1 + 2*p^0
 * [0,1,2,3]
 * [1,2,3]
 * 编码一致，但subarray不同
 * 2^64 % M  -> 在溢出之前取模
 * hash % M
 * 对subarray进行形式上的去重，几乎没有好的办法，90%以上用rolling hash!!!
 */

