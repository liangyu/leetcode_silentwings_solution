package LC2101_2400;
import java.util.*;
public class LC2300_SuccessfulPairsofSpellsandPotions {
    /**
     * You are given two positive integer arrays spells and potions, of length n and m respectively, where spells[i]
     * represents the strength of the ith spell and potions[j] represents the strength of the jth potion.
     *
     * You are also given an integer success. A spell and potion pair is considered successful if the product of their
     * strengths is at least success.
     *
     * Return an integer array pairs of length n where pairs[i] is the number of potions that will form a successful
     * pair with the ith spell.
     *
     * Input: spells = [5,1,3], potions = [1,2,3,4,5], success = 7
     * Output: [4,0,3]
     *
     * Input: spells = [3,1,2], potions = [8,5,8], success = 16
     * Output: [2,0,2]
     *
     * Constraints:
     *
     * n == spells.length
     * m == potions.length
     * 1 <= n, m <= 10^5
     * 1 <= spells[i], potions[i] <= 10^5
     * 1 <= success <= 10^10
     * @param spells
     * @param potions
     * @param success
     * @return
     */
    // time = O(nlogn), space = O(1)
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        Arrays.sort(potions);

        int m = spells.length, n = potions.length;
        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            int left = 0, right = n - 1;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if ((long) potions[mid] * spells[i] < success) left = mid + 1;
                else right = mid;
            }
            res[i] = (long) potions[left] * spells[i] >= success ? n - left : 0;
        }
        return res;
    }
}
