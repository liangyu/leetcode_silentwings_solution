package LC601_900;
import java.util.*;
public class LC621_TaskScheduler {
    /**
     * Given a characters array tasks, representing the tasks a CPU needs to do, where each letter represents a
     * different task. Tasks could be done in any order. Each task is done in one unit of time. For each unit of time,
     * the CPU could complete either one task or just be idle.
     *
     * However, there is a non-negative integer n that represents the cooldown period between two same tasks
     * (the same letter in the array), that is that there must be at least n units of time between any two same tasks.
     *
     * Return the least number of units of times that the CPU will take to finish all the given tasks.
     *
     * Input: tasks = ["A","A","A","B","B","B"], n = 2
     * Output: 8
     *
     * Input: tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
     * Output: 16
     *
     * Constraints:
     *
     * 1 <= task.length <= 10^4
     * tasks[i] is upper-case English letter.
     * The integer n is in the range [0, 100].
     * 
     * @param tasks
     * @param n
     * @return
     */
    // S1: PriorityQueue
    // time = O(mlogm), space = O(m)
    public int leastInterval(char[] tasks, int n) {
        // corner case
        if (tasks == null || tasks.length == 0 || n < 0) return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : tasks) map.put(c, map.getOrDefault(c, 0) + 1);

        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);
        for (char key : map.keySet()) pq.offer(map.get(key));

        // 每一轮处理 n + 1 个
        n++;
        int count = 0;
        while (!pq.isEmpty()) {
            int k = Math.min(pq.size(), n);
            List<Integer> temp = new ArrayList<>();
            for (int i = 0; i < k; i++) { // O(n)
                int freq = pq.poll();
                if (freq - 1 > 0) temp.add(freq - 1);
            }
            if (k == n) count += n;
            else if (temp.size() > 0) count += n;
            else count += k;

            for (int x : temp) pq.offer(x);
        }
        return count;
    }

    // S2: Greedy
    // time = O(m), space = O(1)   m: number of tasks
    public int leastInterval2(char[] tasks, int n) {
        // corner case
        if (tasks == null || tasks.length == 0 || n < 0) return 0;

        HashMap<Character, Integer> map = new HashMap<>();
        for (char c : tasks) map.put(c, map.getOrDefault(c, 0) + 1);

        int maxFreq = 0;
        for (char key : map.keySet()) maxFreq = Math.max(maxFreq, map.get(key));

        int res = (maxFreq - 1) * (n + 1);
        int count = 0;
        for (char key : map.keySet()) {
            if (map.get(key) == maxFreq) count++;
        }
        return Math.max(res + count, tasks.length);
    }
}
/**
 * S1: 全模拟 -> 打印具体方案
 * 任务总数 ~ 10^4 用pq可以接受
 * [A B C] [A B C] [A B C] [A B C] [A B X] [A B X]
 * A X X A X X A ...
 * [A B C] [A B D] [A B C] [A D B]
 *
 * A A A A A A
 * B B B B B
 * C C C
 * D D D D
 * E E
 * F
 *
 * A A A A A
 * B B B B
 *
 * [A B X] [A B X] [A B X] [A B X] [A X X]
 * => PriorityQueue
 * 频次高的元素应该尽量紧凑的频率高的去使用它，瓶颈就在这个地方
 * 非常紧凑的排列方式，每一轮取高频的n+1个
 *
 * 永远是每次我们取当前频次最高的n种元素，每种元素取1个，如果队列里没有n种元素，就拿idle来凑
 * 最后一轮不需要加idle -> 看temp，不需要塞回pq
 *
 * S2:
 * A A A A A
 * B B B B C
 * C C D D D
 * E E F
 *
 * A A A A A
 * B B B B X
 * X X X X X
 *
 * 贪心法 => 一开始要保证它们分的足够开，强制分开至少为n
 */
