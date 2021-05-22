package LC001_300;
import java.util.*;
public class LC127_WordLadder {
    /**
     * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words
     * beginWord -> s1 -> s2 -> ... -> sk such that:
     *
     * Every adjacent pair of words differs by a single letter.
     * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
     * sk == endWord
     * Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest
     * transformation sequence from beginWord to endWord, or 0 if no such sequence exists.
     *
     * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= beginWord.length <= 10
     * endWord.length == beginWord.length
     * 1 <= wordList.length <= 5000
     * wordList[i].length == beginWord.length
     * beginWord, endWord, and wordList[i] consist of lowercase English letters.
     * beginWord != endWord
     * All the words in wordList are unique.
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    // time = O(V + E), space = O(k * n)
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        // corner case
        if (beginWord == null || endWord == null) return 0;

        HashSet<String> dict = new HashSet<>(wordList);
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        int minLen = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                String cur = queue.poll();
                if (cur.equals(endWord)) return minLen;
                List<String> nexts = convert(cur, dict);
                for (String next : nexts) {
                    queue.offer(next);
                    dict.remove(next);
                }
            }
            minLen++;
        }
        return 0;
    }

    private List<String> convert(String cur, HashSet<String> dict) {
        List<String> nexts = new ArrayList<>();
        char[] chars = cur.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char temp = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                chars[i] = c;
                String str = String.valueOf(chars);
                if (i != temp && dict.contains(str)) nexts.add(str);
            }
            chars[i] = temp;
        }
        return nexts;
    }
}
