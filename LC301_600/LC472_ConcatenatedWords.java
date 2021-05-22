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
    // time = O(n * k^2)), space = O(k), k: max length of the word in the words list
    public List<String> findAllConcatenatedWordsInADict2(String[] words) {
        List<String> res = new ArrayList<>();
        // corner case
        if (words == null || words.length == 0) return res;

        Arrays.sort(words, (o1, o2) -> o1.length() - o2.length()); // O(nlogn)

        Trie trie = new Trie();

        for (String word : words) { // O(n)
            if (word.length() != 0 && check(word, trie.root)) { // O(k^2)
                res.add(word);
            }
            trie.insert(word);
        }
        return res;
    }

    private boolean check(String word, TrieNode root) {
        int[] visited = new int[word.length()]; // O(k)
        return dfs(word, root, 0, visited);
    }

    private boolean dfs(String word, TrieNode root, int idx, int[] visited) {
        // base case - success
        if (idx == word.length()) return true;

        if (visited[idx] == 1) return false;

        TrieNode cur = root;
        for (int i = idx; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (cur.nexts[ch - 'a'] != null) {
                cur = cur.nexts[ch - 'a'];
                if (cur.isWord && dfs(word, root, i + 1, visited)) {
                    return true;
                }
            } else break;
        }
        visited[idx] = 1;
        return false;
    }

    private class TrieNode {
        private char ch;
        private TrieNode[] nexts;
        private boolean isWord;
        public TrieNode(char ch) {
            this.ch = ch;
            this.nexts = new TrieNode[26];
            this.isWord = false;
        }
    }

    private class Trie {
        private TrieNode root;
        public Trie() {
            root = new TrieNode('\0');
        }

        private void insert(String word) {
            TrieNode cur = root;
            for (char ch : word.toCharArray()) {
                if (cur.nexts[ch - 'a'] == null) {
                    cur.nexts[ch - 'a'] = new TrieNode(ch);
                }
                cur = cur.nexts[ch - 'a'];
            }
            cur.isWord = true;
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
