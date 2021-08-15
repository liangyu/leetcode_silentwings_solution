package LC1801_2100;
import java.util.*;
public class LC1942_TheNumberoftheSmallestUnoccupiedChair {
    /**
     * There is a party where n friends numbered from 0 to n - 1 are attending. There is an infinite number of chairs
     * in this party that are numbered from 0 to infinity. When a friend arrives at the party, they sit on the
     * unoccupied chair with the smallest number.
     *
     * For example, if chairs 0, 1, and 5 are occupied when a friend comes, they will sit on chair number 2.
     * When a friend leaves the party, their chair becomes unoccupied at the moment they leave. If another friend
     * arrives at that same moment, they can sit in that chair.
     *
     * You are given a 0-indexed 2D integer array times where times[i] = [arrivali, leavingi], indicating the arrival
     * and leaving times of the ith friend respectively, and an integer targetFriend. All arrival times are distinct.
     *
     * Return the chair number that the friend numbered targetFriend will sit on.
     *
     * Input: times = [[1,4],[2,3],[4,6]], targetFriend = 1
     * Output: 1
     *
     * Constraints:
     *
     * n == times.length
     * 2 <= n <= 10^4
     * times[i].length == 2
     * 1 <= arrivali < leavingi <= 10^5
     * 0 <= targetFriend <= n - 1
     * Each arrivali time is distinct.
     * @param times
     * @param targetFriend
     * @return
     */
    // S1: TreeSet + HashMap
    // time = O(nlogn), space = O(n)
    public int smallestChair(int[][] times, int targetFriend) {
        // corner case
        if (times == null || times.length == 0 || times[0] == null || times[0].length == 0) return -1;

        int n = times.length;
        List<int[]> eps = new ArrayList<>(); // [start/end, isEnd, idx]
        for (int i = 0;i < n; i++) {
            eps.add(new int[]{times[i][0], 0, i});
            eps.add(new int[]{times[i][1], 1, i});
        }

        Collections.sort(eps, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o2[1] - o1[1]); // first end, then start

        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < n; i++) set.add(i);
        HashMap<Integer, Integer> map = new HashMap<>();

        for (int[] ep : eps) {
            int time = ep[0], idx = ep[2];
            if (ep[1] == 0) { // start
                int seat = set.first();
                set.remove(seat);
                map.put(idx, seat);
            } else { // leave
                set.add(map.get(idx));
            }
            if (idx == targetFriend) return map.get(idx);
        }
        return -1;
    }

    // S2: PQ
    // time = O(nlogn), space = O(n)
    public int smallestChair2(int[][] times, int targetFriend) {
        // corner case
        if (times == null || times.length == 0 || times[0] == null || times[0].length == 0) return -1;

        PriorityQueue<Integer> empty = new PriorityQueue<>();
        PriorityQueue<int[]> used = new PriorityQueue<>((o1, o2) -> o1[0] - o2[0]);

        for (int i = 0; i < 10000; i++) empty.offer(i);

        int[][] time = new int[times.length][3];
        for (int i = 0; i < times.length; i++) {
            time[i][0] = times[i][0];
            time[i][1] = times[i][1];
            time[i][2] = i;
        }
        Arrays.sort(time, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]); // {start, end, personId}

        for (int[] t : time) {
            int start = t[0];
            int end = t[1];
            int person = t[2];

            while (!used.isEmpty() && used.peek()[0] <= start) empty.offer(used.poll()[1]);

            int chair = empty.poll();
            if (person == targetFriend) return chair;
            used.offer(new int[]{end, chair});
        }
        return 0;
    }
}
/**
 * 模拟的过程
 * PQ_empty: [0，1，2，3，...10000]
 * 优先分配id小的 -> 放到pq里
 * PQ_used: [{returnTime, ID}, {}, {}]
 */