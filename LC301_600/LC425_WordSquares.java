package LC301_600;
import java.util.*;
public class LC425_WordSquares {
    /**
     * Given an array of unique strings words, return all the word squares you can build from words. The same word from
     * words can be used multiple times. You can return the answer in any order.
     *
     * A sequence of strings forms a valid word square if the kth row and column read the same string, where
     * 0 <= k < max(numRows, numColumns).
     *
     * For example, the word sequence ["ball","area","lead","lady"] forms a word square because each word reads the same
     * both horizontally and vertically.
     *
     * Input: words = ["area","lead","wall","lady","ball"]
     * Output: [["ball","area","lead","lady"],["wall","area","lead","lady"]]
     *
     * Constraints:
     *
     * 1 <= words.length <= 1000
     * 1 <= words[i].length <= 5
     * All words[i] have the same length.
     * words[i] consists of only lowercase English letters.
     * All words[i] are unique.
     * @param words
     * @return
     */
    // time = O(n * 26^k), space = O(n * k)  n: number of words, k: the length of a single word
    public List<List<String>> wordSquares(String[] words) {
        List<List<String>> res = new ArrayList<>();

        int n = words[0].length();

        // built HashMap
        HashMap<String, List<String>> map = new HashMap<>();
        for (String word : words) {
            for (int i = 0; i < n; i++) {
                String prefix = word.substring(0, i); // 注意这里是(0,i),空String作为key，val就是所有可能的单词word!!!
                map.putIfAbsent(prefix, new ArrayList<>());
                map.get(prefix).add(word);
            }
        }

        dfs(0, new ArrayList<>(), words, map, res);
        return res;
    }

    private void dfs(int row, List<String> square, String[] words, HashMap<String, List<String>> map, List<List<String>> res) {
        int n = words[0].length();
        // base case
        if (row == n) {
            res.add(new ArrayList<>(square));
            return;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row; i++) {
            sb.append(square.get(i).charAt(row));
        }

        String prefix = sb.toString();
        for (String word : map.getOrDefault(prefix, new ArrayList<>())) {
            square.add(word);
            dfs(row + 1, square, words, map, res);
            square.remove(square.size() - 1);
        }
    }
}
/**
 * dfs 搜索 => 挑选5个单词
 * 第一行随便选，但从第2行开始就有约束了，前1个字母是固定的，以此类推
 * for i-th row, find a word whose prefix [0: i-1] = square[0:i-1][i]
 * 提前建立hash表，将所有单词的所有前缀作为key，映射到对应的单词 => HashMap
 */