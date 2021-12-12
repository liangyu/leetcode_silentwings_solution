package LC301_600;

public class LC409_LongestPalindrome {
    /**
     * Given a string s which consists of lowercase or uppercase letters, return the length of the longest palindrome
     * that can be built with those letters.
     *
     * Letters are case sensitive, for example, "Aa" is not considered a palindrome here.
     *
     * Input: s = "abccccdd"
     * Output: 7
     *
     * Constraints:
     *
     * 1 <= s.length <= 2000
     * s consists of lowercase and/or uppercase English letters only.
     * @param s
     * @return
     */
    // time = O(n), space = O(1)
    public int longestPalindrome(String s) {
        int[] freq = new int[128];
        for (char c : s.toCharArray()) {
            freq[c]++;
        }

        boolean flag = false;
        int count = 0;
        for (int x : freq) {
            if (x % 2 == 0) count += x;
            else {
                if (!flag) {
                    count += x;
                    flag = true; // 可以出现一个奇数次
                }
                else count += x - 1;
            }
        }
        return count;
    }
}
