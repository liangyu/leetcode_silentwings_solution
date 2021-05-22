package LC1801_2100;
import java.util.*;
public class LC1860_IncrementalMemoryLeak {
    /**
     * You are given two integers memory1 and memory2 representing the available memory in bits on two memory sticks.
     * There is currently a faulty program running that consumes an increasing amount of memory every second.
     *
     * At the ith second (starting from 1), i bits of memory are allocated to the stick with more available memory
     * (or from the first memory stick if both have the same available memory). If neither stick has at least i bits
     * of available memory, the program crashes.
     *
     * Return an array containing [crashTime, memory1crash, memory2crash], where crashTime is the time (in seconds)
     * when the program crashed and memory1crash and memory2crash are the available bits of memory in the first and
     * second sticks respectively.
     *
     * Input: memory1 = 2, memory2 = 2
     * Output: [3,1,0]
     *
     * Constraints:
     *
     * 0 <= memory1, memory2 <= 2^31 - 1
     * @param memory1
     * @param memory2
     * @return
     */
    // S1: 最优解！
    // time = O(1), space = O(1)
    public int[] memLeak(int memory1, int memory2) {
        int time = 1;
        while (Math.max(memory1, memory2) >= time) {
            if (memory1 >= memory2) memory1 -= time;
            else memory2 -= time;
            time++;
        }
        return new int[]{time, memory1, memory2};
    }

    // S2: Heap [Test Solution]
    // time = O(1), space = O(1)
    public int[] memLeak2(int memory1, int memory2) {
        int[] res = new int[3];

        int time = 1;
        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[1] == o2[1] ? o1[0] - o2[0] : o2[1] - o1[1]);
        pq.offer(new int[]{1, memory1});
        pq.offer(new int[]{2, memory2});

        while (true) {
            if (time > pq.peek()[1]) break;
            else {
                int[] cur = pq.poll();
                cur[1] -= time;
                time++;
                pq.offer(cur);
            }
        }
        res[0]= time;
        int[] top = pq.poll();
        int[] bot = pq.poll();
        if (top[0] == 1) {
            res[1] = top[1];
            res[2] = bot[1];
        } else {
            res[1] = bot[1];
            res[2] = top[1];
        }
        return res;
    }
}
