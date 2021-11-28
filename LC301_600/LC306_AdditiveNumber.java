package LC301_600;

public class LC306_AdditiveNumber {
    /**
     * Additive number is a string whose digits can form additive sequence.
     *
     * A valid additive sequence should contain at least three numbers. Except for the first two numbers, each subsequent
     * number in the sequence must be the sum of the preceding two.
     *
     * Given a string containing only digits '0'-'9', write a function to determine if it's an additive number.
     *
     * Note: Numbers in the additive sequence cannot have leading zeros, so sequence 1, 2, 03 or 1, 02, 3 is invalid.
     *
     * Input: "112358"
     * Output: true
     *
     * Constraints:
     *
     * num consists only of digits '0'-'9'.
     * 1 <= num.length <= 35
     * Follow up:
     * How would you handle overflow for very large input integers?
     * @param num
     * @return
     */
    // time = O(n^2), space = O(n)
    public boolean isAdditiveNumber(String num) {
        int n = num.length();
        return dfs(num, n, 0, 0, 0, 0);
    }

    private boolean dfs(String s, int n, int idx, long nextSum, long prev, int count) {
        // base case
        if (idx == n) return count >= 3; // when reach the end, check if it has at least 3 numbers to return true

        long val = 0;
        for (int i = idx; i < n; i++) {
            // check leading 0
            if (i > idx && s.charAt(idx) == '0') break; // 剪枝1: has leading 0, immediately break
            // make up the current val
            val = val * 10 + s.charAt(i) - '0'; // make up the number

            if (count >= 2) { // 剪枝2
                if (val < nextSum) continue; // continue to make up larger number
                else if (val > nextSum) break; // the number made is too large, no need to continue
            }

            // if count >= 2 -> nextSum == val; if count < 2 -> nextSum = prev + val
            // 注意：当前这个数字拼到了i位，下面的数字要从i + 1开始
            if (dfs(s, n, i + 1, prev + val, val, count + 1)) return true;
        }
        return false;
    }
}
