package LC901_1200;
import java.util.*;
public class LC991_BrokenCalculator {
    /**
     * On a broken calculator that has a number showing on its display, we can perform two operations:
     *
     * Double: Multiply the number on the display by 2, or;
     * Decrement: Subtract 1 from the number on the display.
     * Initially, the calculator is displaying the number X.
     *
     * Return the minimum number of operations needed to display the number Y.
     *
     * Input: X = 2, Y = 3
     * Output: 2
     *
     * Note:
     *
     * 1 <= X <= 10^9
     * 1 <= Y <= 10^9
     *
     * @param X
     * @param Y
     * @return
     */
    // time = O(logY), space = O(1)
    public int brokenCalc(int X, int Y) {
        // corner case
        if (X == Y) return 0;

        int count = 0;
        while (Y > X) {
            if (Y % 2 == 1) Y++;
            else Y /= 2;
            count++;
        }
        return count + X - Y;
    }
}
