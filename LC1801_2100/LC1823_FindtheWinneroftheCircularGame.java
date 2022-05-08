package LC1801_2100;
import java.util.*;
public class LC1823_FindtheWinneroftheCircularGame {
    /**
     * There are n friends that are playing a game. The friends are sitting in a circle and are numbered from 1 to n
     * in clockwise order. More formally, moving clockwise from the ith friend brings you to the (i+1)th friend for
     * 1 <= i < n, and moving clockwise from the nth friend brings you to the 1st friend.
     *
     * The rules of the game are as follows:
     *
     * Start at the 1st friend.
     * Count the next k friends in the clockwise direction including the friend you started at. The counting wraps
     * around the circle and may count some friends more than once.
     * The last friend you counted leaves the circle and loses the game.
     * If there is still more than one friend in the circle, go back to step 2 starting from the friend immediately
     * clockwise of the friend who just lost and repeat.
     * Else, the last friend in the circle wins the game.
     * Given the number of friends, n, and an integer k, return the winner of the game.
     *
     * Input: n = 5, k = 2
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= k <= n <= 500
     * @param n
     * @param k
     * @return
     */
    // S1: Math
    // time = O(n), space = O(1)
    public int findTheWinner(int n, int k) {
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res = (res + k) % i;
        }
        return res + 1;
    }

    // S2: Simulation
    // time = O(n), space = O(n)
    public int findTheWinner2(int n, int k) {
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= n; i++) nums.add(i);
        int idx = k - 1;

        while (n > 1) {
            nums.remove(idx); // remove people id = k while the real idx = k - 1!!!
            n--;
            idx += k - 1;
            if (idx < n) continue;
            else idx = (idx - n) % nums.size();
        }
        return nums.get(0);
    }
}
/**
 * In the end,n = 1,
 * the index of winner index is 0 (base-0)
 *
 * We think with one step back,
 * when n = 2,
 * the index of winner is 0 + k,
 * but we have only n peopple,
 * so the winner index is (0 + k) % 2 (base-0)
 *
 * We think with one more step back,
 * when n = 3,
 * the index of winner is f(2) + k,
 * but we have only n peopple,
 * so the winner index is (f(2) + k) % 3 (base-0)
 *
 * We think with n more step back,
 * the index of winner is f(n-1) + k,
 * but we have only n peopple,
 * so the winner index is (f(n-1) + k) % n (base-0)
 */
