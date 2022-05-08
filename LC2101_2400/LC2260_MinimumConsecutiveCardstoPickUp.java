package LC2101_2400;
import java.util.*;
public class LC2260_MinimumConsecutiveCardstoPickUp {
    /**
     * You are given an integer array cards where cards[i] represents the value of the ith card. A pair of cards are
     * matching if the cards have the same value.
     *
     * Return the minimum number of consecutive cards you have to pick up to have a pair of matching cards among the
     * picked cards. If it is impossible to have matching cards, return -1.
     *
     * Input: cards = [3,4,2,3,4,7]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= cards.length <= 10^5
     * 0 <= cards[i] <= 10^6
     * @param cards
     * @return
     */
    // time = O(n), space = O(n)
    public int minimumCardPickup(int[] cards) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = cards.length, res = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if (map.containsKey(cards[i])) {
                res = Math.min(res, i - map.get(cards[i]) + 1);
            }
            map.put(cards[i], i);
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
}
