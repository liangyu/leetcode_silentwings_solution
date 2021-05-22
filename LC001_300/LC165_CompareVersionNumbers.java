package LC001_300;
import java.util.*;
public class LC165_CompareVersionNumbers {
    /**
     * Given two version numbers, version1 and version2, compare them.
     *
     * Version numbers consist of one or more revisions joined by a dot '.'. Each revision consists of digits and
     * may contain leading zeros. Every revision contains at least one character. Revisions are 0-indexed from left
     * to right, with the leftmost revision being revision 0, the next revision being revision 1, and so on.
     * For example 2.5.33 and 0.1 are valid version numbers.
     *
     * To compare version numbers, compare their revisions in left-to-right order. Revisions are compared using their
     * integer value ignoring any leading zeros. This means that revisions 1 and 001 are considered equal. If a version
     * number does not specify a revision at an index, then treat the revision as 0. For example, version 1.0 is less
     * than version 1.1 because their revision 0s are the same, but their revision 1s are 0 and 1 respectively,
     * and 0 < 1.
     *
     * Return the following:
     *
     * If version1 < version2, return -1.
     * If version1 > version2, return 1.
     * Otherwise, return 0.
     *
     * Input: version1 = "1.01", version2 = "1.001"
     * Output: 0
     *
     * Constraints:
     *
     * 1 <= version1.length, version2.length <= 500
     * version1 and version2 only contain digits and '.'.
     * version1 and version2 are valid version numbers.
     * All the given revisions in version1 and version2 can be stored in a 32-bit integer.
     * @param version1
     * @param version2
     * @return
     */
    // S1: split + parse
    // time = O(m + n + max(m, n)), space = O(m + n)
    public int compareVersion(String version1, String version2) {
        String[] v1 = version1.split("\\."); // O(m)
        String[] v2 = version2.split("\\."); // O(n)

        for (int i = 0; i < Math.max(v1.length, v2.length); i++) {
            int num1 = i < v1.length ? Integer.valueOf(v1[i]) : 0;
            int num2 = i < v2.length ? Integer.valueOf(v2[i]) : 0;
            if (num1 < num2) return -1;
            else if (num1 > num2) return 1;
        }
        return 0;
    }

    // S2: two pointers 最优解！！！
    // time = O(max(m, n)), space = O(1)
    public int compareVersion2(String version1, String version2) {
        int i = 0, j = 0, v1 = 0, v2 = 0;
        while (i < version1.length() || j < version2.length()) {
            v1 = 0;
            while (i < version1.length() && version1.charAt(i) != '.') {
                v1 = v1 * 10 + (version1.charAt(i) - '0');
                i++;
            }
            v2 = 0;
            while (j < version2.length() && version2.charAt(j) != '.') {
                v2 = v2 * 10 + (version2.charAt(j) - '0');
                j++;
            }

            if (v1 < v2) return -1;
            if (v1 > v2) return 1;
            i++;
            j++;
        }
        return 0;
    }
}
