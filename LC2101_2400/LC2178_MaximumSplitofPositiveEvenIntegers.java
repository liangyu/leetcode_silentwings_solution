package LC2101_2400;
import java.util.*;
public class LC2178_MaximumSplitofPositiveEvenIntegers {
    /**
     * You are given an integer finalSum. Split it into a sum of a maximum number of unique positive even integers.
     *
     * For example, given finalSum = 12, the following splits are valid (unique positive even integers summing up to
     * finalSum): (2 + 10), (2 + 4 + 6), and (4 + 8). Among them, (2 + 4 + 6) contains the maximum number of integers.
     * Note that finalSum cannot be split into (2 + 2 + 4 + 4) as all the numbers should be unique.
     * Return a list of integers that represent a valid split containing a maximum number of integers. If no valid split
     * exists for finalSum, return an empty list. You may return the integers in any order.
     *
     * Input: finalSum = 12
     * Output: [2,4,6]
     *
     * Constraints:
     *
     * 1 <= finalSum <= 10^10
     * @param finalSum
     * @return
     */
    // time = O(sqrt(n)), space = O(1)
    public List<Long> maximumEvenSplit(long finalSum) {
        List<Long> res = new ArrayList<>();
        if (finalSum % 2 != 0) return res;

        long k = 2, sum = 0;
        while (sum + k <= finalSum) {
            res.add(k);
            sum += k;
            k += 2;
        }

        int n = res.size();
        res.set(n - 1, res.get(n - 1) + finalSum - sum);
        return res;
    }
}
/**
 * Take n =14
 *
 * i = 2 , crSum = 0 , list = [] (crSum + 2 <= 14 , so push it) , crSum + i = 2 , list = [2]
 * i = 4 , crSum = 2 , list = [2] (crSum + 4 <= 14 , so push it) , crSum + i = 6 , list = [2,4]
 * i = 6 , crSum = 6 , list = [2,4] (crSum + 6 <= 14 , so push it) , crSum + i = 12 , list = [2,4,6]
 * i = 8 , crSum = 12 , list = [2,4,6] (crSum + 8 > 14 , so don't push it , break the loop)
 * Now we have crSum = 12 , and we want 14 , so simply add difference (which is 14-12 = 2 ) in the last element of list
 */