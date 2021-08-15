package LC901_1200;

public class LC1184_DistanceBetweenBusStops {
    /**
     * A bus has n stops numbered from 0 to n - 1 that form a circle. We know the distance between all pairs of
     * neighboring stops where distance[i] is the distance between the stops number i and (i + 1) % n.
     *
     * The bus goes along both directions i.e. clockwise and counterclockwise.
     *
     * Return the shortest distance between the given start and destination stops.
     *
     * Input: distance = [1,2,3,4], start = 0, destination = 1
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= n <= 10^4
     * distance.length == n
     * 0 <= start, destination < n
     * 0 <= distance[i] <= 10^4
     * @param distance
     * @param start
     * @param destination
     * @return
     */
    // time = O(n), space = O(1)
    public int distanceBetweenBusStops(int[] distance, int start, int destination) {
        // corner case
        if (distance == null || distance.length == 0) return 0;

        int dist1 = 0, dist2 = 0, n = distance.length;
        for (int i = start; i < (destination < start ? destination + n : destination); i++) {
            dist1 += distance[i % n];
        }

        for (int i = destination; i < (start < destination ? start + n : start); i++) {
            dist2 += distance[i % n];
        }
        return Math.min(dist1, dist2);
    }
}
