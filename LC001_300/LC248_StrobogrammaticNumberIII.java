package LC001_300;
import java.util.*;
public class LC248_StrobogrammaticNumberIII {
    /**
     * Given two strings low and high that represent two integers low and high where low <= high, return the number of
     * strobogrammatic numbers in the range [low, high].
     *
     * A strobogrammatic number is a number that looks the same when rotated 180 degrees (looked at upside down).
     *
     * Input: low = "50", high = "100"
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= low.length, high.length <= 15
     * low and high consist of only digits.
     * low <= high
     * low and high do not contain any leading zeros except for zero itself.
     * @param low
     * @param high
     * @return
     */
    // time = O(5^(n/2)), space = O(5^(n/2))
    public int strobogrammaticInRange(String low, String high) {
        int res = 0;
        List<String> list = new ArrayList<>();
        int m = low.length(), n = high.length();
        for (int i = m; i <= n; i++) {
            list.addAll(helper(i, i));
        }

        for (String s : list) {
            if (s.length() == m && s.compareTo(low) < 0 || s.length() == n && s.compareTo(high) > 0) continue;
            res++;
        }
        return res;
    }

    private List<String> helper(int n, int m) {
        // base case
        if (n == 0) return new ArrayList<>(Arrays.asList(""));
        if (n == 1) return new ArrayList<>(Arrays.asList("0", "1", "8"));

        List<String> list = helper(n - 2, m);
        List<String> res = new ArrayList<>();

        for (String s : list) {
            // "00" is not legal here, so "0" can be added at the head and tail => "0" can only be added in the middle
            if (n != m) res.add("0" + s + "0");
            res.add("1" + s + "1");
            res.add("6" + s + "9");
            res.add("8" + s + "8");
            res.add("9" + s + "6");
        }
        return res;
    }
}
