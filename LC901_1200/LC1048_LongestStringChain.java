package LC901_1200;
import java.util.*;
public class LC1048_LongestStringChain {
    /**
     * Given a list of words, each word consists of English lowercase letters.
     *
     * Let's say word1 is a predecessor of word2 if and only if we can add exactly one letter anywhere in word1 to make
     * it equal to word2. For example, "abc" is a predecessor of "abac".
     *
     * A word chain is a sequence of words [word_1, word_2, ..., word_k] with k >= 1, where word_1 is a predecessor of
     * word_2, word_2 is a predecessor of word_3, and so on.
     *
     * Return the longest possible length of a word chain with words chosen from the given list of words.
     *
     * Input: words = ["a","b","ba","bca","bda","bdca"]
     * Output: 4
     *
     * Input: words = ["xbc","pcxbcf","xb","cxbc","pcxbc"] -> xb xbc cxbc pcxbc pcxbcf => 5
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= words.length <= 1000
     * 1 <= words[i].length <= 16
     * words[i] only consists of English lowercase letters.
     * @param words
     * @return
     */
    // time = O(n * (logn + k^2)), space = O(n)  k: the maximum possible length of a word
    public int longestStrChain(String[] words) {
        // corner case
        if (words == null || words.length == 0) return 0;

        HashMap<String, Integer> map = new HashMap<>();
        Arrays.sort(words, (o1, o2) -> o1.length() - o2.length()); // O(nlogn)
        int res = 0;

        for (String word : words) { // O(n)
            int best = 0;
            for (int i = 0; i < word.length(); i++) { // O(k)
                String prev = word.substring(0, i) + word.substring(i + 1); // O(k)
                best = Math.max(best, map.getOrDefault(prev, 0) + 1);
            }
            map.put(word, best);
            res = Math.max(res, best);
        }
        return res;
    }
}
/**
 * HashMap在这里是统计到当前这个key string为止，已经累计到的longest possible chain's length
 * 先根据上一个word可能出现的情况，即比当前word少一个char的情况，遍历所有可能出现的情况，找出可能存在的最长possible chain的length，
 * 然后与res去比较找到全局最大值。
 * 同时注意为了能够做到从短到长的推演，必须先根据word长度对string array先进行排序！
 */
