package LC2101_2400;
import java.util.*;
public class LC2111_MinimumOperationstoMaketheArrayKIncreasing {
    /**
     * You are given a 0-indexed array arr consisting of n positive integers, and a positive integer k.
     *
     * The array arr is called K-increasing if arr[i-k] <= arr[i] holds for every index i, where k <= i <= n-1.
     *
     * For example, arr = [4, 1, 5, 2, 6, 2] is K-increasing for k = 2 because:
     * arr[0] <= arr[2] (4 <= 5)
     * arr[1] <= arr[3] (1 <= 2)
     * arr[2] <= arr[4] (5 <= 6)
     * arr[3] <= arr[5] (2 <= 2)
     * However, the same arr is not K-increasing for k = 1 (because arr[0] > arr[1]) or k = 3 (because arr[0] > arr[3]).
     * In one operation, you can choose an index i and change arr[i] into any positive integer.
     *
     * Return the minimum number of operations required to make the array K-increasing for the given k.
     *
     * Input: arr = [5,4,3,2,1], k = 1
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= arr.length <= 10^5
     * 1 <= arr[i], k <= arr.length
     * @param arr
     * @param k
     * @return
     */
    // time = O(klog(n/k)), space = O(n/k)
    public int kIncreasing(int[] arr, int k) {
        int n = arr.length, res = 0;
        for (int t = 0; t < k; t++) {
            List<Integer> nums = new ArrayList<>();
            for (int i = t; i < n; i += k) nums.add(arr[i]);
            res += nums.size() - LIS(nums);
        }
        return res;
    }

    private int LIS(List<Integer> nums) {
        int n = nums.size();
        List<Integer> buffer = new ArrayList<>();

        for (int x : nums) {
            int idx = upperBound(buffer, x);
            if (idx == buffer.size()) buffer.add(x);
            else buffer.set(idx, x);
        }
        return buffer.size();
    }

    private int upperBound(List<Integer> buffer, int t) {
        if (buffer.size() == 0) return 0;
        int left = 0, right = buffer.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (buffer.get(mid) <= t) left = mid + 1;
            else right = mid;
        }
        return buffer.get(left) > t ? left : left + 1;
    }

    // S2: TreeSet
    public int kIncreasing2(int[] arr, int k) {
        int n = arr.length, res = 0;

        for (int t = 0; t < k; t++) {
            List<Integer> nums = new ArrayList<>();
            for (int i = t; i < n; i += k) {
                nums.add(arr[i]);
            }
            res += nums.size() - helper(nums);
        }
        return res;
    }

    private int helper(List<Integer> nums) {
        TreeSet<int[]> set = new TreeSet<>((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : o1[1] - o2[1]);
        for (int i = 0; i < nums.size(); i++) {
            int[] hk = set.higher(new int[]{nums.get(i), set.size()});
            if (hk == null) set.add(new int[]{nums.get(i), set.size()});
            else {
                int idx = hk[1];
                set.remove(hk);
                set.add(new int[]{nums.get(i), idx});
            }
        }
        return set.size();
    }
}
/**
 * 0,0+k,0+2k,0+3k,...
 * 1,1+k,1+2k,1+3k,...
 * 2,2+k,2+2k,2+3k,...
 * 彼此互不影响
 * x [x] x x [x] => O(n^2)
 * 二分：O(nlogn)
 * 此外有一个followup：如果要求构造所有元素为正、且严格的递增序列怎么办？
 * 这里会出现的一个问题是，找到LIS之后，其他元素的改动可能无法实现。
 * 比如[1,1,2]，最长严格递增序列是[1,2]，但是你无法只修改一个数字得到合法的解。
 * 方法是，将所有的元素都减去其下标(1,2,3...)，然后去除掉负数，在其中找LIS（更确切的说是非递减序列）。
 * 在这个例子中，变换后的数组是[0,-1,-1]。所以其LIS的长度其实只有1.
 * 去掉负数的那些元素k无论如何都无法成为一个严格递增序列里面的成员，
 * 原因是它之前的元素个数太少，即使第一个元素从1开始以公差1递增，到该元素时也超过了k本身。
 * 此外有一个followup：如果要求构造所有元素为正、且严格的递增序列怎么办？
 * 这里会出现的一个问题是，找到LIS之后，其他元素的改动可能无法实现。
 * 比如[1,1,2]，最长严格递增序列是[1,2]，但是你无法只修改一个数字得到合法的解。
 * 方法是，将所有的元素都减去其下标(1,2,3...)，然后去除掉负数，在其中找LIS（更确切的说是非递减序列）。
 * 在这个例子中，变换后的数组是[0,-1,-1]。
 * 所以其LIS的长度其实只有1.负数是非改不可的。
 * 去掉负数的那些元素k无论如何都无法成为一个严格递增序列里面的成员，
 * 原因是它之前的元素个数太少，即使第一个元素从1开始以公差1递增，到该元素时也超过了k本身。
 */
