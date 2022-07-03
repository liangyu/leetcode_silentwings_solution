package LC2101_2400;

public class LC2287_RearrangeCharacterstoMakeTargetString {
    /**
     * You are given two 0-indexed strings s and target. You can take some letters from s and rearrange them to form
     * new strings.
     *
     * Return the maximum number of copies of target that can be formed by taking letters from s and rearranging them.
     *
     * Input: s = "ilovecodingonleetcode", target = "code"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * 1 <= target.length <= 10
     * s and target consist of lowercase English letters.
     * @param s
     * @param target
     * @return
     */
    // time = O(m + n), space = O(1)
    public int rearrangeCharacters(String s, String target) {
        int[] cnt1 = new int[26];
        int[] cnt2 = new int[26];

        int m = s.length(), n = target.length();
        for (int i = 0; i < m; i++) cnt1[s.charAt(i) - 'a']++;
        for (int i = 0; i < n; i++) cnt2[target.charAt(i) - 'a']++;

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) {
            if (cnt2[i] > 0) res = Math.min(res, cnt1[i] / cnt2[i]);
        }
        return res;
    }
}
