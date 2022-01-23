package LC901_1200;
import java.util.*;
public class LC1015_SmallestIntegerDivisiblebyK {
    /**
     * Given a positive integer k, you need to find the length of the smallest positive integer n such that n is
     * divisible by k, and n only contains the digit 1.
     *
     * Return the length of n. If there is no such n, return -1.
     *
     * Note: n may not fit in a 64-bit signed integer.
     *
     * Input: k = 1
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= k <= 10^5
     * @param k
     * @return
     */
    // S1: HashSet
    // time = O(k), space = O(k)
    public int smallestRepunitDivByK(int k) {
        int n = 1, count = 1;
        if (k % 2 == 0 || k % 5 == 0) return -1;
        HashSet<Integer> set = new HashSet<>();
        while (true) {
            int r = n % k;
            if (r == 0) return count;
            if (!set.add(r)) break;
            n = n % k * 10 + 1; // r1 = 10r1 + 1
            count++;
        }
        return -1;
    }

    // S2
    // time = O(k), space = O(1)
    public int smallestRepunitDivByK2(int k) {
        int r = 0;
        if (k % 2 == 0 || k % 5 == 0) return -1;

        for (int i = 1; i <= k; i++) {
            r = (r * 10 + 1) % k;
            if (r == 0) return i;
        }
        return -1;
    }
}
/**
 * 1, 11, 111, 1111, ....
 * x1 = k * q + r1
 * x2 = 10 * x1 + 1 = k * 10q + 10r1 + 1 = r2(mod k)
 * x3 = 10 * x2 + 1 = 10r2 + 1 = r3 (mod k)
 * 只关心余数的话，不会有溢出的问题
 * xk   rk 同余
 * after k次，出现k+1次时，会出现循环重复，新的一轮循环开始 (抽屉原理)
 * 要么整除，要么循环，永远除不尽 => return -1
 * 小数除法，循环节，无限循环小数
 * 有一个快捷的办法判断肯定不能被整除。
 * k % 2 == 0 || k % 5 == 0
 * x1,x2,x3,...xi,...,xj,...
 * r1,r2,r3,...ri,...,rj,...
 * (10*ri+1)%k = (10*rj+1)%k
 * 只差整数个k倍
 * 10*ri+1 + ak = 10*rj+1
 * (rj-ri) = ak/10  什么时候有整数解呢？   => 0 < a < 10
 *
 * Let's say the final result has N digits of 1.
 * If N exist, N <= K, just do a brute force check.
 * Also if K % 2 == 0, return -1, because 111....11 can't be even.
 * Also if K % 5 == 0, return -1, because 111....11 can't end with 0 or 5.
 *
 * Why 5 is a corner case? It has a reason and we can find it.
 * Assume that N = 1 to N = K, if there isn't 111...11 % K == 0
 * There are at most K - 1 different remainders: 1, 2, .... K - 1.
 *
 * So this is a pigeon holes problem:
 * There must be at least 2 same remainders.
 */
