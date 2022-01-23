package LC601_900;
import java.util.*;
public class LC849_MaximizeDistancetoClosestPerson {
    /**
     * You are given an array representing a row of seats where seats[i] = 1 represents a person sitting in the ith
     * seat, and seats[i] = 0 represents that the ith seat is empty (0-indexed).
     *
     * There is at least one empty seat, and at least one person sitting.
     *
     * Alex wants to sit in the seat such that the distance between him and the closest person to him is maximized.
     *
     * Return that maximum distance to the closest person.
     *
     * Input: seats = [1,0,0,0,1,0,1]
     * Output: 2
     *
     * Constraints:
     *
     * 2 <= seats.length <= 2 * 10^4
     * seats[i] is 0 or 1.
     * At least one seat is empty.
     * At least one seat is occupied.
     * @param seats
     * @return
     */
    // time = O(n), space = O(1)
    public int maxDistToClosest(int[] seats) {
        int n = seats.length;
        int prev = -1, future = 0;
        int res = 0;

        for (int i = 0; i < n; i++) {
            if (seats[i] == 1) prev = i;
            else {
                while (future < n && seats[future] == 0 || future < i) future++;
                int left = prev == -1 ? n : i - prev;
                int right = future == n ? n : future - i;
                res = Math.max(res, Math.min(left, right));
            }
        }
        return res;
    }

    // S2: TreeSet
    // time = O(nlogn), space = O(n)
    public int maxDistToClosest2(int[] seats) {
        int n = seats.length;
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            if (seats[i] == 1) set.add(i);
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            if (seats[i] == 0) {
                int closest = n;
                Integer ck = set.higher(i);
                Integer fk = set.lower(i);
                if (ck != null) closest = Math.min(closest, ck - i);
                if (fk != null) closest = Math.min(closest, i - fk);
                res = Math.max(res, closest);
            }
        }
        return res;
    }
}
