package LC2101_2400;

public class LC2202_MaximizetheTopmostElementAfterKMoves {
    /**
     * You are given a 0-indexed integer array nums representing the contents of a pile, where nums[0] is the topmost
     * element of the pile.
     *
     * In one move, you can perform either of the following:
     *
     * If the pile is not empty, remove the topmost element of the pile.
     * If there are one or more removed elements, add any one of them back onto the pile. This element becomes the new
     * topmost element.
     * You are also given an integer k, which denotes the total number of moves to be made.
     *
     * Return the maximum value of the topmost element of the pile possible after exactly k moves. In case it is not
     * possible to obtain a non-empty pile after k moves, return -1.
     *
     * Input: nums = [5,2,2,4,0,6], k = 4
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i], k <= 10^9
     * @param nums
     * @param k
     * @return
     */
    // S1: Greedy
    // time = O(n), space = O(1)
    public int maximumTop(int[] nums, int k) {
        int n = nums.length;
        if (n == 1 && k % 2 == 1) return -1; // 只有一个元素时，奇数次一定拿空

        int max = -1;
        // 注意：这里是到k-1，留1次要么把max放到top，要么就把第k-1个移走使第k个成为top(如果k < n存在第k个)
        for (int i = 0; i < Math.min(k - 1, n); i++) {
            max = Math.max(max, nums[i]);
        }
        if (k < n) max = Math.max(max, nums[k]);
        return max;
    }

    // S2: Constructive
    // time = O(n), space = O(1)
    public int maximumTop2(int[] nums, int k) {
        int n = nums.length, res= Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) { // 1-index
            if (k < i - 1) continue;
            int diff = k - (i - 1);

            boolean flag = false;
            if (diff == 0) flag = true;
            else if (diff == 1) flag = false;
            else if (diff % 2 == 0) flag = true;
            else if (diff % 2 == 1 && i + 1 <= n) flag = true;
            else if (diff % 2 == 1 && i - 1 >= 1) flag = true;

            if (flag) res = Math.max(res, nums[i - 1]);
        }
        return res == Integer.MIN_VALUE ? -1 : res;
    }
}
/**
 * x x x x x x x
 * 跳着看效率并不高
 * 对任何一个元素的判定，都只要O(1)时间就可以实现
 * x x i x x x x
 * RARARRRA  考虑这些操作的顺序是否可以打乱
 * 只要保证每个操作都是有效的，先remove和后remove是没有关系的
 * add取决于前面remove了多少次
 * 要把i暴露在最顶端，前面i-1一定会被remove
 * k -= i - 1; // 一定要去掉i - 1个
 * 能否让i保持在队列顶端？
 * 如果 k = 0 => ok
 * k = 1 => 没法使i暴露在第一个 => not ok
 * k = even => remove再add回来 RARARA => ok
 * k = odd => ?  remove掉2个，再把i加回来 k = 3 / 3 + 2 / ... => ok ? => 一定要存在i + 1个元素
 * k = odd && i + 1 <= n => ok ?
 * __ i => ___ => __x => _ix  i就留在了顶端
 * k == odd && i - 1 >= 1 => ok
 */