package LC601_900;

public class LC777_SwapAdjacentinLRString {
    /**
     * In a string composed of 'L', 'R', and 'X' characters, like "RXXLRXRXL", a move consists of either replacing one
     * occurrence of "XL" with "LX", or replacing one occurrence of "RX" with "XR". Given the starting string start and
     * the ending string end, return True if and only if there exists a sequence of moves to transform one string to the
     * other.
     *
     * Input: start = "RXXLRXRXL", end = "XRLXXRRLX"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= start.length <= 10^4
     * start.length == end.length
     * Both start and end will only consist of characters in 'L', 'R', and 'X'.
     * @param start
     * @param end
     * @return
     */
    // time = O(n), space = O(1)
    public boolean canTransform(String start, String end) {
        String a = "", b = "";
        for (char c : start.toCharArray()) {
            if (c != 'X') a += c;
        }

        for (char c : end.toCharArray()) {
            if (c != 'X') b += c;
        }

        if (!a.equals(b)) return false;

        int m = start.length(), n = end.length();

        for (int i = 0, j = 0; i < m; i++, j++) {
            while (i < m && start.charAt(i) != 'L') i++;
            while (j < n && end.charAt(j) != 'L') j++;
            if (i < j) return false;
        }


        for (int i = 0, j = 0; i < m; i++, j++) {
            while (i < m && start.charAt(i) != 'R') i++;
            while (j < n && end.charAt(j) != 'R') j++;
            if (i > j) return false;
        }
        return true;
    }
}
/**
 * XXL -> L只能向左移动，而R只能向右移动，并且只能跨过X
 * L与R的相对顺序必然不变
 * 必要条件：
 * 1. 删掉X后一定相等
 * 2. 从相对顺序上来看，L只能向左，R只能向右
 * 充分条件？
 */