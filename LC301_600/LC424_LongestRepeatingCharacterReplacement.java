package LC301_600;

public class LC424_LongestRepeatingCharacterReplacement {
    /**
     * You are given a string s and an integer k. You can choose any character of the string and change it to any other
     * uppercase English character. You can perform this operation at most k times.
     *
     * Return the length of the longest substring containing the same letter you can get after performing the above
     * operations.
     *
     * Input: s = "ABAB", k = 2
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= s.length <= 10^5
     * s consists of only uppercase English letters.
     * 0 <= k <= s.length
     * @param s
     * @param k
     * @return
     */
    // S1: Two Pointers
    // time = O(n), space = O(1)
    public int characterReplacement(String s, int k) {
        // corner case
        if (s == null || s.length() == 0 || k < 0) return 0;

        int n = s.length(), j = 0, res = 0;
        int[] count = new int[26];
        for (int i = 0; i < n; i++) {
            while (j < n && checkOK(j, count, s, k, j - i + 1)) j++;
            res = Math.max(res, j - i);
            count[s.charAt(i) - 'A']--;
        }
        return res;
    }

    private boolean checkOK(int j, int[] count, String s, int k, int L) {
        count[s.charAt(j) - 'A']++;
        int maxCount = 0;
        for (int i = 0; i < 26; i++) maxCount = Math.max(maxCount, count[i]);
        if (L - maxCount <= k) return true;
        count[s.charAt(j) - 'A']--; // 不合适 -> count要setback
        return false;
    }

    // S2: Two Pointers -> fix right point
    // time = O(n), space = O(1)
    public int characterReplacement2(String s, int k) {
        // corner case
        if (s == null || s.length() == 0 || k < 0) return 0;

        int n = s.length(), i = 0, res = 0;
        int[] count = new int[26];
        for (int j = 0; j < n; j++) {
            count[s.charAt(j) - 'A']++;
            int maxCount = 0;
            for (int idx = 0; idx < 26; idx++) maxCount = Math.max(maxCount, count[idx]);
            while (j - i + 1 - maxCount > k) {
                count[s.charAt(i) - 'A']--;
                i++;
            }
            res = Math.max(res, j - i + 1); // 停下来的时候，i一定是ok的
        }
        return res;
    }
}
/**
 * 构造一个最长的substring, subarray
 * 尽量把k次用满，不用白不用
 * 把LC1004 call 26次，把A ~ Z都call一遍找出最长的即可
 * x x [i x x x x x x j] x
 * time = O(n)
 * LC1004就一定要固定一个字符，显得很笨重
 * 不需要预先定义是基准字符，灵活调整，动态调整
 * total L - Majority Element M <= k
 */