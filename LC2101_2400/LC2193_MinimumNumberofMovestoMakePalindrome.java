package LC2101_2400;
import java.util.*;
public class LC2193_MinimumNumberofMovestoMakePalindrome {
    /**
     * You are given a string s consisting only of lowercase English letters.
     *
     * In one move, you can select any two adjacent characters of s and swap them.
     *
     * Return the minimum number of moves needed to make s a palindrome.
     *
     * Note that the input will be generated such that s can always be converted to a palindrome.
     *
     * Input: s = "aabb"
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= s.length <= 2000
     * s consists only of lowercase English letters.
     * s can be converted to a palindrome using a finite number of moves.
     * @param s
     * @return
     */
    // S1: greedy
    // time = O(n^2), space = O(n)
    public int minMovesToMakePalindrome(String s) {
        char[] chars = s.toCharArray();
        int n = chars.length, count = 0; // how many left-pair characters have been processed
        int res = 0;
        for (int i = 0; i < n / 2; i++) {
            int j = n - 1 - count;
            while (chars[j] != chars[i]) j--;
            if (i != j) {
                int k = n - 1 - count - j;
                res += k;
                while (k-- > 0) {
                    swap(chars, j, j + 1);
                    j++;
                }
                count++;
            } else { // i == j ->
                int k = n / 2 - j;
                res += k;
            }
        }
        return res;
    }

    private void swap(char[] chars, int i, int j) {
        char t = chars[i];
        chars[i] = chars[j];
        chars[j] = t;
    }

    // S2: reverse pairs
    // time = O(nlogn), space = O(n)
    public int minMovesToMakePalindrome2(String s) {
        int n = s.length(), count = 0, res = 0;
        HashMap<Character, Deque<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            map.putIfAbsent(s.charAt(i), new LinkedList<>());
            map.get(s.charAt(i)).offerLast(i);
        }

        List<Integer> arr = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (map.get(s.charAt(i)).isEmpty()) continue;
            else if (map.get(s.charAt(i)).size() == 1) {
                res += i + (n / 2 - count) - n / 2;
                map.get(s.charAt(i)).pollLast();
            } else {
                arr.add(map.get(s.charAt(i)).peekLast());
                map.get(s.charAt(i)).pollFirst();
                map.get(s.charAt(i)).pollLast();
                res += i - count;
                count++;
            }
        }

        Collections.reverse(arr);
        int[] nums = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++) nums[i] = arr.get(i);
        res += reversePairs(nums);
        return res;
    }

    private int reversePairs(int[] nums) {
        int n = nums.length;
        int[] sorted = nums.clone();
        return helper(nums, sorted, 0, n - 1);
    }

    private int helper(int[] nums, int[] sorted, int a, int b) {
        if (a >= b) return 0;

        int res = 0;
        int mid = a + (b - a) / 2;
        res += helper(nums, sorted, a, mid);
        res += helper(nums, sorted, mid + 1, b);

        for (int j = mid + 1; j <= b; j++) {
            int idx = upperBound(sorted, a, mid, (long)nums[j]);
            res += mid - idx + 1;
        }
        Arrays.sort(sorted, a, b + 1);
        return res;
    }

    private int upperBound(int[] sorted, int left, int right, long target) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (sorted[mid] <= target) left = mid + 1;
            else right = mid;
        }
        return sorted[left] > target ? left : left + 1;
    }

    // S3
    // time = O(n^2), space = O(1)
    public int minMovesToMakePalindrome3(String s) {
        int res = 0;
        while (s.length() > 0) {
            int i = s.indexOf(s.charAt(s.length() - 1));
            if (i == s.length() - 1) res += i / 2;
            else {
                res += i;
                s = s.substring(0, i) + s.substring(i + 1);
            }
            s = s.substring(0, s.length() - 1);
        }
        return res;
    }
}
/**
 * oooxoooxooo
 * "letelt" -> 到底把哪一个配对拉到最外面呢？
 * l -> etet -> tt
 * x..y..y..x...
 * x..y..x..y... -> x or y to the outside
 * x ..(a).. y..(b).. x ..(c).. y ..(d)..
 * xy.....yx   c + d + 1 + a + d
 * yx.....xy   1 + a + d + c + d
 * x ..(a).. x..(b).. y ..(c).. y ..(d)..
 * xy.....yx   b + c + d + 2 + a + b + d
 * yx.....xy   2 + a + b + d + b + c + d
 * 落单的字母
 * 如果有一种字母出现频次是奇数个 => 放中间
 * w........
 * waabb => wabba => abwba  4
 * waabb => aawbb => abwba  6
 * 先忽略掉w，剩下该干嘛干嘛，最后再移动w => i -> n/2 位置
 *
 * S2: reverse pairs
 * 0123456789
 * abyyxccaxb
 * 1. 将所有配对字符的左半边，按照出场顺序都移动到s的左侧
 * 2. 确定中心字符需要的交换次数 i+(n/2-count) - n/2 = i - count
 * 3. 将此事s右侧的substring按照左侧的顺序重新调整
 * abyy xcc axb
 * ^^^  ^^
 *       36789
 * abyxc|ycaxb
 *       cxyba
 *       68397 => 3
 * s -> t
 * 14325 -> 12345
 * (4,3)
 * (4,2)
 * (3,2)
 * the minimum adjacent swaps to make it sorted = # of reverse pairs
 */
