package LC2101_2400;
import java.util.*;
public class LC2332_TheLatestTimetoCatchaBus {
    /**
     * You are given a 0-indexed integer array buses of length n, where buses[i] represents the departure time of the
     * ith bus. You are also given a 0-indexed integer array passengers of length m, where passengers[j] represents the
     * arrival time of the jth passenger. All bus departure times are unique. All passenger arrival times are unique.
     *
     * You are given an integer capacity, which represents the maximum number of passengers that can get on each bus.
     *
     * The passengers will get on the next available bus. You can get on a bus that will depart at x minutes if you
     * arrive at y minutes where y <= x, and the bus is not full. Passengers with the earliest arrival times get on the
     * bus first.
     *
     * Return the latest time you may arrive at the bus station to catch a bus. You cannot arrive at the same time as
     * another passenger.
     *
     * Note: The arrays buses and passengers are not necessarily sorted.
     *
     * Input: buses = [10,20], passengers = [2,17,18,19], capacity = 2
     * Output: 16
     *
     * Input: buses = [20,30,10], passengers = [19,13,26,4,25,11,21], capacity = 2
     * Output: 20
     *
     * Constraints:
     *
     * n == buses.length
     * m == passengers.length
     * 1 <= n, m, capacity <= 10^5
     * 2 <= buses[i], passengers[i] <= 10^9
     * Each element in buses is unique.
     * Each element in passengers is unique.
     * @param buses
     * @param passengers
     * @param capacity
     * @return
     */
    // S1: sort
    // time = O(mlogm + nlogn), space = O(1)
    public int latestTimeCatchTheBus(int[] buses, int[] passengers, int capacity) {
        Arrays.sort(buses);
        Arrays.sort(passengers);
        int m = buses.length, n = passengers.length;
        int j = 0, res = -1;
        for (int i = 0; i < m; i++) {
            int cap = capacity;
            while (j < n && passengers[j] <= buses[i] && cap > 0) {
                // passengers[j] -> buses[i]
                if (j == 0 || j >= 1 && passengers[j] - 1 != passengers[j - 1]) {
                    res = passengers[j] - 1;
                }
                cap--;
                j++;
            }
            if (cap > 0) {
                if (j == 0 || j >= 1 && passengers[j - 1] != buses[i]) {
                    res = buses[i];
                }
            }
        }
        return res;
    }

    // S2: sort
    // time = O(mlogm + nlogn), space = O(n)
    public int latestTimeCatchTheBus2(int[] buses, int[] passengers, int capacity) {
        Arrays.sort(buses);
        Arrays.sort(passengers);

        int m = buses.length, n = passengers.length;
        int i = 0, j = 0, count = 0;
        HashSet<Integer> set = new HashSet<>();
        for (int x : passengers) set.add(x);
        while (i < m && j < n) {
            int c = capacity;
            while (j < n && c > 0 && passengers[j] <= buses[i]) {
                if (i == m - 1) count++;
                c--;
                j++;
            }
            i++;
        }
        if (count == 0) return buses[m - 1];
        if (count < capacity) {
            if (!set.contains(buses[m - 1])) return buses[m - 1];
            else {
                int k = buses[m - 1];
                while (set.contains(k)) k--;
                return k;
            }
        } else {
            int k = passengers[j - 1] - 1;
            while (set.contains(k)) k--;
            return k;
        }
    }

    // S3: Binary Search
    // time = O(mlogm + nlogn), space = O(n)
    public int latestTimeCatchTheBus3(int[] buses, int[] passengers, int capacity) {
        Arrays.sort(buses);
        Arrays.sort(passengers);
        int n = buses.length, m = passengers.length;
        int l = 0, r = (int) 1e9;
        while (l < r) {
            int mid = r - (r - l) / 2;
            if (check(buses, passengers, capacity, mid)) l = mid;
            else r = mid - 1;
        }

        HashSet<Integer> set = new HashSet<>();
        for (int x : passengers) set.add(x);
        while (set.contains(l)) l--;
        return l;
    }

    private boolean check(int[] bs, int[] ps, int c, int t) {
        int n = bs.length, m = ps.length;
        List<Integer> temp = new ArrayList<>();
        for (int x : ps) temp.add(x);
        temp.add(t);
        int k = m;
        while (k > 0 && temp.get(k - 1) > temp.get(k)) {
            swap(temp, k - 1, k);
            k--;
        }

        for (int i = 0, j = 0; i < n; i++) {
            for (k = 0; k < c && j <= m && temp.get(j) <= bs[i]; k++, j++) {
                if (temp.get(j) >= t) return true;
            }
        }
        return false;
    }

    private void swap(List<Integer> nums, int i, int j) {
        int t = nums.get(i);
        nums.set(i, nums.get(j));
        nums.set(j, t);
    }
}
/**
 * 不考虑"我"的因素，哪些人上哪些车
 * xxxx | xxxxxx | xx    x | xxxx xxxxxOjxxx|
 * 1. 这辆车没有满，我卡点上车即可
 * 2. 已经装满了，可以比某个乘客早到一秒钟，也能上这辆车，限制不能和另一个乘客同时到
 * 针对某辆车，扫一遍，把j往后挤
 */