package LC2101_2400;

public class LC2156_FindSubstringWithGivenHashValue {
    /**
     * The hash of a 0-indexed string s of length k, given integers p and m, is computed using the following function:
     *
     * hash(s, p, m) = (val(s[0]) * p0 + val(s[1]) * p1 + ... + val(s[k-1]) * pk-1) mod m.
     * Where val(s[i]) represents the index of s[i] in the alphabet from val('a') = 1 to val('z') = 26.
     *
     * You are given a string s and the integers power, modulo, k, and hashValue. Return sub, the first substring of s
     * of length k such that hash(sub, power, modulo) == hashValue.
     *
     * The test cases will be generated such that an answer always exists.
     *
     * A substring is a contiguous non-empty sequence of characters within a string.
     *
     * Input: s = "leetcode", power = 7, modulo = 20, k = 2, hashValue = 0
     * Output: "ee"
     *
     * Constraints:
     *
     * 1 <= k <= s.length <= 2 * 10^4
     * 1 <= power, modulo <= 10^9
     * 0 <= hashValue < modulo
     * s consists of lowercase English letters only.
     * The test cases are generated such that an answer always exists.
     * @param s
     * @param power
     * @param modulo
     * @param k
     * @param hashValue
     * @return
     */
    // time = O(n), space = O(1)
    public String subStrHash(String s, int power, int modulo, int k, int hashValue) {
        long cur = 0, pk = 1;
        int n = s.length(), res = 0;
        for (int i = n - 1; i >= 0; i--) {
            cur = (cur * power + s.charAt(i) - 'a' + 1) % modulo;
            if (i + k >= n) pk = pk * power % modulo;
            else cur = (cur - (s.charAt(i + k) - 'a' + 1) * pk % modulo + modulo) % modulo;
            if (cur == hashValue) res = i;
        }
        return s.substring(res, res + k);
    }
}
