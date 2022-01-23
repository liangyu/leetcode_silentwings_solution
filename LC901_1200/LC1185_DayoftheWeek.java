package LC901_1200;

public class LC1185_DayoftheWeek {
    /**
     * Given a date, return the corresponding day of the week for that date.
     *
     * The input is given as three integers representing the day, month and year respectively.
     *
     * Return the answer as one of the following values {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday",
     * "Friday", "Saturday"}.
     *
     * Input: day = 31, month = 8, year = 2019
     * Output: "Saturday"
     *
     * Constraints:
     *
     * The given dates are valid dates between the years 1971 and 2100.
     * @param day
     * @param month
     * @param year
     * @return
     */
    // time = O(1), space = O(1)
    public String dayOfTheWeek(int day, int month, int year) {
        String[] week = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        int[] monthDays = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30};

        int days = day;
        if (isLeapYear(year) && month > 2) days++; // if leap year, one extra day is counted after Feb as Feb has 29 days
        days += countLeapYear(year - 1) + (year - 1) * 365; // 从公元0年开始算，第一天是Sunday

        for (int i = 0; i < month - 1; i++) days += monthDays[i]; // 当前月的天数已经作为day加入到days里了
        return week[days % 7];
    }

    private int countLeapYear(int year) {
        return year / 4 - year / 100 + year / 400;
    }

    private boolean isLeapYear(int year) {
        if (year % 4 != 0) return false;
        if (year % 100 != 0) return true;
        if (year % 400 != 0) return false;
        return true;
    }
}
