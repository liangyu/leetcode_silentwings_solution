package LC1801_2100;
import java.util.*;
public class LC1994_TheNumberofGoodSubsets {
    /**
     * You are given an integer array nums. We call a subset of nums good if its product can be represented as a product
     * of one or more distinct prime numbers.
     *
     * For example, if nums = [1, 2, 3, 4]:
     * [2, 3], [1, 2, 3], and [1, 3] are good subsets with products 6 = 2*3, 6 = 2*3, and 3 = 3 respectively.
     * [1, 4] and [4] are not good subsets with products 4 = 2*2 and 4 = 2*2 respectively.
     * Return the number of different good subsets in nums modulo 109 + 7.
     *
     * A subset of nums is any array that can be obtained by deleting some (possibly none or all) elements from nums.
     * Two subsets are different if and only if the chosen indices to delete are different.
     *
     * Input: nums = [1,2,3,4]
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 1 <= nums[i] <= 30
     * @param nums
     * @return
     */
    // time = O(n * 2^10), space = O(2^10 + n)
    private int[] primes = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29};
    public int numberOfGoodSubsets(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        long M = (long)(1e9 + 7);

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int x : nums) map.put(x, map.getOrDefault(x, 0) + 1);

        int n = primes.length;
        long[] dp = new long[1 << n];
        dp[0] = 1; // 空集的个数也是1个，把这个作为种子，将dp推动起来

        for (int key : map.keySet()) {
            int num = key, count = map.get(key);

            if (num == 1) continue; // 先把1丢掉
            int encode = encoding(num); // check encode是否是state的一个子集
            if (encode == -1) continue; // 4, 9 这类就标记下为-1，就不往下走了
            for (int state = (1 << n) - 1; state >= 1; state--) {
                if ((encode & state) == encode) { // 判断encode是否为state的一个子集的方法！！！
                    dp[state] += dp[state - encode] * count;
                    dp[state] %= M;
                }
            }
        }
        long res = 0;
        for (int state = 1; state < (1 << n); state++) {
            res = (res + dp[state]) % M;
        }

        long power2 = 1;
        for (int i = 0; i < map.getOrDefault(1, 0); i++) {
            power2 = power2 * 2 % M;
        }
        return (int)(res * power2 % M);
    }

    private int encoding(int num) {
        int encode = 0;
        for (int i = 0; i < primes.length; i++) {
            if (num % primes[i] == 0) { // 包含
                encode += (1 << i);
                num /= primes[i];
            }
            if (num % primes[i] == 0) return -1;
        }
        return encode;
    }
}
/**
 * nums[i] <= 30  -> 有大量重复的元素在里面
 * 挑选的subset里乘积不能有相同的质因数
 * 挑的不能有重复的元素
 * [1 6 7 5 6 8]  1除外，都不影响subset的合法性 -> 暂时先把1丢掉，最后特殊处理
 * good subset最多也就30个元素 [1 2 3 4 5 6 ... 28 29 30]
 * 2^30 ~ 10^9 搞不定
 * 4包含2个2，9包含3*3，这些都是不能放在集合里的，可以先把4,9,16,25等这些丢掉
 * => [2 3 5 6 7 10 11 13 14 15 19 21 22 23 26 29 30]
 * good subset形式就是这18个数的combination
 * 2^18 = 10^6 / 2^2 = 25 * 10^4 = 2 * 10^5 都可以枚举一遍
 * 如果nums里没有出现比如5之类的，还能去掉，范围更小
 * 穷举 => bitmask
 * for (int state = 1; state < (1 << n); state++) {  // 10100010100001  代表combination的种类  O(2^n)
 *      if (checkOK(state)) { // O(n^2)
 *          res += count[2] * count[7] * count[15] * count[22];
 *      }
 * }
 * 构造同一种subset可能有好几个不同的方法，因为里面有重复元素，比如2有5个，就表示有5种选2的方法
 * 并不是每个state都是一个合法的集合
 * 比如，选中3，11，15，22，这里质因数3重复了2次，所以要过滤掉一些state
 * 怎么checkOK呢？只能两两组合配对，看两者是否互质。
 * 1还没考虑到，因为1出现多少次，甚至不出现都可以。
 * 以上所有subset都不包括1，所以可以在此基础上添加1
 * return res * 2^count[1];
 * => O(2^18 * 18^2) = 8 * 10^7  暴力枚举，用bitmask来穷举
 *
 * S2: 也用到bitmask
 * 另外一种方法，你的集合里有哪些质因数，质因数比18更少
 * state: [2 3 5 7 11 13 17 19 23 29]
 *         ^   ^      ^
 * 任何一种subset对应的是里面的一种组合，同时也是对应于上面质因数的一个组合
 * 放眼的是prime，因为定义的就是没有重复的质因数
 * 2^10 = 1024，这样穷举的话效率就高了
 * dp[state]: 对应这种subset有多少个
 * {10,13}
 * {2,5,13}
 * 有点背包的感觉，把每个num一个个拿出来看
 * 意味着state里对应的num是要瓜分这个质因数的
 * 有点像子集，裂解这个子集
 * 看这个num是否能贡献给state -> num所对应的质因数分解正好对应state里的一个子集
 * 比如遍历到一个num = 10 = 2 * 5
 * dp[{2,5,7,13}] => dp[{7,13}] * count[10];
 * for (int num : nums) { // 过滤下num，数值只遍历一次，但要统计下有多少个num
 *     for (int state = (1 << n) - 1; state >= 1; state--) {
 *         if (num is a subset of state) {
 *             dp[state] += dp[state - subset] * count[num]  // 有多种裂解方式
 *         }
 *     }
 * }
 * res = sum{dp[state]};
 * return res * 2^count[1];
 * 为什么从大到小来遍历呢？
 * 因为num对所有dp[state]集合的影响
 * for (int num : nums) {
 *     int dp_new[];
 *     for (int state = 0; state < (1 << n); state++) {
 *         dp_new[state] += dp[state - subset]
 *     }
 *     dp = dp_new;
 * }
 * time = O(30 * 10^20) = 30720 = 10^4
 */