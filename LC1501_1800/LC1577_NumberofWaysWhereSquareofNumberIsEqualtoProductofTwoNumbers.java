package LC1501_1800;
import java.util.*;
public class LC1577_NumberofWaysWhereSquareofNumberIsEqualtoProductofTwoNumbers {
    /**
     * Given two arrays of integers nums1 and nums2, return the number of triplets formed (type 1 and type 2) under the
     * following rules:
     *
     * Type 1: Triplet (i, j, k) if nums1[i]2 == nums2[j] * nums2[k] where 0 <= i < nums1.length and 0 <= j < k <
     * nums2.length.
     * Type 2: Triplet (i, j, k) if nums2[i]2 == nums1[j] * nums1[k] where 0 <= i < nums2.length and 0 <= j < k <
     * nums1.length.
     *
     * Input: nums1 = [7,4], nums2 = [5,2,8,9]
     * Output: 1
     *
     * Constraints:
     *
     * 1 <= nums1.length, nums2.length <= 1000
     * 1 <= nums1[i], nums2[i] <= 10^5
     * @param nums1
     * @param nums2
     * @return
     */
    // S1: HashMap
    // time = O(m * n), space = O(m + n)
    public int numTriplets(int[] nums1, int[] nums2) {
        int res = 0;
        res += helper(nums1, nums2);
        res += helper(nums2, nums1);
        return res;
    }

    private int helper(int[] nums1, int[] nums2) {
        int res = 0;
        for (long x : nums1) {
            HashMap<Integer, Integer> map = new HashMap();
            for (int i = 0; i < nums2.length; i++) {
                if (x * x % nums2[i] == 0) {
                    if (map.containsKey((int)(x * x / nums2[i]))) {
                        res += map.get((int)(x * x / nums2[i]));
                    }
                }
                map.put(nums2[i], map.getOrDefault(nums2[i], 0) + 1); // 先check hashmap再加入hashmap
            }
        }
        return res;
    }

    // S2: Two Pointers
    // time = O(mlogm + nlogn + m * n), space = O(1)
    public int numTriplets2(int[] nums1, int[] nums2) {
        int res = 0;
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        res += helper2(nums1, nums2);
        res += helper2(nums2, nums1);
        return res;
    }

    private int helper2(int[] nums1, int[] nums2) {
        int res = 0;
        for (long x : nums1) {
            int i = 0, j = nums2.length - 1;
            while (i < j) { // 必须是2个数，所以 i != j
                if (x * x > (long)nums2[i] * nums2[j]) i++;
                else if (x * x < (long)nums2[i] * nums2[j]) j--;
                else { // 这里面数是可以重复的
                    if (nums2[i] != nums2[j]) {
                        int i0 = i, j0 = j;
                        while (i + 1 < j && nums2[i] == nums2[i + 1]) i++;
                        while (j - 1 > i && nums2[j] == nums2[j - 1]) j--;
                        res += (i - i0 + 1) * (j0 - j + 1);
                        i++;
                        j--;
                    } else {
                        int t = j - i + 1;
                        res += t * (t - 1) / 2;
                        break;
                    }
                }
            }
        }
        return res;
    }
}
/**
 * 6
 * ... 2 2 3 3 3 ....
 *     i       j
 * 如果2*3是一对的话，就要考虑2有几个，3有几个
 * .....2 2 2 2 2 2 .....
 * 6 * 5 / 2 = 15
 *
 */

