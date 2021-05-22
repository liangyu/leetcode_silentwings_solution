package LC1201_1500;
import java.util.*;
public class LC1423_MaximumPointsYouCanObtainfromCards {
    /**
     * There are several cards arranged in a row, and each card has an associated number of points The points are given
     * in the integer array cardPoints.
     *
     * In one step, you can take one card from the beginning or from the end of the row. You have to take exactly k
     * cards.
     *
     * Your score is the sum of the points of the cards you have taken.
     *
     * Given the integer array cardPoints and the integer k, return the maximum score you can obtain.
     *
     * Input: cardPoints = [1,2,3,4,5,6,1], k = 3
     * Output: 12
     *
     * Constraints:
     *
     * 1 <= cardPoints.length <= 10^5
     * 1 <= cardPoints[i] <= 10^4
     * 1 <= k <= cardPoints.length
     * @param cardPoints
     * @param k
     * @return
     */
    public int maxScore(int[] cardPoints, int k) {
        // corner case
        if (cardPoints == null || cardPoints.length == 0 || k <= 0) return 0;

        int n = cardPoints.length;
        int sum = 0;
        for (int c : cardPoints) sum += c;

        int res = 0;
        for (int i = 0; i <= k; i++) {
            int window = calSum(cardPoints, i, i + n - k - 1);
            res = Math.max(res, sum - window);
        }
        return res;
    }

    private int calSum(int[] nums, int start, int end) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            sum += nums[i];
        }
        return sum;
    }
}
