package LC301_600;
import java.util.*;
public class LC393_UTF8Validation {
    /**
     * Given an integer array data representing the data, return whether it is a valid UTF-8 encoding.
     *
     * A character in UTF8 can be from 1 to 4 bytes long, subjected to the following rules:
     *
     * For a 1-byte character, the first bit is a 0, followed by its Unicode code.
     * For an n-bytes character, the first n bits are all one's, the n + 1 bit is 0, followed by n - 1 bytes with the
     * most significant 2 bits being 10.
     *
     * Note: The input is an array of integers. Only the least significant 8 bits of each integer is used to store the
     * data. This means each integer represents only 1 byte of data.
     *
     * Input: data = [197,130,1]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= data.length <= 2 * 10^4
     * 0 <= data[i] <= 255
     * @param data
     * @return
     */
    // time = O(n), space = O(1)
    public boolean validUtf8(int[] data) {
        // corner case
        if (data == null || data.length == 0) return false;

        int left = 0;
        for (int d : data) {
            if (left == 0) { // check the first byte
                if ((d >> 3) == 0b11110) left = 3;
                else if ((d >> 4) == 0b1110) left = 2;
                else if ((d >> 5) == 0b110) left = 1;
                else if ((d >> 7) == 0b0) left = 0;
                else return false;
            } else {
                if ((d >> 6) != 0b10) return false;
                left--;
            }
        }
        return left == 0;
    }
}
/**
 * how to check the prefix in bianry format?
 * (a >> k) == mask, k = 8 - len(mask)
 * eg. mask = 110, len(mask) = 3, (11000010 >> (8 - 3)) = 110 == mask
 */
