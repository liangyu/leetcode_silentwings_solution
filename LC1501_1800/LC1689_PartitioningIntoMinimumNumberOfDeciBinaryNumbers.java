package LC1501_1800;
import java.util.*;
public class LC1689_PartitioningIntoMinimumNumberOfDeciBinaryNumbers {
    /**
     * A decimal number is called deci-binary if each of its digits is either 0 or 1 without any leading zeros.
     * For example, 101 and 1100 are deci-binary, while 112 and 3001 are not.
     *
     * Given a string n that represents a positive decimal integer, return the minimum number of positive deci-binary
     * numbers needed so that they sum up to n.
     *
     * Input: n = "32"
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= n.length <= 10^5
     * n consists of only digits.
     * n does not contain any leading zeros and represents a positive integer.
     * @param n
     * @return
     */
    // time = O(m), space = O(1)
    public int minPartitions(String n) {
        // corner case
        if (n == null || n.length() == 0) return 0;

        int max = 0;
        for (int i = 0; i < n.length(); i++) {
            int num = n.charAt(i) - '0';
            max = Math.max(max, num);
        }
        return max;
    }
}
