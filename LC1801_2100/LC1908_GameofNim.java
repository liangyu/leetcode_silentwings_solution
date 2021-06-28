package LC1801_2100;
import java.util.*;
public class LC1908_GameofNim {
    /**
     * Alice and Bob take turns playing a game with Alice starting first.
     *
     * In this game, there are n piles of stones. On each player's turn, the player should remove any positive number
     * of stones from a non-empty pile of his or her choice. The first player who cannot make a move loses, and the
     * other player wins.
     *
     * Given an integer array piles, where piles[i] is the number of stones in the ith pile, return true if Alice wins,
     * or false if Bob wins.
     *
     * Both Alice and Bob play optimally.
     *
     * Input: piles = [1]
     * Output: true
     *
     * Constraints:
     *
     * n == piles.length
     * 1 <= n <= 7
     * 1 <= piles[i] <= 7
     *
     *
     * Follow-up: Could you find a linear time solution?
     * @param piles
     * @return
     */
    // S1: dfs
    // time = O(n^2), space = O(n)
    HashMap<String, Boolean> map = new HashMap<>();
    public boolean nimGame(int[] piles) {
        // corner case
        if (piles == null || piles.length == 0) return false;

        if (map.containsKey(helper(piles))) return map.get(helper(piles));
        for (int i = 0; i < piles.length; i++) { // O(n)
            if (piles[i] == 0) continue;
            for (int j = 1; j <= piles[i]; j++) { // O(7)
                piles[i] -= j;
                if (!nimGame(piles)) {
                    piles[i] += j;
                    map.put(helper(piles), true);
                    return true;
                }
                piles[i] += j;
            }
        }
        map.put(helper(piles), false);
        return false;
    }

   private String helper(int[] piles) {
        StringBuilder sb = new StringBuilder();
        for (int n : piles) sb.append(n);
        return sb.toString();
   }

   // S2: Math
    // time = O(n), space = O(1)
   public boolean nimGame2(int[] piles) {
       // corner case
       if (piles == null || piles.length == 0) return false;

       int sum = 0;
       for (int num : piles) sum ^= num;
       return sum != 0;
   }
}
/**
 * There are only 2 states
 * a. the xor of all piles=0
 * b. the xor of all piles != 0
 * A player can always change the state form a->b, or from b->a in each move
 * The final state (the player must lose) should be state a (the xor of all piles)
 * -> If the player starts with state b, he/she wins
 * -> else he/she loses
 * eg. If xor(piles)=0
 * ->no matter what Alice moves, Bob changes a pile to make xor(piles)=0
 * eg. If xor(piles)!=0
 * ->Alices makes xor(piles)=0 in 1 move, and Bob must loses (Bob starts with xor(piles)=0)
 * Note:
 * xor(piles) alway change for each move, so if currently xor(piles)=0, then after a move, it must not be 0
 * Conclusion:
 * If xor(piles)=0, then Alice loses, else she wins.
 */
