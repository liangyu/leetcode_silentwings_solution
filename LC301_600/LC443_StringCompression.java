package LC301_600;

public class LC443_StringCompression {
    /**
     * Given an array of characters chars, compress it using the following algorithm:
     *
     * Begin with an empty string s. For each group of consecutive repeating characters in chars:
     *
     * If the group's length is 1, append the character to s.
     * Otherwise, append the character followed by the group's length.
     * The compressed string s should not be returned separately, but instead be stored in the input character array
     * chars. Note that group lengths that are 10 or longer will be split into multiple characters in chars.
     *
     * After you are done modifying the input array, return the new length of the array.
     *
     * You must write an algorithm that uses only constant extra space.
     *
     * Input: chars = ["a","a","b","b","c","c","c"]
     * Output: Return 6, and the first 6 characters of the input array should be: ["a","2","b","2","c","3"]
     *
     * Constraints:
     *
     * 1 <= chars.length <= 2000
     * chars[i] is a lower-case English letter, upper-case English letter, digit, or symbol.
     * @param chars
     * @return
     */
    // time = O(n), space = O(1)
    public int compress(char[] chars) {
        // corner case
        if (chars == null || chars.length == 0) return 0;

        int res = 0, idx = 0;
        while (idx < chars.length) {
            char cur = chars[idx];
            int count = 0;
            while (idx < chars.length && chars[idx] == cur) {
                idx++;
                count++;
            }
            chars[res++] = cur;
            if (count > 1) {
                for (char c : String.valueOf(count).toCharArray()) {
                    chars[res++] = c;
                }
            }
        }
        return res;
    }
}
