package LC601_900;
import java.util.*;
public class LC880_DecodedStringatIndex {
    /**
     * for some encoded string S, and an index K, find and return the K-th letter (1 indexed) in the decoded string.
     *
     * If the character read is a letter, that letter is written onto the tape.
     * If the character read is a digit (say d), the entire current tape is repeatedly written d-1 more times in total.
     *
     * Input: S = "leet2code3", K = 10
     * Output: "o"
     *
     * Input: S = "ha22", K = 5
     * Output: "h"
     *
     * Input: S = "a2345678999999999999999", K = 1
     * Output: "a"
     *
     * @param S
     * @param K
     * @return
     */
    // time = O(n), space = O(1)
    public String decodeAtIndex(String S, int K) {
        // corner case
        if (S == null || S.length() == 0 || K < 0) return "";

        int cur = 0, prev = 0;

        for (int i = 0; i < S.length(); i++) { // O(n)
            char c = S.charAt(i);
            if (Character.isDigit(c)) {
                cur *= c - '0';
                if (cur >= K) {
                    return decodeAtIndex(S, (K - 1) % prev + 1); // K is 1 indexed, so index = K - 1
                }
            } else {
                cur++;
                prev = cur;
                if (cur == K) return "" + c;
            }
        }
        return "";
    }
}
