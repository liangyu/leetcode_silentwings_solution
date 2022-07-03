package LC2101_2400;
import java.util.*;
public class LC2296_DesignaTextEditor {
    /**
     * Design a text editor with a cursor that can do the following:
     * <p>
     * Add text to where the cursor is.
     * Delete text from where the cursor is (simulating the backspace key).
     * Move the cursor either left or right.
     * When deleting text, only characters to the left of the cursor will be deleted. The cursor will also remain within
     * the actual text and cannot be moved beyond it. More formally, we have that
     * 0 <= cursor.position <= currentText.length always holds.
     * <p>
     * Implement the TextEditor class:
     * <p>
     * TextEditor() Initializes the object with empty text.
     * void addText(string text) Appends text to where the cursor is. The cursor ends to the right of text.
     * int deleteText(int k) Deletes k characters to the left of the cursor. Returns the number of characters actually
     * deleted.
     * string cursorLeft(int k) Moves the cursor to the left k times. Returns the last min(10, len) characters to the
     * left of the cursor, where len is the number of characters to the left of the cursor.
     * string cursorRight(int k) Moves the cursor to the right k times. Returns the last min(10, len) characters to the
     * left of the cursor, where len is the number of characters to the left of the cursor.
     * <p>
     * Input
     * ["TextEditor", "addText", "deleteText", "addText", "cursorRight", "cursorLeft", "deleteText", "cursorLeft",
     * "cursorRight"]
     * [[], ["leetcode"], [4], ["practice"], [3], [8], [10], [2], [6]]
     * Output
     * [null, null, 4, null, "etpractice", "leet", 4, "", "practi"]
     * <p>
     * Constraints:
     * <p>
     * 1 <= text.length, k <= 40
     * text consists of lowercase English letters.
     * At most 2 * 10^4 calls in total will be made to addText, deleteText, cursorLeft and cursorRight.
     */
    // S1: StringBuilder
    StringBuilder sb;
    int p;

    public LC2296_DesignaTextEditor() {
        sb = new StringBuilder();
        p = 0;
    }

    // time = O(n), space = O(n)
    public void addText(String text) {
        sb.insert(p, text);
        p += text.length();
    }

    // time = O(n), space = O(n)
    public int deleteText(int k) {
        int len = Math.min(p, k);
        int start = p - len;
        sb.delete(start, p);
        p -= len;
        return len;
    }

    // time = O(n), space = O(n)
    public String cursorLeft(int k) {
        int len = Math.min(p, k);
        p -= len;
        int start = Math.max(0, p - 10);
        return sb.substring(start, p).toString();
    }

    // time = O(n), space = O(n)
    public String cursorRight(int k) {
        int len = Math.min(sb.length(), p + k) - p;
        p += len;
        return sb.substring(Math.max(0, p - 10), p).toString();
    }

    // S2: Stack
    class TextEditor {
        Stack<Character> stk1;
        Stack<Character> stk2;

        public TextEditor() {
            stk1 = new Stack<>();
            stk2 = new Stack<>();
        }
        // time = O(n), sapce = O(n)
        public void addText(String text) {
            for (char c : text.toCharArray()) {
                stk1.push(c);
            }
        }
        // time = O(n), sapce = O(n)
        public int deleteText(int k) {
            int count = Math.min(k, stk1.size());
            for (int i = 0; i < count; i++) {
                stk1.pop();
            }
            return count;
        }
        // time = O(n), sapce = O(n)
        public String cursorLeft(int k) {
            int count = Math.min(k, stk1.size());
            for (int i = 0; i < count; i++) {
                stk2.push(stk1.pop());
            }

            count = Math.min(10, stk1.size());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                sb.append(stk1.pop());
            }
            sb.reverse();
            for (char c : sb.toString().toCharArray()) {
                stk1.push(c);
            }
            return sb.toString();
        }
        // time = O(n), sapce = O(n)
        public String cursorRight(int k) {
            int count = Math.min(k, stk2.size());
            for (int i = 0; i < count; i++) {
                stk1.push(stk2.pop());
            }
            count = Math.min(10, stk1.size());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < count; i++) {
                sb.append(stk1.pop());
            }
            sb.reverse();
            for (char c : sb.toString().toCharArray()) {
                stk1.push(c);
            }
            return sb.toString();
        }
    }
/**
 * Your TextEditor object will be instantiated and called as such:
 * TextEditor obj = new TextEditor();
 * obj.addText(text);
 * int param_2 = obj.deleteText(k);
 * String param_3 = obj.cursorLeft(k);
 * String param_4 = obj.cursorRight(k);
 */
}
/**
 * 设计一个线性数据结构，方便插入删除
 * 赤裸裸告诉我们这可能是一个链表
 * 满足链表所有性质
 * S2: 搞2个栈，以光标为分界点
 * 删除 -> 退栈
 * 所有操作都是O(1)
 * 打印 -> 10个字符先弹出来，打印后再塞回去
 */
