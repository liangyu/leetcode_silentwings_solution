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
    // S1: PriorityQueue + TreeMap
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

    // S2: PriorityQueue
    // time = O(nlogn), space = O(n)   n: # of intervals
    public int[] minInterval2(int[][] intervals, int[] queries) {
        List<int[]> qs = new ArrayList<>();
        for (int i = 0; i < queries.length; i++) {
            qs.add(new int[]{queries[i], i});
        }

        Collections.sort(qs, (o1, o2) -> o1[0] - o2[0]);
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);

        PriorityQueue<int[]> pq = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);
        int i = 0;
        int[] res = new int[queries.length];
        Arrays.fill(res, -1);

        for (int[] query : qs) {
            int q = query[0];
            int idx = query[1];
            while (i < intervals.length && intervals[i][0] <= q) {
                pq.offer(new int[]{intervals[i][1] - intervals[i][0] + 1, intervals[i][1]});
                i++;
            }
            while (!pq.isEmpty() && pq.peek()[1] < q) pq.poll();
            if (!pq.isEmpty()) res[idx] = pq.peek()[0];
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
 *
 * S2：
 * O(QN) -> LTE
 * offline query 一次性query都告诉你 -> 打乱顺序求解？=> 排序！！！
 * online query 相对较难
 * left <= q
 * 先按照left进行排序 -> 进一步筛选
 * pq: {right, idx}  => pq直接按照duration排序
 * TreeMap: {duration, idx}  操作要与pq同步进行 -> 首元素直接拿出即可
 * => pq：{duration, right} 弹到第一个符合right >= q 鱼龙混杂没关系，出来时check right即可
 * 能这么做的前提条件：扔掉的interval对q1而言不合要求，那肯定对后面的q2也不合要求，因为query是根据从小到大排序的。
 * right >= q
 * min duration
 *
 * summary:
 * offline query => sort query
 * interval => sort + pq 先尝试按照一个属性进行排序，然后满足某个阈值的元素再扔到pq里按照另一个属性排序
 */
