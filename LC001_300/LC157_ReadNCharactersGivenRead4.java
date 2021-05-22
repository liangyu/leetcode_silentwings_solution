package LC001_300;
import java.util.*;
public class LC157_ReadNCharactersGivenRead4 {
    /**
     * Given a file and assume that you can only read the file using a given method read4, implement a method to read
     * n characters.
     *
     * Method read4:
     *
     * The API read4 reads four consecutive characters from file, then writes those characters into the buffer array
     * buf4.
     *
     * The return value is the number of actual characters read.
     *
     * Note that read4() has its own file pointer, much like FILE *fp in C.
     *
     * Input: file = "abc", n = 4
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= file.length <= 500
     * file consist of English letters and digits.
     * 1 <= n <= 1000
     * @param buf
     * @param n
     * @return
     */
    // time = O(n), space = O(1)
    public int read(char[] buf, int n) {
        char[] temp = new char[4];
        int index = 0;

        while (true) {
            int count = read4(temp);
            count = Math.min(count, n - index);
            for (int i = 0; i < count; i++) buf[index++] = temp[i];
            if (index == n || count < 4) return index;
        }
    }

    // helper function
    private int read4(char[] temp) {
        char[] res = new char[4];
        int index = 0;
        for (int i = 0; i < temp.length; i++) {
            res[index++] = temp[i];
        }
        return index;
    }
}
