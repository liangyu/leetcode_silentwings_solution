package LC301_600;
import java.util.*;
public class LC411_MinimumUniqueWordAbbreviation {
    /**
     * A string can be abbreviated by replacing any number of non-adjacent substrings with their lengths. For example,
     * a string such as "substitution" could be abbreviated as (but not limited to):
     *
     * "s10n" ("s ubstitutio n")
     * "sub4u4" ("sub stit u tion")
     * "12" ("substitution")
     * "su3i1u2on" ("su bst i t u ti on")
     * "substitution" (no substrings replaced)
     * Note that "s55n" ("s ubsti tutio n") is not a valid abbreviation of "substitution" because the replaced
     * substrings are adjacent.
     *
     * The length of an abbreviation is the number of letters that were not replaced plus the number of substrings
     * that were replaced. For example, the abbreviation "s10n" has a length of 3 (2 letters + 1 substring) and
     * "su3i1u2on" has a length of 9 (6 letters + 3 substrings).
     *
     * Given a target string target and an array of strings dictionary, return an abbreviation of target with the
     * shortest possible length such that it is not an abbreviation of any string in dictionary. If there are multiple
     * shortest abbreviations, return any of them.
     *
     * Input: target = "apple", dictionary = ["blade"]
     * Output: "a4"
     *
     * Constraints:
     *
     * target.length == m
     * dictionary.length == n
     * 1 <= m <= 21
     * 0 <= n <= 1000
     * 1 <= dictionary[i] <= 100
     * log2(n) + m <= 21 if n > 0
     * @param target
     * @param dictionary
     * @return
     */
    // time = O(2^m * n), space = O(2^m * n)
    private int m;
    public String minAbbreviation(String target, String[] dictionary) {
        if (dictionary.length == 0) return String.valueOf(target.length());

        m = target.length();
        HashSet<String> set = new HashSet<>();
        for (String word : dictionary) {
            if (word.length() == m) set.add(word);
        }


        List<int[]> masks = new ArrayList<>();
        for (int state = 0; state < (1 << m); state++) {
            // 优先看长度比较短的缩写 -> 排序 -> 有点风险
            masks.add(new int[]{getLen(state), state});
        }
        Collections.sort(masks, (o1, o2) -> o1[0] - o2[0]);

        for (int[] x : masks) {
            int mask = x[1];
            String abbr = getAbbr(target, mask);
            boolean flag = true;

            for (String word : set) {
                String s = getAbbr(word, mask);
                if (s.equals(abbr)) {
                    flag = false;
                    break;
                }
            }
            if (flag) return abbr;
        }
        return "";
    }

    private int getLen(int mask) {
        int count = 0;
        for (int i = 0; i < m; i++) {
            if (((mask >> i) & 1) == 1) count++;
            else {
                int j = i;
                while (j < m && ((mask >> j) & 1) == 0) j++;
                count += String.valueOf(j - i).length();
                i = j - 1;
            }
        }
        return count;
    }

    private String getAbbr(String word, int mask) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m; i++) {
            if (((mask >> i) & 1) == 1) sb.append(word.charAt(i));
            else {
                int j = i;
                while (j < m && ((mask >> j) & 1) == 0) j++;
                sb.append(j - i);
                i = j - 1;
            }
        }
        return sb.toString();
    }
}
/**
 * i18n = internationalism
 * 1 <= m <= 21  -> 2^m = 10^6
 * 本质意味着2^m缩写的方法
 * 2种情况：不被缩写和被缩写
 * 无脑遍历 => bitmask => 00000 ~ 11111a
 * 11111 apple
 * 11001 ap2e
 * 00000 5
 * target -> (bitmask) -> abbr <= dictionary[i] / word
 * time = O(2^m) * n
 * log2(n) + m <= 21 if n > 0  => 2^m * n <= 2^21
 * 不管怎么缩写，长度不会改变，所以字典里长度不等的单词一定不可能有相同的缩写，不会产生冲突
 * 一种缩写只会对应一种缩写单词的长度，所以长度不等的单词可以在dict里直接pass掉
 */