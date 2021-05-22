package LC1801_2100;
import java.util.*;
public class LC1851_MinimumIntervaltoIncludeEachQuery {
    /**
     * You are given a 2D integer array intervals, where intervals[i] = [lefti, righti] describes the ith interval
     * starting at lefti and ending at righti (inclusive). The size of an interval is defined as the number of integers
     * it contains, or more formally righti - lefti + 1.
     *
     * You are also given an integer array queries. The answer to the jth query is the size of the smallest interval i
     * such that lefti <= queries[j] <= righti. If no such interval exists, the answer is -1.
     *
     * Return an array containing the answers to the queries.
     *
     * Input: intervals = [[1,4],[2,4],[3,6],[4,4]], queries = [2,3,4,5]
     * Output: [3,3,1,4]
     *
     * Constraints:
     *
     * 1 <= intervals.length <= 10^5
     * 1 <= queries.length <= 10^5
     * queries[i].length == 2
     * 1 <= lefti <= righti <= 10^7
     * 1 <= queries[j] <= 10^7
     * @param intervals
     * @param queries
     * @return
     */
    // time = O((m + n) * logn + mlogm), space = O(n)   m: number of queries, n: number of intervals
    public int[] minInterval(int[][] intervals, int[] queries) {
        int[][] que = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            que[i][0] = queries[i];
            que[i][1] = i;
        }
        Arrays.sort(que, (o1, o2) -> o1[0] - o2[0]); // O(mlogm)
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]); // O(nlogn)

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        TreeMap<Integer, Integer> map = new TreeMap<>();

        int i = 0;
        int[] res = new int[que.length];
        for (int[] q : que) { // O(m)
            while (i < intervals.length && intervals[i][0] <= q[0]) { // O(n)
                pq.offer(new int[]{intervals[i][1], intervals[i][1] - intervals[i][0] + 1}); // O(logn)
                map.put(intervals[i][1] - intervals[i][0] + 1, map.getOrDefault(intervals[i][1] - intervals[i][0] + 1, 0) + 1);
                i++;
            }
            while (pq.size() > 0 && pq.peek()[0] < q[0]) {
                int val = pq.peek()[1];
                map.put(val, map.get(val) - 1);
                if (map.get(val) == 0) map.remove(val);
                pq.poll();
            }
            if (map.size() > 0) {
                res[q[1]] = map.firstKey();
            } else res[q[1]] = -1;
        }
        return res;
    }
}
/**
 * [start, end]
 * add: start <= q
 * pop: end < q -> 只会入和出pool一次，弹出来end比较早的，就再也不会用到了
 * 按照end的时间从早到晚优先队列排序，没有过期就不扔，按照end来排序
 * O(Q + nlogn) = O(nlogn) n: interval的个数
 * 那如何知道duration最小呢？ => 再设计一个数据结构,treeSet，也加interval跟pq更新同步，但按照duration排序
 * offline querying + trie / Union Find / Set / PQ + Set
 */
