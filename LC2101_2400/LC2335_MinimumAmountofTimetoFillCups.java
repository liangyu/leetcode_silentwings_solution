package LC2101_2400;
import java.util.*;
public class LC2335_MinimumAmountofTimetoFillCups {
    /**
     * You have a water dispenser that can dispense cold, warm, and hot water. Every second, you can either fill up 2
     * cups with different types of water, or 1 cup of any type of water.
     *
     * You are given a 0-indexed integer array amount of length 3 where amount[0], amount[1], and amount[2] denote the
     * number of cold, warm, and hot water cups you need to fill respectively. Return the minimum number of seconds
     * needed to fill up all the cups.
     *
     * Input: amount = [1,4,2]
     * Output: 4
     *
     * Constraints:
     *
     * amount.length == 3
     * 0 <= amount[i] <= 100
     * @param amount
     * @return
     */
    // S1: Greedy
    // time = O(1), space = O(1)
    public int fillCups(int[] amount) {
        Arrays.sort(amount);
        if (amount[2] > amount[0] + amount[1]) return amount[2];
        return (amount[0] + amount[1] + amount[2] + 1) / 2;
    }

    // S2: PQ
    // time = O(1), space = O(1)
    public int fillCups2(int[] amount) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        int count = 0;
        for (int x : amount) {
            if (x > 0) pq.offer(x);
        }
        while (pq.size() > 1) {
            int a = pq.poll();
            int b = pq.poll();
            count++;
            a--;
            b--;
            if (a != 0) pq.offer(a);
            if (b != 0) pq.offer(b);
        }
        while (!pq.isEmpty()) count += pq.poll();
        return count;
    }

    // S3
    // time = O(1), space = O(1)
    public int fillCups3(int[] amount) {
        int res = 0;
        while (amount[0] + amount[1] + amount[2] > 0) {
            Arrays.sort(amount);
            amount[2]--;
            if (amount[1] > 0) amount[1]--;
            res++;
        }
        return res;
    }
}
/**
 * a >= b >= c
 * 1. a > b + c => 操作a次,一定可以把b和c都带掉
 * 2. a <= b + c => 每次必然可以操作2个数 [(a+b+c)/2] 上取整
 * (1) a-1, b-1, c => a-1 >= b - 1 + c  因为 a >= b + c
 * (2) a-1,b-1,c => a-1 == b-1 只能是一开始都是c c c
 * [a/b]上取整 = [(a+b-1)/b]下取整
 * => x/2上取整 = (x+2-1)/2下取整 = (x+1)/2下取整
 * if a == kb => k == k
 * if a = kb + r  (1 <= r <= b - 1)  => b <= r + b - 1 <= 2b - 2
 * k + 1 == k + 1
 */