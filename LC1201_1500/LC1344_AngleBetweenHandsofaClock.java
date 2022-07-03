package LC1201_1500;

public class LC1344_AngleBetweenHandsofaClock {
    /**
     * Given two numbers, hour and minutes, return the smaller angle (in degrees) formed between the hour and the minute
     * hand.
     *
     * Answers within 10-5 of the actual value will be accepted as correct.
     *
     * Input: hour = 12, minutes = 30
     * Output: 165
     *
     * Constraints:
     *
     * 1 <= hour <= 12
     * 0 <= minutes <= 59
     * @param hour
     * @param minutes
     * @return
     */
    // time = O(1), space = O(1)
    public double angleClock(int hour, int minutes) {
        double h = hour * (360.0 / 12) + minutes * (360.0 / 720); // 12 hour = 12 * 60 = 720 min -> 360 degree
        double m = minutes * (360.0 / 60);
        double diff = Math.abs(h - m);
        return Math.min(diff, 360 - diff);
    }
}
