package LC301_600;
import java.util.*;
public class LC388_LongestAbsoluteFilePath {
    /**
     * Suppose we have a file system that stores both files and directories. An example of one system is represented in
     * the following picture:
     *
     * Here, we have dir as the only directory in the root. dir contains two subdirectories, subdir1 and subdir2.
     * subdir1 contains a file file1.ext and subdirectory subsubdir1. subdir2 contains a subdirectory subsubdir2, which
     * contains a file file2.ext.
     *
     * In text form, it looks like this (with ⟶ representing the tab character):
     *
     * dir
     * ⟶ subdir1
     * ⟶ ⟶ file1.ext
     * ⟶ ⟶ subsubdir1
     * ⟶ subdir2
     * ⟶ ⟶ subsubdir2
     * ⟶ ⟶ ⟶ file2.ext
     * If we were to write this representation in code, it will look like this:
     * "dir\n\tsubdir1\n\t\tfile1.ext\n\t\tsubsubdir1\n\tsubdir2\n\t\tsubsubdir2\n\t\t\tfile2.ext".
     * Note that the '\n' and '\t' are the new-line and tab characters.
     *
     * Every file and directory has a unique absolute path in the file system, which is the order of directories that
     * must be opened to reach the file/directory itself, all concatenated by '/'s. Using the above example, the
     * absolute path to file2.ext is "dir/subdir2/subsubdir2/file2.ext". Each directory name consists of letters,
     * digits, and/or spaces. Each file name is of the form name.extension, where name and extension consist of letters,
     * digits, and/or spaces.
     *
     * Given a string input representing the file system in the explained format, return the length of the longest
     * absolute path to a file in the abstracted file system. If there is no file in the system, return 0.
     *
     * Input: input = "dir\n\tsubdir1\n\tsubdir2\n\t\tfile.ext"
     * Output: 20
     *
     * Constraints:
     *
     * 1 <= input.length <= 10^4
     * input may contain lowercase or uppercase English letters, a new line character '\n', a tab character '\t',
     * a dot '.', a space ' ', and digits.
     * @param input
     * @return
     */
    // time = O(n), space = O(n)
    public int lengthLongestPath(String input) {
        String[] files = input.split("\n");

        List<String> dir = new ArrayList<>();
        int res = 0;
        for (String s : files) {
            int k = 0;
            while (k < s.length() && s.charAt(k) == '\t') k++; // k tabs -> k levels
            while (dir.size() <= k) dir.add(""); // make up the room up to k, notice even when k = 0, dir has to have one element!
            dir.set(k, s.substring(k));
            if (dir.get(k).contains(".")) {  // this is a doc
                int count = 0;
                for (int i = 0; i <= k; i++) { // only reach to level k, so can't use for (String x : dir)!!!
                    count += dir.get(i).length();
                }
                count += k; // k level means needing to add k '\'
                res = Math.max(count, res);
            }
        }
        return res;
    }

    // S2: stack
    // time = O(n), space = O(n)
    public int lengthLongestPath2(String input) {
        Stack<Integer> stack = new Stack<>();
        int n = input.length(), res = 0, sum = 0; // sum存的是当前路径上所有元素的长度，即栈里所有数的加和
        for (int i = 0; i < n; i++) {
            int k = 0; // k代表层数
            while (i < n && input.charAt(i) == '\t') { // 看一下当前是在第几层，数一下开头有几个制表符
                i++;
                k++;
            }
            while (k < stack.size()) sum -= stack.pop(); // 如果k比较小，就一直退栈
            int j = i; // 把当前的点找出来
            while (j < n && input.charAt(j) != '\n') j++;
            String s = input.substring(i, j);
            stack.push(s.length());
            sum += s.length();
            if (s.contains(".")) res = Math.max(res, sum + stack.size() - 1); // 所有路径字符长度+各个层级字符串之间的'\'数目！
            i = j; // i更新到j的位置，即当前在'\'上，所以下面i++可以走到下一个有效字符串路径的开头！
        }
        return res;
    }
}
/**
 * 特别注意：'\n'，'\t' 这种转义字符被认为是一个字符！
 * '\n' 换行
 * '\t' 缩进一个层级
 * 只要保留每个层级
 * 字符串数组，分别代表每个层级
 * 以后下面如果有刷新，以后必然也会跟着刷新
 * 把层级加起来，中间加上'/'
 * 输出时永远看最新的层级，后面的就把前面的给覆盖了
 */
