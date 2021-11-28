package LC301_600;

public class LC387_FirstUniqueCharacterinaString {
    /**
     * Given a string s, find the first non-repeating character in it and return its index. If it does not exist,
     * return -1.
     *
     * Input: s = "leetcode"
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists of only lowercase English letters.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int firstUniqChar(String s) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (freq[c - 'a'] == 1) return i;
        }
        return -1;
    }
}
