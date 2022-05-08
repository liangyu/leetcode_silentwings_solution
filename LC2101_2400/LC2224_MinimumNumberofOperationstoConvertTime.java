package LC2101_2400;

public class LC2224_MinimumNumberofOperationstoConvertTime {
    /**
     * You are given two strings current and correct representing two 24-hour times.
     *
     * 24-hour times are formatted as "HH:MM", where HH is between 00 and 23, and MM is between 00 and 59. The earliest
     * 24-hour time is 00:00, and the latest is 23:59.
     *
     * In one operation you can increase the time current by 1, 5, 15, or 60 minutes. You can perform this operation any
     * number of times.
     *
     * Return the minimum number of operations needed to convert current to correct.
     *
     * Input: current = "02:30", correct = "04:35"
     * Output: 3
     *
     * Constraints:
     *
     * current and correct are in the format "HH:MM"
     * current <= correct
     * @param current
     * @param correct
     * @return
     */
    // time = O(1), space = O(1)
    public int convertTime(String current, String correct) {
        int[] options = new int[]{60, 15, 5, 1};
        int cur = convertToMin(current), cor = convertToMin(correct);
        int diff = cor - cur;
        int res = 0;
        for (int x : options) {
            res += diff / x;
            diff %= x;
        }
        return res;
    }

    private int convertToMin(String t) {
        String[] strs = t.split(":");
        return Integer.parseInt(strs[0]) * 60 + Integer.parseInt(strs[1]);
    }
}
