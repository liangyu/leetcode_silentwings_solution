package LC601_900;
import java.util.*;
public class LC745_PrefixandSuffixSearch {
    /**
     * Design a special dictionary which has some words and allows you to search the words in it by a prefix and a
     * suffix.
     *
     * Implement the WordFilter class:
     *
     * WordFilter(string[] words) Initializes the object with the words in the dictionary.
     * f(string prefix, string suffix) Returns the index of the word in the dictionary which has the prefix prefix and
     * the suffix suffix. If there is more than one valid index, return the largest of them. If there is no such word
     * in the dictionary, return -1.
     *
     * Input
     * ["WordFilter", "f"]
     * [[["apple"]], ["a", "e"]]
     * Output
     * [null, 0]
     *
     * Constraints:
     *
     * 1 <= words.length <= 15000
     * 1 <= words[i].length <= 10
     * 1 <= prefix.length, suffix.length <= 10
     * words[i], prefix and suffix consist of lower-case English letters only.
     * At most 15000 calls will be made to the function f.
     * @param words
     */
    // time = O(nk^2), space = O(nk^2)
    private TrieNode root;
    public LC745_PrefixandSuffixSearch(String[] words) {
        root = new TrieNode('\0');
        for (int i = 0; i < words.length; i++) { // O(n)
            String word = words[i] + "{";
            for (int j = 0; j < word.length(); j++) { // O(k)
                TrieNode cur = root;
                cur.weight = i;
                // 把suffix放在前面去做处理，prefix则通过在原来的单词后加上分隔符，再连上一个完整单词来进行prefix的查询！
                // add "apple{apple", "pple{apple", "ple{apple", "le{apple", "e{apple", "{apple" into the Trie Tree
                for (int k = j; k < 2 * word.length() - 1; k++) { // O(k)
                    int idx = word.charAt(k % word.length()) - 'a';
                    if (cur.nexts[idx] == null) {
                        cur.nexts[idx] = new TrieNode('\0');
                    }
                    cur = cur.nexts[idx];
                    cur.weight = i;
                }
            }
        }
    }
    // time = O(k), space = O(nk^2)
    public int f(String prefix, String suffix) {
        TrieNode cur = root;
        for (char c : (suffix + '{' + prefix).toCharArray()) { // 注意：拼接的时候，这里是suffix在前！
            if (cur.nexts[c - 'a'] == null) return -1;
            cur = cur.nexts[c - 'a'];
        }
        return cur.weight;
    }

    private class TrieNode {
        private char ch;
        private TrieNode[] nexts;
        private int weight;
        public TrieNode(char ch) {
            this.ch = ch;
            this.nexts = new TrieNode[27];
            this.weight = 0;
        }
    }
}
