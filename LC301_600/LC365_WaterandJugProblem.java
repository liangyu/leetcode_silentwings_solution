package LC301_600;
import java.util.*;
public class LC365_WaterandJugProblem {
    /**
     * You are given two jugs with capacities jug1Capacity and jug2Capacity liters. There is an infinite amount of water
     * supply available. Determine whether it is possible to measure exactly targetCapacity liters using these two jugs.
     *
     * If targetCapacity liters of water are measurable, you must have targetCapacity liters of water contained within
     * one or both buckets by the end.
     *
     * Operations allowed:
     *
     * Fill any of the jugs with water.
     * Empty any of the jugs.
     * Pour water from one jug into another till the other jug is completely full, or the first jug itself is empty.
     *
     * Input: jug1Capacity = 3, jug2Capacity = 5, targetCapacity = 4
     * Output: true
     *
     * Constraints:
     *
     * 1 <= jug1Capacity, jug2Capacity, targetCapacity <= 10^6
     * @param jug1Capacity
     * @param jug2Capacity
     * @param targetCapacity
     * @return
     */
    // S1: Math
    // time = O(1), space = O(1)
    public boolean canMeasureWater(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        int x = jug1Capacity, y = jug2Capacity, z = targetCapacity;
        return z >= 0 && z <= x + y && z % gcd(x, y) == 0;
    }

    private int gcd(int a, int b) {
        if (b == 0) return a;
        return gcd(b, a % b);
    }

    // S2: bfs
    // time = O(x * y), space = O(x * y)
    public boolean canMeasureWater2(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        int x = jug1Capacity, y = jug2Capacity, z = targetCapacity;
        Queue<Pair> queue = new LinkedList<>();
        queue.offer(new Pair(x, y));
        HashSet<Pair> set = new HashSet<>();
        set.add(new Pair(x, y));

        while (!queue.isEmpty()) {
            Pair cur = queue.poll();
            int jug1 = cur.x, jug2 = cur.y;
            if (jug1 == z || jug2 == z || jug1 + jug2 == z) return true;

            List<Pair> nexts = convert(jug1, jug2, x, y);
            for (Pair next : nexts) {
                if (set.add(next)) queue.offer(next);
            }
        }
        return false;
    }

    private List<Pair> convert(int jug1, int jug2, int x, int y) {
        List<Pair> res = new ArrayList<>();
        int s1 = 0, s2 = 0;

        // case 1: empty jug1
        res.add(new Pair(0, jug2));

        // case 2: fill jug1 full
        res.add(new Pair(x, jug2));

        // case 3: empty jug2
        res.add(new Pair(jug1, 0));

        // case 4: fill jug2 full
        res.add(new Pair(jug1, y));

        // case 5:  fill jug2 to the maximum or full by using jug1
        s2 = Math.min(jug1 + jug2, y);
        s1 = jug1 - (s2 - jug2);
        res.add(new Pair(s1, s2));

        // case 6:  fill jug1 to the maximum or full by using jug2
        s1 = Math.min(jug1 + jug2, x);
        s2 = jug2 - (s1 - jug1);
        res.add(new Pair(s1, s2));

        return res;
    }

    private class Pair {
        private int x, y;
        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            return x * 31 + y;
        }

        public boolean equals(Object o) {
            if (o == null) return false;
            if (o instanceof Pair) {
                Pair that = (Pair) o;
                return this.x == that.x && this.y == that.y;
            } else return false;
        }
    }
}
/**
 * add
 * remove
 */