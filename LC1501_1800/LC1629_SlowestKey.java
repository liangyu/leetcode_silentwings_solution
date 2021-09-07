package LC1501_1800;
public class LC1629_SlowestKey {
    /**
     * A newly designed keypad was tested, where a tester pressed a sequence of n keys, one at a time.
     *
     * You are given a string keysPressed of length n, where keysPressed[i] was the ith key pressed in the testing
     * sequence, and a sorted list releaseTimes, where releaseTimes[i] was the time the ith key was released.
     * Both arrays are 0-indexed. The 0th key was pressed at the time 0, and every subsequent key was pressed at the
     * exact time the previous key was released.
     *
     * The tester wants to know the key of the keypress that had the longest duration. The ith keypress had a duration
     * of releaseTimes[i] - releaseTimes[i - 1], and the 0th keypress had a duration of releaseTimes[0].
     *
     * Note that the same key could have been pressed multiple times during the test, and these multiple presses of the
     * same key may not have had the same duration.
     *
     * Return the key of the keypress that had the longest duration. If there are multiple such keypresses, return the
     * lexicographically largest key of the keypresses.
     *
     * Input: releaseTimes = [9,29,49,50], keysPressed = "cbcd"
     * Output: "c"
     *
     * Constraints:
     *
     * releaseTimes.length == n
     * keysPressed.length == n
     * 2 <= n <= 1000
     * 1 <= releaseTimes[i] <= 10^9
     * releaseTimes[i] < releaseTimes[i+1]
     * keysPressed contains only lowercase English letters.
     * @param releaseTimes
     * @param keysPressed
     * @return
     */
    // time = O(n), space = O(1)
    public char slowestKey(int[] releaseTimes, String keysPressed) {
        int n = releaseTimes.length;

        int max = releaseTimes[0];
        char res = keysPressed.charAt(0);
        for (int i = 1; i < n; i++) {
            int duration = releaseTimes[i] - releaseTimes[i - 1];
            if (duration >= max) {
                if (duration == max) {
                    if (keysPressed.charAt(i) > res) res = keysPressed.charAt(i);
                } else {
                    max = duration;
                    res = keysPressed.charAt(i);
                }
            }
        }
        return res;
    }
}
