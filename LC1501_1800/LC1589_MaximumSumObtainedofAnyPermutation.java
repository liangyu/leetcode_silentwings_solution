package LC1501_1800;
import java.util.*;
public class LC1589_MaximumSumObtainedofAnyPermutation {
    /**
     * We have an array of integers, nums, and an array of requests where requests[i] = [starti, endi]. The ith request
     * asks for the sum of nums[starti] + nums[starti + 1] + ... + nums[endi - 1] + nums[endi]. Both starti and endi
     * are 0-indexed.
     *
     * Return the maximum total sum of all requests among all permutations of nums.
     *
     * Since the answer may be too large, return it modulo 109 + 7.
     *
     * Input: nums = [1,2,3,4,5], requests = [[1,3],[0,1]]
     * Output: 19
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^5
     * 0 <= nums[i] <= 10^5
     * 1 <= requests.length <= 10^5
     * requests[i].length == 2
     * 0 <= starti <= endi < n
     * @param nums
     * @param requests
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int maxSumRangeQuery(int[] nums, int[][] requests) {
        // corner case
        if (nums == null || nums.length == 0 || requests == null || requests.length == 0) return 0;

        int n = nums.length;
        int[] diff = new int[n + 1];
        for (int[] request : requests) {
            diff[request[0]]++;
            diff[request[1] + 1]--;
        }

        int[] freq = new int[n];
        int pre = 0;
        for (int i = 0; i < n; i++) {
            pre = pre + diff[i];
            freq[i] = pre;
        }
        Arrays.sort(freq);
        Arrays.sort(nums);

        long res = 0;
        long M = (long)(1e9 + 7);
        for (int i = 0; i < n; i++) {
            res = (res + (long)freq[i] * (long)nums[i]) % M;
        }
        return (int)res;
    }
}
/**
 * 0 1 2 3 4 5 6
 * x x x x x x x
 *   + + +
 * + +
 *       + + + +
 * nums: 1 2 1 2 1 1 1
 * freq: 1 1 1 1 1 2 2
 * 数值与频次相乘相加要最大
 * 频次大的给大的元素，频次小的给小的数 => 排序
 * 如何统计频次？
 * [0-5], [0-5]
 * 0 1 2 3 4 5 6
 * x x x x x x x
 * + + + + + +
 * + + + + + +
 * + + + + + +
 * 如何处理？-> 用差分数组，只要记录两端频次的阶跃就可以，中间变化为0不需要记录 -> 只要记录两个端点的频次变化，而不是频次本身
 * diff[i]: freq[i] - freq[i-1]
 * freq[i-1] + diff[i] = freq[i] -> 相当于前缀数组
 * 最初始的freq一定是0
 * 差分数组，扫描线 -> start处+1，end后面一个-1
 * [1-3], [2,5]
 * 0 1 2 3 4 5
 *  +1    -1
 *    +1       -1
 * ================
 * 0 1 1 0 -1 -1  [diff]
 * 0 1 2 2 1 0    [freq]
 *
 * Meeting room:
 * (1,+1)
 * (4,-1)
 * (2,+1)
 * (6,-1)
 * => sort: (1,+1) (2,+1) (4,-1) (6,-1)
 * 从前缀开始不停加这些diff =>
 * sum:     0   1   2    1    0
 *              1   2    4    6
 * 每个区间的首尾离散化，只要记录一些关键点
 */
