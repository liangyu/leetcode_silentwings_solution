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
    // S1
    // time = O(n), space = O(n)
    public boolean canTransform(String start, String end) {
        StringBuilder sb1 = new StringBuilder(), sb2 = new StringBuilder();
        for (char c : start.toCharArray()) {
            if (c != 'X') sb1.append(c);
        }
        for (char c : end.toCharArray()) {
            if (c != 'X') sb2.append(c);
        }
        if (!sb1.toString().equals(sb2.toString())) return false;

        int n = start.length();
        for (int i = 0, j = 0; i < n; i++, j++) {
            while (i < n && start.charAt(i) != 'L') i++;
            while (j < n && end.charAt(j) != 'L') j++;
            if (i < j) return false;
        }

        for (int i = 0, j = 0; i < n; i++, j++) {
            while (i < n && start.charAt(i) != 'R') i++;
            while (j < n && end.charAt(j) != 'R') j++;
            if (i > j) return false;
        }
        return true;
    }

    // S2
    // time = O(n), space = O(1)
    public boolean canTransform2(String start, String end) {
        int n = start.length(), j = 0;
        for (int i = 0; i < n; i++) {
            if (start.charAt(i) == 'X') continue;
            while (j < n && end.charAt(j) == 'X') j++;
            if (j == n) return false;
            if (start.charAt(i) != end.charAt(j)) return false;
            if (start.charAt(i) == 'L' && i < j) return false;
            if (start.charAt(i) == 'R' && i > j) return false;
            j++;
        }

        for (int i = j; i < n; i++) {
            if (end.charAt(i) != 'X') return false;
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
 * 是否充分条件？是
 * 只要满足这2个条件，一定能构造出来
 * 给出一种构造方式
 */