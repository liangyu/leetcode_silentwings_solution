package LC601_900;
import java.util.*;
public class LC648_ReplaceWords {
    /**
     * In English, we have a concept called root, which can be followed by some other word to form another longer word
     * - let's call this word successor. For example, when the root "an" is followed by the successor word "other", we
     * can form a new word "another".
     *
     * Given a dictionary consisting of many roots and a sentence consisting of words separated by spaces, replace all
     * the successors in the sentence with the root forming it. If a successor can be replaced by more than one root,
     * replace it with the root that has the shortest length.
     *
     * Return the sentence after the replacement.
     *
     * Input: dictionary = ["cat","bat","rat"], sentence = "the cattle was rattled by the battery"
     * Output: "the cat was rat by the bat"
     *
     * Constraints:
     *
     * 1 <= dictionary.length <= 1000
     * 1 <= dictionary[i].length <= 100
     * dictionary[i] consists of only lower-case letters.
     * 1 <= sentence.length <= 10^6
     * sentence consists of only lower-case letters and spaces.
     * The number of words in sentence is in the range [1, 1000]
     * The length of each word in sentence is in the range [1, 1000]
     * Every two consecutive words in sentence will be separated by exactly one space.
     * sentence does not have leading or trailing spaces.
     * @param dictionary
     * @param sentence
     * @return
     */
    // time = O(m + n), space = O(m + n)
    public String replaceWords(List<String> dictionary, String sentence) {
        TrieNode root = new TrieNode();
        for (String s : dictionary) { // O(m)
            TrieNode node = root;
            for (char c : s.toCharArray()) {
                if (node.next[c - 'a'] == null) {
                    node.next[c - 'a'] = new TrieNode();
                }
                node = node.next[c - 'a'];
            }
            node.isEnd = true;
        }

        String[] arr = sentence.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            TrieNode node = root;
            int i = 0;
            boolean flag = false;
            for (i = 0; i < s.length(); i++) {
                if (node.next[s.charAt(i) - 'a'] == null) break;
                else node = node.next[s.charAt(i) - 'a'];
                if (node.isEnd) {
                    flag = true;
                    break;
                }
            }
            if (flag) sb.append(s.substring(0, i + 1)).append(" ");
            else sb.append(s).append(" ");
        }
        return sb.substring(0, sb.length() - 1).toString();
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
