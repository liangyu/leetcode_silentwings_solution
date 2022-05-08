package LC301_600;

public class LC552_StudentAttendanceRecordII {
    /**
     * An attendance record for a student can be represented as a string where each character signifies whether the
     * student was absent, late, or present on that day. The record only contains the following three characters:
     *
     * 'A': Absent.
     * 'L': Late.
     * 'P': Present.
     * Any student is eligible for an attendance award if they meet both of the following criteria:
     *
     * The student was absent ('A') for strictly fewer than 2 days total.
     * The student was never late ('L') for 3 or more consecutive days.
     * Given an integer n, return the number of possible attendance records of length n that make a student eligible for
     * an attendance award. The answer may be very large, so return it modulo 10^9 + 7.
     *
     * Input: n = 2
     * Output: 8
     *
     * Constraints:
     *
     * 1 <= n <= 10^5
     * @param n
     * @return
     */
    // time = O(n), space = O(n)
    public int checkRecord(int n) {
        long[][][] dp = new long[n + 1][2][3];
        long M = (long)(1e9 + 7);
        dp[0][0][0] = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 3; k++) {
                    if (j + 1 < 2) dp[i + 1][j + 1][0] = (dp[i + 1][j + 1][0] + dp[i][j][k]) % M;
                    if (k + 1 < 3) dp[i + 1][j][k + 1] = (dp[i + 1][j][k + 1] + dp[i][j][k]) % M;
                    dp[i + 1][j][0] = (dp[i + 1][j][0] + dp[i][j][k]) % M;
                }
            }
        }

        long res = 0;
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < 3; k++) {
                res = (res + dp[n][j][k]) % M;
            }
        }
        return (int) res;
    }
}
/**
 * A的数量 <= 1
 * 连续L的数量<=2
 * f(i,j,k)
 * i:1~n
 * j:0~1
 * k:0~2
 * f[i][j][k] 枚举下一位填什么？ A， L， P
 * A: f[i+1][j+1][0]  j+1<=1?
 * L: f[i+1][j][k+1]  k+1<=2?
 * P: f[i+1][j][0]  一定合法！
 * init: 0个A，0个L => f[0][0][0]=1
 * time = O(n)
 */
