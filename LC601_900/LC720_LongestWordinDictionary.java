package LC601_900;
import java.util.*;
public class LC720_LongestWordinDictionary {
    /**
     * Given an array of strings words representing an English Dictionary, return the longest word in words that can be
     * built one character at a time by other words in words.
     *
     * If there is more than one possible answer, return the longest word with the smallest lexicographical order. If
     * there is no answer, return the empty string.
     *
     * Input: words = ["w","wo","wor","worl","world"]
     * Output: "world"
     *
     * Constraints:
     *
     * 1 <= words.length <= 1000
     * 1 <= words[i].length <= 30
     * words[i] consists of lowercase English letters.
     * @param words
     * @return
     */
    // time = O(nlogn), space = O(n)
    public String longestWord(String[] words) {
        Arrays.sort(words, (o1, o2) -> o1.length() != o2.length() ? o2.length() - o1.length() : o1.compareTo(o2));

        TrieNode root = new TrieNode();
        for (String word : words) {
            TrieNode node = root;
            for (char c : word.toCharArray()) {
                if (node.next[c - 'a'] == null) {
                    node.next[c - 'a'] = new TrieNode();
                }
                node = node.next[c - 'a'];
            }
            node.isEnd = true;
        }

        for (String word : words) {
            TrieNode node = root;
            boolean flag = true;
            for (char c : word.toCharArray()) {
                if (node.next[c - 'a'] != null && !node.next[c - 'a'].isEnd) {
                    flag = false;
                    break;
                }
                node = node.next[c - 'a'];
            }
            if (flag) return word;
        }
        return "";
    }

    private class TrieNode {
        private TrieNode[] next;
        private boolean isEnd;
        public TrieNode() {
            this.next = new TrieNode[26];
            this.isEnd = false;
        }
    }
}
