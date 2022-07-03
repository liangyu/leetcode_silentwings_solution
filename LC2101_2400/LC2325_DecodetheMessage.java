package LC2101_2400;
import java.util.*;
public class LC2325_DecodetheMessage {
    /**
     * You are given the strings key and message, which represent a cipher key and a secret message, respectively. The
     * steps to decode message are as follows:
     *
     * Use the first appearance of all 26 lowercase English letters in key as the order of the substitution table.
     * Align the substitution table with the regular English alphabet.
     * Each letter in message is then substituted using the table.
     * Spaces ' ' are transformed to themselves.
     * For example, given key = "happy boy" (actual key would have at least one instance of each letter in the
     * alphabet), we have the partial substitution table of ('h' -> 'a', 'a' -> 'b', 'p' -> 'c', 'y' -> 'd', 'b' -> 'e',
     * 'o' -> 'f').
     * Return the decoded message.
     *
     * Input: key = "the quick brown fox jumps over the lazy dog", message = "vkbs bs t suepuv"
     * Output: "this is a secret"
     *
     * Constraints:
     *
     * 26 <= key.length <= 2000
     * key consists of lowercase English letters and ' '.
     * key contains every letter in the English alphabet ('a' to 'z') at least once.
     * 1 <= message.length <= 2000
     * message consists of lowercase English letters and ' '.
     * @param key
     * @param message
     * @return
     */
    // time = O(m + n), space = O(n)
    public String decodeMessage(String key, String message) {
        int[] dict = new int[26];
        Arrays.fill(dict, -1);
        int id = 0;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (c == ' ') continue;
            if (dict[c - 'a'] == -1) dict[c - 'a'] = id++;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            char c = message.charAt(i);
            if (c == ' ') sb.append(c);
            else sb.append((char)(dict[c - 'a'] + 'a'));
        }
        return sb.toString();
    }
}
