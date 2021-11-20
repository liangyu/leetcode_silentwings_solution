package LC901_1200;

import java.util.Arrays;
import java.util.HashMap;

public class LC1088_ConfusingNumberII {
    /**
     * A confusing number is a number that when rotated 180 degrees becomes a different number with each digit valid.
     *
     * We can rotate digits of a number by 180 degrees to form new digits.
     *
     * When 0, 1, 6, 8, and 9 are rotated 180 degrees, they become 0, 1, 9, 8, and 6 respectively.
     * When 2, 3, 4, 5, and 7 are rotated 180 degrees, they become invalid.
     * Note that after rotating a number, we can ignore leading zeros.
     *
     * For example, after rotating 8000, we have 0008 which is considered as just 8.
     * Given an integer n, return the number of confusing numbers in the inclusive range [1, n].
     *
     * Input: n = 20
     * Output: 6
     *
     * Constraints:
     *
     * 1 <= n <= 10^9
     * @param n
     * @return
     */
    // S1: Ennumeration
    // time = O(n), space = O(n)
    private int res = 0;
    HashMap<Integer, Integer> map;
    public int confusingNumberII(int n) {
        map = new HashMap<>();
        map.put(0, 0);
        map.put(1, 1);
        map.put(6, 9);
        map.put(8, 8);
        map.put(9, 6);
        helper(0, n);
        return res;
    }

    private void helper(long cur, int n) {
        if (isValid(cur)) res++;

        for (int key : map.keySet()) {
            long num = cur * 10 + key;
            if (num <= n && num != 0) helper(num, n);
        }
    }

    private boolean isValid(long num) {
        int n = (int) num;
        int cur = 0, temp = n;
        while (n > 0) {
            int r = n % 10;
            if (!map.containsKey(r)) return false;
            cur = cur * 10 + map.get(r);
            n /= 10;
        }
        return cur != temp;
    }

    // S2: Math
    // time = O(n), space = O(n)
    private int count = 0, n = 0;
    private String s = "";
    public int confusingNumberII2(int n) {
        s = String.valueOf(n);
        this.n = n;

        for (int i = 1; i < s.length(); i++) {
            count += permutation(i) - symmetric(i);
        }

        for (long x : Arrays.asList(1, 6, 8, 9)) {
            dfs(x, 1);
        }
        return count;
    }

    private long permutation(int x) {
        return (long)(Math.pow(5, x - 1) * 4);
    }

    private long symmetric(int x) {
        if (x % 2 == 0) return (long)(Math.pow(5, x / 2 - 1) * 4);
        return (long)(Math.pow(5, x / 2 - 1) * 4 * 3);
    }

    private void dfs(long cur, int k) {
        if (k == s.length()) {
            if (cur > n) return;
            if (isConfusing(cur)) count++;
            return;
        }

        for (long x : Arrays.asList(0, 1, 6, 8, 9)) {
            dfs(cur * 10 + x, k + 1);
        }
    }

    private boolean isConfusing(long num) {
        int n = (int) num;
        // corner case
        if (n == 0) return false;

        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);
        map.put(1, 1);
        map.put(6, 9);
        map.put(8, 8);
        map.put(9, 6);

        int cur = 0, temp = n;
        while (n > 0) {
            int r = n % 10;
            if (!map.containsKey(r)) return false;
            cur = cur * 10 + map.get(r);
            n /= 10;
        }
        return cur != temp;
    }
}
/**
 * 有一种稍微优化点的方案，就是对于digit位数小于N的confusing number，我们不需要一一列举，而是用数学方法直接表达。
 * 我们知道confusing number只能用0,1,6,8,9这五个数字组数。
 * 先考虑给定这个digit集合能组成多少个长度为len的数？
 * 答案是4*pow(5,len-1)，其中4是考虑到了高位首位不能为零。
 * 然后我们考虑在上述这些数中间有多少non-confusing number呢？
 * Non-confusing number的定义就是翻转之后和原来的数字一样，
 * 也就是说，相对中间位置对称的两个digit需要是翻转对称的（也就是1->1,6->9,8->8,9->6,0->0），
 * 并且如果长度是奇数那么中间一个digit与自己翻转堆成（也就是1->1,8->8,0->0）.
 * 所以对于长度为len的数，non-confusing number的个数的计算方法：
 *         if (len%2==0)
 *             return 4*pow(5,len/2-1);
 *         else
 *             return 4*pow(5,len/2-1)*3;
 * 同理，4表示高位第一位不能是0，所以是四种选择。3表示长度是奇数时，中间一个digit的选择只有三种。
 * 其他位置（从高位第一位到中间一位）的选择是五种。
 * len/2的操作是因为，对于non-confusing number，只要确定了前一般长度的digit，后一半的digit也就确定了。
 * 所以对于长度为len的confusing number，就是上面计算得到的两个结果之差。
 * 同理，所有小于N的长度的len，我们可以这样用数学方法计算长度为len的confusing number的数目。
 * 至于len等于N的长度的情况，我们依然可以采用DFS（也就是递归）的笨办法。最终时间复杂度能够beat 50%.
 */