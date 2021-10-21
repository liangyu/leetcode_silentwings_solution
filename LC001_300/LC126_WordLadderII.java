package LC001_300;
import java.util.*;
public class LC126_WordLadderII {
    /**
     * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words
     * beginWord -> s1 -> s2 -> ... -> sk such that:
     *
     * Every adjacent pair of words differs by a single letter.
     * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
     * sk == endWord
     * Given two words, beginWord and endWord, and a dictionary wordList, return all the shortest transformation
     * sequences from beginWord to endWord, or an empty list if no such sequence exists. Each sequence should be
     * returned as a list of the words [beginWord, s1, s2, ..., sk].
     *
     * Input: beginWord = "hit", endWord = "cog", wordList = ["hot","dot","dog","lot","log","cog"]
     * Output: [["hit","hot","dot","dog","cog"],["hit","hot","lot","log","cog"]]
     *
     * Constraints:
     *
     * 1 <= beginWord.length <= 5
     * endWord.length == beginWord.length
     * 1 <= wordList.length <= 1000
     * wordList[i].length == beginWord.length
     * beginWord, endWord, and wordList[i] consist of lowercase English letters.
     * beginWord != endWord
     * All the words in wordList are unique.
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    // time = O(V + E), space = O(n * k)  k: 单词的长度，n: wordList的长度
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        // corner case
        if (beginWord == null || endWord == null) return res;

        HashSet<String> dict = new HashSet<>(wordList);
        HashMap<String, List<String>> map = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);
        boolean isOver = false;

        // BFS
        while (!queue.isEmpty()) {
            int size = queue.size();
            HashSet<String> visited = new HashSet<>();
            while (size-- > 0) {
                String cur = queue.poll();
                List<String> nexts = convert(cur, dict);
                for (String next : nexts) {
                    if (next.equals(endWord)) isOver = true;
                    if (visited.add(next)) {
                        queue.offer(next);
                        map.putIfAbsent(next, new ArrayList<>());
                    }
                    map.get(next).add(cur);
                }
            }
            dict.removeAll(visited);
            if (isOver) {
                List<String> path = new LinkedList<>();
                path.add(endWord);
                dfs(map, path, endWord, beginWord, res);
                return res;
            }
        }
        return res;
    }

    private void dfs(HashMap<String, List<String>> map, List<String> path, String cur, String end, List<List<String>> res) {
        // base case - success
        if (cur.equals(end)) {
            res.add(new ArrayList<>(path));
            return;
        }

        List<String> nexts = map.get(cur);
        for (String next : nexts) {
            path.add(0, next);
            dfs(map, path, next, end, res);
            path.remove(0);
        }
    }

    private List<String> convert(String cur, HashSet<String> dict) {
        List<String> nexts = new ArrayList<>();
        char[] chars = cur.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char temp = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                chars[i] = c;
                String str = String.valueOf(chars);
                if (c != temp && dict.contains(str)) nexts.add(str);
            }
            chars[i] = temp;
        }
        return nexts;
    }
}
/**
 * A, {B, D}, {C}
 * 记录下前面是通过什么走到C的，用一个HashMap记录下
 * prev[C] = {B, D}
 * prev[B] = {A}
 * prev[D] = {A}
 * 所有元素，不管后面加什么，所有元素都是从beginWord开始
 * 根据哈希表肯定可以倒推
 */
