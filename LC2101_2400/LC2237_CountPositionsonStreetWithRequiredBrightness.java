package LC2101_2400;

public class LC2237_CountPositionsonStreetWithRequiredBrightness {
    /**
     * You are given an integer n. A perfectly straight street is represented by a number line ranging from 0 to n - 1.
     * You are given a 2D integer array lights representing the street lamp(s) on the street. Each lights[i] =
     * [positioni, rangei] indicates that there is a street lamp at position positioni that lights up the area from
     * [max(0, positioni - rangei), min(n - 1, positioni + rangei)] (inclusive).
     *
     * The brightness of a position p is defined as the number of street lamps that light up the position p. You are
     * given a 0-indexed integer array requirement of size n where requirement[i] is the minimum brightness of the ith
     * position on the street.
     *
     * Return the number of positions i on the street between 0 and n - 1 that have a brightness of at least
     * requirement[i].
     *
     * Input: n = 5, lights = [[0,1],[2,1],[3,2]], requirement = [0,2,1,4,1]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * 1 <= lights.length <= 10^5
     * 0 <= positioni < n
     * 0 <= rangei <= 10^5
     * requirement.length == n
     * 0 <= requirement[i] <= 10^5
     * @param n
     * @param lights
     * @param requirement
     * @return
     */
    // time = O(n), space = O(n)
    public int meetRequirement(int n, int[][] lights, int[] requirement) {
        int[] diff = new int[n + 1];
        for (int[] x : lights) {
            int a = Math.max(0, x[0] - x[1]);
            int b = Math.min(n - 1, x[0] + x[1]);
            diff[a]++;
            diff[b + 1]--;
        }

        int sum = 0, res = 0;
        for (int i = 0; i < n; i++) {
            sum += diff[i];
            if (sum >= requirement[i]) res++;
        }
        return res;
    }
}
