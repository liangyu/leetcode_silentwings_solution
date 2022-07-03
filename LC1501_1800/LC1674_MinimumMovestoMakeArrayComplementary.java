package LC1501_1800;
import java.util.*;
public class LC1674_MinimumMovestoMakeArrayComplementary {
    /**
     * You are given an integer array nums of even length n and an integer limit. In one move, you can replace any
     * integer from nums with another integer between 1 and limit, inclusive.
     *
     * The array nums is complementary if for all indices i (0-indexed), nums[i] + nums[n - 1 - i] equals the same
     * number. For example, the array [1,2,3,4] is complementary because for all indices i, nums[i] + nums[n - 1 - i] = 5.
     *
     * Return the minimum number of moves required to make nums complementary.
     *
     * Input: nums = [1,2,4,3], limit = 4
     * Output: 1
     *
     * Constraints:
     *
     * n == nums.length
     * 2 <= n <= 10^5
     * 1 <= nums[i] <= limit <= 10^5
     * n is even.
     * @param nums
     * @param limit
     * @return
     */
    // S1: diff array
    // time = O(max(n, C)), space = O(C)  C: limit
    public int minMoves(int[] nums, int limit) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int[] diff = new int[2 * limit + 2];

        int n = nums.length;
        for (int i = 0; i < n / 2; i++) {
            int a = Math.min(nums[i], nums[n - 1 - i]);
            int b = Math.max(nums[i], nums[n - 1 - i]);

            diff[2] += 2;
            diff[a + 1]--;
            diff[a + b]--;
            diff[a + b + 1]++;
            diff[b + 1 + limit]++;
        }

        int y = 0, res = Integer.MAX_VALUE;
        for (int x = 2; x <= limit * 2; x++) {
            y += diff[x];
            res = Math.min(res, y);
        }
        return res;
    }

    // S2: TreeMap
    // time = O(nlogn), space = O(n)   当limit比较大的时候，就只能用这种做法!!!
    public int minMoves2(int[] nums, int limit) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        TreeMap<Integer, Integer> diff = new TreeMap<>();
        for (int i = 0; i < n / 2; i++) {
            int a = Math.min(nums[i], nums[n - 1 - i]);
            int b = Math.max(nums[i], nums[n - 1 - i]);

            diff.put(2, diff.getOrDefault(2, 0) + 2);
            diff.put(a + 1, diff.getOrDefault(a + 1, 0) - 1);
            diff.put(a + b, diff.getOrDefault(a + b, 0) - 1);
            diff.put(a + b + 1, diff.getOrDefault(a + b + 1, 0) + 1);
            diff.put(limit + b + 1, diff.getOrDefault(limit + b + 1, 0) + 1);
        }

        int y = 0, res = Integer.MAX_VALUE;
        for (int key : diff.keySet()) {
            y += diff.get(key);
            res = Math.min(y, res);
        }
        return res;
    }
}
/**
 * 1. what is the ultimate pair sum x? [2, 2 * 10^5] => O(nlogn) ? => loop一遍,暴力搜索
 * 2. how many moves required to make each pair sum equal to x?
 * nums[i] + nums[n-1-i] => x
 *     a   <   b
 * 要多少次操作变成x呢？可以做0次，1次和2次move
 * 0 move: a + b
 * 1 move: [a+b+1,limit+b]
 * 2 move: [limit+b+1,2*limit]
 *
 * 1 move: [a+1,a+b-1] 较大元素替换掉
 * 2 move: [2,a]
 *
 * 把所有段的曲线都加起来，得到一个总的曲线f(x)
 * 真正有用的是仅有的几个拐点 => 并不需要所有的10^5 * 10^5 => 差分
 * y = 2
 * default diff = 0
 * diff[2] = 2
 * diff[a+1] -= 1, y += diff[a+b+1]
 * diff[a+b] -= 1
 * diff[a+b+1] += 1
 * diff[limit+b+1] += 1
 * diff对一个前缀和进行增增减减
 *
 * 这里diff是根据limit来开，如果limit非常大，比如1e9该怎么做？
 * 事实上如果limit非常大，也可以做，因为大多数diff[x]都是0 => 只需要存那些非0的diff[x]，不用每个x都遍历，只要从小到大遍历是拐点的x
 * => 不用开数组了，开一个HashMap，只存那些有拐点的x，但考虑要排序 => 用TreeMap,遍历key-val
 */