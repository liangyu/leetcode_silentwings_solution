package LC2101_2400;

public class LC2212_MaximumPointsinanArcheryCompetition {
    /**
     * Alice and Bob are opponents in an archery competition. The competition has set the following rules:
     *
     * Alice first shoots numArrows arrows and then Bob shoots numArrows arrows.
     * The points are then calculated as follows:
     * The target has integer scoring sections ranging from 0 to 11 inclusive.
     * For each section of the target with score k (in between 0 to 11), say Alice and Bob have shot ak and bk arrows on
     * that section respectively. If ak >= bk, then Alice takes k points. If ak < bk, then Bob takes k points.
     * However, if ak == bk == 0, then nobody takes k points.
     * For example, if Alice and Bob both shot 2 arrows on the section with score 11, then Alice takes 11 points. On the
     * other hand, if Alice shot 0 arrows on the section with score 11 and Bob shot 2 arrows on that same section, then
     * Bob takes 11 points.
     *
     * You are given the integer numArrows and an integer array aliceArrows of size 12, which represents the number of
     * arrows Alice shot on each scoring section from 0 to 11. Now, Bob wants to maximize the total number of points he
     * can obtain.
     *
     * Return the array bobArrows which represents the number of arrows Bob shot on each scoring section from 0 to 11.
     * The sum of the values in bobArrows should equal numArrows.
     *
     * If there are multiple ways for Bob to earn the maximum total points, return any one of them.
     *
     * Input: numArrows = 9, aliceArrows = [1,1,0,1,0,0,2,1,0,1,2,0]
     * Output: [0,0,0,0,1,1,0,0,1,2,3,1]
     *
     * Constraints:
     *
     * 1 <= numArrows <= 10^5
     * aliceArrows.length == bobArrows.length == 12
     * 0 <= aliceArrows[i], bobArrows[i] <= numArrows
     * sum(aliceArrows[i]) == numArrows
     * @param numArrows
     * @param aliceArrows
     * @return
     */
    // time = O(2^12), space = O(1)
    public int[] maximumBobPoints(int numArrows, int[] aliceArrows) {
        int[] res = new int[12];
        int max = 0, maxState = 0;
        for (int state = 0; state < (1 << 12); state++) {
            int sum = 0, count = 0;
            boolean flag = true;
            for (int i = 0; i < 12; i++) {
                if (((state >> i) & 1) == 1) {
                    count += aliceArrows[i] + 1;
                    sum += i;
                }
                if (count > numArrows) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                if (sum > max) {
                    max = sum;
                    maxState = state;
                }
            }
        }

        int total = 0;
        for (int i = 0; i < 12; i++) {
            if (((maxState >> i) & 1) == 1) {
                res[i] = aliceArrows[i] + 1;
                total += res[i];
            }
        }
        res[0] += numArrows - total;
        return res;
    }
}
