package LC2101_2400;
import java.util.*;
public class LC2306_NamingaCompany {
    /**
     * You are given an array of strings ideas that represents a list of names to be used in the process of naming a
     * company. The process of naming a company is as follows:
     *
     * Choose 2 distinct names from ideas, call them ideaA and ideaB.
     * Swap the first letters of ideaA and ideaB with each other.
     * If both of the new names are not found in the original ideas, then the name ideaA ideaB (the concatenation of
     * ideaA and ideaB, separated by a space) is a valid company name.
     * Otherwise, it is not a valid name.
     * Return the number of distinct valid names for the company.
     *
     * Input: ideas = ["coffee","donuts","time","toffee"]
     * Output: 6
     *
     * Input: ideas = ["lack","back"]
     * Output: 0
     *
     * Constraints:
     *
     * 2 <= ideas.length <= 5 * 10^4
     * 1 <= ideas[i].length <= 10
     * ideas[i] consists of lowercase English letters.
     * All the strings in ideas are unique.
     * @param ideas
     * @return
     */
    // time = O(26n), space = O(n)
    public long distinctNames(String[] ideas) {
        HashSet<String> set = new HashSet<>();
        for (String x : ideas) set.add(x);

        int[][] dp = new int[26][26];
        for (String idea : ideas) {
            char[] chars = idea.toCharArray();
            int a = chars[0] - 'a';
            for (int i = 0; i < 26; i++) {
                chars[0] = (char)(i + 'a');
                String s = String.valueOf(chars);
                if (!set.contains(s)) dp[a][i]++;
            }
        }

        long res = 0;
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                res += dp[i][j] * dp[j][i];
            }
        }
        return res;
    }

    // S2
    // time = O(n), space = O(n)
    public long distinctNames2(String[] ideas) {
        HashSet<String>[] sets = new HashSet[26];
        for (int i = 0; i < 26; i++) sets[i] = new HashSet<>();
        for (String idea : ideas) {
            char c = idea.charAt(0);
            sets[c - 'a'].add(idea.substring(1));
        }

        long res = 0;
        for (int i = 0; i < 26; i++) {
            for (int j = i + 1; j < 26; j++) {
                long x = sets[i].size();
                long y = sets[j].size();

                int k = 0;
                for (String s : sets[i]) {
                    if (sets[j].contains(s)) k++;
                }
                res += (x - k) * (y - k) * 2;
            }
        }
        return res;
    }
}
/**
 * f(i,j): 有多少个首字母为i的字符串，将首字母换成换成j后未出现过
 * {a}: all original suf words starting with a
 * A is from {a}, but cannot be in {b}
 * B is from {b}, but cannot be in {a}
 * aA + bB => aB + bA
 * {a} size = x
 * {b} size = y
 * {a}{b} common elements size = k
 * (x-k)*(y-k)
 */
