package LC301_600;
import java.util.*;
public class LC575_DistributeCandies {
    /**
     * Alice has n candies, where the ith candy is of type candyType[i]. Alice noticed that she started to gain weight,
     * so she visited a doctor.
     *
     * The doctor advised Alice to only eat n / 2 of the candies she has (n is always even). Alice likes her candies
     * very much, and she wants to eat the maximum number of different types of candies while still following the
     * doctor's advice.
     *
     * Given the integer array candyType of length n, return the maximum number of different types of candies she can
     * eat if she only eats n / 2 of them.
     *
     * Input: candyType = [1,1,2,2,3,3]
     * Output: 3
     *
     * Constraints:
     *
     * n == candyType.length
     * 2 <= n <= 10^4
     * n is even.
     * -10^5 <= candyType[i] <= 10^5
     *
     * @param candyType
     * @return
     */
    // time = O(n), space = O(n);
    public int distributeCandies(int[] candyType) {
        // corner case
        if (candyType == null || candyType.length == 0) return 0;

        HashSet<Integer> set = new HashSet<>();
        for (int n : candyType) set.add(n);

        int max = candyType.length / 2;
        return Math.min(set.size(), max);
    }
}
