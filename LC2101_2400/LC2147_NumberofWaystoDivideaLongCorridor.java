package LC2101_2400;
import java.util.*;
public class LC2147_NumberofWaystoDivideaLongCorridor {
    /**
     * Along a long library corridor, there is a line of seats and decorative plants. You are given a 0-indexed string
     * corridor of length n consisting of letters 'S' and 'P' where each 'S' represents a seat and each 'P' represents
     * a plant.
     *
     * One room divider has already been installed to the left of index 0, and another to the right of index n - 1.
     * Additional room dividers can be installed. For each position between indices i - 1 and i (1 <= i <= n - 1), at
     * most one divider can be installed.
     *
     * Divide the corridor into non-overlapping sections, where each section has exactly two seats with any number of
     * plants. There may be multiple ways to perform the division. Two ways are different if there is a position with a
     * room divider installed in the first way but not in the second way.
     *
     * Return the number of ways to divide the corridor. Since the answer may be very large, return it modulo 109 + 7.
     * If there is no way, return 0.
     *
     * Input: corridor = "SSPPSPS"
     * Output: 3
     *
     * Input: corridor = "PPSPSP"
     * Output: 1
     *
     * Constraints:
     *
     * n == corridor.length
     * 1 <= n <= 10^5
     * corridor[i] is either 'S' or 'P'.
     * @param corridor
     * @return
     */
    // S1
    // time = O(n), space = O(n)
    public int numberOfWays(String corridor) {
        int n = corridor.length(), count = 0;
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            if (corridor.charAt(i) == 'S') count++;
            arr[i] = count;
        }
        if (count == 0 || count % 2 == 1) return 0;

        long M = (long)(1e9 + 7), res = 1;
        int j = -1, th = 2;
        for (int i = 0; i < n; i++) {
            if (arr[i] == th && j == -1) j = i;
            if (arr[i] == th + 1) {
                res = res * (i - j) % M;
                j = -1;
                th += 2;
            }
        }
        return (int) res;
    }

    // S2
    // time = O(n), space = O(n)
    public int numberOfWays2(String corridor) {
        int n = corridor.length();
        long M = (long)(1e9 + 7);
        List<Integer> pos = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (corridor.charAt(i) == 'S') pos.add(i);
        }

        if (pos.size() == 0 || pos.size() % 2 != 0) return 0;

        long res = 1;
        for (int i = 2; i + 2 <= pos.size(); i+= 2) {
            long diff = pos.get(i) - pos.get(i - 1);
            res *= diff;
            res %= M;
        }
        return (int) res;
    }
}
/**
 * 本题的思想非常简单，就是将每两个沙发作为一组，然后查看每组之间有几个植物。
 * 这些植物之间、植物与两侧的沙发之间都可以插板。
 * 然后用乘法原理计算总的策略数目。
 * 本题如果在原数组上操作，会显得有些繁琐。
 * 直接将沙发的index拿出来放在一个新数组里，这样每相邻两个元素一组，每组之间的间隔就一目了然。
 * pos[0,1,4,6]
 */