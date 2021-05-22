package LC001_300;
import java.util.*;
public class LC119_PascalsTriangleII {
    /**
     * Given an integer rowIndex, return the rowIndexth (0-indexed) row of the Pascal's triangle.
     *
     * In Pascal's triangle, each number is the sum of the two numbers directly above it as shown:
     *
     * Input: rowIndex = 3
     * Output: [1,3,3,1]
     *
     * Constraints:
     *
     * 0 <= rowIndex <= 33
     *
     *
     * Follow up: Could you optimize your algorithm to use only O(rowIndex) extra space?
     * @param rowIndex
     * @return
     */
    // S1: refer to LC118
    // time = O(n^2), space = O(1)
    public List<Integer> getRow(int rowIndex) {
        List<Integer> res = new LinkedList<>();

        for (int i = 0; i <= rowIndex; i++) {
            res.add(0, 1);
            for (int j = 1; j < res.size() - 1; j++) {
                res.set(j, res.get(j) + res.get(j + 1));
            }
        }
        return res;
    }
}
