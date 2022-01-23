package LC301_600;
import java.util.*;
public class LC472_ConcatenatedWords {
    /**
     * Given a list of words (without duplicates), please write a program that returns all concatenated words in the
     * given list of words.
     *
     * A concatenated word is defined as a string that is comprised entirely of at least two shorter words in the given
     * array.
     *
     * @param words
     * @return
     */
    // S1: DP
    // time = O(n * k^2)), space = O(k), k: max length of the word in the words list
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        List<String> res = new ArrayList<>();
        // corner case
        if (words == null || words.length == 0) return res;

        HashSet<String> set = new HashSet<>();
        Arrays.sort(words, new Comparator<String>() {  // O(nlogn)
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });

        for (int i = 0; i < words.length; i++) { // O(n)
            if (canForm(words[i], set)) res.add(words[i]); // O(k^2)
            set.add(words[i]);
        }
        return res;
    }

    private boolean canForm(String word, HashSet<String> set) {
        if (set.size() == 0) return false;

        boolean[] dp = new boolean[word.length() + 1]; // O(k)
        dp[0] = true;

        for (int i = 0; i <= word.length(); i++) {  // O(k^2)
            for (int j = 0; j < i; j++) {
                if (!dp[j]) continue;
                if (set.contains(word.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[word.length()];
    }

    // We iterate through each word and see if it can be formed by using other words.
    // It is also obvious that a word can only be formed by words shorter than it. So we can first sort the input by
    // length of each word, and only try to form one word by using words in front of it.

    // S2: Trie
    // time = O(sum(k) * logn + sum(k^2)), space = O(sum(k) * 26), k: 单词words[i] 的长度
    TrieNode root;
    public List<String> findAllConcatenatedWordsInADict2(String[] words) {
        List<String> res = new ArrayList<>();
        root = new TrieNode();
        Arrays.sort(words, (o1, o2) -> o1.length() - o2.length()); // O(sum(k) * logn)

        for (String word : words) { // O(n)
            if (word.length() != 0 && check(word)) res.add(word);

            TrieNode node = root;
            for (char ch : word.toCharArray()) {
                if (node.next[ch - 'a'] == null) {
                    node.next[ch - 'a'] = new TrieNode();
                }
                node = node.next[ch - 'a'];
            }
            node.isEnd = true;
        }
        return res;
    }

    private boolean check(String word) {
        boolean[] memo = new boolean[word.length()];
        return dfs(word, 0, memo);
    }

    private boolean dfs(String word, int cur, boolean[] memo) {
        // base case
        if (cur == word.length()) return true;
        if (memo[cur]) return false;

        TrieNode node = root;
        for (int i = cur; i < word.length(); i++) {
            if (node.next[word.charAt(i) - 'a'] != null) {
                node = node.next[word.charAt(i) - 'a'];
                if (node.isEnd && dfs(word, i + 1, memo)) return true;
            } else break;
        }
        memo[cur] = true;
        return false;
    }

    private class TrieNode {
        private TrieNode[] next;
        private boolean isEnd;
        public TrieNode() {
            next = new TrieNode[26];
            isEnd = false;
        }
    }
}
/**
 * 本题与word break高度类似,就是word break的翻版
 * 先从小到大排个序，先看之前的单词组成的字典树跟某个word是否匹配，然后把这个word加入字典树，不断增大字典树，而不是每次重新构造trie，效率高。
 * 找比它长度小的单词放入字典树里去查看
 * 所有单词按照单词长度排个序
 * trie + dfs + pruning
 */
