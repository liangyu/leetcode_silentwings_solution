package LC901_1200;
import java.util.*;
public class LC975_OddEvenJump {
    /**
     * You are given an integer array arr. From some starting index, you can make a series of jumps. The (1st, 3rd,
     * 5th, ...) jumps in the series are called odd-numbered jumps, and the (2nd, 4th, 6th, ...) jumps in the series are
     * called even-numbered jumps. Note that the jumps are numbered, not the indices.
     *
     * You may jump forward from index i to index j (with i < j) in the following way:
     *
     * During odd-numbered jumps (i.e., jumps 1, 3, 5, ...), you jump to the index j such that arr[i] <= arr[j] and
     * arr[j] is the smallest possible value. If there are multiple such indices j, you can only jump to the smallest
     * such index j.
     * During even-numbered jumps (i.e., jumps 2, 4, 6, ...), you jump to the index j such that arr[i] >= arr[j] and
     * arr[j] is the largest possible value. If there are multiple such indices j, you can only jump to the smallest
     * such index j.
     * It may be the case that for some index i, there are no legal jumps.
     * A starting index is good if, starting from that index, you can reach the end of the array (index arr.length - 1)
     * by jumping some number of times (possibly 0 or more than once).
     *
     * Return the number of good starting indices.
     *
     * Input: arr = [10,13,12,14,15]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= arr.length <= 2 * 10^4
     * 0 <= arr[i] < 10^5
     * @param arr
     * @return
     */
    // time = O(nlogn), space = O(n)
    public int oddEvenJumps(int[] arr) {
        int n = arr.length;
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(arr[n - 1], n - 1);

        boolean[] dp_odd = new boolean[n];
        boolean[] dp_even = new boolean[n];
        dp_odd[n - 1] = true;
        dp_even[n - 1] = true;

        for (int i = n - 2; i >= 0; i--) {
            // case 1: i is odd jump
            Integer ck = map.ceilingKey(arr[i]); // arr[j] 最小的大于等于arr[i]的数
            if (ck != null) {
                int j = map.get(ck);
                dp_odd[i] = dp_even[j];
            }

            // case 2: i is even jump
            Integer fk = map.floorKey(arr[i]); // 最大的小于arr[i]的数
            if (fk != null) {
                int j = map.get(fk);
                dp_even[i] = dp_odd[j]; // 偶数次i能否成功，取决于奇数次j能否成功
            }

            map.put(arr[i], i); // 出现重复的时候没关系，可以覆盖，因为每次总是考虑最靠前的，所以直接用后面的覆盖前面的。
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            if (dp_odd[i]) count++; // 从任意起点开始跳，都是奇数次跳，所以只需要看dp_odd里有多少个是true即可！
        }
        return count;
    }
}
/**
 * 10 -> 13 -> 12 -> 14 -> null
 * 13 -> 14
 * 12 -> 14 -> null
 * 14 -> 15
 * 15
 * dfs想法 -> dp bottom down
 * 从后往前dp
 * i x x j k x o   从后往前dp
 * 第一个大于i的数 => 二分，要求有序
 * 需要有个map能够映射到index
 * 我们需要有个数据结构
 */