package LC901_1200;
import java.util.*;
public class LC962_MaximumWidthRamp {
    /**
     * A ramp in an integer array nums is a pair (i, j) for which i < j and nums[i] <= nums[j]. The width of such a
     * ramp is j - i.
     *
     * Given an integer array nums, return the maximum width of a ramp in nums. If there is no ramp in nums, return 0.
     *
     * Input: nums = [6,0,8,2,1,5]
     * Output: 4
     *
     * Constraints:
     *
     * 2 <= nums.length <= 5 * 10^4
     * 0 <= nums[i] <= 5 * 10^4
     * @param nums
     * @return
     */
    // S1: B.S.
    // time = O(nlogn), space = O(n)
    public int maxWidthRamp(int[] nums) {
        TreeSet<int[]> set = new TreeSet<>((o1, o2) -> o1[0] - o2[0]);
        int n = nums.length, res = 0;

        for (int i = 0; i < n; i++) {
            if (set.size() == 0 || set.first()[0] > nums[i]) set.add(new int[]{nums[i], i});
            else {
                int[] x = set.floor(new int[]{nums[i], i});
                if (x != null) res = Math.max(res, i - x[1]);
            }
        }
        return res;
    }

    // S2: Stack (optimal Solution)
    // time = O(n), space = O(n)
    public int maxWidthRamp2(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int n = nums.length, res = 0;

        for (int i = 0; i < n; i++) { // 维护一个单调递减的栈
            if (stack.isEmpty() || nums[stack.peek()] > nums[i]) stack.push(i);
        }

        for (int j = n - 1; j >= 0; j--) {  // 再遍历一遍，寻找最大值
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[j]) res = Math.max(res, j - stack.pop());
        }
        return res;
    }
}
/**
 * [x x x x x] j
 * 1 2 3 4 5 6   7
 * [6 5 4 3 2 1] 4  -> 维持一个递减的序列
 *      ^
 * [6 5 4] 5   与4比起来，5毫无优势，不需要加入；如果是3，把3放入
 * [6 5 4 3] j  => O(nlogn)
 * O(n)解法：构造一个单调递减的序列
 * [6,0,8,2,1,5] -> [6,0]
 */