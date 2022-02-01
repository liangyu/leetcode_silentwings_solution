package LC2101_2400;

public class LC2151_MaximumGoodPeopleBasedonStatements {
    /**
     * There are two types of persons:
     *
     * The good person: The person who always tells the truth.
     * The bad person: The person who might tell the truth and might lie.
     * You are given a 0-indexed 2D integer array statements of size n x n that represents the statements made by n
     * people about each other. More specifically, statements[i][j] could be one of the following:
     *
     * 0 which represents a statement made by person i that person j is a bad person.
     * 1 which represents a statement made by person i that person j is a good person.
     * 2 represents that no statement is made by person i about person j.
     * Additionally, no person ever makes a statement about themselves. Formally, we have that statements[i][i] = 2 for
     * all 0 <= i < n.
     *
     * Return the maximum number of people who can be good based on the statements made by the n people.
     *
     * Input: statements = [[2,1,2],[1,2,2],[2,0,2]]
     * Output: 2
     *
     * Input: statements = [[2,0],[0,2]]
     * Output: 1
     *
     * Constraints:
     *
     * n == statements.length == statements[i].length
     * 2 <= n <= 15
     * statements[i][j] is either 0, 1, or 2.
     * statements[i][i] == 2
     * @param statements
     * @return
     */
    // time = O(2^n), space = O(n)
    public int maximumGood(int[][] statements) {
        int n = statements.length, res = 0;
        for (int state = 0; state < (1 << n); state++) {
            if (isValid(statements, state)) res = Math.max(res, Integer.bitCount(state));
        }
        return res;
    }

    private boolean isValid(int[][] statements, int state) {
        int n = statements.length;
        int[] persons = new int[n];
        for (int i = 0; i < n; i++) {
            if (((state >> i) & 1) == 1) persons[i] = 1;
        }

        for (int i = 0; i < n; i++) {
            if (persons[i] == 0) continue;
            for (int j = 0; j < n; j++) {
                if (statements[i][j] == 2) continue;
                if (persons[j] != statements[i][j]) return false;
            }
        }
        return true;
    }

    // S1.2: bitmask + Ghosper's hack
    // time = O(2^n), space = O(n)
    public int maximumGood2(int[][] statements) {
        int n = statements.length, res = 0;
        for (int k = n; k >= 1; k--) {
            int state = (1 << k) - 1;
            while (state < (1 << n)) {
                if (isValid(statements, state)) return k;
                int c = state & -state;
                int r = state + c;
                state = (((r ^ state) >> 2) / c) | r;
            }
        }
        return res;
    }
}
/**
 * 1. good people's statements must be consistent
 * 2. good people verified must be in the combo
 * 3. bad people verified must not be in the combo
 * Ghosper's hack
 * 优先遍历那些1-bit比较多的二进制数
 */
