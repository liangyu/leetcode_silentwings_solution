package LC1801_2100;
import java.util.*;
public class LC1904_TheNumberofFullRoundsYouHavePlayed {
    /**
     * A new online video game has been released, and in this video game, there are 15-minute rounds scheduled every
     * quarter-hour period. This means that at HH:00, HH:15, HH:30 and HH:45, a new round starts, where HH represents an
     * integer number from 00 to 23. A 24-hour clock is used, so the earliest time in the day is 00:00 and the latest is 23:59.
     *
     * Given two strings startTime and finishTime in the format "HH:MM" representing the exact time you started and
     * finished playing the game, respectively, calculate the number of full rounds that you played during your game
     * session.
     *
     * For example, if startTime = "05:20" and finishTime = "05:59" this means you played only one full round from
     * 05:30 to 05:45. You did not play the full round from 05:15 to 05:30 because you started after the round began,
     * and you did not play the full round from 05:45 to 06:00 because you stopped before the round ended.
     * If finishTime is earlier than startTime, this means you have played overnight (from startTime to the midnight
     * and from midnight to finishTime).
     *
     * Return the number of full rounds that you have played if you had started playing at startTime and finished at
     * finishTime.
     *
     * Input: startTime = "12:01", finishTime = "12:44"
     * Output: 1
     *
     * Constraints:
     *
     * startTime and finishTime are in the format HH:MM.
     * 00 <= HH <= 23
     * 00 <= MM <= 59
     * startTime and finishTime are not equal.
     * @param startTime
     * @param finishTime
     * @return
     */
    // time = O(1), space = O(1)
    public int numberOfRounds(String startTime, String finishTime) {
        int sh = Integer.parseInt(startTime.substring(0, 2));
        int fh = Integer.parseInt(finishTime.substring(0, 2));
        int sm = Integer.parseInt(startTime.substring(3));
        int fm = Integer.parseInt(finishTime.substring(3));

        if (sm > 0 && sm < 15) sm = 15;
        else if (sm > 15 && sm < 30) sm = 30;
        else if (sm > 30 && sm < 45) sm = 45;
        else if (sm > 45 && sm < 60) sm = 60;

        int hdiff = fh - sh, mdiff = fm - sm;
        // 00:01 -> "00:00
        if (hdiff < 0 || (hdiff == 0 && mdiff < 0)) hdiff += 24;
        return (hdiff * 60 + mdiff) / 15;
    }

    // S2
    // time = O(1), space = O(1)
    public int numberOfRounds2(String startTime, String finishTime) {
        int sh = Integer.parseInt(startTime.substring(0, 2));
        int fh = Integer.parseInt(finishTime.substring(0, 2));
        int sm = Integer.parseInt(startTime.substring(3));
        int fm = Integer.parseInt(finishTime.substring(3));

        int[] start = new int[]{sh, sm};
        int[] end = new int[]{fh, fm};

        // 跨过0点 -> 补上24小时
        if (start[0] * 60 + start[1] > end[0] * 60 + end[1]) {
            end[0] += 24;
        }

        start[1] = (start[1] + 14) / 15 * 15; // 向后取整
        end[1] = end[1] / 15 * 15; // 向前取整

        // hour -> min
        int a = start[0] * 60 + start[1];
        int b = end[0] * 60 + end[1];

        return Math.max(0,(b - a) / 15); // 00:47 00:57 这样的例子，a = 60, b = 45, 产生交错而导致变成负数，所以要取0
    }
}
/**
 * 有零有整
 * 处理边角余料 01 -> 15   向后取整
 * finish time 往前取整
 * 中间把时刻一减即可
 *
 * 00 -> 00
 * 01 -> 15
 * 02 -> 15
 * 15 -> 15
 * 16 -> 30
 */
