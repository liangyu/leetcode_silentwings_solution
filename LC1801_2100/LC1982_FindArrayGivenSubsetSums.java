package LC1801_2100;
import java.util.*;
public class LC1982_FindArrayGivenSubsetSums {
    /**
     * You are given an integer n representing the length of an unknown array that you are trying to recover.
     * You are also given an array sums containing the values of all 2n subset sums of the unknown array
     * (in no particular order).
     *
     * Return the array ans of length n representing the unknown array. If multiple answers exist, return any of them.
     *
     * An array sub is a subset of an array arr if sub can be obtained from arr by deleting some (possibly zero or all)
     * elements of arr. The sum of the elements in sub is one possible subset sum of arr. The sum of an empty array is
     * considered to be 0.
     *
     * Note: Test cases are generated such that there will always be at least one correct answer.
     *
     * Input: n = 3, sums = [-3,-2,-1,0,0,1,2,3]
     * Output: [1,2,-3]
     * Constraints:
     *
     * 1 <= n <= 15
     * sums.length == 2^n
     * -10^4 <= sums[i] <= 10^4
     *
     * @param n
     * @param sums
     * @return
     */
    // S1
    // time = O(nklogk), space = O(n * k)
    public int[] recoverArray(int n, int[] sums) {
        // corner case
        if (sums == null || sums.length == 0) return new int[0];

        List<Integer> res = new ArrayList<>();
        List<Integer> arr = new ArrayList<>();
        for (int sum : sums) arr.add(sum); // O(k)

        if (dfs(arr, n, res)) {
            int[] ans = new int[res.size()];
            for (int i = 0; i < res.size(); i++) ans[i] = res.get(i); // O(n)
            return ans;
        }
        return new int[0];
    }

    private boolean dfs(List<Integer> arr, int n, List<Integer> res) {
        // base case
        if (n == 1) { // 只有2个元素
            if (arr.get(0) != 0 && arr.get(1) != 0) return false;
            res.add(arr.get(0) == 0 ? arr.get(1) : arr.get(0));
            return true;
        }

        int k = arr.size();
        Collections.sort(arr); // O(klogk)
        // branch 1
        int x = arr.get(k - 1) - arr.get(k - 2);
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int sum : arr) map.put(sum, map.getOrDefault(sum, 0) + 1); // O(klogk)
        List<Integer> sum1 = new ArrayList<>();

        for (int i = 0; i < k / 2; i++) {  // O(k)
            int num = map.lastKey(); // O(logk)
            if (!map.containsKey(num - x)) break; // 找不到这一对，直接break；
            map.put(num, map.get(num) - 1); // 找到了一对，把这一对从map里删掉
            if (map.get(num) == 0) map.remove(num);
            map.put(num - x, map.get(num - x) - 1);
            if (map.get(num - x) == 0) map.remove(num - x);
            sum1.add(num - x); // 不包含x的元素需要继续保留递归
        }

        if (sum1.size() == k / 2) { // 拆分成功
            res.add(x);
            if (dfs(sum1, n - 1, res)) return true;
            res.remove(res.size() - 1);
        }
        // branch 2
        x = -(arr.get(k - 1) - arr.get(k - 2));
        TreeMap<Integer, Integer> map2 = new TreeMap<>();
        for (int sum : arr) map2.put(sum, map2.getOrDefault(sum, 0) + 1);
        List<Integer> sum2 = new ArrayList<>();

        for (int i = 0; i < k / 2; i++) {
            int num = map2.firstKey(); // 最小值一定是包含x
            if (!map2.containsKey(num - x)) break; // 找不到这一对，直接break；
            map2.put(num, map2.get(num) - 1); // 找到了一对，把这一对从map里删掉
            if (map2.get(num) == 0) map2.remove(num);
            map2.put(num - x, map2.get(num - x) - 1);
            if (map2.get(num - x) == 0) map2.remove(num - x);
            sum2.add(num - x); // 不包含x的元素需要继续保留递归
        }

        if (sum2.size() == k / 2) { // 拆分成功
            res.add(x);
            if (dfs(sum2, n - 1, res)) return true;
            res.remove(res.size() - 1);
        }

        return false;
    }

    // S2
    // time = O(m * (logm + n)), space = O(m + n)
    public int[] recoverArray2(int n, int[] sums) {
        // corner case
        if (sums == null || sums.length == 0) return new int[0];

        Arrays.sort(sums); // O(mlogm)

        int m = sums.length;
        int[] res = new int[n];
        int[] left = new int[m / 2], right = new int[m / 2];

        for (int i = 0; i < n; i++) {
            int diff = sums[1] - sums[0];
            int hasZero = 0, p = -1, q = -1, k = 0;
            for (int j = 0; j < m; j++) {
                if (k <= q && right[k] == sums[j]) k++;
                else {
                    if (sums[j] == 0) hasZero = 1;
                    left[++p] = sums[j];
                    right[++q] = sums[j] + diff;
                }
            }
            if (hasZero == 1) {
                res[i] = diff;
                sums = left;
            } else {
                res[i] = -diff;
                sums = right;
            }
            m /= 2;
        }
        return res;
    }
}
/**
 * 允许有负数，如果限制为正数的话，就容易很多。
 * 假设原数组都是正数
 * [1 3 6]
 * sums = [0, 1, 3, 6, 4, 9, 7, 10]
 * sums里最小一定是0，对应于每个元素都不取
 * 倒数第二小的就是这个数组里最小的元素
 * 接下来把sum拆封，从8个里面挑出4个来，把1的影响剥离开来
 * x x x 9
 *      +1
 * x x x 10
 * 最大值10肯定是在下面这个部分 -> 上面肯定有个9
 * 去掉9和10， 然后最大值是7 -> 肯定在包含1的下面这个部分，上面肯定有个6
 * 以此类推...
 * [0, 1, 3, 6, 4, 9, 7, 10] => {1, ? ?}
 * [0 3 6 9] => {3, ?}
 * [0, 6] => {?}
 *        => {6}
 * 非常确定性的做法
 * 这道题的突破口就是先确定一个元素，然后拆解成两部分，一部分是包含这个元素的subset，另一部分是不包含
 * 接下来递归处理那些不包含这个元素的subset，构造剩下n - 1个元素就可以了
 *
 * sums = [-3,-2,-1,0,0,1,2,3]
 * arr = N N N P P P P P
 * 最小的数一定是所有负数的subset，最大的正数一定是所有正数的subset
 * 次大比最大小一点：
 * 1. 少加一个正数 => X = FstMaxNum - secMaxNum
 * 2. 加上一个绝对值最小的负值 => X = -(FstMaxNum - secMaxNum)
 * 两种可能 => 分叉，负数是从小到大扫描
 */
