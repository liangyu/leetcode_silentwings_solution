package LC1801_2100;
import java.util.*;
public class LC1921_EliminateMaximumNumberofMonsters {
    /**
     * You are playing a video game where you are defending your city from a group of n monsters. You are given a
     * 0-indexed integer array dist of size n, where dist[i] is the initial distance in meters of the ith monster from
     * the city.
     *
     * The monsters walk toward the city at a constant speed. The speed of each monster is given to you in an integer
     * array speed of size n, where speed[i] is the speed of the ith monster in meters per minute.
     *
     * The monsters start moving at minute 0. You have a weapon that you can choose to use at the start of every minute,
     * including minute 0. You cannot use the weapon in the middle of a minute. The weapon can eliminate any monster
     * that is still alive. You lose when any monster reaches your city. If a monster reaches the city exactly at the
     * start of a minute, it counts as a loss, and the game ends before you can use your weapon in that minute.
     *
     * Return the maximum number of monsters that you can eliminate before you lose, or n if you can eliminate all the
     * monsters before they reach the city.
     *
     * Input: dist = [1,3,4], speed = [1,1,1]
     * Output: 3
     *
     * Constraints:
     *
     * n == dist.length == speed.length
     * 1 <= n <= 10^5
     * 1 <= dist[i], speed[i] <= 10^5
     * @param dist
     * @param speed
     * @return
     */
    // S1: PQ
    // time = O(nlogn), space = O(n)
    public int eliminateMaximum(int[] dist, int[] speed) {
        // corner case
        if (dist == null || dist.length == 0 || speed == null || speed.length == 0) return 0;

        int n = dist.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int i = 0; i < n; i++) {
            int arrTime = dist[i] % speed[i] > 0 ? dist[i] / speed[i] + 1 : dist[i] / speed[i];
            pq.offer(arrTime);
        }

        int t = 0, count = 0;
        while (!pq.isEmpty()) {
            if (pq.peek() > t) {
                pq.poll();
                count++;
            } else break;
            t++;
        }
        return count;
    }

    // S2: sort (最优解！！！)
    // time = O(nlogn), space = O(1)
    public int eliminateMaximum2(int[] dist, int[] speed) {
        // corner case
        if (dist == null || dist.length == 0 || speed == null || speed.length == 0) return 0;

        int n = dist.length;
        for (int i = 0; i < n; i++) {
            dist[i] = (dist[i] - 1) / speed[i];
        }
        Arrays.sort(dist);
        for (int i = 0; i < n; i++) {
            if (i > dist[i]) return i;
        }
        return n;
    }
}
