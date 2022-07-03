package LC2101_2400;
import java.util.*;
public class LC2289_StepstoMakeArrayNondecreasing {
    /**
     * You are given a 0-indexed integer array nums. In one step, remove all elements nums[i] where
     * nums[i - 1] > nums[i] for all 0 < i < nums.length.
     *
     * Return the number of steps performed until nums becomes a non-decreasing array.
     *
     * Input: nums = [5,3,4,4,7,3,6,11,8,5,11]
     * Output: 3
     *
     * Input: nums = [4,5,7,7,13]
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 10^9
     * @param nums
     * @return
     */
    // S1: Monotonic Stack
    // time = O(n), space = O(n)
    public int totalSteps(int[] nums) {
        int n = nums.length, res = 0;
        Stack<int[]> stack = new Stack<>();
        for (int i = n - 1; i >= 0; i--) {
            if (stack.isEmpty() || stack.peek()[0] >= nums[i]) stack.push(new int[]{nums[i], 0});
            else {
                int count = 0;
                while (!stack.isEmpty() && stack.peek()[0] < nums[i]) {
                    count++;
                    int[] x = stack.pop();
                    if (count < x[1]) count = x[1];
                }
                stack.push(new int[]{nums[i], count});
                res = Math.max(res, count);
            }
        }
        return res;
    }
}
/**
 * i-1,i,i+1,i+2,...,j-1,j
 *     x  x   x       x  o
 * 下一个回合，可能被叉掉的是j
 * 意味着在当前回合里没叉掉一个元素，有可能在下一回合叉掉右边另一个元素
 * 找到一个高效的办法，从i跳到j => 从右往左挨个判断
 * 用链表 => 最大的好处，中间删掉后，next(i) = j, prev(i) = i - 1
 * O(1)删除
 * O(1)查找 => List + Hash<idx :: iterator>  => LRU Cache
 * List: {0,1,2,3,...,n-1}
 * Hash: idx -> list :: iterator
 * i => nums[*prev(Hash[i])] > nums[i]
 * j = *next(Hash[i])
 * List.erase(Hash[i])
 */