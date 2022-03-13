package LC001_300;
import java.util.*;
public class LC68_TextJustification {
    /**
     * Given an array of words and a width maxWidth, format the text such that each line has exactly maxWidth characters
     * and is fully (left and right) justified.
     *
     * You should pack your words in a greedy approach; that is, pack as many words as you can in each line. Pad extra
     * spaces ' ' when necessary so that each line has exactly maxWidth characters.
     *
     * Extra spaces between words should be distributed as evenly as possible. If the number of spaces on a line do not
     * divide evenly between words, the empty slots on the left will be assigned more spaces than the slots on the right.
     *
     * For the last line of text, it should be left justified and no extra space is inserted between words.
     *
     * Note:
     *
     * A word is defined as a character sequence consisting of non-space characters only.
     * Each word's length is guaranteed to be greater than 0 and not exceed maxWidth.
     * The input array words contains at least one word.
     *
     * Input: words = ["This", "is", "an", "example", "of", "text", "justification."], maxWidth = 16
     * Output:
     * [
     *    "This    is    an",
     *    "example  of text",
     *    "justification.  "
     * ]
     *
     * Constraints:
     *
     * 1 <= words.length <= 300
     * 1 <= words[i].length <= 20
     * words[i] consists of only English letters and symbols.
     * 1 <= maxWidth <= 100
     * words[i].length <= maxWidth
     * @param words
     * @param maxWidth
     * @return
     */
    // time = O(n), space = O(n)   n: 数组 words 中所有字符串的长度之和
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> res = new ArrayList<>();
        int n = words.length;
        for (int i = 0; i < n; i++) {
            int j = i, count = 0;
            while (j < n && count <= maxWidth) {
                if (count == 0) count += words[j].length();
                else count += 1 + words[j].length(); // 前面要有个空格！
                j++;
            }
            j--; // 把最后一次出while loop时加的j++先还原
            // words[i:j]
            if (count > maxWidth) {
                count -= 1 + words[j].length(); // 最后一个越界的单词吐出来
                j--; // 退掉最后一个越界单词的影响
            }

            if (j == n - 1) {  // 最后一行正常写
                StringBuilder sb = new StringBuilder();
                for (int k = i; k <= j; k++) {
                    sb.append(words[k]).append(" ");
                }
                sb.setLength(sb.length() - 1);
                sb.append(addSpaces(maxWidth - sb.length())); // 别忘了最后还要加空格填满行宽
                res.add(sb.toString());
            } else res.add(printLine(words, i, j, maxWidth));
            i = j; // 处理下一行
        }
        return res;
    }

    private String printLine(String[] words, int a, int b, int maxWidth) {
        if (a == b) return words[a] + addSpaces(maxWidth - words[a].length());

        int totalLetters = 0;
        for (int i = a; i <= b; i++) {
            totalLetters += words[i].length();
        }
        int spaces = (maxWidth - totalLetters) / (b - a);
        int k = maxWidth - totalLetters - spaces * (b - a);

        StringBuilder sb = new StringBuilder();
        for (int i = a; i < a + k; i++) {
            sb.append(words[i]).append(addSpaces(spaces + 1));
        }
        for (int i = a + k; i < b; i++) { //
            sb.append(words[i]).append(addSpaces(spaces)); // b后面不需要加空格，拿出来特殊处理，这里只遍历到b-1行
        }
        sb.append(words[b]); // 单独处理第b行，后面不用加空格！
        return sb.toString();
    }

    private String addSpaces(int k) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < k; i++) sb.append(" ");
        return sb.toString();
    }
}
/**
 * 两端对齐
 * 模拟，照着思路走一遍
 * "this is an example" 超过行宽限制，example吐出来
 * 总的行宽-单词的长度 = 空格
 * 5个间隙，多了3个空格，前3个间隙就要多一个空格
 * |xx  xx  xx xx xx xx|
 * 6 / 4 = 1 ... 2
 * 如果是最后一行，就不需要做两端对齐，正常输出即可，空格都放到最后面。
 */