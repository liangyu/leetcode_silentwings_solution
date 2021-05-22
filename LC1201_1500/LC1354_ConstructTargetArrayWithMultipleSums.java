package LC1201_1500;
import java.util.*;
public class LC1354_ConstructTargetArrayWithMultipleSums {
    /**
     * Given an array of integers target. From a starting array, A consisting of all 1's, you may perform the following
     * procedure :
     *
     * let x be the sum of all elements currently in your array.
     * choose index i, such that 0 <= i < target.size and set the value of A at index i to x.
     * You may repeat this procedure as many times as needed.
     * Return True if it is possible to construct the target array from A otherwise return False.
     *
     * Input: target = [9,3,5]
     * Output: true
     *
     * Constraints:
     *
     * N == target.length
     * 1 <= target.length <= 5 * 10^4
     * 1 <= target[i] <= 10^9
     * @param target
     * @return
     */
    // time = O(n + klogn), space = O(n) k: the maximum of the target array
    // The k comes from the cost of reducing the largest item. In the worst case, it is massive.
    // For example, consider a target of [1, 1_000_000_000]. One step of the algorithm will reduce it to [1, 999_999_999].
    // And the next is [1, 999_999_998].
    public boolean isPossible(int[] target) {
        // corner case
        if (target == null || target.length == 0) return false;

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int t : target) maxHeap.offer(t);

        // 10^9 -> sum may go to 10^10 overflow of Integer => long
        long sum = 0;
        for (int t : target) sum += t;

        while (maxHeap.peek() != 1) {
            long a = maxHeap.poll();
            long others = sum - a;

            if (others == 0 || a <= others) return false; // 注意： a <= others (==)也是可以的，那时b = 0，也是不行的，会进入死循环
            long b = a % others;

            sum = others + b;
            maxHeap.offer((int)b);
        }
        return true;
    }
}
/**
 * 突破口：最后一步是把什么替换成了最大元素 -> 前面一轮这里是什么东西
 * last round:     x x x x a
 * last-1 round:   x x x x b
 * last-2 round: 找到最大值，用最大值 - others 就能得到上一轮
 * a = x + x + x + x + b
 * a 已知，x x x x也都是知道的，所以b就知道了 => b = a - (x + x + x + x)
 * 确定性的倒推问题
 *
 * TLE
 * [1, 1000000000]
 * [1, 999999999]
 * [1, 999999998]
 * ...... 搞10^9次 => TLE
 * [1, 1]
 * 规律：特别大，others没有变化，永远减掉的都是前面这一堆的和 => 与其做那么多次，不如做一次除法
 * 如果发现b比others大，就直接 b = a % others算它的余数就可以了
 * => b <= 0 不可能出现
 * 1. others == 0 -> return false;
 */
