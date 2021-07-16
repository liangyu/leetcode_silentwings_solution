package LC1801_2100;

public class LC1925_CountSquareSumTriples {
    /**
     * A square triple (a,b,c) is a triple where a, b, and c are integers and a^2 + b^2 = c^2.
     *
     * Given an integer n, return the number of square triples such that 1 <= a, b, c <= n.
     *
     * Input: n = 5
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= n <= 250
     * @param n
     * @return
     */
    // time = O(n^2), space = O(n^2)
    public int countTriples(int n) {
        boolean[] squares = new boolean[n * n + 1];
        for (int i = 1; i <= n; i++) {
            squares[i * i] = true;
        }

        int count = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = i; i * i + j * j <= n * n; j++) {
                if (squares[i * i + j * j]) count += 2;
            }
        }
        return count;
    }
}
