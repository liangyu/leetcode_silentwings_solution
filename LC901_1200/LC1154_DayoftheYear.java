package LC901_1200;

public class LC1154_DayoftheYear {
    /**
     * Given a string date representing a Gregorian calendar date formatted as YYYY-MM-DD, return the day number of the
     * year.
     *
     * Input: date = "2019-01-09"
     * Output: 9
     *
     * Constraints:
     *
     * date.length == 10
     * date[4] == date[7] == '-', and all other date[i]'s are digits
     * date represents a calendar date between Jan 1st, 1900 and Dec 31, 2019.
     * @param date
     * @return
     */
    // time = O(1), space = O(1)
    public int dayOfYear(String date) {
        String[] arr = date.split("-");
        int year = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int day = Integer.parseInt(arr[2]);
        boolean flag = false;
        if (year % 4 == 0) flag = true;
        int res = 0;
        for (int i = 1; i < month; i++) {
            if (i == 1 || i == 3 || i == 5 || i == 7 || i == 8 || i == 10 || i == 12) res += 31;
            else if (i == 4 || i == 6 || i == 9 || i == 11) res += 30;
            else if (i == 2) {
                if (flag) res += 29;
                else res += 28;
            }
        }
        return res + day;
    }
}
