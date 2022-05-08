package LC301_600;
import java.util.*;
public class LC591_TagValidator {
    /**
     * Given a string representing a code snippet, implement a tag validator to parse the code and return whether it is
     * valid.
     *
     * A code snippet is valid if all the following rules hold:
     *
     * The code must be wrapped in a valid closed tag. Otherwise, the code is invalid.
     * A closed tag (not necessarily valid) has exactly the following format : <TAG_NAME>TAG_CONTENT</TAG_NAME>. Among
     * them, <TAG_NAME> is the start tag, and </TAG_NAME> is the end tag. The TAG_NAME in start and end tags should be
     * the same. A closed tag is valid if and only if the TAG_NAME and TAG_CONTENT are valid.
     * A valid TAG_NAME only contain upper-case letters, and has length in range [1,9]. Otherwise, the TAG_NAME is invalid.
     * A valid TAG_CONTENT may contain other valid closed tags, cdata and any characters (see note1) EXCEPT
     * unmatched <, unmatched start and end tag, and unmatched or closed tags with invalid TAG_NAME. Otherwise, the
     * TAG_CONTENT is invalid.
     * A start tag is unmatched if no end tag exists with the same TAG_NAME, and vice versa. However, you also need to
     * consider the issue of unbalanced when tags are nested.
     * A < is unmatched if you cannot find a subsequent >. And when you find a < or </, all the subsequent characters
     * until the next > should be parsed as TAG_NAME (not necessarily valid).
     * The cdata has the following format : <![CDATA[CDATA_CONTENT]]>. The range of CDATA_CONTENT is defined as the
     * characters between <![CDATA[ and the first subsequent ]]>.
     * CDATA_CONTENT may contain any characters. The function of cdata is to forbid the validator to parse CDATA_CONTENT,
     * so even it has some characters that can be parsed as tag (no matter valid or invalid), you should treat it as
     * regular characters.
     *
     * Input: code = "<DIV>This is the first line <![CDATA[<div>]]></DIV>"
     * Output: true
     *
     * Constraints:
     *
     * 1 <= code.length <= 500
     * code consists of English letters, digits, '<', '>', '/', '!', '[', ']', '.', and ' '.
     * @param code
     * @return
     */
    // time = O(n), space = O(n)
    public boolean isValid(String code) {
        Stack<String> stack = new Stack<>();

        int i = 0, n = code.length();
        boolean ever = false;
        while (i < n) {
            if (i + 8 < n && code.substring(i, i + 9).equals("<![CDATA[")) {  // CDATA rule
                i += 9;
                int i0 = i;
                while (i + 2 < n && !code.substring(i, i + 3).equals("]]>")) i++;
                if (i + 2 == n) return false;
                i += 3;
            } else if (i + 1 < n && code.substring(i, i + 2).equals("</")) { //
                i += 2;
                int i0 = i;
                while (i < n && code.charAt(i) != '>') i++;
                if (i == n) return false;
                String tagName = code.substring(i0, i);
                if (stack.isEmpty() || !stack.peek().equals(tagName)) return false;
                stack.pop();
                i++;
                if (stack.isEmpty() && i != n) return false; // 最后一个尾tag了，触底了
            } else if (code.charAt(i) == '<') {
                i++;
                int i0 = i;
                while (i < n && code.charAt(i) != '>') i++;
                if (i == n) return false;
                String tagName = code.substring(i0, i);
                if (!helper(tagName)) return false;
                if (!ever && i0 != 1) return false; // 第一个tag头
                ever = true;
                stack.push(tagName);
                i++;
            } else i++;
        }
        if (!stack.isEmpty()) return false;
        if (!ever) return false;
        return true;
    }

    private boolean helper(String tagName) {
        int n = tagName.length();
        if (n < 1 || n > 9) return false;
        for (char c : tagName.toCharArray()) {
            if (!Character.isUpperCase(c)) return false;
        }
        return true;
    }
}
/**
 * tag成对出现且嵌套
 * 找到tag头就入栈，找到tag尾就出栈
 * 1. tag name <________>  </________>  大写英文字母，1 <= len <= 9
 * 2. tag content ------- 任意字符(除了 < )
 *                ------- tag
 *                ------- cdata <![cdata[....]]>
 * 最外层只能有一个，不能有多个并列
 * 可以嵌套很多，每个嵌套的一对，名字必须要匹配
 * use stack
 * 用一个栈去维护当前路径上所有的tag
 * 内容直接按照两种类型去过滤
 * cdata可以使任意字符
 * tag content的话是非 < 的字符
 */