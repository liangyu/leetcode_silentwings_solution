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
        int n = s.length();
        long pk = 1, M = modulo, hash = 0;
        for (int i = 0; i < k - 1; i++) {
            pk = pk * power % M;
        }

        int pos = -1;
        for (int i = n - 1; i >= 0; i--) {
            if (i + k < n) {
                hash = hash - (s.charAt(i + k) - 'a' + 1) * pk % M;
                hash = (hash + M) % M;
            }
            hash = hash * power % M;
            hash = (hash + (s.charAt(i) - 'a' + 1)) % M;
            if (i + k - 1 <= n - 1 && hash == hashValue) pos = i;
        }
        return s.substring(pos, pos + k);
    }
}
/**
 * rolling hash 裸题
 * abcdef => 0*26^5+1*26^4+2*26^3+3*26^2+4*26^1+5*26^0  26进制
 * abcd...每个字符对应数字
 * 本题高位在右边，与上面正好相反 => 从右往左来遍历
 * xxxxx [abcded] xxxxxx  sliding window
 * xxxxx [bcdefg] xxxxxx
 * 位数比较长，每次重新算不合算
 * 提前处理下幂次的运算
 * 最高位砍掉，剩下整体抬升一位，再在最后面加上新引进的字符
 * 43234-> 4323 * 10 + 1 => 14323 (注意，高位在右边，读起来要从右往左来看)
 */