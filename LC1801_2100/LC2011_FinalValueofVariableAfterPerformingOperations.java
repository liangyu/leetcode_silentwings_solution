package LC1801_2100;

public class LC2011_FinalValueofVariableAfterPerformingOperations {
    /**
     * There is a programming language with only four operations and one variable X:
     *
     * ++X and X++ increments the value of the variable X by 1.
     * --X and X-- decrements the value of the variable X by 1.
     * Initially, the value of X is 0.
     *
     * Given an array of strings operations containing a list of operations, return the final value of X after
     * performing all the operations.
     *
     * Input: operations = ["--X","X++","X++"]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= operations.length <= 100
     * operations[i] will be either "++X", "X++", "--X", or "X--".
     * @param operations
     * @return
     */
    // time = O(n), space = O(1)
    public int finalValueAfterOperations(String[] operations) {
        // corner case
        if (operations == null || operations.length == 0) return 0;

        int x = 0;
        for (String s : operations) {
            if (s.charAt(1) == '+') x++;
            else x--;
        }
        return x;
    }
}
