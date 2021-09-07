package LC1501_1800;
import java.util.*;
public class LC1648_SellDiminishingValuedColoredBalls {
    /**
     * You have an inventory of different colored balls, and there is a customer that wants orders balls of any color.
     *
     * The customer weirdly values the colored balls. Each colored ball's value is the number of balls of that color
     * you currently have in your inventory. For example, if you own 6 yellow balls, the customer would pay 6 for the
     * first yellow ball. After the transaction, there are only 5 yellow balls left, so the next yellow ball is then
     * valued at 5 (i.e., the value of the balls decreases as you sell more to the customer).
     *
     * You are given an integer array, inventory, where inventory[i] represents the number of balls of the ith color
     * that you initially own. You are also given an integer orders, which represents the total number of balls that
     * the customer wants. You can sell the balls in any order.
     *
     * Return the maximum total value that you can attain after selling orders colored balls. As the answer may be
     * too large, return it modulo 10^9 + 7
     *
     * Input: inventory = [2,5], orders = 4
     * Output: 14
     *
     * Constraints:
     *
     * 1 <= inventory.length <= 10^5
     * 1 <= inventory[i] <= 10^9
     * 1 <= orders <= min(sum(inventory[i]), 10^9)
     * @param inventory
     * @param orders
     * @return
     */
    // S1: Math + Greedy
    // time = O(n), space = O(logn)
    public int maxProfit(int[] inventory, int orders) {
        // corner case
        if (inventory == null || inventory.length == 0) return 0;

        Arrays.sort(inventory);

        int n = inventory.length;
        long res = 0;
        long M = (long)(1e9 + 7);

        for (int i = n - 1; i >= 0; i--) {
            long a = inventory[i], b = (i == 0 ? 0 : inventory[i - 1]) + 1;
            // 从a砍到b，双闭区间，b应该是nums[i-1]+1,因为从nums[i-1]开始是新的一个回合了。
            long total = (a - b + 1) * (n - i); // n - 1 - i + 1 = n - i

            if (total <= orders) {
                res += (a + b) * (a - b + 1) / 2 * (n - i); // (首项 + 末项) * 个数 / 2 * 球种类数
                orders -= total;
                res %= M;
            } else {
                int k = orders / (n - i); // 轮数
                res += (a + a - k + 1) * k / 2 * (n - i);
                int r = orders % (n - i);
                res += (a - k) * r;
                res %= M;
                break;
            }
            if (orders <= 0) break;
        }
        return (int)res;
    }

    // S1.2: Math + Greedy (第2种写法！）
    public int maxProfit12(int[] inventory, int orders) {
        int n = inventory.length;
        Integer[] nums = new Integer[n + 1];
        for (int i = 0; i < n; i++) nums[i] = inventory[i];
        nums[n] = 0;

        Arrays.sort(nums, (o1, o2) -> o2 - o1);

        long res = 0;
        long M = (long)(1e9 + 7);
        for (int i = 0; i < n; i++) {
            long a = nums[i], b = nums[i + 1] + 1;
            long total = (a - b + 1) * (i + 1);


            if (total <= orders) {
                res += (a + b) * (a - b + 1) / 2 * (i + 1);
                orders -= total;
                res %= M;
            } else {
                int k = orders / (i + 1);
                res += (a + a - k + 1) * k / 2 * (i + 1);
                int r = orders % (i + 1);
                res += (a - k) * r;
                res %= M;
                break;
            }
            if (orders == 0) break;
        }
        return (int)res;
    }

    // S2: BS
    // time = O(nlogn), space = O(logn)
    public int maxProfit2(int[] inventory, int orders) {
        // corner case
        if (inventory == null || inventory.length == 0) return 0;

        int n = inventory.length;
        long res = 0;
        long M = (long)(1e9 + 7);

        Arrays.sort(inventory);

        long left = 1, right = inventory[n - 1];
        while (left < right) {
            long mid = left + (right - left) / 2;
            if (count(inventory, mid) <= orders) right = mid;
            else left = mid + 1;
        }
        long k = left;
        for (int i = n - 1; i >= 0; i--) {
            if (inventory[i] < k) break;
            res += (inventory[i] + k) * (inventory[i] - k + 1) / 2;
            res %= M;
        }
        long r = orders - count(inventory, k);
        res += (k - 1) * r;
        return (int) (res % M);
    }

    private long count(int[] nums, long k) {
        int n = nums.length;
        long M = (long)(1e9 + 7), res = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] < k) break;
            res += nums[i] - k + 1;
        }
        return res;
    }
}
/**
 * 先吃肥的 -> 排序
 * order = 10
 * 10 8 6 4 2
 * 回合1：
 * 9 8 6 4 2   +10
 * 8 8 6 4 2   +9
 * 回合2：
 * 7 7 6 4 2   +8 *2
 * 6 6 6 4 2   +7 *2
 * 回合3：
 * 5 5 5 4 2   +6 *3
 * 4 4 4 4 2   +5 *3
 * 回合4：
 * 3 3 3 3 2   +4 *4
 * 2 2 2 2 2   +3 *4
 * 回合5：
 * 1 1 1 1 1   +2 *5
 * 0 0 0 0 0   +1*5
 * 每次都把最多的砍成跟第二多的相同
 * 注意砍到哪就可以了
 * S2: BS
 * 每一轮取多少球都固定
 * 猜最终我们要恰好到达orders的时候，分值会降到多少
 * 因为每轮我们所取的球的分值都是递减1的，我们可以尝试猜测最后一整轮的球的分值是k，
 * 另外可能还有一些零头的球它们的分值是k-1
 * 我们需要寻找最大的k，使得分值大于等于k的球的总数不超过orders。
 * 对于任何一种颜色inventory[i]，如果inventory[i]>=k，
 * 那么它必然能贡献inventory[i]-k+1个球，其中最大分数是inventory[i]，最小分数是k。
 * 扫描一遍整个数组，我们就能把总球数求出来，与orders比较一下。
 * 如果大于orders，说明取的球太多了，k要提升一下。反之，k就要下降一点。
 * 确定了k之后，我们还要手工计算一下零头的数量是多少，他们每个球贡献的分数是k-1.
 */
