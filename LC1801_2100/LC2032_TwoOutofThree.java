package LC1801_2100;
import java.util.*;
public class LC2032_TwoOutofThree {
    /**
     * Given three integer arrays nums1, nums2, and nums3, return a distinct array containing all the values that are
     * present in at least two out of the three arrays. You may return the values in any order.
     *
     * Input: nums1 = [1,1,3,2], nums2 = [2,3], nums3 = [3]
     * Output: [3,2]
     *
     * Constraints:
     *
     * 1 <= nums1.length, nums2.length, nums3.length <= 100
     * 1 <= nums1[i], nums2[j], nums3[k] <= 100
     * @param nums1
     * @param nums2
     * @param nums3
     * @return
     */
    // S1: Set
    // time = O(n), space = O(n)
    public List<Integer> twoOutOfThree(int[] nums1, int[] nums2, int[] nums3) {
        HashSet<Integer> set = new HashSet<>();
        HashSet<Integer> res = new HashSet<>();
        HashSet<Integer> temp = new HashSet<>();
        for (int num : nums1) set.add(num);
        for (int num : nums2) {
            if (set.contains(num)) res.add(num);
            else temp.add(num);
        }
        set.addAll(temp);
        for (int num : nums3) {
            if (set.contains(num)) res.add(num);
        }
        List<Integer> ans = new ArrayList<>();
        for (int num : res) ans.add(num);
        return ans;
    }

    // S2: Bit
    // time = O(n), space = O(n)
    public List<Integer> twoOutOfThree2(int[] nums1, int[] nums2, int[] nums3) {
        List<Integer> res = new ArrayList<>();
        int[] count = new int[101];

        for (int num : nums1) count[num] |= 1;
        for (int num : nums2) count[num] |= 1 << 1;
        for (int num : nums3) count[num] |= 1 << 2;

        for (int i = 1; i <= 100; i++) {
            if (count[i] == 3 || count[i] == 5 || count[i] == 6 || count[i] == 7) {
                res.add(i);
            }
        }
        return res;
    }
}
