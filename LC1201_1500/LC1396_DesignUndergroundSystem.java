package LC1201_1500;
import java.util.*;
public class LC1396_DesignUndergroundSystem {
    /**
     * Implement the UndergroundSystem class:
     *
     * void checkIn(int id, string stationName, int t)
     * A customer with a card id equal to id, gets in the station stationName at time t.
     * A customer can only be checked into one place at a time.
     *
     * void checkOut(int id, string stationName, int t)
     * A customer with a card id equal to id, gets out from the station stationName at time t.
     *
     * double getAverageTime(string startStation, string endStation)
     * Returns the average time to travel between the startStation and the endStation.
     * The average time is computed from all the previous traveling from startStation to endStation that happened directly.
     *
     * Call to getAverageTime is always valid.
     * You can assume all calls to checkIn and checkOut methods are consistent. If a customer gets in at time t1 at some
     * station, they get out at time t2 with t2 > t1. All events happen in chronological order.
     *
     * Constraints:
     *
     * There will be at most 20000 operations.
     * 1 <= id, t <= 10^6
     * All strings consist of uppercase and lowercase English letters, and digits.
     * 1 <= stationName.length <= 10
     * Answers within 10^-5 of the actual value will be accepted as correct.
     */
    // time = O(1), space = O(n)
    HashMap<Integer, Pair> map1;
    HashMap<String, Cell> map2;
    public LC1396_DesignUndergroundSystem() {
        map1 = new HashMap<>();
        map2 = new HashMap<>();
    }

    public void checkIn(int id, String stationName, int t) {
        map1.put(id, new Pair(stationName, t));
    }

    public void checkOut(int id, String stationName, int t) {
        Pair p = map1.get(id);
        String route = p.s + "#" + stationName;
        int time = t - p.t;
        if (map2.containsKey(route)) {
            Cell cell = map2.get(route);
            cell = new Cell(cell.cnt + 1, cell.sum + time);
            map2.put(route, cell);
        } else map2.put(route, new Cell(1, time));
    }

    public double getAverageTime(String startStation, String endStation) {
        String route = startStation + "#" + endStation;
        Cell cell = map2.get(route);
        return cell.sum / cell.cnt;
    }

    private class Pair {
        private String s;
        private int t;
        public Pair(String s, int t) {
            this.s = s;
            this.t = t;
        }
    }

    private class Cell {
        private int cnt;
        private double sum;
        public Cell(int cnt, double sum) {
            this.cnt = cnt;
            this.sum = sum;
        }
    }
}
