package LC301_600;
import java.util.*;
public class LC315_CountofSmallerNumbersAfterSelf {
    /**
     * You are given an integer array nums and you have to return a new counts array. The counts array has the property
     * where counts[i] is the number of smaller elements to the right of nums[i].
     *
     * Input: nums = [5,2,6,1]
     * Output: [2,1,1,0]
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -10^4 <= nums[i] <= 10^4
     * @param nums
     * @return
     */
    // time = O(nlogn), space = O(n)
    public List<Integer> countSmaller(int[] nums) {
        List<Integer> res = new ArrayList<>();
        // corner case
        if (nums == null || nums.length == 0) return res;

        int n = nums.length;
        int[] sorted = nums.clone();
        int[] ans = new int[n];
        helper(nums, sorted, 0, n - 1, ans);
        for (int num : ans) res.add(num);
        return res;
    }

    private void helper(int[] nums, int[] sorted, int a, int b, int[] ans) {
        if (a >= b) return;

        int mid = a + (b - a) / 2;
        helper(nums, sorted, a, mid, ans);
        helper(nums, sorted, mid + 1, b, ans);

        for (int i = a; i <= mid; i++) {
            int idx = findIndex(sorted, mid + 1, b, nums[i]);
            ans[i] += idx - (mid + 1);
        }
//        Arrays.sort(sorted, a, b + 1);

        // merge sort
        int[] temp = new int[b - a + 1];
        int i = a, j = mid + 1, p = 0;
        while (i <= mid && j <= b) {
            if (sorted[i] <= sorted[j]) {
                temp[p] = sorted[i];
                i++;
            } else {
                temp[p] = sorted[j];
                j++;
            }
        }
        while (i <= mid) {
            temp[p] = sorted[i];
            i++;
            p++;
        }
        while (j <= b) {
            temp[p] = sorted[j];
            j++;
            p++;
        }

        for (i = 0; i < b - a + 1; i++) {
            sorted[a + i] = temp[i];
        }
    }

    private int findIndex(int[] sorted, int left, int right, int target) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (sorted[mid] < target) left = mid + 1;
            else right = mid;
        }
        return left;
    }
}
/**
 * ref: LC1649: 翻译过来就是count smaller number before self 与该题几乎一致
 * 都可以用分治来做，用处并不是特别大，思想很经典
 * 分治法一般会和归并排序放在一起用
 * A: [y y y y y z z z z z]
 * B: [y y y y y] C:[z z z z z] + ...
 *     a      mid   mid+1    b
 * 固定一个y，z扫一遍 => C是有序的话，可以用二分法提高效率
 * 分治 + 归并排序 -> 小问题 解决 大问题，返回到上一级，也把它搞成有序
 * 先拆分分解递归，返回之后要搞成有序，这样再往上传递方便解决更大的问题 -> 归并排序搞成有序的
 * 有点像递归，最大特色是返回的时候需要额外做一个排序
 */
