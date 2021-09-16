package LC301_600;
import java.util.*;
public class LC446_ArithmeticSlicesIISubsequence {
    /**
     * Given an integer array nums, return the number of all the arithmetic subsequences of nums.
     *
     * A sequence of numbers is called arithmetic if it consists of at least three elements and if the difference
     * between any two consecutive elements is the same.
     *
     * For example, [1, 3, 5, 7, 9], [7, 7, 7, 7], and [3, -1, -5, -9] are arithmetic sequences.
     * For example, [1, 1, 2, 5, 7] is not an arithmetic sequence.
     * A subsequence of an array is a sequence that can be formed by removing some elements (possibly none) of the array.
     *
     * For example, [2,5,10] is a subsequence of [1,2,1,2,4,1,5,10].
     * The test cases are generated so that the answer fits in 32-bit integer.
     *
     * Input: nums = [2,4,6,8,10]
     * Output: 7
     *
     * Constraints:
     *
     * 1  <= nums.length <= 1000
     * -2^31 <= nums[i] <= 2^31 - 1
     * @param nums
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public int numberOfArithmeticSlices(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length, res = 0;
        HashMap<Long, Integer>[] dp = new HashMap[n];
        for (int i = 0; i < n; i++) dp[i] = new HashMap<>(); // 注意：这里不能用Arrays.fill(dp, new HashMap<>());

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                long diff = (long)nums[i] - (long)nums[j]; // 与前面的数组成各种diff
                // 可能选不同的j对应同一个diff，对于dp[i][diff]并不区分不同的j，最后都累加到同一个diff[i][diff]
                // 比如1 3 5 7 7 9中，两个7对应两个不同的j，所以这里必须是dp[i][diff] += dp[j][diff] + 1
                dp[i].put(diff, dp[i].getOrDefault(diff, 0) + dp[j].getOrDefault(diff, 0) + 1);
                res += dp[j].getOrDefault(diff, 0); // 最后那个79不算，必须>=3，所以我们只要加dp[j][diff]即可！
            }
        }
        return res;
    }

    // S2
    // time = O(n), space = O(n)
    public int numberOfArithmeticSlices2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.putIfAbsent(nums[i], new ArrayList<>());
            map.get(nums[i]).add(i);
        }

        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                long diff = (long)nums[i] - (long)nums[j];
                long m = (long)nums[j] - diff;
                if (m > Integer.MAX_VALUE || m < Integer.MIN_VALUE) continue;
                if (map.containsKey((int)m)) {
                    for (int k : map.get((int)m)) {
                        if (k < j) {
                            dp[j][i] += dp[k][j] + 1;
                        }
                    }
                }
            }
        }
        int res = 0;
        for (int j = 0; j < n; j++) {
            for (int i = j + 1; i < n; i++) {
                res += dp[j][i];
            }
        }
        return res;
    }
}
/**
 * dp
 * 等差数列， LC1027
 * 等差数列常见做法：如何确定一个等差数列？
 * 对于dp题，一般都是从前往后扫描
 * dp[i][diff]:以A[i]结尾，diff为公差，(长度>=2)的等差数列个数
 * 只考虑dp[i]与dp[i-1]之类有什么关系 dp[i] = f(dp[i-1])
 * 以i为结尾的等差数列有多少个
 * sum dp[i] for i = 0, 1, ... n - 1
 * 结尾元素 + 公差往前推
 * dp[i][diff] = dp[j][diff] + 1
 * diff考虑i之前所有的元素
 * [1 3 5 7 7 9 x x]
 * dp[3][2] = 3     357 1357  得至少3个数，所以57不算
 * dp[4][2] = dp[3][2] + 1
 * result += dp[3][2]
 * fill(object[] a, int fromIndex, int toIndex, object val) method assigns the specified "Object reference"
 * to each element of the specified range of the specified array of objects.
 * 2个数就能确定一个公差，而题目要求三个数才形成一个等差数列，这也是我们采用长度>=2的定义出发再修改
 *
 * S2:
 * dp[j][i]: 以nums[j], nums[i]为最后两项的等差数列的个数 => 公差也就有了
 * 倒数第3项: nums[k], nums[j], nums[i] 能够组成等差数列倒数三项
 * dp[j][i] += dp[k][j] + 1  (k < j)
 * 怎么找这个k呢？知道nums[k] -> k
 * 先用HashMap撸一遍，就可以根据val -> index
 *
 * 总结：
 * 对于求等差数列的题目，2种方法：
 * 1. dp[i][diff] 就能确定一个等差数列
 * 2. dp[j][i] 末两项，同样可以确定一个等差数列，找到k得到末尾三项
 * 这2个方法基本思想等价
 */
