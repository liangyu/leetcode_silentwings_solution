package LC301_600;

public class LC551_StudentAttendanceRecordI {
    /**
     * You are given a string s representing an attendance record for a student where each character signifies whether
     * the student was absent, late, or present on that day. The record only contains the following three characters:
     *
     * 'A': Absent.
     * 'L': Late.
     * 'P': Present.
     * The student is eligible for an attendance award if they meet both of the following criteria:
     *
     * The student was absent ('A') for strictly fewer than 2 days total.
     * The student was never late ('L') for 3 or more consecutive days.
     * Return true if the student is eligible for an attendance award, or false otherwise.
     *
     * Input: s = "PPALLP"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= s.length <= 1000
     * s[i] is either 'A', 'L', or 'P'.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public boolean checkRecord(String s) {
        int n = s.length(), count = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == 'A') count++;
            else if (s.charAt(i) == 'L') {
                int j = i;
                while (j < n && s.charAt(j) == 'L') j++;
                if (j - i >= 3) return false;
                i = j - 1;
            }
        }
        return count < 2;
    }
}
