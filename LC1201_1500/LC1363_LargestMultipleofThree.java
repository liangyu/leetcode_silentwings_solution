package LC1201_1500;
import java.util.*;
public class LC1363_LargestMultipleofThree {
    /**
     * Given an array of digits digits, return the largest multiple of three that can be formed by concatenating some
     * of the given digits in any order. If there is no answer return an empty string.
     *
     * Since the answer may not fit in an integer data type, return the answer as a string. Note that the returning
     * answer must not contain unnecessary leading zeros.
     *
     * Input: digits = [8,1,9]
     * Output: "981"
     *
     * Constraints:
     *
     * 1 <= digits.length <= 10^4
     * 0 <= digits[i] <= 9
     * @param digits
     * @return
     */
    // S1: DP
    // time = O(nlogn), space = O(n)
    public String largestMultipleOfThree(int[] digits) {
        Arrays.sort(digits);
        reverse(digits);
        int n = digits.length;
        String[][] dp = new String[n + 1][3];
        dp[0][0] = "";
        dp[0][1] = "#"; // invalid state, put a mark here!!!
        dp[0][2] = "#"; // invalid state

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 3; j++) {
                int k = digits[i - 1] % 3;
                if (dp[i - 1][j].equals("#") && dp[i - 1][(j - k + 3) % 3].equals("#")) dp[i][j] = "#";
                else if (dp[i - 1][j].equals("#")) dp[i][j] = dp[i - 1][(j - k + 3) % 3] + digits[i - 1];
                else if (dp[i - 1][(j - k + 3) % 3].equals("#")) dp[i][j] = dp[i - 1][j];
                else if (dp[i - 1][j].length() >= dp[i - 1][(j - k + 3) % 3].length() + 1) dp[i][j] = dp[i - 1][j];
                else dp[i][j] = dp[i - 1][(j - k + 3) % 3] + digits[i - 1];
            }
        }
        if (dp[n][0].length() > 0 && dp[n][0].charAt(0) == '0') return "0"; // 第一位是0的话，后面一定为0，因为数字从大到小排列的！
        return dp[n][0];
    }

    private void reverse(int[] digits) {
        int i = 0, j = digits.length - 1;
        while (i < j) {
            int t = digits[i];
            digits[i++] = digits[j];
            digits[j--] = t;
        }
    }

    // S2: dp
    // time = O(nlogn),space = O(n)
    public String largestMultipleOfThree2(int[] digits) {
        Arrays.sort(digits);
        reverse(digits);
        int n = digits.length;
        int[][] dp = new int[n + 1][3];
        dp[0][0] = 0;
        dp[0][1] = Integer.MIN_VALUE;
        dp[0][2] = Integer.MIN_VALUE;

        boolean[][] pick = new boolean[n + 1][3];
        int[][] prev = new int[n + 1][3];

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j < 3; j++) {
                int k = digits[i - 1] % 3;
                if (dp[i - 1][j] >= dp[i - 1][(j - k + 3) % 3] + 1) { // 长度相等的时候，选择第一种！！！
                    dp[i][j] = dp[i - 1][j];
                    pick[i][j] = false;
                    prev[i][j] = j;
                } else if (dp[i - 1][j] < dp[i - 1][(j - k + 3) % 3] + 1) {
                    dp[i][j] = dp[i - 1][(j - k + 3) % 3] + 1;
                    pick[i][j] = true;
                    prev[i][j] = (j - k + 3) % 3;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        int j = 0; // dp[n][0]
        for (int i = n; i >= 1; i--) {
            if (pick[i][j]) sb.append((char)(digits[i - 1] + '0'));
            j = prev[i][j];
        }
        String res = sb.reverse().toString();
        if (res.length() > 0 && res.charAt(0) == '0') return "0";
        return res;
    }
}
/**
 * 优先挑数字大的
 * solution 1: Math
 * if totalSum % 3 == 0, select all digits
 * if totalSum % 3 == 1,
 *      exclude the smallest element (%3 == 1)
 *      exclude two smallest elements (%3 == 2)
 * if totalSUm % 3 == 2,
 *      exclude the smallest element (%3 == 2)
 *      exclude two smallest elements (%3 == 1)
 *
 * solution 2: dp
 * dp[i][j]: the largest string (%3 = j) we can construct using digits[1:i]
 * sort(digits)
 *
 * x x x x x x i
 * dp[i][0]
 * dp[i][1]
 * dp[i][2]
 * 1. do not pick digits[i]: dp[i][j] = dp[i-1][j]
 * 2. pick digits[i]: dp[i][j] = dp[i-1][j-k] + digits[i]   (k == digits[i] % 3)
 * dp[i][j] = better(dp[i-1][j], dp[i-1][j-k] + digits[i])
 * return dp[n][0]
 * 缺点：会有大量的重复
 * dp[10][1] = 10000000000000000
 * dp[11][1] = 100000000000000000
 * 如何节省空间呢？
 * => 重构最优解常用的一种方法，需要倒推！
 * To reconstruct the string, we need to know two things:
 * 1. for the i-th digit, did we pick it in the final solution?
 * 2. what is the previous state for (i, j) => (i-1, ?)
 * i: 1 2 3 4 5 ... n-1 n
 * j: 0 1 2 2 1      1  0
 * dp[i][j]: the length of the largest string (%3 = j) we can construct using digits[1:i]
 * 存的是路径，顺着走一遍即可。
 * 打印路径，需要存一个类似prev的数组，回溯状态，每一步再现当时做决策的情景！！！
 */