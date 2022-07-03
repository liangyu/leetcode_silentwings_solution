package LC901_1200;

public class LC944_DeleteColumnstoMakeSorted {
    /**
     * You are given an array of n strings strs, all of the same length.
     *
     * The strings can be arranged such that there is one on each line, making a grid. For example, strs = ["abc", "bce",
     * "cae"] can be arranged as:
     *
     * abc
     * bce
     * cae
     * You want to delete the columns that are not sorted lexicographically. In the above example (0-indexed), columns 0
     * ('a', 'b', 'c') and 2 ('c', 'e', 'e') are sorted while column 1 ('b', 'c', 'a') is not, so you would delete
     * column 1.
     *
     * Return the number of columns that you will delete.
     *
     * Input: strs = ["cba","daf","ghi"]
     * Output: 1
     *
     * Constraints:
     *
     * n == strs.length
     * 1 <= n <= 100
     * 1 <= strs[i].length <= 1000
     * strs[i] consists of lowercase English letters.
     * @param strs
     * @return
     */
    // time = O(m * n), space = O(1)
    public int minDeletionSize(String[] strs) {
        int m = strs.length, n = strs[0].length();
        int count = 0;
        for (int j = 0; j < n; j++) {
            for (int i = 0; i < m - 1; i++) {
                if (strs[i + 1].charAt(j) < strs[i].charAt(j)) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
