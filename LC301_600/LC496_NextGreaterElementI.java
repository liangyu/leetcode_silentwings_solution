package LC301_600;
import java.util.*;
public class LC496_NextGreaterElementI {
    /**
     * The next greater element of some element x in an array is the first greater element that is to the right of x in
     * the same array.
     *
     * You are given two distinct 0-indexed integer arrays nums1 and nums2, where nums1 is a subset of nums2.
     *
     * For each 0 <= i < nums1.length, find the index j such that nums1[i] == nums2[j] and determine the next greater
     * element of nums2[j] in nums2. If there is no next greater element, then the answer for this query is -1.
     *
     * Return an array ans of length nums1.length such that ans[i] is the next greater element as described above.
     *
     * Input: nums1 = [4,1,2], nums2 = [1,3,4,2]
     * Output: [-1,3,-1]
     *
     * Constraints:
     *
     * 1 <= nums1.length <= nums2.length <= 1000
     * 0 <= nums1[i], nums2[i] <= 10^4
     * All integers in nums1 and nums2 are unique.
     * All the integers of nums1 also appear in nums2.
     *
     *
     * Follow up: Could you find an O(nums1.length + nums2.length) solution?
     * @param nums1
     * @param nums2
     * @return
     */
    // time = O(n), space = O(n)
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();

        int m = nums1.length, n = nums2.length;
        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && nums2[stack.peek()] < nums2[i]) {
                map.put(nums2[stack.pop()], nums2[i]);
            }
            stack.push(i);
        }

        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            res[i] = map.getOrDefault(nums1[i], -1);
        }
        return res;
    }
}
/**
 * 如何用单调栈来解题
 * 其实一个数组就可以了。
 * 每个元素都可以找或者找不到
 * naive解法: O(n^2)
 * 5 4 3 2 1 4  -> 5 4 4 3 2 1 6 -> 6 7 -> 7
 * 保持单调递减
 * follow-up: next prev greater element
 * 1 2 3 4 5 2 x
 * 5 4 3 2 1 4
 * next smaller element
 * prev smaller element
 * 每个元素最多只会遍历2次
 * 维护一个单调递减的栈，即当nums[i]小于栈顶元素时就不断入栈，这个时候的nums[i]是不存在next greater element的。
 * 当发现nums[i]大于栈顶元素时，说明nums[i]就是此栈顶元素所遇到的第一个greater number，
 * 把这个信息记录在一个Hash Map里，然后把栈顶元素退栈；
 * 重复上述操作直至nums[i]小于栈顶元素，再将其入栈并继续遍历nums。
 * follow-up: next smaller -> 单调递增栈
 * previous greater -> 从后往前扫，一模一样的算法
 */