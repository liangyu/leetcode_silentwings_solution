package LC1801_2100;
import java.util.*;
public class LC1883_MinimumSkipstoArriveatMeetingOnTime {
    /**
     * You are given an integer hoursBefore, the number of hours you have to travel to your meeting. To arrive at your
     * meeting, you have to travel through n roads. The road lengths are given as an integer array dist of length n,
     * where dist[i] describes the length of the ith road in kilometers. In addition, you are given an integer speed,
     * which is the speed (in km/h) you will travel at.
     *
     * After you travel road i, you must rest and wait for the next integer hour before you can begin traveling on the
     * next road. Note that you do not have to rest after traveling the last road because you are already at the meeting.
     *
     * For example, if traveling a road takes 1.4 hours, you must wait until the 2 hour mark before traveling the next
     * road. If traveling a road takes exactly 2 hours, you do not need to wait.
     * However, you are allowed to skip some rests to be able to arrive on time, meaning you do not need to wait for the
     * next integer hour. Note that this means you may finish traveling future roads at different hour marks.
     *
     * For example, suppose traveling the first road takes 1.4 hours and traveling the second road takes 0.6 hours.
     * Skipping the rest after the first road will mean you finish traveling the second road right at the 2 hour mark,
     * letting you start traveling the third road immediately.
     * Return the minimum number of skips required to arrive at the meeting on time, or -1 if it is impossible.
     *
     * Input: dist = [1,3,2], speed = 4, hoursBefore = 2
     * Output: 1
     *
     * Constraints:
     *
     * n == dist.length
     * 1 <= n <= 1000
     * 1 <= dist[i] <= 10^5
     * 1 <= speed <= 10^6
     * 1 <= hoursBefore <= 10^7
     * @param dist
     * @param speed
     * @param hoursBefore
     * @return
     */
    public int minSkips(int[] dist, int speed, int hoursBefore) {
        // corner case
        if (dist == null || dist.length == 0) return 0;

        double total = 0, n = dist.length;
        for (int i = 0; i < n; i++) total += dist[i] / speed * 1.0;
        if (total > hoursBefore * 1.0) return -1;


    }
}
