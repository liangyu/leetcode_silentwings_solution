package LC001_300;
import java.util.*;
public class LC271_EncodeandDecodeStrings {
    /**
     * Design an algorithm to encode a list of strings to a string. The encoded string is then sent over the network
     * and is decoded back to the original list of strings.
     *
     * Machine 1 (sender) has the function:
     *
     * string encode(vector<string> strs) {
     *   // ... your code
     *   return encoded_string;
     * }
     * Machine 2 (receiver) has the function:
     * vector<string> decode(string s) {
     *   //... your code
     *   return strs;
     * }
     * So Machine 1 does:
     *
     * string encoded_string = encode(strs);
     * and Machine 2 does:
     *
     * vector<string> strs2 = decode(encoded_string);
     * strs2 in Machine 2 should be the same as strs in Machine 1.
     *
     * Implement the encode and decode methods.
     *
     * You are not allowed to solve the problem using any serialize methods (such as eval).
     *
     * Input: dummy_input = ["Hello","World"]
     * Output: ["Hello","World"]
     *
     * Constraints:
     *
     * 1 <= strs.length <= 200
     * 0 <= strs[i].length <= 200
     * strs[i] contains any possible characters out of 256 valid ASCII characters.
     *
     *
     * Follow up: Could you write a generalized algorithm to work on any possible set of characters?
     * @param strs
     * @return
     */
    // time = O(n), space = O(n)
    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str.length()).append('/').append(str);
        }
        return sb.toString();
    }
    // time = O(n), space = O(1)
    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> res = new ArrayList<>();
        int i = 0;
        while (i < s.length()) {
            int slash = s.indexOf('/', i);
            int size = Integer.valueOf(s.substring(i, slash));
            res.add(s.substring(slash + 1, slash + size + 1));
            i = slash + size + 1;
        }
        return res;
    }
}
