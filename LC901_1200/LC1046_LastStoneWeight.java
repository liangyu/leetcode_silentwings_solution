package LC901_1200;
import java.util.*;
public class LC1046_LastStoneWeight {
    /**
     * You are given an array of integers stones where stones[i] is the weight of the ith stone.
     *
     * We are playing a game with the stones. On each turn, we choose the heaviest two stones and smash them together.
     * Suppose the heaviest two stones have weights x and y with x <= y. The result of this smash is:
     *
     * If x == y, both stones are destroyed, and
     * If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.
     * At the end of the game, there is at most one stone left.
     *
     * Return the smallest possible weight of the left stone. If there are no stones left, return 0.
     *
     * Input: stones = [2,7,4,1,8,1]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= stones.length <= 30
     * 1 <= stones[i] <= 1000
     * @param stones
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (int x : stones) pq.offer(x);
        while (pq.size() > 1) {
            int a = pq.poll(), b = pq.poll();
            if (a == b) continue;
            else if (a >= b) pq.offer(a - b);
            else pq.offer(b - a);
        }
        return pq.isEmpty() ? 0 : pq.peek(); // 注意：[2,2]这样的case,可能导致pq最后为空！！！
    }
}
