package LC1801_2100;
import java.util.*;
public class LC1858_LongestWordWithAllPrefixes {
    /**
     * Given an array of strings words, find the longest string in words such that every prefix of it is also in words.
     *
     * For example, let words = ["a", "app", "ap"]. The string "app" has prefixes "ap" and "a", all of which are in words.
     * Return the string described above. If there is more than one string with the same length, return the
     * lexicographically smallest one, and if no string exists, return "".
     *
     * Input: words = ["k","ki","kir","kira", "kiran"]
     * Output: "kiran"
     *
     * Constraints:
     *
     * 1 <= words.length <= 10^5
     * 1 <= words[i].length <= 10^5
     * 1 <= sum(words[i].length) <= 10^5
     * @param words
     * @return
     */
    // S1: HashSet
    // time = O(n^2), space = O(n)
    public String longestWord(String[] words) {
        // corner case
        if (words == null || words.length == 0) return "";

        Arrays.sort(words, (o1, o2) -> o1.length() - o2.length()); // O(nlogn) 一定要先按长度排序！
        HashSet<String> set = new HashSet<>();
        String res = "";
        set.add(""); // 注意：set必须先init存入一个空串，否则第一个prefix无法加入！
        for (String word : words) { // O(n)
            if (set.contains(word.substring(0, word.length() - 1))) { // O(n)
                set.add(word);
                if (res.length() < word.length()) res = word;
                else if (res.length() == word.length()) {
                    if (res.compareTo(word) > 0) res = word;
                }
            }
        }
        return res;
    }

    // S2: Trie
    // time = O(n * k), space = O(26 * k) = O(1), k: the length of the longest word in the array
    public String longestWord2(String[] words) {
        // corner case
        if (words == null || words.length == 0) return "";

        TrieNode root = new TrieNode(); // 不需要通过排序来建trie
        for (String word : words) {
            TrieNode cur = root;
            for (char ch : word.toCharArray()) {
                if (cur.nexts[ch - 'a'] == null) {
                    cur.nexts[ch - 'a'] = new TrieNode();
                }
                cur = cur.nexts[ch - 'a'];
            }
            cur.isWord = true;
        }

        String res = "";
        for (String word : words) {
            TrieNode cur = root;
            boolean flag = true; // 设置一个flag来判断是否是因为走到底而跳出for loop还是因为array中没有prefix而跳出！
            for (char ch : word.toCharArray()) {
                if (!cur.nexts[ch - 'a'].isWord) { // isWord == false意味着array里不存在当前位置的prefix，则一定不符合条件！
                    flag = false; // 打上标记，阻止进入下面的if语句进行答案筛选，这里根本不是一个备选项
                    break;
                }
                cur = cur.nexts[ch - 'a'];
            }
            if (flag && (word.length() > res.length() || (word.length() == res.length() && word.compareTo(res) < 0))) {
                res = word;
            }
        }
        return res;
    }

    private class TrieNode {
        private TrieNode[] nexts;
        private boolean isWord;
        public TrieNode() {
            this.nexts = new TrieNode[26];
            this.isWord = false;
        }
    }
}
