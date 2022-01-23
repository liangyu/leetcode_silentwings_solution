package LC2101_2400;

public class LC2138_DivideaStringIntoGroupsofSizek {
    /**
     * A string s can be partitioned into groups of size k using the following procedure:
     *
     * The first group consists of the first k characters of the string, the second group consists of the next k
     * 'characters of the string, and so on. Each character can be a part of exactly one group.
     * For the last group, if the string does not have k characters remaining, a character fill is used to complete the
     * group.
     * Note that the partition is done so that after removing the fill character from the last group (if it exists) and
     * concatenating all the groups in order, the resultant string should be s.
     *
     * Given the string s, the size of each group k and the character fill, return a string array denoting the
     * composition of every group s has been divided into, using the above procedure.
     *
     * Input: s = "abcdefghi", k = 3, fill = "x"
     * Output: ["abc","def","ghi"]
     *
     * Constraints:
     *
     * 1 <= s.length <= 100
     * s consists of lowercase English letters only.
     * 1 <= k <= 100
     * fill is a lowercase English letter.
     * @param s
     * @param k
     * @param fill
     * @return
     */
    // time = O(n), space = O(k)
    public String[] divideString(String s, int k, char fill) {
        int n = s.length(), len = n % k == 0 ? n / k : n / k + 1;
        String[] res = new String[len];
        int start = 0;

        for (int i = 0; i < len; i++) {
            if (i < len - 1) {
                res[i] = s.substring(start, start + k);
                start += k;
            } else {
                if (n % k == 0) res[i] = s.substring(start, start + k);
                else {
                    StringBuilder sb = new StringBuilder();
                    sb.append(s.substring(start));
                    int diff = k - n % k;
                    while (diff-- > 0) sb.append(fill);
                    res[i] = sb.toString();
                }
            }
        }
        return res;
    }
}
