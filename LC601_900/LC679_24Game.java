package LC601_900;
import java.util.*;
public class LC679_24Game {
    /**
     * You are given an integer array cards of length 4. You have four cards, each containing a number in the range
     * [1, 9]. You should arrange the numbers on these cards in a mathematical expression using the operators
     * ['+', '-', '*', '/'] and the parentheses '(' and ')' to get the value 24.
     *
     * You are restricted with the following rules:
     *
     * The division operator '/' represents real division, not integer division.
     * For example, 4 / (1 - 2 / 3) = 4 / (1 / 3) = 12.
     * Every operation done is between two numbers. In particular, we cannot use '-' as a unary operator.
     * For example, if cards = [1, 1, 1, 1], the expression "-1 - 1 - 1 - 1" is not allowed.
     * You cannot concatenate numbers together
     * For example, if cards = [1, 2, 1, 2], the expression "12 + 12" is not valid.
     * Return true if you can get such expression that evaluates to 24, and false otherwise.
     *
     * Input: cards = [4,1,8,7]
     * Output: true
     *
     * Constraints:
     *
     * cards.length == 4
     * 1 <= cards[i] <= 9
     * @param cards
     * @return
     */
    // time = O(n^2), space = O(n)
    public boolean judgePoint24(int[] cards) {
        // corner case
        if (cards == null || cards.length == 0) return false;

        List<Double> nums = new ArrayList<>();
        for (int x : cards) nums.add(x * 1.0);
        return dfs(nums);
    }

    private boolean dfs(List<Double> nums) {
        // base case
        if (nums.size() == 1) {
            return Math.abs(nums.get(0) - 24) < 1e-6;
        }

        int n = nums.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i == j) continue;
                List<Double> nums2 = new ArrayList<>();
                for (int k = 0; k < n; k++) {
                    if (k != i && k != j) nums2.add(nums.get(k));
                }
                double a = nums.get(i), b = nums.get(j);

                nums2.add(a + b);
                if (dfs(nums2)) return true;
                nums2.remove(nums2.size() - 1);

                nums2.add(a - b);
                if (dfs(nums2)) return true;
                nums2.remove(nums2.size() - 1);

                nums2.add(a * b);
                if (dfs(nums2)) return true;
                nums2.remove(nums2.size() - 1);

                if (b != 0) {
                    nums2.add(a / b);
                    if (dfs(nums2)) return true;
                    nums2.remove(nums2.size() - 1);
                }
            }
        }
        return false;
    }
}
/**
 * 本质是任选2个数，算出来把结果放回去，3个数中算24，以此类推
 * 4 1 8 7
 * 4 1 7  => 肯定会用到中间结果
 * 4 6 => 24
 * 中间结果放回去，本质就是需要用递归来实现
 */
