package LC1201_1500;
import java.util.*;
public class LC1342_NumberofStepstoReduceaNumbertoZero {
    /**
     * Given a non-negative integer num, return the number of steps to reduce it to zero. If the current number is even,
     * you have to divide it by 2, otherwise, you have to subtract 1 from it.
     *
     * Input: num = 14
     * Output: 6
     *
     * Constraints:
     *
     * 0 <= num <= 10^6
     *
     * @param num
     * @return
     */
    // time = O(logn), space = O(1)
    public int numberOfSteps (int num) {
        int count = 0;
        while (num > 0) {
            if (num % 2 == 0) num /= 2;
            else num--;
            count++;
        }
        return count;
    }
}
