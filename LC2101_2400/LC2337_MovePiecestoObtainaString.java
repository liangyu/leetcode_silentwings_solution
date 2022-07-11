package LC2101_2400;

public class LC2337_MovePiecestoObtainaString {
    /**
     * You are given two strings start and target, both of length n. Each string consists only of the characters 'L',
     * 'R', and '_' where:
     *
     * The characters 'L' and 'R' represent pieces, where a piece 'L' can move to the left only if there is a blank
     * space directly to its left, and a piece 'R' can move to the right only if there is a blank space directly to its
     * right.
     * The character '_' represents a blank space that can be occupied by any of the 'L' or 'R' pieces.
     * Return true if it is possible to obtain the string target by moving the pieces of the string start any number of
     * times. Otherwise, return false.
     *
     * Input: start = "_L__R__R_", target = "L______RR"
     * Output: true
     *
     * Input: start = "R_L_", target = "__LR"
     * Output: false
     *
     * Input: start = "_R", target = "R_"
     * Output: false
     *
     * n == start.length == target.length
     * 1 <= n <= 10^5
     * start and target consist of the characters 'L', 'R', and '_'.
     * @param start
     * @param target
     * @return
     */
    // time = O(n), space = O(1)
    public boolean canChange(String start, String target) {
        int n = start.length();
        int j = 0;
        for (int i = 0; i < n; i++) {
            if (start.charAt(i) == '_') continue;
            while (j < n && target.charAt(j) == '_') j++;
            if (j == n) return false;
            if (start.charAt(i) != target.charAt(j)) return false;
            if (start.charAt(i) == 'L' && i < j) return false;
            if (start.charAt(i) == 'R' && i > j) return false;
            j++;
        }

        for (int i = j; j < n; j++) {
            if (target.charAt(i) != '_') return false;
        }
        return true;
    }
}
/**
 * same as LC777
 * 数量相同，而且相对位置保持不变
 * 上面的L要在下面L的右边，上面的R要在下面的R的左边
 * 只要满足必要条件，一定也是充分的
 */