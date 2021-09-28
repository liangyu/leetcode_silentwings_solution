package LC1801_2100;
import java.util.*;
public class LC2009_MinimumNumberofOperationstoMakeArrayContinuous {
    /**
     * You are given an integer array nums. In one operation, you can replace any element in nums with any integer.
     *
     * nums is considered continuous if both of the following conditions are fulfilled:
     *
     * All elements in nums are unique.
     * The difference between the maximum element and the minimum element in nums equals nums.length - 1.
     * For example, nums = [4, 2, 5, 3] is continuous, but nums = [1, 2, 3, 5, 6] is not continuous.
     *
     * Return the minimum number of operations to make nums continuous.
     *
     * Input: nums = [4,2,5,3]
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // S1: BS
    // time = O(nlogn), space = O(n)
    public int minOperations(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        Arrays.sort(nums);
        List<Integer> list = new ArrayList<>();
        list.add(nums[0]);
        for (int i = 1; i < n; i++) {
            if (nums[i] == nums[i - 1]) continue;
            list.add(nums[i]);
        }

        int count = n;
        for (int i = 0; i < list.size(); i++) {
            // left = i, right = i + n - 1;
            int j = list.get(i) + n - 1;
            int idx = helper(list, j); // explore the potential right end
            int uniqueLen = idx - i + 1; // unique elements to left intact
            count = Math.min(count, n - uniqueLen); // # of elements need to be changed
        }
        return count;
    }

    private int helper(List<Integer> list, int t) {
        int left = 0, right = list.size() - 1;
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (list.get(mid) <= t) left = mid;
            else right = mid - 1;
        }
        return list.get(left) <= t ? left : left - 1;
    }

    // S2: sliding window
    // time = O(nlogn), space = O(n)
    public int minOperations2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        List<Integer> list = new ArrayList<>();
        Arrays.sort(nums);
        list.add(nums[0]);
        for (int i = 1; i < n; i++) {
            if (nums[i] != nums[i - 1]) list.add(nums[i]);
        }

        int j = 0, res = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            while (j < list.size() && list.get(j) - list.get(i) <= n - 1) {
                res = Math.min(res, n - (j - i + 1));
                j++;
            }
        }
        return res;
    }
}
/**
 * 连续，递增1 => 确定一个左端点和一个右端点
 * 固定一个端点，无脑遍历一个端点来看是否能确定右端点
 * 固定左端点，是否是数组中的某一个元素
 * 最优解左端点有没有可能是其他元素？不可能。
 * 如果能确定一个左端点，就能确定右端点，剩下的元素往中间塞即可。
 * 我们只需要遍历以nums里面的元素作为左端点
 * 我们只需要枚举这个连续区间的左端点即可。对于最优解而言，连续区间的左端点一定是落在nums的某一个数值上。
 * 假设存在某一个是最优解的连续区间，它的左端点不在nums里，那么我们可以将这个区间整体右移，直至左端点落在nums的某个数值上。
 * 这样，移动的过程中，区间的左边没有排除任何一个已经包含的nums的数值，而区间的右边还可能纳入新的nums的数值，肯定不会亏。
 * 如果我们令这个连续区间的左端点在nums[i]，那么我们会单调地增大j，往右探索区间的右端点nums[j]。
 * 假设我们希望构造的数值区间就是nums[i]到nums[j]，那么其中已经包含的nums元素就有j-i+1个，剩余的“空隙”需要把数值区间外的nums挪过来填充。
 * 我们只需要简单地检查一下nums[j]-nums[i]+1的个数是否小于等于N即可。
 * 是的话，那么这个数值区间一定能填满，也有可能还有多余的元素，我们就将它们继续拼接在区间的右边即可。
 * 对于固定的i，我们能找到最大的j，使得这个区间尽量地大，那么需要从外面拿进来填充的nums自然就会尽量的少了。
 * 然后我们将i右移一位，发现与之匹配的最大的j肯定也是会往右边移动的。所以这就是一个双指针的问题。
 */
