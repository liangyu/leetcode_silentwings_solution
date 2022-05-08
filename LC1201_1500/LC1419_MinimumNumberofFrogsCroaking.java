package LC1201_1500;

public class LC1419_MinimumNumberofFrogsCroaking {
    /**
     * You are given the string croakOfFrogs, which represents a combination of the string "croak" from different frogs,
     * that is, multiple frogs can croak at the same time, so multiple "croak" are mixed.
     *
     * Return the minimum number of different frogs to finish all the croaks in the given string.
     *
     * A valid "croak" means a frog is printing five letters 'c', 'r', 'o', 'a', and 'k' sequentially. The frogs have to
     * print all five letters to finish a croak. If the given string is not a combination of a valid "croak" return -1.
     *
     * Input: croakOfFrogs = "croakcroak"
     * Output: 1
     *
     * Input: croakOfFrogs = "crcoakroak"
     * Output: 2
     *
     * Input: croakOfFrogs = "croakcrook"
     * Output: -1
     *
     *
     Constraints:

     1 <= croakOfFrogs.length <= 10^5
     croakOfFrogs is either 'c', 'r', 'o', 'a', or 'k'.
     * @param croakOfFrogs
     * @return
     */
    // time = O(n), space = O(1)
    public int minNumberOfFrogs(String croakOfFrogs) {
        int c = 0, r = 0, o = 0, a = 0, res = 0;

        for (char ch : croakOfFrogs.toCharArray()) {
            if (ch == 'c') c++;
            else if (ch == 'r') {
                c--;
                r++;
                if (c < 0) return -1;
            } else if (ch == 'o') {
                r--;
                o++;
                if (r < 0) return -1;
            } else if (ch == 'a') {
                o--;
                a++;
                if (o < 0) return -1;
            } else {
                a--;
                if (a < 0) return -1;
            }
            res = Math.max(res, c + r + o + a);
        }
        return c + r + o + a != 0 ? -1 : res;
    }
}
/**
 * 本题是说最多有多少个青蛙在并行地叫。
 * 4个状态，c r o a
 *
 */