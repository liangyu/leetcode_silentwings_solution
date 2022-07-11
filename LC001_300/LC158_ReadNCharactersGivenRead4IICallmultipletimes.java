package LC001_300;
import java.util.*;
public class LC158_ReadNCharactersGivenRead4IICallmultipletimes {
    /**
     * Given a file and assume that you can only read the file using a given method read4, implement a method read to
     * read n characters. Your method read may be called multiple times.
     *
     * Note:
     *
     * Consider that you cannot manipulate the file directly. The file is only accessible for read4 but not for read.
     * The read function may be called multiple times.
     * Please remember to RESET your class variables declared in Solution, as static/class variables are persisted
     * across multiple test cases. Please see here for more details.
     * You may assume the destination buffer array, buf, is guaranteed to have enough space for storing n characters.
     * It is guaranteed that in a given test case the same buffer buf is called by read.
     *
     * Input: file = "abc", queries = [1,2,1]
     * Output: [1,2,0]
     *
     * Constraints:
     *
     * 1 <= file.length <= 500
     * file consist of English letters and digits.
     * 1 <= queries.length <= 10
     * 1 <= queries[i] <= 500
     * @param buf
     * @param n
     * @return
     */
    /**
     * The read4 API is defined in the parent class Reader4.
     *     int read4(char[] buf4);
     */
    // time = O(n), space = O(1)
    char[] readBuf = new char[4];
    int count = 0, i = 0;
    public int read(char[] buf, int n) {
        int j = 0;
        for (j = 0; j < n; j++) {
            if (count == 0) {
                count = read4(readBuf);
                i = 0;
                if (count == 0) break;
            }
            buf[j] = readBuf[i];
            i++;
            count--;
        }
        return j;
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
