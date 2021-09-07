package LC1201_1500;
import java.util.*;
public class LC1229_MeetingScheduler {
    /**
     * Given the availability time slots arrays slots1 and slots2 of two people and a meeting duration duration, return
     * the earliest time slot that works for both of them and is of duration duration.
     *
     * If there is no common time slot that satisfies the requirements, return an empty array.
     *
     * The format of a time slot is an array of two elements [start, end] representing an inclusive time range from
     * start to end.
     *
     * It is guaranteed that no two availability slots of the same person intersect with each other. That is, for any
     * two time slots [start1, end1] and [start2, end2] of the same person, either start1 > end2 or start2 > end1.
     *
     * Input: slots1 = [[10,50],[60,120],[140,210]], slots2 = [[0,15],[60,70]], duration = 8
     * Output: [60,68]
     *
     * Constraints:
     *
     * 1 <= slots1.length, slots2.length <= 10^4
     * slots1[i].length, slots2[i].length == 2
     * slots1[i][0] < slots1[i][1]
     * slots2[i][0] < slots2[i][1]
     * 0 <= slots1[i][j], slots2[i][j] <= 10^9
     * 1 <= duration <= 10^6
     * @param slots1
     * @param slots2
     * @param duration
     * @return
     */
    // time = O(mlogm + nlogn), space = O(1)
    public List<Integer> minAvailableDuration(int[][] slots1, int[][] slots2, int duration) {
        Arrays.sort(slots1, (o1, o2) -> o1[0] - o2[0]);
        Arrays.sort(slots2, (o1, o2) -> o1[0] - o2[0]);

        int i = 0, j = 0;
        while (i < slots1.length && j < slots2.length) {
            int start = Math.max(slots1[i][0], slots2[j][0]);
            int end = Math.min(slots1[i][1], slots2[j][1]);

            if (start + duration <= end) {
                return Arrays.asList(start, start + duration);
            } else if (slots1[i][1] < slots2[j][1]) i++;
            else j++;
        }
        return new ArrayList<>();
    }

    // S2: Sweep Line
    // time = O((m + n) * log(m + n)), space = O(m + n)
    public List<Integer> minAvailableDuration2(int[][] slots1, int[][] slots2, int duration) {
        List<int[]> list = new ArrayList<>();
        for (int[] x : slots1) {
            list.add(new int[]{x[0], 1});
            list.add(new int[]{x[1], -1});
        }
        for (int[] x : slots2) {
            list.add(new int[]{x[0], 1});
            list.add(new int[]{x[1], -1});
        }

        Collections.sort(list, (o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);

        int count = 0, start = -1;
        for (int[] x : list) {
            count += x[1];
            if (x[1] == 1 && count == 2) start = x[0];
            else if (x[1] == -1 && count == 1 && x[0] - start >= duration) {
                return Arrays.asList(start, start + duration);
            }
        }
        return new ArrayList<>();
    }
}
