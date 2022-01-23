package LC301_600;
import java.util.*;
public class LC539_MinimumTimeDifference {
    /**
     * Given a list of 24-hour clock time points in "HH:MM" format, return the minimum minutes difference between any
     * two time-points in the list.
     *
     *
     * Example 1:
     *
     * Input: timePoints = ["23:59","00:00"]
     * Output: 1
     * Example 2:
     *
     * Input: timePoints = ["00:00","23:59","00:00"]
     * Output: 0
     *
     * Constraints:
     *
     * 2 <= timePoints <= 2 * 10^4
     * timePoints[i] is in the format "HH:MM".
     * @param timePoints
     * @return
     */
    // S1
    // time = O(nlogn), space = O(1)
    public int findMinDifference(List<String> timePoints) {
        int n = timePoints.size(), res = Integer.MAX_VALUE;
        if (n > 1440) return 0; // 剪枝，24*60=1440，一定有重复！

        Collections.sort(timePoints, (o1, o2) -> o1.compareTo(o2));

        for (int i = 1; i < n; i++) {
            res = Math.min(res, helper(timePoints.get(i - 1), timePoints.get(i), 0));
        }
        res = Math.min(res, helper(timePoints.get(n - 1), timePoints.get(0), 24));
        return res;
    }

    private int helper(String s1, String s2, int t) {
        String[] strs1 = s1.split(":");
        String[] strs2 = s2.split(":");

        int hour = Integer.valueOf(strs2[0]) + t - Integer.valueOf(strs1[0]);
        int min = Integer.valueOf(strs2[1]) - Integer.valueOf(strs1[1]);
        return 60 * hour + min;
    }

    // S2
    // time = O(nlogn), space = O(n)
    public int findMinDifference2(List<String> timePoints) {
        int n = timePoints.size(), res = Integer.MAX_VALUE;
        if (n > 60 * 24) return 0;

        int[] time = new int[n];
        for (int i = 0; i < n; i++) {
            String str = timePoints.get(i);
            time[i] = str.charAt(0) * 600 + str.charAt(1) * 60 + str.charAt(3) * 10 + str.charAt(4);
        }

        Arrays.sort(time);
        for (int i = 1; i < n; i++) {
            res = Math.min(res, time[i] - time[i - 1]);
        }
        res = Math.min(res, time[0] + 1440 - time[n - 1]);
        return res;
    }
}
