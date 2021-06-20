package LC601_900;
import java.util.*;
public class LC871_MinimumNumberofRefuelingStops {
    /**
     * A car travels from a starting position to a destination which is target miles east of the starting position.
     *
     * Along the way, there are gas stations.  Each station[i] represents a gas station that is station[i][0] miles
     * east of the starting position, and has station[i][1] liters of gas.
     *
     * The car starts with an infinite tank of gas, which initially has startFuel liters of fuel in it.  It uses 1
     * liter of gas per 1 mile that it drives.
     *
     * When the car reaches a gas station, it may stop and refuel, transferring all the gas from the station into the
     * car.
     *
     * What is the least number of refueling stops the car must make in order to reach its destination?  If it cannot
     * reach the destination, return -1.
     *
     * Note that if the car reaches a gas station with 0 fuel left, the car can still refuel there.  If the car reaches
     * the destination with 0 fuel left, it is still considered to have arrived.
     *
     * Input: target = 1, startFuel = 1, stations = []
     * Output: 0
     *
     * Note:
     *
     * 1 <= target, startFuel, stations[i][1] <= 10^9
     * 0 <= stations.length <= 500
     * 0 < stations[0][0] < stations[1][0] < ... < stations[stations.length-1][0] < target
     * @param target
     * @param startFuel
     * @param stations
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int curFuel = startFuel; // the total added gas up to now
        int curStation = 0, count = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>((o1, o2) -> o2 - o1);

        while (curStation < stations.length) {
            if (curFuel >= stations[curStation][0]) {
                pq.offer(stations[curStation][1]);
                curStation += 1;
            } else {
                while (!pq.isEmpty() && curFuel < stations[curStation][0]) {
                    curFuel += pq.poll();
                    count++;
                }
                if (curFuel < stations[curStation][0]) return -1;
            }
        }

        // check target, treat target as the last gas station
        if (curFuel < target) {
            while (!pq.isEmpty() && curFuel < target) {
                curFuel += pq.poll();
                count++;
            }
            if (curFuel < target) return -1;
        }
        return count;
    }
}
/**
 * 0 ... A ... B ...... C ....D ... E ... F ... Target
 * 如何千方百计走向下一个加油站 => 贪心
 * 不加最后target，只能表明千万百计到达最后一个加油站，而不是到达target
 */
