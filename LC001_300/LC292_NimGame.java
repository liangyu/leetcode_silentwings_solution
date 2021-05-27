package LC001_300;
import java.util.*;
public class LC292_NimGame {
    /**
     * You are playing the following Nim Game with your friend:
     *
     * Initially, there is a heap of stones on the table.
     * You and your friend will alternate taking turns, and you go first.
     * On each turn, the person whose turn it is will remove 1 to 3 stones from the heap.
     * The one who removes the last stone is the winner.
     * Given n, the number of stones in the heap, return true if you can win the game assuming both you and your friend
     * play optimally, otherwise return false.
     *
     * Input: n = 4
     * Output: false
     *
     * Constraints:
     *
     * 1 <= n <= 2^31 - 1
     * @param n
     * @return
     */
    // time = O(1), space = O(1)
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }
}
