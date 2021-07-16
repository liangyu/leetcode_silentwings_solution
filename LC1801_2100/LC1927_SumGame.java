package LC1801_2100;

public class LC1927_SumGame {
    /**
     * Alice and Bob take turns playing a game, with Alice starting first.
     *
     * You are given a string num of even length consisting of digits and '?' characters. On each turn, a player will
     * do the following if there is still at least one '?' in num:
     *
     * Choose an index i where num[i] == '?'.
     * Replace num[i] with any digit between '0' and '9'.
     * The game ends when there are no more '?' characters in num.
     *
     * For Bob to win, the sum of the digits in the first half of num must be equal to the sum of the digits in the
     * second half. For Alice to win, the sums must not be equal.
     *
     * For example, if the game ended with num = "243801", then Bob wins because 2+4+3 = 8+0+1. If the game ended with
     * num = "243803", then Alice wins because 2+4+3 != 8+0+3.
     * Assuming Alice and Bob play optimally, return true if Alice will win and false if Bob will win.
     *
     * Input: num = "5023"
     * Output: false
     *
     * Constraints:
     *
     * 2 <= num.length <= 10^5
     * num.length is even.
     * num consists of only digits and '?'.
     * @param num
     * @return
     */
    // time = O(n), space = O(1)
    public boolean sumGame(String num) {
        int n = num.length(), left = 0, right = 0, diff = 0;
        for (int i = 0; i < n / 2; i++) {
            if (num.charAt(i) == '?') left++;
            else diff += num.charAt(i) - '0';
        }

        for (int i = n / 2; i < n; i++) {
            if (num.charAt(i) == '?') right++;
            else diff -= num.charAt(i) - '0';
        }

        // case 2
        if ((left + right) % 2 == 1) return true;

        // case 1 & 3
        if (left == right) return diff != 0;

        int round = (left - right) / 2;
        // case a
        if (diff * round >= 0) return true;
        return Math.abs(diff) != Math.abs(round * 9);
    }
}
/**
 * First we scan the whole string, and find the
 *
 * 1. Difference between left part and right part, diff = leftSum - rightSum
 * 2. The number of '?' of left part and right part, as left and right
 * It will come to the following situation:
 *
 * If there is no '?', trivial answer.
 * If the number of the '?' left is odd, Alice will 100% win as Alice will take the last step.
 * If (left == right), it will be slightly tricky as whatever Alice placed a number in the question mark, Bob can always
 * place the same number in the opporsite half to cancel the effect.
 * So if (diff == 0), Alice will definately lose
 * But if (diff != 0), say, diff > 0, Alice can place 9 on one of the left '?' and no matter what Bob do, Alice will win.
 * Vise versa.
 * if (diff != 0), Alice will definately win.
 * This is actually the same situation as (1) if there is no '?'
 * If (left != right) and (left + right) is even, say, left > right, we can deduce that:
 * we can simply consider the situation that there is actually (left - right) '?' on the left and 0 '?' on the right.
 * Because If Alice does not fill left first, and no matter how she tries to expand the abs(diff), Bob can always place
 * a number on the opposite side.
 * So the question became: you will have to fill (left - right) gaps, to fulfill the difference diff.
 * There will be round = (left - right) /2 rounds. Remember (left - right) will always be even.
 *
 * a. If the diff >= 0, while round > 0, Alice will win because there is no way for Bob to insert a negative number.
 * Same case if diff <= 0.
 *
 * b. Now diff * round < 0 case. The only situation Alice will lose, is the abs(diff) == abs(round * 9). It is because
 * whatever number Alice chose, Bob can always find a number to make the sum to 9.
 */
