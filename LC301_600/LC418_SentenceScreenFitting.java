package LC301_600;

public class LC418_SentenceScreenFitting {
    /**
     * Given a rows x cols screen and a sentence represented as a list of strings, return the number of times the
     * given sentence can be fitted on the screen.
     *
     * The order of words in the sentence must remain unchanged, and a word cannot be split into two lines. A single
     * space must separate two consecutive words in a line.
     *
     * Input: sentence = ["hello","world"], rows = 2, cols = 8
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= sentence.length <= 100
     * 1 <= sentence[i].length <= 10
     * sentence[i] consists of lowercase English letters.
     * 1 <= rows, cols <= 2 * 10^4
     * @param sentence
     * @param rows
     * @param cols
     * @return
     */
    // time = O(n), space = O(n)
    public int wordsTyping(String[] sentence, int rows, int cols) {
        StringBuilder sb = new StringBuilder();
        for (String s : sentence) {
            if (s.length() > cols) return 0; // 太长，任何一行也装不下，无解！
            sb.append(s).append(" ");
        }

        int idx = 0, n = sb.length();
        for (int k = 0; k < rows; k++) {
            idx += cols;
            while (sb.charAt(idx % n) != ' ') idx--; // 回退到之前的空格为止
            idx++; // 下一回从idx+1开始
        }
        return idx / n;
    }
}
/**
 * 指针，每个单词顺序处理
 * 二维的话，规模有10^8
 * 如果所有单词都特别短，相当于我要填充4 * 10^8 => TLE
 * 不是特别高效的方法
 * |xxx xxx|xxxx|xxx|xxxx x|x
 * 拆行 cols 每一行宽度
 * 套窗口，要切断字符了，就把窗口缩窄一些，回退
 * 因为要不停重复的打印句子，所以最后一个单词之后也需要空格。
 */